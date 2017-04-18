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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trimark.api.service.ILotteryApiService;
import com.trimark.api.service.request.ListLotteriesGameAdminApiServiceRequest;
import com.trimark.api.service.response.ApiServiceResponse;
import com.trimark.api.service.response.data.LotteryApiServiceResponseData;
import com.trimark.backoffice.enumeration.PlayMode;
import com.trimark.backoffice.model.LotteryModel;
import com.trimark.backoffice.model.LotteryModelModel;
import com.trimark.backoffice.service.ILotteryModelService;
import com.trimark.backoffice.web.dto.ListLotteriesDTO;
import com.trimark.backoffice.web.dto.LotteryModelDTO;
import com.trimark.backoffice.web.response.BackofficeResponse;
import com.trimark.backoffice.web.response.ErrorBackofficeResponse;
import com.trimark.backoffice.web.response.SuccessBackofficeResponse;
import com.trimark.backoffice.web.security.BackofficeUsernamePasswordAuthenticationToken;

@Controller
public class LotteryController {
	
	@Autowired
	private ILotteryModelService lotteryModelService;
	
	@Autowired
	private ILotteryApiService lotteryApiService;
	
	@RequestMapping(value = "/lotteries/findAllModels", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getAllModels() {
		List<LotteryModelModel> lotteryModelModels = lotteryModelService.findAll();
		return new ResponseEntity<SuccessBackofficeResponse<List<LotteryModelModel>>>(
				new SuccessBackofficeResponse<List<LotteryModelModel>>(lotteryModelModels), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lotteries/models/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> create(@RequestBody LotteryModelDTO lotteryModelDTO) {
		try {
			LotteryModelModel lotteryModelModel = new LotteryModelModel();
			lotteryModelModel.setGridColumn(lotteryModelDTO.getGridColumn());
			lotteryModelModel.setGridRow(lotteryModelDTO.getGridRow());
			lotteryModelModel.setNumOfLines(lotteryModelDTO.getNumOfLines());
			lotteryModelModel.setNumOfPrizes(lotteryModelDTO.getNumOfPrizes());
			lotteryModelModel.setNumOfMultipliers(lotteryModelDTO.getNumOfLines());
			lotteryModelModel.setRowMatches(lotteryModelDTO.getRowMatches());
			lotteryModelModel.setColumnMatches(lotteryModelDTO.getColumnMatches());
			lotteryModelModel.setDiagonalMatches(lotteryModelDTO.getDiagonalMatches());
			lotteryModelModel.setModelName(lotteryModelDTO.getModelName());
			lotteryModelModel.setVariantName(lotteryModelDTO.getVariantName());
			lotteryModelModel.setMultipliers(lotteryModelDTO.getMultipliers());
			lotteryModelModel.setNearWinsPairHigh(lotteryModelDTO.getNearWinsPairHigh());
			lotteryModelModel.setNearWinsPairLow(lotteryModelDTO.getNearWinsPairLow());
			lotteryModelModel.setDistinctWinPattern(lotteryModelDTO.getDistinctWinPattern());
			lotteryModelModel.setNearWinsPairProbabilityPct(lotteryModelDTO.getNearWinsPairProbabilityPct());
			lotteryModelModel.setNearWinsPrizeProbabilityPct(lotteryModelDTO.getNearWinsPrizeProbabilityPct());
			lotteryModelModel.setNearWinsMultiplierProbabilityPct(lotteryModelDTO.getNearWinsMultiplierProbabilityPct());
			lotteryModelModel.setWinMechanic(lotteryModelDTO.getWinMechanic());
			lotteryModelModel.setScratchMechanic(lotteryModelDTO.getScratchMechanic());
			lotteryModelModel.setMatchMechanic(lotteryModelDTO.getMatchMechanic());
			lotteryModelModel.setAnticipationControl(lotteryModelDTO.getAnticipationControl());
			lotteryModelService.create(lotteryModelModel);
			return new ResponseEntity<SuccessBackofficeResponse<String>>(new SuccessBackofficeResponse<String>("Success"), HttpStatus.OK); 			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lotteries/findAllLotteries", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<? extends BackofficeResponse<?>> getAllLotteries(@RequestBody ListLotteriesDTO listLotteriesDTO) {
		BackofficeUsernamePasswordAuthenticationToken authentication = 
				(BackofficeUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			ListLotteriesGameAdminApiServiceRequest listLotteriesGameAdminApiServiceRequest = 
					new ListLotteriesGameAdminApiServiceRequest(listLotteriesDTO.getGameId(), listLotteriesDTO.getDrawId(), authentication.getGameSessionId());
			ApiServiceResponse<?> apiServiceResponse = lotteryApiService.listLotteries(listLotteriesGameAdminApiServiceRequest);
			List<LotteryApiServiceResponseData> lotteryApiServiceResponseDataList = (List<LotteryApiServiceResponseData>) apiServiceResponse.getData();
			List<LotteryModel> lotteryModels = new ArrayList<LotteryModel>();
			CollectionUtils.collect(lotteryApiServiceResponseDataList, new Transformer<LotteryApiServiceResponseData, LotteryModel>() {

				@Override
				public LotteryModel transform(LotteryApiServiceResponseData input) {
					LotteryModel lotteryModel = new LotteryModel();
					lotteryModel.setName(input.getName());
					lotteryModel.setDescription(input.getDescription());
					lotteryModel.setModelName(input.getModelName());
					lotteryModel.setStatus(input.getStatus());
					lotteryModel.setCreated(input.getCreated());
					lotteryModel.setPlayMode(PlayMode.RealMode);
					return lotteryModel;
				}
			}, lotteryModels);
			return new ResponseEntity<SuccessBackofficeResponse<List<LotteryModel>>>(new SuccessBackofficeResponse<List<LotteryModel>>(lotteryModels), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ErrorBackofficeResponse>(new ErrorBackofficeResponse(-1, "Technical Error"), HttpStatus.BAD_REQUEST);
		}
	}
}
