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

  // Build uniform belief state.
  def uniformBelief(nags: Int): SpecificBelief = {
    Vector.tabulate(nags)((i: Int) => (i + 1).toDouble / nags.toDouble)
  }

  // Builds mildly polarized belief state
  def midlyBelief(nags: Int): SpecificBelief = {
    val middle = nags / 2
    Vector.tabulate(nags)((i: Int) =>
      if (i < middle) math.max(0.25 - 0.01 * (middle - i - 1), 0)
      else math.min(0.75 - 0.01 * (middle - i), 1))
  }

  // Builds extreme polarized belief state
  def allExtremeBelief(nags: Int): SpecificBelief = {
    val middle = nags / 2
    Vector.tabulate(nags)((i: Int) =>
      if (i < middle) 0.0 else 1.0)
  }

  // Builds three-pole belief state
  def allTripleBelief(nags: Int): SpecificBelief = {
    val oneThird = nags / 3
    val twoThird = (nags / 3) * 2
    Vector.tabulate(nags)((i: Int) =>
      if (i < oneThird) 0.0
      else if (i >= twoThird) 1.0
      else 0.5)
  }

  // Builds consensus belief state
  def consensusBelief(b: Double)(nags: Int): SpecificBelief = {
    Vector.tabulate(nags)((i: Int) => b)
  }

}