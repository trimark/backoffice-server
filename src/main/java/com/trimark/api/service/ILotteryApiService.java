package com.trimark.api.service;

import com.trimark.api.service.request.ListLotteriesGameAdminApiServiceRequest;
import com.trimark.api.service.response.ApiServiceResponse;

public interface ILotteryApiService {
	
	ApiServiceResponse<?> listLotteries(ListLotteriesGameAdminApiServiceRequest listLotteriesGameAdminApiServiceRequest);
}
