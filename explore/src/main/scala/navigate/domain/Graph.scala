package navigate.domain

import scala.collection.{mutable => m}
import scala.language.implicitConversions

trait Floor {
  implicit val NodeStore: m.HashMap[Int, Node]
  val graph: WeightedGraph
}

sealed trait Node
case class RoadNode(id: Int, name: String) extends Node
case class LiftNode(id: Int, name: String) extends Node
case class StairsNode(id: Int, name: String) extends Node
case class ServiceNode(id: Int, name: String, tags: Set[String] = Set.empty) extends Node
case class RoomNode(id: Int, name: String, tags: Set[String] = Set.empty) extends Node

case class Distance(value: Int)

case class Edge(from: Node, to: Node, weight: Distance) {
  def opposite: Edge = Edge(to, from, weight)
}

case class ShortestPathResult(
  edgeTo: m.HashMap[Node, Option[Edge]],
  distance: m.HashMap[Node, Int]
) {
  def to(node: Node): Either[String, (List[Edge], Int)] = {
    @scala.annotation.tailrec
    def findPath(path: List[Edge], v: Node): List[Edge] = {
      edgeTo(v) match {
        case Some(e) => findPath(e :: path, e.from)
        case None => path
      }
    }
    if (!distance.get(node).exists(_ < Int.MaxValue))
      Left(s"Not found distance")
    else Right((findPath(Nil, node), distance(node)))
  }
}

object EnrichGraph {
  class IdRange(val value: Int)

  implicit def fromIntToNode(value: Int)(implicit NodeStore: m.HashMap[Int, Node]): Node =
    NodeStore(value)

  implicit class EnrichIntForGraph(value: Int) {
    def meters: Distance = Distance(value)

    def ~(anotherValue: Int)(implicit idRange: IdRange, NodeStore: m.HashMap[Int, Node]): (Node, Node) =
      (NodeStore(idRange.value + value), NodeStore(idRange.value + anotherValue))
  }

  implicit class EnrichNodesToEdge(nodes: (Node, Node)) {
    def costs(dist: Distance): Edge = Edge(nodes._1, nodes._2, dist)
  }
}

case class WeightedGraph(adj: Map[Node, List[Edge]] = Map.empty) {
  // add edge
  def + (edge: Edge): WeightedGraph = {
    val list = adj.getOrElse(edge.from, List.empty)
    val oppositeList = adj.getOrElse(edge.to, List.empty)
    WeightedGraph( adj +
      (edge.from -> (edge :: list)) +
      (edge.to -> (edge.opposite :: oppositeList))
    )
  }

  // concat graphs
  def ++ (anotherGraph: WeightedGraph): WeightedGraph = {
    val keys = adj.keySet ++ anotherGraph.adj.keySet
    val concatKeys = keys.map { key =>
      key -> (adj(key) ++ anotherGraph.adj(key).distinct)
    }.toMap
    WeightedGraph(concatKeys)
  }

  // shortest path (seq Dijkstra)
  def shortestPathFrom(from: Node): Either[String, ShortestPathResult] = {
    if (!adj.contains(from)) Left(s"Not found $from in adjacency list")
    else { 
      val dist: m.HashMap[Node, Int]          = m.HashMap.from(adj.keySet.map(_ -> Int.MaxValue))
      val prev: m.HashMap[Node, Option[Edge]] = m.HashMap.from(adj.keySet.map(_ -> None))
      dist(from) = 0
      val pq: m.PriorityQueue[(Int, Node)] =
        m.PriorityQueue((dist(from), from))(Ordering.fromLessThan(_._1 > _._1))
      while (pq.nonEmpty) {
        val (curDist, curNode) = pq.dequeue()
        for {
          edges <- adj.get(curNode) if curDist <= dist(curNode)
          e     <- edges            if dist(e.to) > dist(e.from) + e.weight.value
        } {
          dist(e.to) = dist(e.from) + e.weight.value
          prev(e.to) = Some(e)
          pq.enqueue((dist(e.to), e.to))
        }
      }
      Right(ShortestPathResult(prev, dist))
    }
  }

}