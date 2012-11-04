/**
 * SessionValidationResponse.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * SessionValidationResponse
 * TODO: type description 
 *
 * @author cory.a.johannsen@gmail.com
 *
 */
@XmlRootElement
public class SessionValidationResponse extends BaseResponse {
    
    public String mSessionId;

    /**
     * 
     */
    public SessionValidationResponse() {
        super();
    }
    
    /**
     * 
     */
    public SessionValidationResponse(String error) {
        super(error);
    }
    
    

}
