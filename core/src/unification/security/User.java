/**
 * User.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.security;

/**
 * User 
 * Represents the core information related to a system user, such as 
 * name and email address
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
public interface User {

    void setDisplayName(String displayName);

    String getDisplayName();

    void setCommonName(String commonName);

    String getCommonName();

    void setEmail(String email);

    String getEmail();

    void setSurname(String surname);

    String getSurname();

    void setGivenName(String givenName);

    String getGivenName();

    void setUsername(String username);

    String getUsername();

}
