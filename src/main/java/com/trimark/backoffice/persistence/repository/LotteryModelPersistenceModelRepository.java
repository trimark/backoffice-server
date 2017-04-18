package com.trimark.backoffice.persistence.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.trimark.backoffice.persistence.model.LotteryModelPersistenceModel;

@Repository("lotteryModelPersistenceModelRepository")
public class LotteryModelPersistenceModelRepository extends BaseRepository<Long, LotteryModelPersistenceModel> implements ILotteryModelPersistenceModelRepository {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LotteryModelPersistenceModel> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<LotteryModelPersistenceModel>) criteria.list();
	}

	@Override
	public void create(LotteryModelPersistenceModel lotteryModelPersistenceModel) {
		persist(lotteryModelPersistenceModel);
	}
}
