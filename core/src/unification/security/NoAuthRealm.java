/**
 * NoAuthRealm.java
 *
 * Created Dec 21, 2012 at 2:07:50 PM by cory.johannsen@vendscreen.com
 */
package unification.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AuthenticationInfo info = new SimpleAccount(token.getPrincipal(), token.getCredentials(), "javaunification.org");
        System.out.println("NoAuthRealm: User " + info.getPrincipals().getPrimaryPrincipal() + " has been authenticated.");
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AuthorizationInfo info = new SimpleAuthorizationInfo(toSet("developer", ","));
        return info;
    }
}
