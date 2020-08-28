package com.mercadolibre.mutants.controller;

public interface IMutantRestController {

	String stats();

	boolean validateDNA(String[] dna);

}
