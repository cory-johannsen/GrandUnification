/**
 * ObjectMapperProvider.java 
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.service;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

/**
 * ObjectMapperProvider
 * JAX-RS ObjectMapper provider that configures the system from 
 * annotation based serialization/deserialization using the Jackson
 * libraries.
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private ObjectMapper mObjectMapper;

    /**
     * 
     */
    public ObjectMapperProvider() {
        mObjectMapper = new ObjectMapper();
        //mObjectMapper.configure(
        //        SerializationConfig.Feature.AUTO_DETECT_GETTERS, false);
        //mObjectMapper.configure(
        //        SerializationConfig.Feature.AUTO_DETECT_IS_GETTERS, false);
        JacksonAnnotationIntrospector jacksonAnnotationIntrospector = new JacksonAnnotationIntrospector();
        JaxbAnnotationIntrospector jaxbAnnotationIntrospector = new JaxbAnnotationIntrospector();
        
        DeserializationConfig deserializationConfig = mObjectMapper.getDeserializationConfig();
        deserializationConfig = deserializationConfig.withAnnotationIntrospector(jacksonAnnotationIntrospector);
        deserializationConfig = deserializationConfig.withAppendedAnnotationIntrospector(jaxbAnnotationIntrospector);
        deserializationConfig = deserializationConfig.with(DeserializationConfig.Feature.USE_ANNOTATIONS);
        
        SerializationConfig serializationConfig = mObjectMapper.getSerializationConfig();
        serializationConfig = serializationConfig.withAnnotationIntrospector(jacksonAnnotationIntrospector);
        serializationConfig = serializationConfig.withAppendedAnnotationIntrospector(jaxbAnnotationIntrospector);
        serializationConfig = serializationConfig.with(SerializationConfig.Feature.USE_ANNOTATIONS);
        
        mObjectMapper.configure(Feature.WRAP_ROOT_VALUE, true);
        mObjectMapper.configure(Feature.WRITE_NULL_MAP_VALUES, false);
        mObjectMapper.setDeserializationConfig(deserializationConfig);
        mObjectMapper.setSerializationConfig(serializationConfig);
    }
    

    /*
     * (non-Javadoc)
     * 
     * @see javax.ws.rs.ext.ContextResolver#getContext(java.lang.Class)
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mObjectMapper;
    }
}
