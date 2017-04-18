package com.trimark.backoffice.persistence.repository;

import java.util.List;

import com.trimark.backoffice.persistence.model.LotteryModelPersistenceModel;

public interface ILotteryModelPersistenceModelRepository {
	
	List<LotteryModelPersistenceModel> findAll();
	
	void create(LotteryModelPersistenceModel lotteryModelPersistenceModel);
}
