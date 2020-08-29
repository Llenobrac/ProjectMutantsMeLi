package com.mercadolibre.mutants.util;

import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class UtilsForArray {

	public String[] rotateArray(String[] dnaBits) {
		String[] columnsToRow = changeColumnsToRow(dnaBits);
		return Stream.of(columnsToRow).map(ctr -> new StringBuilder(ctr).reverse().toString()).toArray(String[]::new);
	}
	
	public String[] changeColumnsToRow(final String[] dnaBits) {
		final int sizeArray = dnaBits.length;
		String[] rDnaBits = new String[sizeArray];
		for (int i = 0; i < sizeArray; i++) {
			int[] index = new int[] { i };
			rDnaBits[i] = String.join("", Stream.of(dnaBits).map(dnaR -> String.valueOf(dnaR.charAt(index[0]))).toArray(String[]::new));
		}
		return rDnaBits;
	}

	public String[] getDiagonalRows(final String[] dnaBits, final int lengthDNA) {
		int j = 0;
		int lRows = dnaBits.length - lengthDNA;
		String[] cDnaRows = new String[(lRows * 2) + 1];
		cDnaRows[j++] = getMainDiagonal(dnaBits);
		for (int i = 1; i <= lRows; i++) {
			cDnaRows[j++] = getUpperDiaonal(dnaBits, i);
			cDnaRows[j++] = getLowerDiaonal(dnaBits, i);
		}
		return cDnaRows;
	}

	private String getLowerDiaonal(final String[] cDnaRows, final int i) {
		String strNewDNA = String.join("", cDnaRows).substring(i * cDnaRows.length);
		return getMainDiagonal(strNewDNA.split("(?<=\\G.{" + cDnaRows.length + "})"));
	}

	private String getUpperDiaonal(final String[] dnaBits, final int initialPosition) {
		String[] reducedColumns = Stream.of(dnaBits).map(r -> r.substring(initialPosition)).toArray(String[]::new);
		return getMainDiagonal(reducedColumns);
	}

	public String getMainDiagonal(final String[] dnaBits) {

		return String.join("", Stream.of(dnaBits).map(new Function<String, String>() {
			private int i = 0;

			@Override
			public String apply(String nR) {
				if(i < nR.length()) {
					return String.valueOf(nR.charAt(i++));
				}else {
					return "";
				}
			}
		}).toArray(String[]::new));
	}

}
