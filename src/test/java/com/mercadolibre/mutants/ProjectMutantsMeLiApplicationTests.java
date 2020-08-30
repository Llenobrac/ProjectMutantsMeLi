package com.mercadolibre.mutants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.mercadolibre.mutants.model.dto.StatsDto;
import com.mercadolibre.mutants.service.IMutantService;

@SpringBootTest
class ProjectMutantsMeLiApplicationTests {

	@Autowired
	private IMutantService iMutantService;

	@Test
	void selectStatsEmpty() {
		StatsDto stats = iMutantService.selectStats();
		assertNotNull(stats);
		;
	}

	@Test
	void dnaInvalidByMinimunSize() {
		String[] dna = new String[] { "ATG", "CGG", "TTG" };
		boolean result = iMutantService.isMutant(dna);
		assertFalse(result);
	}

	@Test
	void dnaInvalidBySize() {
		String[] dna = new String[] { "ATGCGAT", "CAGTGCT", "TTATGTT", "AGAAGGT", "CCCCTAT", "TCACTGT" };
		boolean result = iMutantService.isMutant(dna);
		assertFalse(result);
	}

	@Test
	void dnaInvalidByCharacters() {
		String[] dna = new String[] { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "AGAAGG", "AGAOGG" };
		boolean result = iMutantService.isMutant(dna);
		assertFalse(result);
	}

	@Test
	void dnaValidButNoMutant() {
		String[] dna = new String[] { "ATGCAA", "CAGTGC", "TTATGT", "AGAAGG", "CTCCTA", "TCACTG" };
		boolean result = iMutantService.isMutant(dna);
		assertFalse(result);
	}

	@Test
	void dnaMutant() {
		String[] dna = new String[] {"AAAATTTT", "ATGCAATC", "CAGTGCTC", "TTATGTTC", "AGAAGGTC", "CTCCTATC", "TCACTGTC", "AAAATTTC" };
		boolean result = iMutantService.isMutant(dna);
		assertTrue(result);
	}

	@Test
	void givenDnaMutantValidated() throws ClientProtocolException, IOException {

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/mutant/");
		String[] dna = new String[] { "ATGCAAT", "CAGTGCT", "TTATGTT", "AGAAGGT", "CTCCTAT", "TCACTGT", "AAAATTT" };
		String json = new Gson().toJson(dna);

		httpPost.setEntity(new StringEntity("{\"dna\":" + json + "}", ContentType.APPLICATION_JSON));
		CloseableHttpResponse response = client.execute(httpPost);
		assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
		client.close();
	}

	@Test
	void dnaNull() {
		boolean result = iMutantService.isMutant(null);
		assertFalse(result);
	}

	@Test
	void selectStats() {
		StatsDto stats = iMutantService.selectStats();
		assertNotNull(stats);
		;
	}

}
