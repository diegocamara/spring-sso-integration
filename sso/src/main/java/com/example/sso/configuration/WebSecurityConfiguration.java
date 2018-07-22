package com.example.sso.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEnconder;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEnconder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/login", "/logout", "/user/registration", "/user/registrationConfirm", "/webjars/**",
						"/img/**", "/css/**", "/js/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login")
				.loginProcessingUrl("/perform_login").usernameParameter("email").passwordParameter("password")
				.defaultSuccessUrl("/home").permitAll().and().logout().logoutUrl("/perform_logout")
				.logoutSuccessUrl("/login").permitAll().deleteCookies("JSESSIONID").invalidateHttpSession(true).and()
				.csrf().disable();

	}

}
