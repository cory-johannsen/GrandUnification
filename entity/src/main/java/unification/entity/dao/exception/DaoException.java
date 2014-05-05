package unification.entity.dao.exception;

/**
 * User: cjohannsen
 * Date: 5/2/14
 * Time: 1:31 PM
 */
public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
