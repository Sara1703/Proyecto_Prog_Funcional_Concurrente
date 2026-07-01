PROYECTO FINAL: SIMULADOR DE POLARIZACIÓN EN REDES

ASIGNATURA: Fundamentos de Programación Funcional y Concurrente

UNIVERSIDAD DEL VALLE

INTEGRANTES:

Samuel Saldaña Giraldo
Sara Julissa Hernández
Miguel Ángel Martinez
Ronald David Meneses

// DESCRIPCIÓN DE LOS ARCHIVOS ENTREGADOS//---------------------------------------------------------------------------------------------------------

1. Package.scala de "comete":

-- Contiene la lógica matematica de algunas funciones secuenciales que son para la medición dela polarización
en una distribución de frecuencias fijas.
-- Se definen los tipos importantes: DistributionValues, Frequency, y MedidaPol siendo para las funciones de polarización.

-- Contiene la función min_p que implementa un algoritmo de busqueda recursiva pura para encontrar
el punto medio óptimo p que minimiza la distancia de opiniones, garantizando una complejidad de 0(log n).

-- La función rhoCMT_Gen construye y retorna clausuras matemáticas que evalúan la sumatoria del sesgo
de polarización con los parámetros alpha y beta.

-- La función normalizar calcula la maxima polarización binaria teórica modularizando y acotando
los resultados del simulador en un rango [0.0, 1.0]


2. Package.scala de "Opinion":

-- Contiene las demás funciones secuenciales y paralelas.

-- Las funciones rho y rhoPar calculan la distribución de frecuencias de los agentes sobre los límites de los intervalos
(umbrales Likert), y rhoPar optimiza este proceso dividiendo vectorialmente el espacio de busqueda a la mitad
por medio de common.parallel, distribuyendo balanceadamente la carga de conteo en paralelo.

-- La función showWeightedGraph toma una abstracción del grafo conceptual SpecificWeightedGraph
y de forma inmutable materializa su estrcutura relacional latente en una matriz bidimensional densa
combinando vectores tabulados (Vector.tabulate), permitiendo inspeccionar y operar
gráficamente con los pesos fijos de influencia entre todos los agentes de la red.

-- La función confBiasUpdate: Implemente funcionalmente la matriz de actualización secuencial de opiniones aplicando
beta (sesgo) y la matriz de influencia transpuesta de la red.

-- La función paralela confBiasUpdatePar optimiza con paralelismo de datos, utilizando
colecciones paralelas como .par coordinadas sobre los rangos reales de creencias (sb.lenght),
permitiendo que el cálculo de las sumatorias se ejecute concurrentemente en multiples núcleos sin 
riesgo de condiciones de carrera ni excepciones por limites de indices.

-- La función simulate construye la trayectoria temporal de la red social de forma puramente 
funcional. Genera una secuencia de opiniones consecutivas a través de una función de 
actualización inyectada.


//INSTRUCCIONES DE EJECUCIÓN PARA EL ENTORNO VSC// ----------------------------------------------------------------------------------------------------------------

*Paso 1: Crear un archivo llamado Pruebas.worsheet.sc

*Paso 2: Copiar el siguiente codigo y se veran los resultados en la misma linea de codigo:

import Opinion ._
import Benchmark ._
import Comete ._

common.forkJoinPool

val sb_ext=allExtremeBelief(100)
val sb_cons = consensusBelief(0.2)(100)
val sb_unif = uniformBelief(100)
val sb_triple = allTripleBelief(100)
val sb_midly = midlyBelief(100)


val rho1= rho(1.2 , 1.2)
val rho2 = rho(2.0, 1.0)

val dist1 = Vector(0.0, 0.25, 0.50, 0.75, 1.0)
val dist2 = Vector(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)

rho1(sb_ext,dist1)
rho2(sb_ext,dist1)
rho1(sb_ext,dist2)
rho2(sb_ext,dist2)

rho1(sb_cons,dist1)
rho2(sb_cons,dist1)
rho1(sb_cons,dist2)
rho2(sb_cons,dist2)

rho1(sb_unif,dist1)
rho2(sb_unif,dist1)
rho1(sb_unif,dist2)
rho2(sb_unif,dist2)

