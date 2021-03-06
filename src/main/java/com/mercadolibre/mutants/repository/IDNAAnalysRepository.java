package com.mercadolibre.mutants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mercadolibre.mutants.model.dto.StatsDto;
import com.mercadolibre.mutants.model.entity.DNAAnalysis;

@Repository
public interface IDNAAnalysRepository extends JpaRepository<DNAAnalysis, Long> {
	@Query("select da from DNAAnalysis da where da.dna = :strDNA ")
	DNAAnalysis findByDNA(@Param("strDNA") String strDNA);

	@Query("select new com.mercadolibre.mutants.model.dto.StatsDto(coalesce(sum(case da.mutant when true then 1 else 0 end), 0), coalesce(sum(case da.mutant when true then 0 else 1 end), 0) ) from DNAAnalysis da")
	StatsDto selectStats();
}
