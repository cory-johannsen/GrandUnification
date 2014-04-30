package unification.service;

import com.google.inject.Inject;
import unification.entity.ServerStatus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.logging.Logger;

/**
 * User: cjohannsen
 * Date: 4/29/14
 * Time: 2:26 PM
 */
@Path("/{api_version}/status")
public class ServerStatusResource {

    private final Logger mLogger;

    @Inject
    public ServerStatusResource(Logger logger) {
        mLogger = logger;
    }

    @GET
    public ServerStatus get(@PathParam("api_version") String apiVersion) {
        mLogger.info("GET (api version " + apiVersion + ") for server status.");
        return new ServerStatus(0, 0l, "localhost");
    }
}
