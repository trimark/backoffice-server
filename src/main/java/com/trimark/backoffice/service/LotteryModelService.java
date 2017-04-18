package com.trimark.backoffice.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trimark.backoffice.model.LotteryModelModel;
import com.trimark.backoffice.persistence.model.LotteryModelPersistenceModel;
import com.trimark.backoffice.persistence.repository.ILotteryModelPersistenceModelRepository;

@Service("lotteryModelService")
@Transactional
public class LotteryModelService implements ILotteryModelService {
	
	@Autowired
	private ILotteryModelPersistenceModelRepository lotteryModelPersistenceModelRepository;

	@Override
	public List<LotteryModelModel> findAll() {
		List<LotteryModelPersistenceModel> lotteryModelPersistenceModels = lotteryModelPersistenceModelRepository.findAll();
		List<LotteryModelModel> lotteryModelModels = new ArrayList<LotteryModelModel>();
		
		CollectionUtils.collect(lotteryModelPersistenceModels, new Transformer<LotteryModelPersistenceModel, LotteryModelModel>() {

			@Override
			public LotteryModelModel transform(LotteryModelPersistenceModel input) {
				LotteryModelModel lotteryModelModel = new LotteryModelModel();
				lotteryModelModel.setId(input.getId());
				lotteryModelModel.setGridColumn(input.getGridColumn());
				lotteryModelModel.setGridRow(input.getGridRow());
				lotteryModelModel.setNumOfLines(input.getNumOfLines());
				lotteryModelModel.setNumOfPrizes(input.getNumOfPrizes());
				lotteryModelModel.setNumOfMultipliers(input.getNumOfLines());
				lotteryModelModel.setRowMatches(input.getRowMatches());
				lotteryModelModel.setColumnMatches(input.getColumnMatches());
				lotteryModelModel.setDiagonalMatches(input.getDiagonalMatches());
				lotteryModelModel.setModelName(input.getModelName());
				lotteryModelModel.setVariantName(input.getVariantName());
				lotteryModelModel.setMultipliers(input.getMultipliers());
				lotteryModelModel.setNearWinsPairHigh(input.getNearWinsPairHigh());
				lotteryModelModel.setNearWinsPairLow(input.getNearWinsPairLow());
				lotteryModelModel.setDistinctWinPattern(input.getDistinctWinPattern());
				lotteryModelModel.setNearWinsPairProbabilityPct(input.getNearWinsPairProbabilityPct());
				lotteryModelModel.setNearWinsPrizeProbabilityPct(input.getNearWinsPrizeProbabilityPct());
				lotteryModelModel.setNearWinsMultiplierProbabilityPct(input.getNearWinsMultiplierProbabilityPct());
				lotteryModelModel.setWinMechanic(input.getWinMechanic());
				lotteryModelModel.setScratchMechanic(input.getScratchMechanic());
				lotteryModelModel.setMatchMechanic(input.getMatchMechanic());
				lotteryModelModel.setAnticipationControl(input.getAnticipationControl());
				return lotteryModelModel;
			}
		}, lotteryModelModels);
		
		return lotteryModelModels;
	}

	@Override
	public void create(LotteryModelModel lotteryModelModel) {
		LotteryModelPersistenceModel lotteryModelPersistenceModel = new LotteryModelPersistenceModel();
		lotteryModelPersistenceModel.setGridColumn(lotteryModelModel.getGridColumn());
		lotteryModelPersistenceModel.setGridRow(lotteryModelModel.getGridRow());
		lotteryModelPersistenceModel.setNumOfLines(lotteryModelModel.getNumOfLines());
		lotteryModelPersistenceModel.setNumOfPrizes(lotteryModelModel.getNumOfPrizes());
		lotteryModelPersistenceModel.setNumOfMultipliers(lotteryModelModel.getNumOfLines());
		lotteryModelPersistenceModel.setRowMatches(lotteryModelModel.getRowMatches());
		lotteryModelPersistenceModel.setColumnMatches(lotteryModelModel.getColumnMatches());
		lotteryModelPersistenceModel.setDiagonalMatches(lotteryModelModel.getDiagonalMatches());
		lotteryModelPersistenceModel.setModelName(lotteryModelModel.getModelName());
		lotteryModelPersistenceModel.setVariantName(lotteryModelModel.getVariantName());
		lotteryModelPersistenceModel.setMultipliers(lotteryModelModel.getMultipliers());
		lotteryModelPersistenceModel.setNearWinsPairHigh(lotteryModelModel.getNearWinsPairHigh());
		lotteryModelPersistenceModel.setNearWinsPairLow(lotteryModelModel.getNearWinsPairLow());
		lotteryModelPersistenceModel.setDistinctWinPattern(lotteryModelModel.getDistinctWinPattern());
		lotteryModelPersistenceModel.setNearWinsPairProbabilityPct(lotteryModelModel.getNearWinsPairProbabilityPct());
		lotteryModelPersistenceModel.setNearWinsPrizeProbabilityPct(lotteryModelModel.getNearWinsPrizeProbabilityPct());
		lotteryModelPersistenceModel.setNearWinsMultiplierProbabilityPct(lotteryModelModel.getNearWinsMultiplierProbabilityPct());
		lotteryModelPersistenceModel.setWinMechanic(lotteryModelModel.getWinMechanic());
		lotteryModelPersistenceModel.setScratchMechanic(lotteryModelModel.getScratchMechanic());
		lotteryModelPersistenceModel.setMatchMechanic(lotteryModelModel.getMatchMechanic());
		lotteryModelPersistenceModel.setAnticipationControl(lotteryModelModel.getAnticipationControl());
		lotteryModelPersistenceModelRepository.create(lotteryModelPersistenceModel);
	}	
}
