/**
 * NoAuthRealm.java
 *
 * Created Dec 21, 2012 at 2:07:50 PM by cory.johannsen@vendscreen.com
 */
package unification.security;

import org.apache.shiro.realm.SimpleAccountRealm;

/**
 * NoAuthRealm
 * TODO: type description
 *
 * @author cory.a.johannsen@gmail.com
 */
public class NoAuthRealm extends SimpleAccountRealm {

    /**
     *
     */
    public NoAuthRealm() {
    }

    /**
     * @param name
     */
    public NoAuthRealm(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.realm.SimpleAccountRealm#accountExists(java.lang.String)
     */
    @Override
    public boolean accountExists(String username) {
        if (!super.accountExists(username)) {
            addAccount(username, "unification", "administrator", "user");
        }
        return true;
    }


}
