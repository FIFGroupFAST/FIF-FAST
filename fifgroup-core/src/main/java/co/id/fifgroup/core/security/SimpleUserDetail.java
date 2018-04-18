package co.id.fifgroup.core.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import co.id.fifgroup.core.constant.LoginStatus;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public class SimpleUserDetail extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<KeyValue> authorizedCompanies;
	private LoginStatus loginStatus;

	public SimpleUserDetail(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	public SimpleUserDetail(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, List<KeyValue> authorizedCompanies, LoginStatus loginStatus) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.setAuthorizedCompanies(authorizedCompanies);
		this.setLoginStatus(loginStatus);
	}

	public List<KeyValue> getAuthorizedCompanies() {
		return authorizedCompanies;
	}

	public void setAuthorizedCompanies(List<KeyValue> authorizedCompanies) {
		this.authorizedCompanies = authorizedCompanies;
	}

	public LoginStatus getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(LoginStatus loginStatus) {
		this.loginStatus = loginStatus;
	}

}
