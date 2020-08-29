package com.mercadolibre.mutants.service;

import com.mercadolibre.mutants.model.dto.StatsDto;

public interface IMutantService {

	boolean isMutant(String[] dna);

	StatsDto selectStats();

}
