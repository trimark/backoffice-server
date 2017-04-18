package com.trimark.api.service;

import com.trimark.api.service.request.ListGamesGameAdminApiServiceRequest;
import com.trimark.api.service.request.ListGamesGameApiServiceRequest;
import com.trimark.api.service.response.ApiServiceResponse;

public interface IGameApiService {
	ApiServiceResponse<?> listGames(ListGamesGameApiServiceRequest listGamesGameApiServiceRequest);
	
	ApiServiceResponse<?> listGamesAdmin(ListGamesGameAdminApiServiceRequest listGamesGameAdminApiServiceRequest);
}
