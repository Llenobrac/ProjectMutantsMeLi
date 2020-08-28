package com.mercadolibre.mutants.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.mutants.model.dto.RequestDNA;
import com.mercadolibre.mutants.service.IMutantService;


@RestController
public class MutantRestController {
	
	@Autowired
	private IMutantService iMutantService;
	
	@PostMapping("/mutant/")
	public ResponseEntity mutant(@RequestBody RequestDNA req) {
		if(iMutantService.isMutant(req.getDna())) {
			return new ResponseEntity(HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping("/stats")
	@ResponseBody
	public String stats() {
		return "";
	}
}
