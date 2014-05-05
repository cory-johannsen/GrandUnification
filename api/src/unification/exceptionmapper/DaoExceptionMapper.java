package unification.exceptionmapper;

import com.google.inject.Singleton;
import unification.entity.dao.exception.DaoException;
import unification.entity.dao.exception.EntityNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * User: cjohannsen
 * Date: 5/5/14
 * Time: 1:30 PM
 */
@Provider
@Singleton
public class DaoExceptionMapper implements ExceptionMapper<DaoException> {
    /**
     * Map an exception to a {@link javax.ws.rs.core.Response}. Returning
     * {@code null} results in a {@link javax.ws.rs.core.Response.Status#NO_CONTENT}
     * response. Throwing a runtime exception results in a
     * {@link javax.ws.rs.core.Response.Status#INTERNAL_SERVER_ERROR} response
     *
     * @param ex the exception to map to a response
     * @return a response mapped from the supplied exception
     */
    public Response toResponse(DaoException ex) {
        return Response.status(500).type(MediaType.APPLICATION_JSON)
                .entity(String.format("{\"error\":\"%s\"}", ex.getMessage())).build();
    }

}
