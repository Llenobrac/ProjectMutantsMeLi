package com.mercadolibre.mutants.helper;

import com.mercadolibre.mutants.constant.LetterEnum;

public interface IValidationHelper {
	/**
	 * Valida el tamaño/dimensiones y los caracteres dentro del array
	 * @param dna
	 * @return
	 */
	boolean validateInput(String[] dna);

	/**
	 * Validar si las sencuencias 4 es mayor a 1 al recorrer filas, columnas, diagonales y diagonales invertidas 
	 * Por cada una de las letras en {@link LetterEnum}
	 * @param dna
	 * @return
	 * 	true: Es Mutante <br>
	 * 	false: Es Humano
	 */
	boolean validateMutantDNA(String[] dna);
	
}
