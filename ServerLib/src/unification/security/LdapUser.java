/**
 * LdapAuthenticationInfo.java
 * 
 * Created Nov 3, 2012 at 10:27:00 PM by cory.a.johannsen@gmail.com
 */
package unification.security;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * LdapAuthenticationInfo 
 * Concrete implementation of User that is configured for JSON serialization
 * 
 * @author cory.a.johannsen@gmail.com
 * 
 */
@XmlRootElement
public class LdapUser implements User {
    protected String mUsername;
    protected String mGivenName;
    protected String mSurname;
    protected String mEmail;
    protected String mCommonName;
    protected String mDisplayName;

    /**
     * 
     */
    public LdapUser() {
        this(null, null, null, null, null, null);
    }

    /**
     * 
     */
    public LdapUser(String username, String givenName, String surname,
            String email, String commonName, String displayName) {
        mUsername = username;
        mGivenName = givenName;
        mSurname = surname;
        mEmail = email;
        mCommonName = commonName;
        mDisplayName = displayName;
    }

    @Override
    @JsonProperty
    public String getUsername() {
        return mUsername;
    }

    @Override
    public void setUsername(String username) {
        mUsername = username;
    }

    /**
     * @return the givenName
     */
    @Override
    @JsonProperty
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getGivenName() {
        return mGivenName;
    }

    /**
     * @param givenName
     *            the givenName to set
     */
    @Override
    public void setGivenName(String givenName) {
        mGivenName = givenName;
    }

    /**
     * @return the surname
     */
    @Override
    @JsonProperty
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getSurname() {
        return mSurname;
    }

    /**
     * @param surname
     *            the surname to set
     */
    @Override
    public void setSurname(String surname) {
        mSurname = surname;
    }

    /**
     * @return the email
     */
    @Override
    @JsonProperty
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getEmail() {
        return mEmail;
    }

    /**
     * @param email
     *            the email to set
     */
    @Override
    public void setEmail(String email) {
        mEmail = email;
    }

    /**
     * @return the commonName
     */
    @Override
    @JsonProperty
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getCommonName() {
        return mCommonName;
    }

    /**
     * @param commonName
     *            the commonName to set
     */
    @Override
    public void setCommonName(String commonName) {
        mCommonName = commonName;
    }

    /**
     * @return the displayName
     */
    @Override
    @JsonProperty
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getDisplayName() {
        return mDisplayName;
    }

    /**
     * @param displayName
     *            the displayName to set
     */
    @Override
    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

}
