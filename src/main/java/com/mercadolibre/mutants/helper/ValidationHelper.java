package com.mercadolibre.mutants.helper;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercadolibre.mutants.constant.LetterEnum;
import com.mercadolibre.mutants.util.UtilsForArray;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ValidationHelper implements IValidationHelper {

	@Autowired
	private UtilsForArray utilsForArray;
	
	@Override
	public boolean validateInput(String[] dna) {
		try {
			validateCharactersAllowed(dna);
			validateSizeRows(dna);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	private void validateSizeRows(String[] dna) throws Exception {
		Stream<String> stream = Arrays.stream(dna);
		if(dna.length < 4) throw new Exception("DNA no tiene el tamaño minimo permitido");
		
		long numBadRows = stream.filter(r -> r.length() != dna.length).count();
		if(numBadRows > 0L) throw new Exception("DNA no permitido NxM");
	}

	private void validateCharactersAllowed(String[] dna) throws Exception {
		String strValidation = String.join("", dna).toUpperCase();
		for (LetterEnum le : LetterEnum.values()) {
			strValidation = strValidation.replace(le.name(), "");
		}
		if(strValidation.length() > 0) throw new Exception("DNA contiene caracteres no permitidos");
	}

	@Override
	public boolean validateMutantDNA(String[] dna) {
		for (LetterEnum le : LetterEnum.values()) {
			utilsForArray.convertToMatrix(dna, le);
//			validateRows();
//			validateColumns();
//			validateCross();
//			validateInvertedCross();
		}
		return false;
	}
}
