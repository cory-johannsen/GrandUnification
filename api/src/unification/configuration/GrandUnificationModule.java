/**
 * VendScreenAPIServletModule.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.configuration;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.apache.shiro.guice.web.GuiceShiroFilter;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import unification.service.ObjectMapperProvider;


import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.jndi.JndiIntegration;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * GrandUnificationModule
 * Extension of JerseyServletModule that configures the system
 * with Guice dependency injection.  This includes binding
 * the JDBC datasource, connection manager and lifecycle interceptor, and
 * Jackson-JSON serialization engine. The system is then configured to
 * auto-publish and manage all service resources in the unification service
 * packages.
 * 
 * This module filters all request through a GuiceShiroFilter to automatically 
 * enforce Shiro security.
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
public abstract class GrandUnificationModule extends JerseyServletModule {


    // JPA properties
    public static final String JPA_HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String JPA_JDBC_DRIVER = "javax.persistence.jdbc.driver";
    public static final String JPA_JDBC_URL = "javax.persistence.jdbc.url";
    public static final String JPA_JDBC_USER = "javax.persistence.jdbc.user";
    public static final String JPA_JDBC_PASSWORD = "javax.persistence.jdbc.password";
    public static final String JPA_PERSISTENCE_UNIT = "jpa.persistence.unit";

    @Override
    protected void configureServlets() {
        super.configureServlets();
        filter("/*").through(GuiceShiroFilter.class);
        // Load the persistence module properties from the environment
        Properties jpaProperties = new Properties();
        String jpaDialect = System.getProperty(JPA_HIBERNATE_DIALECT);
        String jpaDriver = System.getProperty(JPA_JDBC_DRIVER);
        String jpaUrl = System.getProperty(JPA_JDBC_URL);
        String jpaUser = System.getProperty(JPA_JDBC_USER);
        String jpaPassword = System.getProperty(JPA_JDBC_PASSWORD);
        String jpaPersistenceUnit = System.getProperty(JPA_PERSISTENCE_UNIT);

        System.out.println("Using hibernate dialect " + jpaDialect);
        System.out.println("Using JDBC driver " + jpaDriver);
        System.out.println("Using JDBC URL " + jpaUrl);
        System.out.println("Using JDBC user " + jpaUser);
        System.out.println("Using JDBC password " + jpaPassword);
        System.out.println("Using JPA persistence unit " + jpaPersistenceUnit);

        install(new JpaPersistModule("jpaPersistenceUnit").properties(jpaProperties));
        filter("/*").through(PersistFilter.class);
        
        // Bind the InitialContext implementation class to the Context interface using singleton semantics
        bind(Context.class).to(InitialContext.class).in(Singleton.class);

        //Bind SLF4J Logging listener
        bindListener(Matchers.any(), new SLF4JTypeListener());

        // Bind an ObjectMapper provider to support seamless Jasper JSON serialization/deserialization
        bind(ObjectMapperProvider.class).asEagerSingleton();

        // hook Jersey into Guice Servlet
        bind(GuiceContainer.class);

        // hook Jackson into Jersey as the POJO <-> JSON mapper
        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
        parameters.put(PackagesResourceConfig.PROPERTY_PACKAGES,
                "unification.service");

        parameters.put(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS,
                "com.sun.jersey.api.container.filter.LoggingFilter");
        parameters.put(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS,
                "com.sun.jersey.api.container.filter.LoggingFilter");

        parameters = configureApplicationParameters(parameters);

        serve("/*").with(GuiceContainer.class, parameters);
    }



    @Provides
    @Singleton
    javax.validation.Validator provideValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    protected abstract Map<String, String> configureApplicationParameters(Map<String, String> parameters);
}
