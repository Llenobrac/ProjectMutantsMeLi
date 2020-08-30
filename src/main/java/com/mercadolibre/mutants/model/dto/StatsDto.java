package com.mercadolibre.mutants.model.dto;

public class StatsDto {
	private Long countMutantDNA;
	private Long countHumanDNA;
	private Double ratio;

	public StatsDto(Long countMutantDNA, Long countHumanDNA) {
		this.countMutantDNA = countMutantDNA;
		this.countHumanDNA = countHumanDNA;
	}

	public Long getCountMutantDNA() {
		return countMutantDNA;
	}

	public void setCountMutantDNA(Long countMutantDNA) {
		this.countMutantDNA = countMutantDNA;
	}

	public Long getCountHumanDNA() {
		return countHumanDNA;
	}

	public void setCountHumanDNA(Long countHumanDNA) {
		this.countHumanDNA = countHumanDNA;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
}
