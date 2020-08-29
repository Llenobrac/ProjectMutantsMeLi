package com.mercadolibre.mutants.model.dto;

import lombok.Data;

@Data
public class StatsDto {
	private Long countMutantDNA;
	private Long countHumanDNA;
	private Double ratio;

	public StatsDto(Long countMutantDNA, Long countHumanDNA) {
		this.countMutantDNA = countMutantDNA;
		this.countHumanDNA = countHumanDNA;
	}
}
