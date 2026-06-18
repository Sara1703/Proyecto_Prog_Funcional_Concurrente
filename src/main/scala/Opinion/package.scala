import Comete._

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

}