package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.CartIsEmptyException;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.springbootproject.PagesPathEnum.CART_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.USER_PROFILE_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.ORDERS;
import static by.teachmeskills.springbootproject.ShopConstants.USER;

public interface OrderService extends BaseService<Order>{

    ModelAndView create(User user, Cart cart) throws CartIsEmptyException, AuthorizationException;

    ModelAndView findUserOrders(User user) throws AuthorizationException;

    Order create(Order entity);

    List<Order> getOrdersByUserId(int id);

    ModelAndView importOrdersFromCsv(MultipartFile file, User user);

    void exportOrdersToCsv(HttpServletResponse response, int userId) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}