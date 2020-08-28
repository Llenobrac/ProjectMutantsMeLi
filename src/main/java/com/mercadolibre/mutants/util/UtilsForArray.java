package com.mercadolibre.mutants.util;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class UtilsForArray {

	public String[] rotateArray(String[] dnaBits) {
		final int sizeArray = dnaBits.length;
		String[] rDnaBits = new String[sizeArray];
		for (int i = 0; i < sizeArray; i++) {
			int[] index = new int[]{i};
			rDnaBits[i] = String.join("", Stream.of(dnaBits).map(dnaR -> String.valueOf(dnaR.charAt(index[0]))).toArray(String[]::new));
		}
		return rDnaBits;
	}

	public String[] getCrossedRows(String[] dnaBits) {
		// TODO Auto-generated method stub
		return null;
	}
}
