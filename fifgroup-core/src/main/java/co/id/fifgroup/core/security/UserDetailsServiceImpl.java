package co.id.fifgroup.core.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import bsh.commands.dir;
import co.id.fifgroup.core.constant.LoginStatus;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	
	@Autowired
	private SecurityService securityService;
	
	private boolean enabled = false;
	
	private LdapTemplate ldapTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		LoginStatus loginStatus = null;
		int count = securityService.countActiveUserByUsername(username);
		List<KeyValue> authorizedCompanies = null;
		
		if(count > 0){
			
			if(securityService.isBusinessGroupAdmin(username) > 0){
				
				authorizedCompanies = securityService.getBusinessGroup();
				enabled = true;
				loginStatus = LoginStatus.BUSINESS_GROUP_ADMIN;
			}else{
				if(securityService.isEmployee(username)){
					authorizedCompanies = securityService.getEmployeeAuthorizedCompanies(username);
					loginStatus = LoginStatus.EMPLOYEE;
					
					// add phase 2 for handling cwk
					if(authorizedCompanies.size() == 0 ) {
						authorizedCompanies = securityService.getCwkAuthorizedCompanies(username);
						loginStatus = LoginStatus.CWK;
					}
					// end
				}else{
					authorizedCompanies = securityService.getNonEmployeeAuthorizedCompaniesByUserName(username);
					loginStatus = LoginStatus.NON_EMPOYEE;
				}
				if(authorizedCompanies.size() > 0 )
					enabled = true;
				else
					enabled = false;
			}
			
		} else {
			throw new UsernameNotFoundException("Username or password did not match");
		}
	
		//DirContextAdapter dir = (DirContextAdapter) ldapTemplate.lookup("uid="+username);
		//System.out.println("Password : "+dir.getObjectAttribute("userPassword"));
	
		//return new SimpleUserDetail(username, (String)dir.getObjectAttribute("userPassword"), enabled, true, true, true, new ArrayList<GrantedAuthority>(), authorizedCompanies, loginStatus);
		return new SimpleUserDetail(username, "password", enabled, true, true, true, new ArrayList<GrantedAuthority>(), authorizedCompanies, loginStatus);
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

}
