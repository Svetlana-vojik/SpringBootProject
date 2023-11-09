package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.CartIsEmptyException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;


public interface OrderService extends BaseService<Order>{

    ModelAndView create(User user, Cart cart) throws CartIsEmptyException, AuthorizationException;

    ModelAndView findUserOrders(User user) throws AuthorizationException;

    Order create(Order entity);

    List<Order> findByUserId(int id);

    ModelAndView importOrdersFromCsv(MultipartFile file, User user);

    void exportOrdersToCsv(HttpServletResponse response, int userId) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}