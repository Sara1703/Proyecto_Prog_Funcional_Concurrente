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
        if (f(p1) > f(p2)) {

            min_p(f,p1,max,prec)

        }else{
            min_p(f,min,p2,prec)
        }
        
    }

}

