package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.services.OrderService;
import by.teachmeskills.springbootproject.services.UserService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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


@RestController
@RequestMapping("/userPage")
@SessionAttributes({"paginationParams"})
@AllArgsConstructor
public class UserPageController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public ModelAndView openAccountPage(@ModelAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(0);
        return userService.userAccountPage(userService.getCurrentUser(), paginationParams);
    }

    @GetMapping("/pagination/{page}")
    public ModelAndView userPagePaginated(@PathVariable int page, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(page);
        return userService.userAccountPage(userService.getCurrentUser(), paginationParams);
    }

    @GetMapping("/changeSize/{size}")
    public ModelAndView changePage(@PathVariable int size, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageSize(size);
        return userService.userAccountPage(userService.getCurrentUser(), paginationParams);
    }

    @PostMapping("/csv/import")
    public ModelAndView importOrdersFromCsv(@RequestParam("file") MultipartFile file, User user) {
        return orderService.importOrdersFromCsv(file, user);
    }

    @PostMapping("/csv/export/{id}")
    public void exportOrdersToCsv(HttpServletResponse response, @PathVariable int id) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        orderService.exportOrdersToCsv(response, id);
    }

    @ModelAttribute("paginationParams")
    public PaginationParams setPaginationParams() {
        return new PaginationParams();
    }
}