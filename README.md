# Bienvenido a ProjectMutantsMeLi!

Este proyecto se encarga de validar una secuencia de ADN para identificar si es mutante o no, los seleccionados podrás unirse a equipo de Magneto para luchar con los X-Men 


# Descargar Proyecto

    git clone https://github.com/Llenobrac/ProjectMutantsMeLi.git

## Casos de Prueba

 - DNA no valido por tamaño mínimo < 4
 - DNA no valido por dimensiones NxM
 - DNA no valido por letra no permitida (Solo se permiten los DNA compuestos de A, T, G, C)
 - DNA  valido pero no cumple con mínimo 2 secuencias de 4 letras iguales
 - DNA valido y cumple con el mínimo de secuencias de 4 letras iguales ... **Mutante!!**
	- Por columnas
	- Con Diagonal Invertida
	- Con Diagonal
	- Mezclado

### Ejecutar test
    mvn clean test
El reporte del Code Coverage se encuentra en la ruta:

> ./target/site/jacoco/index.html

![CodeCoverage con JaCoCo](https://i.imgur.com/rpxyEaJ.png)

> #### Nota

Para que se completen todos los casos en local, debe estar el proyecto arriba.

## Ejecutar proyecto

    mvn spring-boot:run

### Verificación servicio /mutant/ 
Desde cualquier cliente rest (Postman, Insomnia, ...) 

`[post]: http://localhost:8080/mutant/`

#### body:
    {
    "dna": ["GCGCCAT", "CGCGACT", "GCGATGT", "GCATGCT","GTTTACT","CTCCGGT"]
    }

 - Si el status code es 403, No es Mutante.
 - Si el status code es 200, Es Mutante.

![Ejemplo desde postman](https://i.imgur.com/N3pX8uJ.png)

### Verificación servicio /stats/ 
`[get]: http://localhost:8080/stats/`
Muestra la cantidad de Mutantes y Humanos validados

![Stats](https://i.imgur.com/r5bJk5o.png)
