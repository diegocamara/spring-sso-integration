package com.example.sso.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.sso.form.FormAuthenticationProvider;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private FormAuthenticationProvider formAuthenticationProvider;

	@Autowired
	private PasswordEncoder passwordEnconder;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
		// auth.authenticationProvider(this.formAuthenticationProvider);
		auth.inMemoryAuthentication().withUser("john").password(this.passwordEnconder.encode("123")).roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login", "/webjars/**", "/css/**").permitAll().anyRequest()
				.authenticated().and().formLogin().loginPage("/login").usernameParameter("email")
				.passwordParameter("password").defaultSuccessUrl("/home").permitAll().and().logout()
				.logoutSuccessUrl("/login?logout").permitAll().deleteCookies("JSESSIONID").invalidateHttpSession(true)
				.and().csrf().disable();

	}

}
