/**
 * SharedSecretAuthenticatingFilter.java
 */
package unification.security;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import unification.configuration.Log;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * @author chrisbjohannsen@gmail.com Creates an Authentication token that
 *         can be consumed by one of the Authentication Realms. A
 *         SharedSecretAuthenticationToken will be created if the
 *         request includes the custom http header named "x-unification-timestamp",
 *         otherwise a standard UsernamePasswordAuthenticationToken will be
 *         created.
 */
public class SharedSecretAuthenticatingFilter extends BasicHttpAuthenticationFilter {

    @Log
    Logger logger;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter#createToken
     * (javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String requestDate = null;
        requestDate = httpRequest.getHeader("x-unification-timestamp");

        String host = httpRequest.getRemoteHost();
        String method = httpRequest.getMethod();
        String resource = WebUtils.getRequestUri(httpRequest);
        String authorizationHeader = getAuthzHeader(request);

        if (authorizationHeader == null || authorizationHeader.length() == 0) {
            // Create an empty authentication token since there is no
            // Authorization header.
            return createToken("", "", request, response);
        }

        logger.info(String.format(
                "Login Attempt Request Headers:: Host: %s | Method: %s | Resource: %s | Timestamp value: %s | AuthorizationHeader: %s",
                host, method, resource, requestDate, authorizationHeader));

        String[] prinCred = getPrincipalsAndCredentials(authorizationHeader, request);

        // We want to return the default UsernamePasswordToken if the
        // x-unification-timestamp header was not included
        if (prinCred == null || prinCred.length < 2 || requestDate == null) {
            // Create an authentication token with an empty password,
            // since one hasn't been provided in the request.
            String username = (prinCred == null) || prinCred.length == 0 ? "" : prinCred[0];
            String password = (prinCred[1] != null) ? prinCred[1] : "";
            return createToken(username, password, request, response);
        }

        String clientId = prinCred[0];
        char[] signature = prinCred[1].toCharArray();

        return new SharedSecretAuthenticationToken(host, method, resource, requestDate,
                clientId, signature);
    }
}
