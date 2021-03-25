package ru.otus.vbaymurzin.exceptions;

public class InvalidCSVResourceException extends Exception {
    private static final long serialVersionUID = -2605720967920637473L;

    public InvalidCSVResourceException(String message) {
        super(message);
    }

    public InvalidCSVResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
