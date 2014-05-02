package main.java.unification.entity.dao.exception;

/**
 * User: cjohannsen
 * Date: 5/2/14
 * Time: 1:33 PM
 */
public class EntityNotFoundException extends DaoException {
    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
