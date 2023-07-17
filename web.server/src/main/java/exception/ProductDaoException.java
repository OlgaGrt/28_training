package exception;


public class ProductDaoException extends RuntimeException {
    public ProductDaoException(String message) {
        super(message);
    }
}
