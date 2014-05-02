/**
 * UserDAO.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.security;

/**
 * UserDAO
 * Data access object interface for User instances 
 *
 * @author cory.a.johannsen@gmail.com
 *
 */
public interface UserDAO {
    
    /**
     * UserDAOException
     * Base exception type for all UserDAO related errors.
     *
     * @author cory.a.johannsen@gmail.com
     *
     */
    public class UserDAOException extends Exception {


		/**
		 * 
		 */
		private static final long serialVersionUID = 6264229399655393177L;

		/**
         * 
         */
        public UserDAOException() {
            super();
        }

        /**
         * @param message
         * @param cause
         */
        public UserDAOException(String message, Throwable cause) {
            super(message, cause);
        }

        /**
         * @param message
         */
        public UserDAOException(String message) {
            super(message);
        }

        /**
         * @param cause
         */
        public UserDAOException(Throwable cause) {
            super(cause);
        }
        
    }
    
    /**
     * UserNotFoundException
     * Sub-class of UserDAOException designed to indicate 
     * that the security system could not locate a user.
     * 
     * @author cjohannsen
     *
     */
    public class UserNotFoundException extends UserDAOException {


		/**
		 * 
		 */
		private static final long serialVersionUID = -2360215757328128438L;

		/**
         * 
         */
        public UserNotFoundException() {
            super();
        }

        /**
         * @param message
         * @param cause
         */
        public UserNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

        /**
         * @param message
         */
        public UserNotFoundException(String message) {
            super(message);
        }

        /**
         * @param cause
         */
        public UserNotFoundException(Throwable cause) {
            super(cause);
        }
        
    }

    /**
     * Loads and returns a populated User instance that corresponds to the given username.
     * @param username the unique username/login fo the user to retrieve.
     * @return a populated User instance that corresponds to the given username.
     * @throws UserNotFoundException no user could be located for the given username.
     * @throws UserDAOException an error occurred accessing the user data store.
     */
    public User loadUser(String username) throws UserNotFoundException, UserDAOException;
}
