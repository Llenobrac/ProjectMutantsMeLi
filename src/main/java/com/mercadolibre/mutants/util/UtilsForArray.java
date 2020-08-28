package com.mercadolibre.mutants.util;

import org.springframework.stereotype.Component;

import com.mercadolibre.mutants.constant.LetterEnum;

@Component
public class UtilsForArray {

	public String[][] convertToMatrix(String[] dna, LetterEnum le) {
		int i = 0;
		int n = dna.length;
		String[][] matrix = new String[n][n];
		for (String rowDNA : dna) {
			String[] nRow = replaceRow(rowDNA, le);
			matrix[i++] = nRow;
		}
		return matrix;
	}
	
	private String[] replaceRow(String row, LetterEnum le) {
		String nRow = row;
		for (LetterEnum lAux : LetterEnum.values()) {
			nRow = nRow.replace(lAux.name(), lAux.equals(le) ? "1" : "0" );
		}
		return nRow.split("");
	}
}
