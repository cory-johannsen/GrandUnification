/**
 * SessionValidationResponse.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * SessionValidationResponse
 * TODO: type description 
 *
 * @author cory.a.johannsen@gmail.com
 *
 */
@XmlRootElement
public class LogoutResponse extends BaseResponse {

    /**
     * 
     */
    public LogoutResponse() {
        super();
    }
    
    /**
     * 
     */
    public LogoutResponse(String error) {
        super(error);
    }
    
    

}
