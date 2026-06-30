import Comete._
import scala.collection.parallel.CollectionConverters._

package object Opinion {

  type SpecificBelief = Vector[Double]
  type GenericBeliefConf = Int => SpecificBelief
  type AgentsPolMeasure = (SpecificBelief, DistributionValues) => Double

  type WeightedGraph = (Int, Int) => Double
  type SpecificWeightedGraph = (WeightedGraph, Int)
  type GenericWeightedGraph = Int => SpecificWeightedGraph
  type FunctionUpdate = (SpecificBelief, SpecificWeightedGraph) => SpecificBelief

  def rho(alpha: Double, beta: Double): AgentsPolMeasure = {
    val medida = normalizar(rhoCMT_Gen(alpha, beta))
    (sb: SpecificBelief, dist: DistributionValues) => {
      val k = dist.length
      val n = sb.length


      val lims = Vector.tabulate(k) { i =>
        if (i == 0) 0.0
        else (dist(i - 1) + dist(i)) / 2.0
      }


      val freq: Frequency = Vector.tabulate(k) { i =>
        val lo = lims(i)
        val hi = if (i == k - 1) 1.0 + 1e-9 else lims(i + 1)
        sb.count(b => b >= lo && b < hi).toDouble / n.toDouble
      }

      medida((freq, dist))
    }
  }

  def showWeightedGraph(swg: SpecificWeightedGraph): IndexedSeq[IndexedSeq[Double]] = {
    val (wg, n) = swg
    Vector.tabulate(n) { i =>
      Vector.tabulate(n) { j =>
        wg(i, j)
      }
    }
  }

  def confBiasUpdate(sb: SpecificBelief, swg: SpecificWeightedGraph): SpecificBelief = {
    val (wg, n) = swg
    Vector.tabulate(n) { i =>
      val effectiveWeights = Vector.tabulate(n) { j =>
        wg(i, j) * (1.0 - math.abs(sb(i) - sb(j)))
      }
      val totalWeight = effectiveWeights.sum
      if (totalWeight == 0.0) sb(i)
      else
        effectiveWeights.zipWithIndex
          .map { case (w, j) => w * sb(j) }
          .sum / totalWeight
    }
  }
  def  simulate(fu: FunctionUpdate,
                swg: SpecificWeightedGraph,
                b0: SpecificBelief,
                t:Int):
  IndexedSeq[SpecificBelief] ={
    (1 to t).scanLeft(b0){(concurrentBelief,_) => fu(concurrentBelief,swg)}

  }


  //Versiones Paralelas

  def rhoPar(alpha: Double, beta: Double): AgentsPolMeasure ={

    val medida = normalizar(rhoCMT_Gen(alpha, beta))
    (sb: SpecificBelief, dist: DistributionValues) => {
      val k = dist.length
      val n = sb.length

      val lims = Vector.tabulate(k) { i =>
        if (i == 0) 0.0
        else (dist(i - 1) + dist(i)) / 2.0
      }

      val indiceI = (0 until k).toVector

      def calcularFrecuenciaGrupo(indices: Vector[Int]): Vector[Double] = indices.map {{ i =>
        val lo = lims(i)
        val hi = if (i == k - 1) 1.0 + 1e-9 else lims(i + 1)
        sb.count(b => b >= lo && b < hi).toDouble / n.toDouble
      }
      }

      val mitad = k/2

      val(indicesP1, indicesP2) = indiceI.splitAt(mitad)

      val(mitad1, mitad2) =common.parallel(
        calcularFrecuenciaGrupo(indicesP1),
        calcularFrecuenciaGrupo(indicesP2)
      )

      val freqParallel: Frequency = mitad1 ++ mitad2

      medida((freqParallel, dist))
    }
  }

  def confBiasUpdatePar(b:SpecificBelief, swg: SpecificWeightedGraph): SpecificBelief = {
    val (wg, n) = swg

    //cada agente i calcula su nueva creencia en paralelo
    (0 until n).par.map { i =>

      // los pesos de cada j se calculan en paralelo
      val effectiveWeights = (0 until n).par.map { j =>
        wg(i, j) * (1.0 - math.abs(b(i) - b(j)))
      }.toVector

      val totalWeight = effectiveWeights.sum
      if (totalWeight == 0.0) b(i)
      else
        effectiveWeights.zipWithIndex
          .map { case (w, j) => w * b(j) }
          .sum / totalWeight

    }.toVector
  }
}