import Opinion ._
import Benchmark ._
import Comete ._

common.forkJoinPool

val sb_ext=allExtremeBelief(100)
val sb_midly = midlyBelief(100)

val rho1= rho(1.2 , 1.2)
val rho2 = rho(2.0, 1.0)

val dist1 = Vector(0.0, 0.25, 0.50, 0.75, 1.0)
val dist2 = Vector(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)

rho1(sb_ext,dist1)
rho1(sb_midly,dist1)
