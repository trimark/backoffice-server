package com.trimark.backoffice.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trimark.api.service.IGameApiService;
import com.trimark.api.service.request.ListGamesGameAdminApiServiceRequest;
import com.trimark.api.service.response.ApiServiceResponse;
import com.trimark.api.service.response.data.GameApiServiceResponseData;
import com.trimark.backoffice.model.GameModel;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ErrorBackofficeResponse;
import com.trimark.backoffice.web.response.SuccessBackofficeResponse;
import com.trimark.backoffice.web.security.BackofficeUsernamePasswordAuthenticationToken;

@Controller
public class GameController {
	
	@Autowired
	private IGameApiService gameApiService;
	
	@RequestMapping(value = "/games/findAllBaseGamesByCategory/{category}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getAllBaseGamesByCategory(@PathVariable("category") String category) {
		BackofficeUsernamePasswordAuthenticationToken authentication = 
				(BackofficeUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			ListGamesGameAdminApiServiceRequest gameAdminListGamesApiServiceRequest = new ListGamesGameAdminApiServiceRequest();
			gameAdminListGamesApiServiceRequest.setSessionId(authentication.getGameSessionId());
			gameAdminListGamesApiServiceRequest.setMode("template");
			gameAdminListGamesApiServiceRequest.setCategory(category);
			return new ResponseEntity<SuccessBackofficeResponse<List<GameModel>>>(
					new SuccessBackofficeResponse<List<GameModel>>(listGames(gameAdminListGamesApiServiceRequest)), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/games/findAllTitleGames", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getAllTitleGames() {
		BackofficeUsernamePasswordAuthenticationToken authentication = 
				(BackofficeUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			ListGamesGameAdminApiServiceRequest gameAdminListGamesApiServiceRequest = new ListGamesGameAdminApiServiceRequest();
			gameAdminListGamesApiServiceRequest.setSessionId(authentication.getGameSessionId());
			gameAdminListGamesApiServiceRequest.setMode("title");			
			return new ResponseEntity<SuccessBackofficeResponse<List<GameModel>>>(
					new SuccessBackofficeResponse<List<GameModel>>(listGames(gameAdminListGamesApiServiceRequest)), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<GameModel> listGames(ListGamesGameAdminApiServiceRequest gameAdminListGamesApiServiceRequest) {
		List<GameModel> result = new ArrayList<GameModel>();
		ApiServiceResponse<?> apiServiceResponse = gameApiService.listGamesAdmin(gameAdminListGamesApiServiceRequest);
		List<GameApiServiceResponseData> gameApiServiceResponseDataList = (List<GameApiServiceResponseData>) apiServiceResponse.getData();
		CollectionUtils.collect(gameApiServiceResponseDataList, new Transformer<GameApiServiceResponseData, GameModel>() {

			@Override
			public GameModel transform(GameApiServiceResponseData input) {
				GameModel gameModel = new GameModel();
				gameModel.setId(input.getGameId());
				return gameModel;
			}
		}, result);
		return result;
	}
}
