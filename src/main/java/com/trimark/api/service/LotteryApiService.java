package com.trimark.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trimark.api.service.request.ListLotteriesGameAdminApiServiceRequest;
import com.trimark.api.service.response.ApiServiceResponse;
import com.trimark.api.service.response.data.LotteryApiServiceResponseData;

@Service
public class LotteryApiService implements ILotteryApiService {
	
	@Autowired
	private ApiService apiService;

	@Override
	public ApiServiceResponse<?> listLotteries(
			ListLotteriesGameAdminApiServiceRequest listLotteriesGameAdminApiServiceRequest) {
		try {
			return apiService.executeList(listLotteriesGameAdminApiServiceRequest, LotteryApiServiceResponseData.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
