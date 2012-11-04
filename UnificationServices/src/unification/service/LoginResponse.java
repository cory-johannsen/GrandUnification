/**
 * LoginResponseMessage.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import unification.security.User;

/**
 * LoginResponseMessage
 * TODO: type description 
 *
 * @author cory.a.johannsen@gmail.com
 *
 */
@XmlRootElement
public class LoginResponse extends BaseResponse {
    
    private User mUser;
	private long mTimeoutInMillis;

    /**
     * 
     */
    public LoginResponse() {
        this(null);
    }
    
    /**
     * 
     */
    public LoginResponse(String error) {
        this(error, null, 5 * 60 * 1000);
    }
    
    /**
     * 
     */
    public LoginResponse(User user, long timeoutInMillis) {
        this(null, user, timeoutInMillis);
    }
    
    /**
     * 
     */
    public LoginResponse(String error, User user, long timeoutInMillis) {
        super(error);
        mUser = user;
        mTimeoutInMillis = timeoutInMillis;
    }

    /**
     * @return the sessionToken
     */
    @JsonProperty
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public User getUser() {
        return mUser;
    }

    /**
     * @param sessionToken the sessionToken to set
     */
    public void setUser(User user) {
        mUser = user;
    }

    /**
     * @return the timeoutInMillis
     */
    public long getTimeoutInMillis() {
        return mTimeoutInMillis;
    }

    /**
     * @param timeoutInMillis the timeoutInMillis to set
     */
    @JsonProperty
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public void setTimeoutInMillis(long timeoutInMillis) {
        mTimeoutInMillis = timeoutInMillis;
    }
    
}
