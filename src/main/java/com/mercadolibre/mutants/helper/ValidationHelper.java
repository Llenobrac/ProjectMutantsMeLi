package com.mercadolibre.mutants.helper;

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
			validateSizeRows(dna);
			validateCharactersAllowed(dna);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Valida el tamaño minimo
	 * Valida que se cumpla la condición NxN
	 * @param dna
	 * @throws Exception
	 */
	
	private void validateSizeRows(final String[] dna) throws Exception {
		if (dna.length < 4)
			throw new Exception("DNA no tiene el tamaño minimo permitido");

		long numBadRows = Stream.of(dna).filter(r -> r.length() != dna.length).count();
		if (numBadRows > 0L)
			throw new Exception("DNA no permitido NxM");
	}
	/**
	 * Reemplaza los caracteres permitidos, si sobra algún caracteres entonces incluye caracteres no permitidos
	 * @param dna
	 * @throws Exception
	 */
	private void validateCharactersAllowed(final String[] dna) throws Exception {
		String strValidation = String.join("", dna).toUpperCase();
		strValidation = strValidation.replaceAll("[" + String.join("|", LetterEnum.toStringArray()) + "]", "");
		if (!strValidation.isEmpty())
			throw new Exception("DNA contiene caracteres no permitidos");
	}
	
	@Override
	public boolean validateMutantDNA(final String[] dna) {
		int cOcurrences = 0;

		for (LetterEnum le : LetterEnum.values()) {
			String[] dnaBits = replaceCurrentLetterByOne(dna, le);

			cOcurrences += validateRows(dnaBits);
			if (cOcurrences > 1)
				return true;
			cOcurrences += validateColumns(dnaBits);
			if (cOcurrences > 1)
				return true;
			cOcurrences += validateCross(dnaBits);
			if (cOcurrences > 1)
				return true;
			cOcurrences += validateInvertedCross(dnaBits);
			if (cOcurrences > 1)
				return true;
		}
		return false;
	}
	
	/**
	 * Reemplaza la letra actual por 1 y las otras en 0, luego vuelve a armar el Array
	 * @param dna
	 * @param le Letra actual recorrida
	 * @return
	 * 		Array transformado
	 */
	private String[] replaceCurrentLetterByOne(String[] dna, LetterEnum le) {
		String strDNA = String.join("", dna);
		String strDnaBits = strDNA.replaceAll(le.name(), "1").replaceAll("[^1]", "0");
		return strDnaBits.split("(?<=\\G.{" + dna.length + "})");
	}

	/**
	 * 1. Rota el Array en sentido de las manesillas del reloj
	 * 2. Obtiene las diagonales después de rotarse
	 * @param dnaBits
	 * @return
	 * 		Cantidad de ocurrencias encontradas en diagonal invertida
	 */
	private int validateInvertedCross(String[] dnaBits) {
		final String[] rDnaBits = utilsForArray.rotateArray(dnaBits);
		final String[] crDnaBits = utilsForArray.getDiagonalRows(rDnaBits, BASE_SEQUENCE.length());
		return validateRows(crDnaBits);
	}

	/**
	 * Obtiene las diagonales permitidas en tamaño
	 * @param dnaBits
	 * @return
	 * 		Cantidad de ocurrencias encontradas en diagonal principal
	 */
	private int validateCross(String[] dnaBits) {
		final String[] cDnaBits = utilsForArray.getDiagonalRows(dnaBits, BASE_SEQUENCE.length());
		return validateRows(cDnaBits);
	}
	/**
	 * 
	 * @param dnaBits
	 * @return
	 * 		Cantidad de ocurrencias encontradas por columnas
	 */
	private int validateColumns(final String[] dnaBits) {
		final String[] rDnaBits = utilsForArray.changeColumnsToRow(dnaBits);
		return validateRows(rDnaBits);
	}

	
	/**
	 * Cuenta la cantidad de ocurrencias que cumplen con la base permitida para la secuencia {@link #BASE_SEQUENCE}
	 * @param dnaBits
	 * @return
	 */
	private int validateRows(final String[] dnaBits) {
		return Stream.of(dnaBits).map(rDna -> StringUtils.countOccurrencesOf(rDna, BASE_SEQUENCE)).reduce(0,
				(num1, num2) -> num1 + num2);
	}
}
