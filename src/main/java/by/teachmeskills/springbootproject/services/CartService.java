package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.PagesPathEnum.CART_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.CART;
import static by.teachmeskills.springbootproject.ShopConstants.PRODUCT;

@Service
public class CartService {
    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ModelAndView addProductToCart(int id, Cart cart) {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.findById(id);
        cart.addProduct(product);
        modelParams.addAttribute(CART, cart);
        modelParams.addAttribute(PRODUCT, product);

        return new ModelAndView(PRODUCT_PAGE.getPath(), modelParams);
    }

    public ModelAndView removeProductFromCart(int id, Cart cart) {
        ModelMap modelParams = new ModelMap();

        cart.removeProduct(id);
        modelParams.addAttribute(CART, cart);

        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }
}