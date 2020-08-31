package com.mercadolibre.mutants.util;

import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class UtilsForArray {

	/**
	 * 1. Convierte las columnas en filas
	 * 2. Cada una de las filas invierte el sentido como un espejo
	 * @param dnaBits
	 * @return
	 */
	public String[] rotateArray(String[] dnaBits) {
		String[] columnsToRow = changeColumnsToRow(dnaBits);
		return Stream.of(columnsToRow).map(ctr -> new StringBuilder(ctr).reverse().toString()).toArray(String[]::new);
	}
	
	/**
	 * Obtiene las columnas de dnaBits para asignarlas en un nuevo array 
	 * @param dnaBits
	 * @return
	 */
	public String[] changeColumnsToRow(final String[] dnaBits) {
		final int sizeArray = dnaBits.length;
		String[] rDnaBits = new String[sizeArray];
		for (int i = 0; i < sizeArray; i++) {
			int[] index = new int[] { i };
			rDnaBits[i] = String.join("", Stream.of(dnaBits).map(dnaR -> String.valueOf(dnaR.charAt(index[0]))).toArray(String[]::new));
		}
		return rDnaBits;
	}

	/**
	 * Obtiene las diagonales del array
	 * 1. Asigna la diagonal principal que inicia en la posición (0,0) y termina en la posición (n-1,n-1)
	 * 2. Crea un nuevo array con la cantidad de diagonales validas 
	 * 3. Obtiene las diagonales superiores e inferiores en cada recorrido 
	 * @param dnaBits
	 * @param lengthDNA
	 * @return
	 */
	public String[] getDiagonalRows(final String[] dnaBits, final int lengthDNA) {
		int j = 0;
		int lRows = dnaBits.length - lengthDNA;
		String[] cDnaRows = new String[(lRows * 2) + 1];
		cDnaRows[j++] = getMainDiagonal(dnaBits);
		for (int i = 1; i <= lRows; i++) {
			cDnaRows[j++] = getUpperDiagonal(dnaBits, i);
			cDnaRows[j++] = getLowerDiagonal(dnaBits, i);
		}
		return cDnaRows;
	}

	/**
	 * Remueve la cadena de caracteres por substring, para reducir la matriz de arriba hacia abajo
	 * @param cDnaRows
	 * @param i
	 * @return
	 */
	private String getLowerDiagonal(final String[] cDnaRows, final int i) {
		String strNewDNA = String.join("", cDnaRows).substring(i * cDnaRows.length);
		return getMainDiagonal(strNewDNA.split("(?<=\\G.{" + cDnaRows.length + "})"));
	}

	/**
	 * Remueve los caracteres de izquierda a derecha en cada una de las posiciones del Array para reducir la matriz
	 * @param dnaBits
	 * @param initialPosition
	 * @return
	 */
	private String getUpperDiagonal(final String[] dnaBits, final int initialPosition) {
		String[] reducedColumns = Stream.of(dnaBits).map(r -> r.substring(initialPosition)).toArray(String[]::new);
		return getMainDiagonal(reducedColumns);
	}

	/**
	 * Obtiene la diagonal principal que inicial desde la posición superior izquierda hasta la posición inferior derecha
	 * @param dnaBits
	 * @return
	 */
	private String getMainDiagonal(final String[] dnaBits) {
		return String.join("", Stream.of(dnaBits).map(new Function<String, String>() {
			private int i = 0;

			@Override
			public String apply(String nR) {
				return i < nR.length() ? String.valueOf(nR.charAt(i++)) : "";
			}
		}).toArray(String[]::new));
	}

}
