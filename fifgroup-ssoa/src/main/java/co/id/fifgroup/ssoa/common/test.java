package co.id.fifgroup.ssoa.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.avm.AVMEngine;
import co.id.fifgroup.core.dao.SecurityProfileFinder;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.validation.TaskExecutionException;


public class test {
	
	/*@Autowired
	private static SecurityProfileFinder securityProfileFinder;*/

	/*public void login(HttpServletRequest request, String userName, String password)
	{

	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);

	    // Authenticate the user
	    Authentication authentication = authenticationManager.authenticate(authRequest);
	    SecurityContext securityContext = SecurityContextHolder.getContext();
	    securityContext.setAuthentication(authentication);

	    // Create a new session and add the security context.
	    HttpSession session = request.getSession(true);
	    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	}*/
	/*ApplicationContext ctx = new ClassPathXmlApplicationContext("avm-context.xml");
	AVMEngine engine = (AVMEngine) ctx.getBean("avmEngine");*/
	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public static void autoLogin(String username, String password) {
	    try {
	      // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated
	      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
	      
	      //token.setDetails(new WebAuthenticationDetails(request));
	      AuthenticationProvider authProvider = (AuthenticationProvider) ApplicationContextUtil.getApplicationContext().getBean("authenticationProvider");
	      System.out.println("authProvider=="+authProvider);
	      Authentication authentication = authProvider.authenticate(token);
	      //SecurityProfile securityProfile = (SecurityProfile) authentication.getDetails();
	      SecurityProfile securityProfile = getSecurityProfile(username, 1L);
	      System.out.println("authentication=="+authentication);
	      //logger.debug("Logging in with {}", authentication.getPrincipal());
	      //SecurityContextHolder.getContext().setAuthentication(authentication);
	    } catch (Exception e) {
	      //SecurityContextHolder.getContext().setAuthentication(null);
	      //logger.error("Failure in autoLogin", e);
	      System.out.println(e);
	    }
	  }
	  
	  public static SecurityProfile getSecurityProfile(String userName, Long companyId) {
		   SecurityProfileFinder securityProfileFinder = (SecurityProfileFinder) ApplicationContextUtil.getApplicationContext().getBean("securityProfileFinder");
			SecurityProfile securityProfile = securityProfileFinder
					.getSecurityProfile(userName, companyId);
			if (securityProfile != null
					&& (securityProfile.getOrganizationId() == null && securityProfile
							.getPersonId() != null)) {
				securityProfile = securityProfileFinder
						.getSecurityProfileIsEmployee(userName);
			}
			if (securityProfile != null && securityProfile.getJobId() != null) // If
																				// has't
																				// assignment
																				// in
																				// this
																				// company
				securityProfile.setRoles(securityProfileFinder
						.getRolesByPersonIdAndJobId(securityProfile.getPersonId(),
								securityProfile.getJobId()));

			return securityProfile;
		}
	
	

	
	
}