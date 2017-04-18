package com.trimark.api.service;

import com.trimark.api.service.request.AuthAdminLoginApiServiceRequest;
import com.trimark.api.service.request.GameAdminLoginApiServiceRequest;
import com.trimark.api.service.response.ApiServiceResponse;

public interface ILoginApiService {
	ApiServiceResponse<?> authAdminLogin(AuthAdminLoginApiServiceRequest authAdminLoginApiServiceRequest);
	
	ApiServiceResponse<?> gameAdminLogin(GameAdminLoginApiServiceRequest gameAdminLoginApiServiceRequest);
}
