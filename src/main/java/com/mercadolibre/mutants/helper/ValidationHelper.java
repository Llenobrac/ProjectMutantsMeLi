package com.mercadolibre.mutants.helper;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mercadolibre.mutants.constant.LetterEnum;
import com.mercadolibre.mutants.util.UtilsForArray;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ValidationHelper implements IValidationHelper {

	@Autowired
	private UtilsForArray utilsForArray;
	
	private final String BASE_SEQUENCE = "1111";
	
	@Override
	public boolean validateInput(final String[] dna) {
		try {
			validateCharactersAllowed(dna);
			validateSizeRows(dna);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	private void validateSizeRows(final String[] dna) throws Exception {
		if(dna.length < 4) throw new Exception("DNA no tiene el tamaño minimo permitido");
		
		long numBadRows = Stream.of(dna).filter(r -> r.length() != dna.length).count();
		if(numBadRows > 0L) throw new Exception("DNA no permitido NxM");
	}

	private void validateCharactersAllowed(final String[] dna) throws Exception {
		String strValidation = String.join("", dna).toUpperCase();
		strValidation = strValidation.replaceAll("["+String.join("|", LetterEnum.toStringArray())+"]", "");
		if(strValidation.length() > 0) throw new Exception("DNA contiene caracteres no permitidos");
	}

	@Override
	public boolean validateMutantDNA(final String[] dna) {
		int cOcurrences = 0;
		String strDNA = String.join("", dna);
		final int sizeR = dna.length;
		for (LetterEnum le : LetterEnum.values()) {
			String strDnaBits = strDNA.replaceAll(le.name(), "1").replaceAll("[^1]", "0");
			String[] dnaBits = strDnaBits.split("(?<=\\G.{"+sizeR+"})");
			
			cOcurrences += validateRows(dnaBits);
			cOcurrences += validateColumns(dnaBits);
//			validateCross();
//			validateInvertedCross();
		}
		return false;
	}

	private int validateColumns(final String[] dnaBits) {
		final String[] rDnaBits = utilsForArray.rotateArray(dnaBits);
		return validateRows(rDnaBits);
	}

	private int validateRows(final String[] dnaBits) {
		return Stream.of(dnaBits).map(rDna -> StringUtils.countOccurrencesOf(rDna, BASE_SEQUENCE)).reduce(0, (num1, num2) -> num1 + num2);
	}
}
