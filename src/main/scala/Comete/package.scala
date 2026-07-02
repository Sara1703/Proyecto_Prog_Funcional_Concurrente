package object Comete {
    type DistributionValues = Vector[Double]
    type Frequency = Vector[Double]
    type Distribution = (Frequency, DistributionValues)
    type MedidaPol = Distribution => Double

    def min_p(f: Double => Double, min: Double, max: Double, prec: Double): Double = {
      if ((max - min) < prec) {
        (min + max) / 2
      } else {
        val p1 = min + (max - min) / 3
        val p2 = max - (max - min) / 3

        if (f(p1) > f(p2)) {
          min_p(f, p1, max, prec)
        } else {
          min_p(f, min, p2, prec)
        }
      }
    }

    def rhoCMT_Gen(alpha:Double, beta:Double): MedidaPol = {

      //función auxiliar para calcular ρ_aux(p)
      def rhoAux( p:Double, dist:Distribution): Double = {
        val (pi,y) = dist
        (pi zip y).map
          { case (pi_,y_) =>
            Math.pow(pi_, alpha) * Math.pow(Math.abs(y_ - p), beta)
            }.sum
      }

      // Se retorna la función MedidaPol (Distribution => Double)
      (dist: Distribution) => {
        val f = (p:Double) => rhoAux(p, dist)
        val PuntoOptimo = min_p(f, 0.0, 1.0, 0.001)
        rhoAux(PuntoOptimo, dist)
      }
    }

  def normalizar(m:MedidaPol): MedidaPol = {

    // Construye el peor caso dinámicamente según k = y.length
    def construirPeorCaso(k: Int): Distribution = {
      val y = if (k == 1) Vector(0.0)
      else Vector.tabulate(k)(i => i.toDouble / (k - 1))

      val pi = if (k == 1) Vector(1.0)
      else Vector.tabulate(k)(i => if (i == 0 || i == k - 1) 0.5 else 0.0)

      (pi, y)
    }

    // Se retorna la función MedidaPol que divide por el peor caso
    (dist: Distribution) => {
      val (_, y) = dist
      val k = y.length
      val peorCaso = construirPeorCaso(k)
      m(dist) / m(peorCaso)
    }
  }
}

