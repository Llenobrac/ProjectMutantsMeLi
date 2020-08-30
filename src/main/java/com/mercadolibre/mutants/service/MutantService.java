package com.mercadolibre.mutants.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.mercadolibre.mutants.helper.IValidationHelper;
import com.mercadolibre.mutants.model.dto.StatsDto;
import com.mercadolibre.mutants.model.entity.DNAAnalysis;
import com.mercadolibre.mutants.repository.IDNAAnalysRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class MutantService implements IMutantService {

	@Autowired
	private IValidationHelper iValidationHelper;

	@Autowired
	private IDNAAnalysRepository iDnaAnalysRepository;

	@Override
	public boolean isMutant(String[] dna) {
		boolean resultDNAMutant = false;
		try {
			String strDNA = String.join("", dna).toUpperCase();

			DNAAnalysis dnaAnalysis = iDnaAnalysRepository.findByDNA(strDNA);
			if (dnaAnalysis != null) {
				return dnaAnalysis.isMutant();
			}

			if (iValidationHelper.validateInput(dna)) {
				resultDNAMutant = iValidationHelper.validateMutantDNA(dna);
			}
			try {
				saveDnaAnalysis(resultDNAMutant, dna);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return resultDNAMutant;
	}

	@Transactional
	private void saveDnaAnalysis(boolean resultDNAMutant, String[] dna) {
		DNAAnalysis da = new DNAAnalysis();
		da.setDna(String.join("", dna).toUpperCase());
		da.setMutant(resultDNAMutant);
		iDnaAnalysRepository.save(da);
	}

	@Override
	public StatsDto selectStats() {
		StatsDto statsDto = iDnaAnalysRepository.selectStats();
		if (statsDto.getCountHumanDNA() > 0L) {
			statsDto.setRatio(statsDto.getCountMutantDNA() / statsDto.getCountHumanDNA().doubleValue());
		}
		return statsDto;
	}

}
