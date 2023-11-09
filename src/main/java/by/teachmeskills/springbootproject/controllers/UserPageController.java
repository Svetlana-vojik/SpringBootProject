package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.services.OrderService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static by.teachmeskills.springbootproject.ShopConstants.USER;

@RestController
@RequestMapping("/userPage")
@SessionAttributes(USER)
@AllArgsConstructor
public class UserPageController {
    private final OrderService orderService;

    @GetMapping
    public ModelAndView openAccountPage(@SessionAttribute(name = USER, required = false) User user) throws AuthorizationException {
        return orderService.findUserOrders(user);
    }

    @PostMapping("/csv/import")
    public ModelAndView importOrdersFromCsv(@RequestParam("file") MultipartFile file, User user) {
        return orderService.importOrdersFromCsv(file, user);
    }

    @PostMapping("/csv/export/{id}")
    public void exportOrdersToCsv(HttpServletResponse response, @PathVariable int id) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        orderService.exportOrdersToCsv(response, id);
    }
}