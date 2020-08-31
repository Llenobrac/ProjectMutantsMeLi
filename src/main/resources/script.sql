-- Drop table

-- DROP TABLE public.dna_analysis;

CREATE TABLE public.dna_analysis (
	id serial NOT NULL,
	dna varchar NOT NULL,
	mutant bool NOT NULL DEFAULT false,
	CONSTRAINT dna_analysis_dna_unique UNIQUE (dna),
	CONSTRAINT dna_analysis_pk PRIMARY KEY (id)
);
CREATE INDEX dna_analysis_dna_idx ON public.dna_analysis USING btree (dna);
