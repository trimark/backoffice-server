package com.trimark.backoffice.web.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("backofficeUserDetailsService")
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/css/**", "/index").permitAll()
				.mvcMatchers("/create/organization").permitAll()
				.antMatchers("/user/**").hasRole("USER")			
				.and()	
			.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
		*/
		
		http.csrf().disable()
			.cors().and()
			.authorizeRequests().antMatchers("/login", "/organizations/listAll").permitAll()
				.antMatchers("/organizations/load/**", "/organizations/listChildOrganizations/**")
				.hasAnyRole("ORGANIZATIONS_READ", "ORGANIZATIONS_UPDATE", "ORGANIZATIONS_CREATE", "ORGANIZATIONS_DELETE")
				.antMatchers("/organizations/create").hasRole("ORGANIZATIONS_CREATE")
				.antMatchers("/roles/findById/**", "/roles/listRolesByOwner/**", "/roles/listRolesByOwnerAndType/**")
				.hasAnyRole("ROLES_READ", "ROLE_UPDATE", "ROLES_CREATE", "ROLES_DELETE")
				.antMatchers("/roles/update").hasAnyRole("ROLES_UPDATE", "ROLES_CREATE", "ROLES_DELETE")
				.antMatchers("/roles/create").hasAnyRole("ROLES_CREATE", "ROLES_DELETE")
				.antMatchers("/users/findByAccountId/**", "/users/listAllByOrganization/**")
				.hasAnyRole("USERS_READ", "USERS_UPDATE", "USERS_CREATE", "USERS_DELETE")
				.antMatchers("/users/update").hasAnyRole("USERS_UPDATE", "USERS_CREATE", "USERS_DELETE")
				.antMatchers("/users/changePassword/**").hasAnyRole("USERS_CHANGEPASSWORD", "USERS_CREATE", "USERS_DELETE")
				.antMatchers("/users/create").hasAnyRole("USERS_CREATE", "USERS_DELETE")
				.antMatchers("/games/findAllBaseGamesByCategory/**", "/games/findAllTitleGames")
				.hasAnyRole("GAMES_READ", "GAMES_UPDATE", "GAMES_CREATE", "GAMES_DELETE")
				.antMatchers("/lotteries/findAllLotteries", "/lotteries/findAllModels")
				.hasAnyRole("LOTTERIES_READ", "LOTTERIES_UPDATE", "LOTTERIES_CREATE", "LOTTERIES_DELETE")
				.antMatchers("/lotteries/models/create").hasAnyRole("LOTTERIES_CREATE", "LOTTERIES_DELETE")
				.anyRequest().authenticated()
				.and()
			.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
		http
        	.addFilterBefore(authenticationTokenFilterBean(), FilterSecurityInterceptor.class);
		http.headers().cacheControl();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManager());
        authenticationTokenFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        return authenticationTokenFilter;
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		return authenticationProvider;
	}
}