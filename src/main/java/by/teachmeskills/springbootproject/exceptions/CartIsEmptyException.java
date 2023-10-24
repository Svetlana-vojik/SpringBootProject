package by.teachmeskills.springbootproject.exceptions;

public class CartIsEmptyException extends Exception {
    public CartIsEmptyException(String message) {
        super(message);
    }
}