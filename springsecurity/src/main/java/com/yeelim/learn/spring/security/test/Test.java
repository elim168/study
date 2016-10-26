/**
 * 
 */
package com.yeelim.learn.spring.security.test;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;


/**
 * @author Yeelim
 * @date 2014-6-7
 * @time 上午10:47:53 
 *
 */
public class Test {

	public static void main(String args[]) {
		AuthenticationProvider dd;
		UsernamePasswordAuthenticationFilter sfs;
		SecurityContextPersistenceFilter a;
		AuthenticationEntryPoint as;
		BasicAuthenticationFilter asd;
		LogoutSuccessHandler fsf;
		AccessDecisionManager adm;
		/*JdbcDaoImpl jdi = new JdbcDaoImpl();
		jdi.setUsersByUsernameQuery("");
		DaoAuthenticationProvider d = null;
		InMemoryDaoImpl i;
//		AuthenticationProvider
		PasswordEncoder e;*/
		RememberMeAuthenticationProvider d;
		String salt = "yeelim";
	Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	encoder.setEncodeHashAsBase64(true);
	System.out.println(encoder.encodePassword("user", "user"));
		System.out.println(encoder.encodePassword("admin", "admin"));
		System.out.println(encoder.encodePassword("elim", "elim"));
		
		LogoutFilter adl;

	}
	

public interface AccessDecisionVoter<S> {

    int ACCESS_GRANTED = 1;
    int ACCESS_ABSTAIN = 0;
    int ACCESS_DENIED = -1;

    boolean supports(ConfigAttribute attribute);

    boolean supports(Class<?> clazz);

    int vote(Authentication authentication, S object, Collection<ConfigAttribute> attributes);
}

public interface AfterInvocationManager {
	
    Object decide(Authentication authentication, Object object, Collection<ConfigAttribute> attributes,
        Object returnedObject) throws AccessDeniedException;

    boolean supports(ConfigAttribute attribute);

    boolean supports(Class<?> clazz);
}
	
	
}
