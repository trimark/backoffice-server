package com.trimark.backoffice.web.dto;

import java.io.Serializable;

import com.trimark.backoffice.enumeration.MatchMechanic;
import com.trimark.backoffice.enumeration.OnOff;
import com.trimark.backoffice.enumeration.ScratchMechanic;
import com.trimark.backoffice.enumeration.WinMechanic;

public class LotteryModelDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private int gridColumn;
	
	private int gridRow;
	
	private int numOfLines;
	
	private int numOfPrizes;
	
	private int numOfMultipliers;
	
	private int rowMatches;
	
	private int columnMatches;
	
	private int diagonalMatches;
	
	private String modelName;
	
	private String variantName;
	
	private String multipliers;
	
	private String nearWinsPairHigh;
	
	private String nearWinsPairLow;
	
	private String distinctWinPattern;
	
	private String nearWinsPairProbabilityPct;
	
	private String nearWinsPrizeProbabilityPct;
	
	private String nearWinsMultiplierProbabilityPct;
	
	private WinMechanic winMechanic;
	
	private ScratchMechanic scratchMechanic;
	
	private MatchMechanic matchMechanic;
	
	private OnOff anticipationControl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
