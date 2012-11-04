/**
 * LoginRequestMessage.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * LoginRequestMessage
 * TODO: type description 
 *
 * @author cory.a.johannsen@gmail.com
 *
 */
@XmlRootElement
public class LoginRequest {
    
    private String mUsername;
    private char[] mPassword;

    /**
     * 
     */
    public LoginRequest() {
    }
    
    /**
     * 
     */
    public LoginRequest(String username, char[] password) {
        mUsername = username;
        mPassword = password;
    }

    /**
     * @return the username
     */
    @JsonProperty
    public String getUsername() {
        return mUsername;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        mUsername = username;
    }

    /**
     * @return the password
     */
    @JsonProperty
    public char[] getPassword() {
        return mPassword;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(char[] password) {
        mPassword = password;
    }

}
