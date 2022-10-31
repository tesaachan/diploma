package navigate

import zio._
import navigate.domain._

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
}

class PathBuilder(floor: Floor) {
  type PathNode = ServiceNode with LiftNode with StairsNode

  def pathBetween(from: Node, to: Node): ZIO[Any, String, (List[Edge], Int)] = {
    (from, to) match {
      case (ServiceNode(id1, _, _), ServiceNode(id2, _, _)) =>
        val (id1str, id2str) = (id1.toString, id2.toString)
        if (id1str == id2str) // the same node
          ZIO.succeed((Nil, 0))
        else if (id1str.take(4) == id2str.take(4)) // the same floor
          pathSameFloor(from ,to)
        else if (id1str.take(2) == id2str.take(2)) for { // the same building
          liftPaths   <- ZIO.fromEither(floor.graph.shortestPathFrom(floor.NodeStore(1008025)))
          stairsPaths <- ZIO.fromEither(floor.graph.shortestPathFrom(floor.NodeStore(1008027)))
          result      <- pathDiffFloorsSameBuilding(liftPaths, stairsPaths)(from, to)
        } yield result
        else // different buildings
          ZIO.succeed((Nil, 0))
      case _ => ZIO.fail("wrong node types")
    }
  }

  private def pathSameFloor(from: Node, to: Node): ZIO[Any, String, (List[Edge], Int)] = ZIO.fromEither {
    // direct request to this floor graph
    // but now just shortest path finding
    for {
      pathsFromF   <- floor.graph.shortestPathFrom(from)
      pathFromFtoT <- pathsFromF.to(to)
    } yield pathFromFtoT
  }


  private def pathDiffFloorsSameBuilding
  (liftPaths: ShortestPathResult, stairsPaths: ShortestPathResult)(from: Node, to: Node)
  : ZIO[Any, String, (List[Edge], Int)] = ZIO.fromEither {
    for {
      pathFromFtoLift   <- liftPaths.to(from)
      pathFromLiftToT   <- liftPaths.to(to)
      pathFromFtoStairs <- stairsPaths.to(from)
      pathFromStairsToT <- stairsPaths.to(to)
    } yield {
      // logic for floors difference
      // ...
      val liftPathLength   = pathFromFtoLift._2 + pathFromLiftToT._2
      val stairsPathLength = pathFromFtoStairs._2 + pathFromStairsToT._2
      if (liftPathLength <= stairsPathLength)
        (pathFromFtoLift._1 ++ pathFromLiftToT._1, liftPathLength)
      else
        (pathFromFtoStairs._1 ++ pathFromStairsToT._1, stairsPathLength)
    }
  }

  // private def pathDiffBuildings: ZIO[Any, String, (List[Edge], Int)] = ???

}

object Routines {
  import MainImplicits._

  def misisBRoutine(): Unit = {
    implicit def IntToNode(value: Int): Node = MisisB.NodeStore(value)

    MisisB.graph.adj.foreach(x => println(x.richToString + "\n"))
    //val begin = Clock.currentTime(TimeUnit.MILLISECONDS)
    //val begin = System.currentTimeMillis()
    for {
      x <- MisisB.graph shortestPathFrom 1
      y <- x to 22
    } {
      //val end = System.currentTimeMillis()
      //val end = System.currentTimeMillis()
      println(y._1.richToString + "\nwith distance " + y._2 + '\n')
      //println(s"${end - begin} ms")
    }
  }

  def sameFloorPathRoutine(floor: Floor): ZIO[Any, String, (List[Edge], Int)] = {
    implicit def IntToNode(value: Int): Node = floor.NodeStore(value)

    //val keys = MisisB8thFloor.graph.adj.keys
    //val numOfEdges = keys.foldLeft(0)(_ + MisisB8thFloor.graph.adj(_).size)
    //println(numOfEdges)
    //
    //val privilegedMode = false
    //val pathsFrom4 = List(1008025,1008027,1008028,1008026).map(MisisB8thFloor.graph.shortestPathFrom(_))

    val b8paths = new PathBuilder(MisisB8thFloor)
    b8paths.pathBetween(1008001, 1008004)
  }
}

object Main extends ZIOAppDefault {
  import MainImplicits._

  def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = for {
    path <- Routines.sameFloorPathRoutine(MisisB8thFloor)
    _    <- Console.printLine(path._1.richToString)
    _    <- Console.printLine(path._2)
  } yield ()
}