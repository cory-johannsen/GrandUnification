/**
 * CurrentUserResponse.java
 *
 * Created Nov 9, 2012 at 7:07:12 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import unification.security.User;

/**
 * CurrentUserResponse - TODO: type description
 *
 * @author cjohannsen
 *
 */
@XmlRootElement
public class CurrentUserResponse extends BaseResponse {

    private User mUser;
    
    /**
     * 
     */
    public CurrentUserResponse() {
        super();
        mUser = null;
    }
    
    /**
     * 
     */
    public CurrentUserResponse(String error) {
        super(error);
        mUser = null;
    }
    
    /**
     * 
     */
    public CurrentUserResponse(User user) {
        mUser = user;
    }

    /**
     * @return the user
     */
    @JsonProperty
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public User getUser() {
        return mUser;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        mUser = user;
    }

}
