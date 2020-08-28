package com.mercadolibre.mutants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.mercadolibre.mutants.helper.IValidationHelper;
import com.mercadolibre.mutants.model.entity.DNAAnalysis;
import com.mercadolibre.mutants.repository.IDNAAnalysRepository;

@Service
//@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class MutantService implements IMutantService {

	@Autowired
	private IValidationHelper iValidationHelper;
	
	@Autowired
	private IDNAAnalysRepository iDnaAnalysRepository;
	
	@Override
	public boolean isMutant(String[] dna) {
		boolean resultDNAMutant = false;
		String strDNA = String.join("", dna).toUpperCase();
		
		DNAAnalysis dnaAnalysis = iDnaAnalysRepository.findByDNA(strDNA);
		if(dnaAnalysis != null) {
			return dnaAnalysis.isMutant();
		}
		
		if(iValidationHelper.validateInput(dna)) {
			resultDNAMutant = iValidationHelper.validateMutantDNA(dna);
			saveDnaAnalysis(resultDNAMutant, dna);
		}
		return resultDNAMutant;
	}
	
	private void saveDnaAnalysis(boolean resultDNAMutant, String[] dna) {
		DNAAnalysis da = new DNAAnalysis();
		da.setDna(String.join("", dna).toLowerCase());
		da.setMutant(resultDNAMutant);
		iDnaAnalysRepository.save(da);
	}

}
