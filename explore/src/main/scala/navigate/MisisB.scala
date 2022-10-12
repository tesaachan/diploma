package navigate

import scala.collection.{mutable => m}

object MisisB {
  val NodeStore: m.HashMap[Int, Node] = m.HashMap(
    // 1st floor in B
    1 -> ServiceNode(1, "Главный вход в корпус Б", Set("корпус Б")),
    2 -> RoadNode(2, "Холл 1.1"),
    3 -> ServiceNode(3, "Читальный зал в Б", Set("читальный зал", "библиотека", "посидеть")),
    4 -> RoadNode(4, "Лестница 0-1"),
    5 -> ServiceNode(5, "Dunkin' Donuts", Set("поесть", "посидеть")),
    6 -> ServiceNode(6, "Рест-зона", Set("посидеть")),
    7 -> RoadNode(7, "Лестница 1-2"),
    8 -> RoomNode(8, "Б1", Set.empty),
    9 -> RoomNode(9, "Б2", Set.empty),
    10 -> RoadNode(10, "Холл 1.2"),
    11 -> LiftNode(11, "Лифт 1 в Б"),
    12 -> LiftNode(12, "Лифт 2 в Б"),
    13 -> LiftNode(13, "Лифт 3 в Б"),
    14 -> LiftNode(14, "Лифт 4 в Б"),
    15 -> ServiceNode(15, "Туалет мужской 1", Set("туалет")),
    16 -> ServiceNode(16, "Туалет женский 1", Set("туалет")),
    17 -> StairsNode(17, "Лестница в Б"),
    18 -> RoomNode(18, "Б3", Set.empty),
    19 -> RoomNode(19, "Б4", Set.empty),
    // 0th floor in B
    20 -> RoadNode(20, "Холл 0.1"),
    21 -> ServiceNode(21, "Библиотека в Б", Set("библиотека")),
    22 -> ServiceNode(22, "Гардероб в Б", Set("гардероб")),
    23 -> ServiceNode(23, "Магазин атрибутики", Set("атрибутика", "магазин")),
    24 -> ServiceNode(24, "Автомат со снеками", Set("поесть")),
    25 -> ServiceNode(25, "Автомат с кофе", Set("кофе"))
  )

  object EnrichGraph {
    implicit def fromIntToNode(value: Int): Node = NodeStore(value)

    implicit class EnrichIntForGraph(value: Int) {
      def meters = Distance(value)
      def ~(anotherValue: Int) = (NodeStore(value), NodeStore(anotherValue))
    }

    implicit class EnrichNodesToEdge(nodes: (Node, Node)) {
      def costs(dist: Distance) = Edge(nodes._1, nodes._2, dist)
    }
  }

  import EnrichGraph._

  val graph: WeightedGraph =
    WeightedGraph() +
    (1 ~ 2 costs 20.meters) + (2 ~ 3 costs 10.meters) +
    (2 ~ 4 costs 10.meters) + (2 ~ 5 costs 10.meters) + 
    (2 ~ 6 costs 10.meters) + (2 ~ 7 costs 10.meters) +
    (2 ~ 8 costs 10.meters) + (2 ~ 9 costs 10.meters) +
    (2 ~ 10 costs 10.meters) + (10 ~ 11 costs 6.meters) +
    (10 ~ 12 costs 6.meters) + (10 ~ 13 costs 6.meters) +
    (10 ~ 14 costs 6.meters) + (10 ~ 15 costs 8.meters) +
    (10 ~ 16 costs 8.meters) + (10 ~ 17 costs 16.meters) +
    (11 ~ 20 costs 30.meters) + (4 ~ 20 costs 8.meters) +
    (20 ~ 21 costs 6.meters) + (20 ~ 22 costs 6.meters)
  
    // + DirectedEdge(from = 1, to = 2, weight = Distance(20))

}