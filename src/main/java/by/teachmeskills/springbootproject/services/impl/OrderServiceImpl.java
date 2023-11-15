package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.csv.converters.OrderConverter;
import by.teachmeskills.springbootproject.csv.dto.OrderCsvDto;
import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.CartIsEmptyException;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import by.teachmeskills.springbootproject.services.OrderService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.teachmeskills.springbootproject.PagesPathEnum.CART_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.USER_PROFILE_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.BIRTHDAY;
import static by.teachmeskills.springbootproject.ShopConstants.EMAIL;
import static by.teachmeskills.springbootproject.ShopConstants.NAME;
import static by.teachmeskills.springbootproject.ShopConstants.ORDERS;
import static by.teachmeskills.springbootproject.ShopConstants.SURNAME;
import static by.teachmeskills.springbootproject.ShopConstants.USER;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderConverter orderConverter;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    public ModelAndView create(User user, Cart cart) throws CartIsEmptyException, AuthorizationException {
        if (Optional.ofNullable(user).isEmpty()) {
            throw new AuthorizationException("Пользователь не авторизован!");
        }
        if (cart.getTotalPrice() == 0) {
            throw new CartIsEmptyException("Корзина пуста!");
        }
        Order order = Order.builder().orderDate(LocalDate.now()).price(cart.getTotalPrice())
                .user(user).productList(cart.getProducts()).build();
        orderRepository.save(order);
        cart.clear();
        cart.setTotalPrice(0);
        ModelAndView modelAndView = new ModelAndView(CART_PAGE.getPath());
        modelAndView.addObject("info", "Заказ оформлен.");
        return modelAndView;
    }

    @Override
    public ModelAndView findUserOrders(User user) throws AuthorizationException {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        if (Optional.ofNullable(user).isPresent()
                && Optional.ofNullable(user.getEmail()).isPresent()
                && Optional.ofNullable(user.getPassword()).isPresent()) {
            User loggedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
            if (Optional.ofNullable(loggedUser).isPresent()) {
                List<Order> orders = orderRepository.findByUserId(loggedUser.getId());
                modelMap.addAttribute(USER, loggedUser);
                modelMap.addAttribute(ORDERS, orders);
                modelAndView.addAllObjects(modelMap);
            }
        } else {
            throw new AuthorizationException("Пользователь не авторизован!");
        }
        modelAndView.setViewName(USER_PROFILE_PAGE.getPath());
        return modelAndView;
    }

    @Override
    public Order create(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public List<Order> read() {
        return orderRepository.findAll();
    }

    @Override
    public void delete(int id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByUserId(int id) {
        return orderRepository.findByUserId(id);
    }

    @Override
    public ModelAndView importOrdersFromCsv(MultipartFile file, User user) {
        ModelMap model = new ModelMap();

        model.addAttribute(USER, user.getId());
        model.addAttribute(NAME, user.getName());
        model.addAttribute(SURNAME, user.getSurname());
        model.addAttribute(BIRTHDAY, user.getBirthday());
        model.addAttribute(EMAIL, user.getEmail());

        List<OrderCsvDto> csvOrders = parseCsv(file);
        List<Order> newOrders = Optional.ofNullable(csvOrders)
                .map(list -> list.stream()
                        .map(orderConverter::fromCsv)
                        .toList())
                .orElse(null);
        if (Optional.ofNullable(newOrders).isPresent()) {
            newOrders.forEach(orderRepository::save);
        }

        List<Order> orders = getOrdersByUserId(user.getId());
        model.addAttribute(orders.stream().collect(Collectors.toList()));

        return new ModelAndView(USER_PROFILE_PAGE.getPath(), model);
    }

    @Override
    public void exportOrdersToCsv(HttpServletResponse response, int userId) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<OrderCsvDto> csvOrders = orderRepository.findByUserId(userId).stream().map(orderConverter::toCsv).toList();
        try (Writer writer = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<OrderCsvDto> statefulBeanToCsv = new StatefulBeanToCsvBuilder<OrderCsvDto>(writer).withSeparator(';').build();
            response.setContentType("text/csv");
            response.addHeader("Content-Disposition", "attachment; filename=" + "orders.csv");
            statefulBeanToCsv.write(csvOrders);
        }
    }

    public List<OrderCsvDto> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<OrderCsvDto> csvToBean = new CsvToBeanBuilder<OrderCsvDto>(reader).withType(OrderCsvDto.class).
                        withIgnoreLeadingWhiteSpace(true).withSeparator(';').build();
                return csvToBean.parse();
            } catch (IOException e) {
                log.error("Exception occurred during csv parsing:" + e.getMessage());
            }
        } else {
            log.error("Empty scv file is uploaded");
        }
        return Collections.emptyList();
    }
}