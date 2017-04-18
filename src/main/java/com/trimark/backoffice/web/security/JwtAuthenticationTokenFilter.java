package com.trimark.backoffice.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

	public JwtAuthenticationTokenFilter() {
		super("/**");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		System.out.println("jwt-Token >>> " + request.getHeader("Jwt-Token"));
		if (request.getHeader("Jwt-Token") != null && !request.getHeader("Jwt-Token").equals("null"))
		{
			String[] userToken = request.getHeader("Jwt-Token").split("\\|");
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					userToken[0].split("=")[1] + "/" + userToken[1].split("=")[1], userToken[2].split("=")[1]);
			try {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) getAuthenticationManager().authenticate(authRequest);
				BackofficeUsernamePasswordAuthenticationToken result = new BackofficeUsernamePasswordAuthenticationToken(usernamePasswordAuthenticationToken);
				result.setGameSessionId(userToken[3].split("=")[1]);
				return result;
			}
			catch (Exception e) {
				e.printStackTrace();
				return SecurityContextHolder.getContext().getAuthentication();
			}
		}
		else
		{
			return SecurityContextHolder.getContext().getAuthentication();
		}
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
