package  object Comete {
    type DistributionValues = Vector[Double]
    type Frequency = Vector[Double]
    type Distribution = (Frequency, DistributionValues)
    type MedidaPol = Distribution => Double

    def min_p(f:Double=>Double, min:Double, max:Double, prec:Double): Double = {

        val p1 = min + (max - min) /3
        val p2 = max - (max - min) /3

        if ((max - min) < prec){
            
        (min + max)/ 2

        }
        else {
          if (f(p1) > f(p2)) {

          min_p(f,p1,max,prec)
          }
          else{
          min_p(f,min,p2,prec)
          }
        }
    }

    def rhoCMT_Gen(alpha:Double, beta:Double): MedidaPol = {

      //función auxiliar para calcular ρ_aux(p)
      def rhoAux( p:Double, dist:Distribution): Double = {
        val (pi,y) = dist
        (y zip pi).map
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

    def normalizar(m:MedidaPol): MedidaPol= {
      //se define el peor caso
      val pi_PeorCaso = Vector(0.5,0.0,0.0,0.0,0.5)
      val y = Vector(0.0,0.25,0.5,0.75,1.0)
      val PeorCaso = m(pi_PeorCaso,y)

      // Se retorna la función MedidaPol que divide por el peor caso
      (dist:Distribution) => { m(dist) / PeorCaso }
    }
}

