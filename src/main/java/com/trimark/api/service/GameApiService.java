package com.trimark.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trimark.api.service.request.ListGamesGameAdminApiServiceRequest;
import com.trimark.api.service.request.ListGamesGameApiServiceRequest;
import com.trimark.api.service.response.ApiServiceResponse;
import com.trimark.api.service.response.data.GameApiServiceResponseData;

@Service
public class GameApiService implements IGameApiService {
	
	@Autowired
	private ApiService apiService;
	
	@Override
	public ApiServiceResponse<?> listGames(ListGamesGameApiServiceRequest listGamesGameApiServiceRequest) {
		try {
			return apiService.executeList(listGamesGameApiServiceRequest, GameApiServiceResponseData.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ApiServiceResponse<?> listGamesAdmin(ListGamesGameAdminApiServiceRequest listGamesGameAdminApiServiceRequest) {
		try {
			return apiService.executeList(listGamesGameAdminApiServiceRequest, GameApiServiceResponseData.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
