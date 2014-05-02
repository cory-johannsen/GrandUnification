/**
 * RolesBannedResourceFilterFactory.java
 * Created Apr 26, 2013 by chris.johannsen@vendscreen.com
 * @copyright VendScreen 2013
 */
package unification.security;

import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.*;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Collections;
import java.util.List;

/**
 * @author cory.a.johannsen@gmail.com
 */
public class RolesBannedResourceFilterFactory implements ResourceFilterFactory {

    private
    @Context
    SecurityContext sc;

    private class Filter implements ResourceFilter, ContainerRequestFilter {

        private final String[] rolesBanned;

        protected Filter(String[] rolesBanned) {
            this.rolesBanned = (rolesBanned != null) ? rolesBanned : new String[]{};
        }

        // ResourceFilter

        @Override
        public ContainerRequestFilter getRequestFilter() {
            return this;
        }

        @Override
        public ContainerResponseFilter getResponseFilter() {
            return null;
        }

        // ContainerRequestFilter

        @Override
        public ContainerRequest filter(ContainerRequest request) {
            for (String role : rolesBanned) {
                if (sc.isUserInRole(role))
                    throw new WebApplicationException(Response.Status.FORBIDDEN);

            }

            return request;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.sun.jersey.spi.container.ResourceFilterFactory#create(com.sun.jersey
     * .api.model.AbstractMethod)
     */
    @Override
    public List<ResourceFilter> create(AbstractMethod am) {
        RolesBanned ra = am.getAnnotation(RolesBanned.class);
        if (ra != null)
            return Collections.<ResourceFilter>singletonList(new Filter(ra.value()));

        return null;
    }

}
