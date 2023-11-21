package by.teachmeskills.springbootproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.teachmeskills.springbootproject.PagesPathEnum.CART_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.CART;
import static by.teachmeskills.springbootproject.ShopConstants.PRODUCT;

@Data
@EqualsAndHashCode(callSuper=false)
public class Cart extends BaseEntity {
    private Map<Integer, Product> products;
    private int totalPrice = 0;

    public Cart() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
        totalPrice += product.getPrice();
    }

    public void removeProduct(int productId) {
        Product product = products.get(productId);
        products.remove(productId);
        if (product != null) {
            totalPrice -= product.getPrice();
        }
    }

    public ModelAndView addProductToCart(Product product, Cart cart) {
        ModelMap modelParams = new ModelMap();
        cart.addProduct(product);
        modelParams.addAttribute(CART, cart);
        modelParams.addAttribute(PRODUCT, product);
        return new ModelAndView(PRODUCT_PAGE.getPath(), modelParams);
    }

    public ModelAndView removeProductFromCart(int productId, Cart cart) {
        ModelMap modelParams = new ModelMap();
        cart.removeProduct(productId);
        modelParams.addAttribute(CART, cart);
        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void clear() {
        products.clear();
    }
}