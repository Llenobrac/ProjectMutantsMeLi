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
	private final int NUM_OCCURRENCES = 4;
	private final int MININUM_SEQUENCE = 2;
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
		if (dna.length < NUM_OCCURRENCES)
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
			String[] dnaBits = utilsForArray.replaceCurrentLetterByOne(dna, le);

			if(validatePossibleSequences(dnaBits)) {
				continue;
			}
			cOcurrences += validateRows(dnaBits);
			if (cOcurrences >= MININUM_SEQUENCE)
				return true;
			cOcurrences += validateColumns(dnaBits);
			if (cOcurrences >= MININUM_SEQUENCE)
				return true;
			cOcurrences = validateDiagonals(cOcurrences, dnaBits);
			if (cOcurrences >= MININUM_SEQUENCE)
				
				return true;
		}
		return false;
	}
	/***
	 *  Valida 
	 * @param dnaBits
	 * @return
	 */
	private boolean validatePossibleSequences(String[] dnaBits) {
		String strBinary = String.join("", dnaBits);
		return StringUtils.countOccurrencesOf(strBinary, "1") < NUM_OCCURRENCES ;
	}

	/**
	 * Obtiene las diagonales del array
	 * 1. Asigna la diagonal principal que inicia en la posición (0,0) y termina en la posición (n-1,n-1)
	 * 2. Obtiene las diagonales superiores e inferiores en cada recorrido 
	 * @param cOcurrences 
	 * @param dnaBits
	 * @param lengthDNA
	 * @return
	 */
	public int validateDiagonals(int cOcurrences, final String[] dnaBits) {
		int i = 1;
		int limitArray = dnaBits.length - 1;
		int lRows = dnaBits.length - NUM_OCCURRENCES;
		cOcurrences += countOccurrences(getMainDiagonal(dnaBits, 0, 0, 1, 1));
		
		cOcurrences += countOccurrences(getMainDiagonal(dnaBits, 0, limitArray, 1, -1));
		while(cOcurrences < MININUM_SEQUENCE && i <= lRows) {
			cOcurrences += countOccurrences(getMainDiagonal(dnaBits, 0, i, 1, 1));
			cOcurrences += countOccurrences(getMainDiagonal(dnaBits, i, 0, 1, 1));
			
			cOcurrences += countOccurrences(getMainDiagonal(dnaBits, 0, limitArray - i, 1, -1));
			cOcurrences += countOccurrences(getMainDiagonal(dnaBits, i, limitArray, 1, -1));
			i++;
		}
		return cOcurrences;
	}

	/**
	 * Obtiene la diagonal principal que inicia desde la posición superior izquierda hasta la posición inferior derecha
	 * @param dnaBits
	 * @return
	 */
	private String getMainDiagonal(final String[] dnaBits, int x, int y, int i, int j) {
		String diagonal = "";
		try {
			do {
				diagonal += dnaBits[x].charAt(y);
				x+=i; y+=j;
			} while (true);
		} catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) { }
		return diagonal;
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
		return Stream.of(dnaBits).map(rDna -> countOccurrences(rDna)).reduce(0, (num1, num2) -> num1 + num2);
	}
	
	private int countOccurrences(String rDna) {
		return StringUtils.countOccurrencesOf(rDna, BASE_SEQUENCE);
	} 
}
