package com.trimark.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trimark.api.service.request.AuthAdminLoginApiServiceRequest;
import com.trimark.api.service.request.GameAdminLoginApiServiceRequest;
import com.trimark.api.service.response.ApiServiceResponse;
import com.trimark.api.service.response.data.UserApiServiceResponseData;

@Service
public class LoginApiService implements ILoginApiService {
	
	@Autowired
	private ApiService apiService;
	
	@Override
	public ApiServiceResponse<?> authAdminLogin(AuthAdminLoginApiServiceRequest authAdminLoginApiServiceRequest) {
		try {
			return apiService.execute(authAdminLoginApiServiceRequest, UserApiServiceResponseData.class);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ApiServiceResponse<?> gameAdminLogin(GameAdminLoginApiServiceRequest gameAdminLoginApiServiceRequest) {
		try {
			return apiService.execute(gameAdminLoginApiServiceRequest, UserApiServiceResponseData.class);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
}
