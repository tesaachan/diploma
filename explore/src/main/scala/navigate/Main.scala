package navigate

object Main extends App {

  implicit class EnrichNodeToString(elem: (Node, List[Edge])) {
    def richToString: String = {
      val prettyEdges = elem._2.map {
        case Edge(_, y, z) => s"\n  $y costs ${z.value}"
      }
      s"${elem._1} -> {${prettyEdges.mkString(",")}\n}"
    }
  }

  implicit class EnrichShortestPathToString(elem: List[Edge]) {
    def richToString: String = elem match {
      case (x :: Nil) => 
        x.from + "\n -> " + x.to
      case (x :: xs) => 
        x.from + "\n -> " + EnrichShortestPathToString(xs).richToString
    }
  }

  implicit def IntToNode(value: Int) = MisisB.NodeStore(value)

  MisisB.graph.adj.foreach(x => println(x.richToString + "\n"))
  val begin = System.currentTimeMillis()
  for {
    x <- MisisB.graph shortestPathFrom 1
    y <- x to 22
  } {
    val end = System.currentTimeMillis()
    println(y._1.richToString + "\nwith distance " + y._2 + '\n')
    println(s"${end - begin} ms")
  }

}