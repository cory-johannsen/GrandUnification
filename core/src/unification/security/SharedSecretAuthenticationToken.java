/**
 * VSharedSecretAuthenticationToken.java
 */
package unification.security;

import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * @author chrisbjohannsen@gmail.com
 *         Custom implementation of a Shiro AuthenticationToken used to implement the custom authentication
 *         algorithm. Each request will be digitally signed with a shared secret key, the resulting hash
 *         will be SHA256 hashed and salted with the secret key. The signature will be made up of the
 *         HTTP method, the Resource (URI) being requested (no Query Params included), and the date stamp
 *         of the request. The request will then be sent as a BASIC http auth request where the
 *         username is the device id and the password is the hash value. An extended version of the
 *         Shiro BasicHttpAutheticationFilter will populate the token values from the http headers
 *         and pass along to a custom Realm for the authentication work.
 */
public class SharedSecretAuthenticationToken implements HostAuthenticationToken {

    /**
     *
     */
    private static final long serialVersionUID = 1951879145614798195L;

    private String host;
    private String httpMethod;
    private String resourceName;
    private String requestDate;
    private String clientId;
    private char[] token;


    /**
     * JavaBeans Default no-arg constructor
     */
    public SharedSecretAuthenticationToken() {
    }

    /**
     *
     * @param host
     * @param httpMethod
     * @param resourceName
     * @param requestDate
     * @param clientId
     * @param token
     */
    public SharedSecretAuthenticationToken(String host, String httpMethod,
                                           String resourceName, String requestDate,
                                           String clientId, char[] token) {
        super();
        this.setHost(host);
        this.setHttpMethod(httpMethod);
        this.setResourceName(resourceName);
        this.setRequestDate(requestDate);
        this.setClientId(clientId);
        this.setToken(token);
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.authc.AuthenticationToken#getCredentials()
     */
    @Override
    public Object getCredentials() {
        return token;
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.authc.AuthenticationToken#getPrincipal()
     */
    @Override
    public Object getPrincipal() {
        return clientId;
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.authc.HostAuthenticationToken#getHost()
     */
    @Override
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the httpMethod
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * @param httpMethod the httpMethod to set
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    /**
     * @return the resourceName
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * @param resourceName the resourceName to set
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * @return the requestDate
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the token
     */
    public char[] getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(char[] token) {
        this.token = token;
    }

}
