/**
 * ValidatorProvider.java
 * Created Mar 22, 2013 by chris.johannsen@vendscreen.com
 * @copyright VendScreen 2013
 */
package unification.configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;import java.lang.Class;import java.lang.Override;

/**
 * Guice Provider for Hibernate Validator integration
 *
 * @author cory.a.johannsen@gmail.com
 */
@Provider
public class ValidatorProvider implements ContextResolver<Validator> {

    private Validator mValidator;

    /**
     *
     */
    public ValidatorProvider() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        mValidator = factory.getValidator();

    }

    /* (non-Javadoc)
     * @see javax.ws.rs.ext.ContextResolver#getContext(java.lang.Class)
     */
    @Override
    public Validator getContext(Class<?> type) {
        return mValidator;
    }

}
