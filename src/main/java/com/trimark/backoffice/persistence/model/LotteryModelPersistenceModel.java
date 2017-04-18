package com.trimark.backoffice.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.trimark.backoffice.enumeration.MatchMechanic;
import com.trimark.backoffice.enumeration.OnOff;
import com.trimark.backoffice.enumeration.ScratchMechanic;
import com.trimark.backoffice.enumeration.WinMechanic;

@Entity
@Table(name = "scratch_lottery_models")
public class LotteryModelPersistenceModel extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "model_name", unique = true)
	private String modelName;
	
	@Column(name = "variant_name")
	private String variantName;
	
	@Column
	private String multipliers;
	
	@Column(name = "near_wins_pair_high")
	private String nearWinsPairHigh;
	
	@Column(name = "near_wins_pair_low")
	private String nearWinsPairLow;
	
	@Column(name = "distinct_win_pattern")
	private String distinctWinPattern;
	
	@Column(name = "near_wins_pair_prob_pct")
	private String nearWinsPairProbabilityPct;
	
	@Column(name = "near_wins_prize_prob_pct")
	private String nearWinsPrizeProbabilityPct;
	
	@Column(name = "near_wins_multiplier_prob_pct")
	private String nearWinsMultiplierProbabilityPct;
	
	@Column(name = "grid_column")
	private int gridColumn;
	
	@Column(name = "grid_row")
	private int gridRow;
	
	@Column(name = "num_of_lines")
	private int numOfLines;
	
	@Column(name = "num_of_prizes")
	private int numOfPrizes;
	
	@Column(name = "num_of_multipliers")
	private int numOfMultipliers;
	
	@Column(name = "row_matches")
	private int rowMatches;
	
	@Column(name = "column_matches")
	private int columnMatches;
	
	@Column(name = "diagonal_matches")
	private int diagonalMatches;	
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "win_mechanic")
	private WinMechanic winMechanic;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "scratch_mechanic")
	private ScratchMechanic scratchMechanic;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "match_mechanic")
	private MatchMechanic matchMechanic;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "anticipantion_control")
	private OnOff anticipationControl;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getVariantName() {
		return variantName;
	}

	public void setVariantName(String variantName) {
		this.variantName = variantName;
	}

	public String getMultipliers() {
		return multipliers;
	}

	public void setMultipliers(String multipliers) {
		this.multipliers = multipliers;
	}

	public String getNearWinsPairHigh() {
		return nearWinsPairHigh;
	}

	public void setNearWinsPairHigh(String nearWinsPairHigh) {
		this.nearWinsPairHigh = nearWinsPairHigh;
	}

	public String getNearWinsPairLow() {
		return nearWinsPairLow;
	}

	public void setNearWinsPairLow(String nearWinsPairLow) {
		this.nearWinsPairLow = nearWinsPairLow;
	}

	public String getDistinctWinPattern() {
		return distinctWinPattern;
	}

	public void setDistinctWinPattern(String distinctWinPattern) {
		this.distinctWinPattern = distinctWinPattern;
	}

	public String getNearWinsPairProbabilityPct() {
		return nearWinsPairProbabilityPct;
	}

	public void setNearWinsPairProbabilityPct(String nearWinsPairProbabilityPct) {
		this.nearWinsPairProbabilityPct = nearWinsPairProbabilityPct;
	}

	public String getNearWinsPrizeProbabilityPct() {
		return nearWinsPrizeProbabilityPct;
	}

	public void setNearWinsPrizeProbabilityPct(String nearWinsPrizeProbabilityPct) {
		this.nearWinsPrizeProbabilityPct = nearWinsPrizeProbabilityPct;
	}

	public String getNearWinsMultiplierProbabilityPct() {
		return nearWinsMultiplierProbabilityPct;
	}

	public void setNearWinsMultiplierProbabilityPct(String nearWinsMultiplierProbabilityPct) {
		this.nearWinsMultiplierProbabilityPct = nearWinsMultiplierProbabilityPct;
	}
	
	public int getGridColumn() {
		return gridColumn;
	}

	public void setGridColumn(int gridColumn) {
		this.gridColumn = gridColumn;
	}

	public int getGridRow() {
		return gridRow;
	}

	public void setGridRow(int gridRow) {
		this.gridRow = gridRow;
	}

	public int getNumOfLines() {
		return numOfLines;
	}

	public void setNumOfLines(int numOfLines) {
		this.numOfLines = numOfLines;
	}

	public int getNumOfPrizes() {
		return numOfPrizes;
	}

	public void setNumOfPrizes(int numOfPrizes) {
		this.numOfPrizes = numOfPrizes;
	}

	public int getNumOfMultipliers() {
		return numOfMultipliers;
	}

	public void setNumOfMultipliers(int numOfMultipliers) {
		this.numOfMultipliers = numOfMultipliers;
	}

	public int getRowMatches() {
		return rowMatches;
	}

	public void setRowMatches(int rowMatches) {
		this.rowMatches = rowMatches;
	}

	public int getColumnMatches() {
		return columnMatches;
	}

	public void setColumnMatches(int columnMatches) {
		this.columnMatches = columnMatches;
	}

	public int getDiagonalMatches() {
		return diagonalMatches;
	}

	public void setDiagonalMatches(int diagonalMatches) {
		this.diagonalMatches = diagonalMatches;
	}

	public WinMechanic getWinMechanic() {
		return winMechanic;
	}

	public void setWinMechanic(WinMechanic winMechanic) {
		this.winMechanic = winMechanic;
	}

	public ScratchMechanic getScratchMechanic() {
		return scratchMechanic;
	}

	public void setScratchMechanic(ScratchMechanic scratchMechanic) {
		this.scratchMechanic = scratchMechanic;
	}

	public MatchMechanic getMatchMechanic() {
		return matchMechanic;
	}

	public void setMatchMechanic(MatchMechanic matchMechanic) {
		this.matchMechanic = matchMechanic;
	}

	public OnOff getAnticipationControl() {
		return anticipationControl;
	}

	public void setAnticipationControl(OnOff anticipationControl) {
		this.anticipationControl = anticipationControl;
	}	
}
