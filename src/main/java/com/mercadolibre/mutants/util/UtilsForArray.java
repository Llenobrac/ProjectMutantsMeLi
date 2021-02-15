package com.mercadolibre.mutants.util;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.mercadolibre.mutants.constant.LetterEnum;

@Component
public class UtilsForArray {

	/**
	 * Reemplaza la letra actual por 1 y las otras en 0, luego vuelve a armar el Array
	 * @param dna
	 * @param le Letra actual recorrida
	 * @return
	 * 		Array transformado
	 */
	public String[] replaceCurrentLetterByOne(String[] dna, LetterEnum le) {
		String strDNA = String.join("", dna);
		String strDnaBits = strDNA.replaceAll(le.name(), "1").replaceAll("[^1]", "0");
		return strDnaBits.split("(?<=\\G.{" + dna.length + "})");
	}
	
	/**
	 * Obtiene las columnas de dnaBits para asignarlas en un nuevo array 
	 * @param dnaBits
	 * @return
	 */
	public String[] changeColumnsToRow(final String[] dnaBits) {
		String[] rDnaBits = new String[dnaBits.length];
		for (int i = 0; i < dnaBits.length; i++) {
			int[] index = new int[] { i };
			rDnaBits[i] = String.join("", Stream.of(dnaBits).map(dnaR -> String.valueOf(dnaR.charAt(index[0]))).toArray(String[]::new));
		}
		return rDnaBits;
	}
}
