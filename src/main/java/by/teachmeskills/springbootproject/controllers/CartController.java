package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.CART;
import static by.teachmeskills.springbootproject.ShopConstants.PRODUCT_ID;
import static by.teachmeskills.springbootproject.PagesPathEnum.CART_PAGE;


@RestController
@RequestMapping("/cart")
@SessionAttributes({CART})

public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/open")
    public ModelAndView openCartPage() {
        return new ModelAndView(CART_PAGE.getPath());
    }

    @GetMapping("/add")
    public ModelAndView addProductToCart(@RequestParam(PRODUCT_ID) int id, @ModelAttribute(CART) Cart cart) {
        return cartService.addProductToCart(id, cart);
    }

    @GetMapping("/delete")
    public ModelAndView removeProductFromCart(@RequestParam(PRODUCT_ID) int id, @ModelAttribute(CART) Cart cart) {
        return cartService.removeProductFromCart(id, cart);
    }

    @ModelAttribute("cart")
    public Cart shoppingCart() {
        return new Cart();
    }
}