/**
 * BaseResponse.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * BaseResponse
 * Base class for service responses that allows errors
 * to be propagated to JSON consumers.  
 *
 * @author cory.a.johannsen@gmail.com
 *
 */
@XmlRootElement
public class BaseResponse {

    private String mError;
    
    /**
     * 
     */
    public BaseResponse() {
        mError = null;
    }

    /**
     * @param error
     */
    public BaseResponse(String error) {
        mError = error;
    }

    /**
     * @return the error
     */
    @JsonProperty
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getError() {
        return mError;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        mError = error;
    }

}
