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
el punto medioi óptimo p que minimiza la distancia de opiniones, garamtizando una complejidad de 0(log n).

-- La función rhoCMT_Gen construye y retorna clausuras matemáticas que evalúan la sumatoria del sesgo
de polarización con los parámetros alpha y beta.

-- La función normalizar caldula la maxima polarización binaria teórica modularizando y acotando
los resultados del simulador en un rango [0.0, 1.0]

------------------------------------------------------------------------------------------------------------------------------------------------------

2. Package.scala de "Opinion":

-- Contiene las demás funciones secuenciales y paralelas.

-- Las funciones rho y rhoPar calculan la distribución de frecuencias de los agentes sobre los límites de los intervalos
(umbrales Likert), y rhoPar opmitiza este proceso dividiendo vectorialmente el espacio de busqueda a la mitad
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


//INSTRUCCIONES DE EJECUCIÓN PARA EL ENTORNO INTELLIJIDEA// ----------------------------------------------------------------------------------------------------------------

*Paso 1 (inicializar interfaz): sbt

*Paso 2 (Limpieza y compilación de módulos): 
clean
compile

*Paso 3 (Ejecución pruebas comparativas de rendimiento):

run
