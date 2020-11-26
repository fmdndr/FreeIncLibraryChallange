package com.freeinc.Library.WebSecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.freeinc.Library.Business.IUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// add a reference to our user service

	private IUserService userService;

	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	public WebSecurityConfig(IUserService userService,
			CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
		super();
		this.userService = userService;
		this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// .antMatchers("/css/**", "/static/**", "/resources/**", "/.*.css").permitAll()

		http.csrf().disable().authorizeRequests().antMatchers("/").hasRole("USERS").antMatchers("/moderator/**")
				.hasRole("MODERATOR").antMatchers("/admin/**").hasRole("ADMIN").and().formLogin().loginPage("/login")
				.loginProcessingUrl("/authenticateTheUser").successHandler(customAuthenticationSuccessHandler)
				.failureUrl("/").permitAll().and().logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll().and()
				.exceptionHandling().accessDeniedPage("/access-denied");

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); // set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); // set the password encoder - bcrypt
		return auth;
	}

}
