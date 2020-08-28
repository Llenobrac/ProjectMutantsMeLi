package com.mercadolibre.mutants.constant;

import java.util.stream.Stream;

public enum LetterEnum {
	A, T, G, C;

	public static String[] toStringArray() {
		return Stream.of(LetterEnum.values()).map(LetterEnum::name).toArray(String[]::new);
	}
}
