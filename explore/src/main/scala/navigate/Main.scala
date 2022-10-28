package navigate

import zio._
import navigate.domain.{Edge, MisisB, MisisB8thFloor, Node}

import scala.language.implicitConversions

private object MainImplicits {
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
      case x :: Nil =>
        x.from + "\n -> " + x.to
      case x :: xs =>
        x.from + "\n -> " + EnrichShortestPathToString(xs).richToString
    }
  }

  implicit def IntToNode(value: Int): Node = MisisB8thFloor.NodeStore(value)
}

object Routines {
  import MainImplicits._
  def misisBRoutine(): Unit = {
    MisisB.graph.adj.foreach(x => println(x.richToString + "\n"))
    //val begin = Clock.currentTime(TimeUnit.MILLISECONDS)
    val begin = System.currentTimeMillis()
    for {
      x <- MisisB.graph shortestPathFrom 1
      y <- x to 22
    } {
      //val end = System.currentTimeMillis()
      val end = System.currentTimeMillis()
      println(y._1.richToString + "\nwith distance " + y._2 + '\n')
      println(s"${end - begin} ms")
    }
  }

  def misisB8thFloorRoutine(): Unit = {
    val keys = MisisB8thFloor.graph.adj.keys
    val numOfEdges = keys.foldLeft(0)(_ + MisisB8thFloor.graph.adj(_).size)
    println(numOfEdges)

    val privilegedMode = false
    val pathsFrom4 = List(1008025,1008027,1008028,1008026).map(MisisB8thFloor.graph.shortestPathFrom(_))

  }
}

object Main extends App {
  Routines.misisB8thFloorRoutine()

}