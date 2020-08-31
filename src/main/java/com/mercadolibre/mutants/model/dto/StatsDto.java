package com.mercadolibre.mutants.model.dto;

public class StatsDto {
	private Long countMutantDNA;
	private Long countHumanDNA;
	private Double ratio;

	public StatsDto(Long countMutantDNA, Long countHumanDNA) {
		this.countMutantDNA = countMutantDNA;
		this.countHumanDNA = countHumanDNA;
		this.ratio = countHumanDNA > 0 ? countMutantDNA / countHumanDNA.doubleValue() : 0.0;
	}

	public Long getCountMutantDNA() {
		return countMutantDNA;
	}

	public Long getCountHumanDNA() {
		return countHumanDNA;
	}

	public Double getRatio() {
		return ratio;
	}

}