rho1(sb_triple,dist1)
rho2(sb_triple,dist1)
rho1(sb_triple,dist2)
rho2(sb_triple,dist2)

rho1(sb_midly,dist1)
rho2(sb_midly,dist1)
rho1(sb_midly,dist2)
rho2(sb_midly,dist2)

//Definición de vectores
val pi_max = Vector ( 0.5 , 0.0  , 0.0  , 0.0 , 0.5 )
val pi_min =Vector ( 0.0 , 0.0  , 1.0 , 0.0  , 0.0 )
val pi_der = Vector ( 0.4 , 0.0  , 0.0 , 0.0  , 0.6 )
val pi_izq = Vector ( 0.6 , 0.0  , 0.0 , 0.0  , 0.4 )
val pi_int1 = Vector ( 0.0 , 0.5 , 0.0 , 0.5 , 0.0 )
val pi_int2 = Vector ( 0.25 , 0.0  , 0.5 , 0.0 , 0.25 )
val pi_int3 = Vector ( 0.25 , 0.25 , 0.0 , 0.25 , 0.25 )
val pi_cons_centro = pi_min
val pi_cons_der = Vector (0.0 , 0.0  , 0.0  ,0.0 , 1.0 )
val pi_cons_izq = Vector ( 1.0 , 0.0  , 0.0  , 0.0  , 0.0 )
val likert5 = Vector ( 0.0 , 0.25 , 0.5 , 0.75 , 1.0 )

//Pruebas rhoCMT_Gen
val cmt1 = rhoCMT_Gen( 1.2 , 1.2 )
cmt1( pi_max , likert5 )
cmt1 ( pi_min , likert5  )
cmt1 ( pi_der , likert5 )
cmt1 ( pi_izq , likert5  )
cmt1 ( pi_int1 , likert5  )
cmt1 ( pi_int2 , likert5  )
cmt1 ( pi_int3 , likert5  )
cmt1 ( pi_cons_centro , likert5  )
cmt1 ( pi_cons_der , likert5  )
cmt1 ( pi_cons_izq , likert5  )

//Pruebas normalizar
val cmt1_norm = normalizar( cmt1 )
cmt1_norm ( pi_max , likert5 )
cmt1_norm ( pi_min , likert5  )
cmt1_norm ( pi_der , likert5 )
cmt1_norm ( pi_izq , likert5 )
cmt1_norm ( pi_int1 , likert5 )
cmt1_norm ( pi_int2 , likert5 )
cmt1_norm ( pi_int3 , likert5 )
cmt1_norm ( pi_cons_centro , likert5 )
cmt1_norm ( pi_cons_der , likert5 )
cmt1_norm ( pi_cons_izq , likert5 )


//pruebas confBiasUpdate

val i1_10=i1 (10)
val i2_10=i2 (10)
val i1_20=i1 (20)
val i2_20=i2 (20)

val sbu_10 = uniformBelief(10)
val sbm_10 = midlyBelief(10)
confBiasUpdate(sbu_10, i1_10)
rho1(sbu_10,dist1)
confBiasUpdate(sbm_10, i1_10)

val  sbms = for {
  n <-2 until 16
  nags = math.pow(2,n).toInt}
yield  midlyBelief(nags)
val  polSec = rho(1.2,1.2)
val  polPar = rhoPar(1.2,1.2)
val  cmp1 = compararMedidasPol(sbms,likert5,polSec,polPar)
println(cmp1)


val i1_32768 = i1 (32768)
val i2_32768 = i2 (32768)

compararFuncionesAct(sbms.take(sbms.length/2), i2_32768, confBiasUpdate, confBiasUpdatePar)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//INSTRUCCIONES DE IMPORTACIÓN Y EJECUCIÓN PARA EL ENTORNO INTELLIJIDEA// ----------------------------------------------------------------------------------------------------------------

EJECUCIÓN POR TERMINAL SBT:

*Paso 1 (inicializar interfaz): Ubicarse en el archivo build.sbt y escribir el comando sbt

*Paso 2 (Limpieza y compilación de módulos): 
clean
compile

*Paso 3 (Ejecutar suite completo de simulaciones y Benchmark usando):
run 

