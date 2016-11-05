package com.trimark.backoffice.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trimark.backoffice.web.dto.LoginDTO;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ErrorBackofficeResponse;
import com.trimark.backoffice.web.response.LoginSuccessResponse;
import com.trimark.backoffice.web.response.model.LoginSuccessModel;
import com.trimark.backoffice.web.response.model.OrganizationModel;

@Controller
public class LoginController {
	
	@Autowired
	@Qualifier("backofficeUserDetailsService")
	private UserDetailsService userDetailsService;
	
	private DaoAuthenticationProvider authenticationProvider;
	
	@Autowired
	public void setUp() {
		authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
	};
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> login(@RequestBody LoginDTO loginDTO) {
		try {
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					loginDTO.getOrganization().getName() + "/" + loginDTO.getUserName(), loginDTO.getPassword());
			if (authenticationProvider.authenticate(authRequest).isAuthenticated())
			{
				LoginSuccessModel loginSuccessModel = new LoginSuccessModel();
				loginSuccessModel.setJwtToken("organization=" + loginDTO.getOrganization().getName() + 
						"|userName=" + loginDTO.getUserName() + "|password=" + loginDTO.getPassword());
				loginSuccessModel.setOrganization(new OrganizationModel(loginDTO.getOrganization().getId(), loginDTO.getOrganization().getName()));
				return new ResponseEntity<LoginSuccessResponse>(new LoginSuccessResponse(loginSuccessModel), HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(1, "Login Failed"), HttpStatus.BAD_REQUEST);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
}
