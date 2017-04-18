package com.trimark.backoffice.service;

import java.util.List;

import com.trimark.backoffice.model.LotteryModelModel;

public interface ILotteryModelService {
	
	List<LotteryModelModel> findAll();
	
	void create(LotteryModelModel lotteryModelModel);

}
