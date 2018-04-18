package co.id.fifgroup.systemadmin.test;


import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
public class LDAPTest {
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Test
	public void test(){
		AndFilter filter = new AndFilter(); 
		filter.and(new EqualsFilter("objectClass", "person"));
		//filter.and(new EqualsFilter("objectClass", "person"));
			List<?> list = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), new AttributesMapper() {
				
				@Override
				public Object mapFromAttributes(Attributes attr) throws NamingException {
					return attr.get("sAMAccountName");
				}
			});
			for (Object object : list) {
				System.out.println(object);
			}
			
	}
}
