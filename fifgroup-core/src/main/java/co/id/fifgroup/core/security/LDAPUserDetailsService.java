package co.id.fifgroup.core.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

public class LDAPUserDetailsService implements UserDetailsContextMapper {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx,
			String username, Collection<? extends GrantedAuthority> authorities) {
		return userDetailsService.loadUserByUsername(username);
	}

	@Override
	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		 throw new UnsupportedOperationException("LDAPUserDetailsService only supports reading from a context. Please" +
	                "use a subclass if mapUserToContext() is required.");
		
	}

}
