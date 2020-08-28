package com.mercadolibre.mutants.util;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.mercadolibre.mutants.constant.LetterEnum;

@Component
public class UtilsForArray {

	public String[][] convertToMatrix(String[] dna, LetterEnum le) {
		int i = 0;
		int n = dna.length;
		String[][] matrix = new String[n][n];
		for (String rowDNA : dna) {
			String[] nRow = rowDNA.replaceAll(le.name(), "1").replaceAll("[^1]", "0").split(""); 
			matrix[i++] = nRow;
		}
		return matrix;
	}

	public String[] rotateArray(String[] dnaBits) {
		final int sizeArray = dnaBits.length;
		String[] rDnaBits = new String[sizeArray];
		for (int i = 0; i < sizeArray; i++) {
			int[] index = new int[]{i};
			rDnaBits[i] = String.join("", Stream.of(dnaBits).map(dnaR -> dnaR.charAt(index[0])).toArray(String[]::new));
		}
		return rDnaBits;
	}
}
