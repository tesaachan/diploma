package navigate.domain

import scala.collection.{mutable => m}


// soon move it all to DB
object MisisB8thFloor extends Floor {
  private def indoorRoadNode(id: Int): RoadNode = RoadNode(id, "Коридор")

  implicit val NodeStore: m.HashMap[Int, Node] = m.HashMap(
    // 8th floor in B: 10080**
    1008001 -> ServiceNode(1008001, "Б807"),
    1008002 -> ServiceNode(1008002, "Б808"),
    1008003 -> ServiceNode(1008003, "Б810"),
    1008004 -> ServiceNode(1008004, "Б811"),
    1008005 -> ServiceNode(1008005, "Б812"),
    1008006 -> ServiceNode(1008006, "Б814"),
    1008007 -> ServiceNode(1008007, "Б816"),
    1008008 -> ServiceNode(1008008, "Б817"),
    1008009 -> ServiceNode(1008009, "Б819"),
    1008010 -> ServiceNode(1008010, "Б821"),
    1008011 -> ServiceNode(1008011, "Б822"),
    1008012 -> ServiceNode(1008012, "Б824"),
    1008013 -> ServiceNode(1008013, "Б826"),
    1008014 -> ServiceNode(1008014, "Б828"),
    1008015 -> ServiceNode(1008015, "Б829"),
    1008016 -> ServiceNode(1008016, "Б831"),
    1008017 -> ServiceNode(1008017, "Б833"),
    1008018 -> ServiceNode(1008018, "Б835"),
    1008019 -> ServiceNode(1008019, "Б845"),
    1008020 -> ServiceNode(1008020, "Б846"),
    1008021 -> ServiceNode(1008021, "Б847"),
    1008022 -> ServiceNode(1008022, "Б849"),
    1008023 -> ServiceNode(1008023, "Б850"),
    1008024 -> ServiceNode(1008024, "Б851"),
    1008025 -> LiftNode(1008025, "Общий лифт в Б"),
    1008026 -> LiftNode(1008026, "Специальный лифт в Б"),
    1008027 -> StairsNode(1008027, "Ближняя лестница в Б"),
    1008028 -> StairsNode(1008028, "Дальняя лестница в Б"),
    1008029 -> RoadNode(1008029, "Коридор"),
    1008030 -> RoadNode(1008030, "Коридор"),
    1008031 -> RoadNode(1008031, "Коридор"),
    1008032 -> RoadNode(1008032, "Коридор"),
    1008033 -> RoadNode(1008033, "Коридор"),
    1008034 -> RoadNode(1008034, "Коридор"),
    1008035 -> RoadNode(1008035, "Коридор"),
    1008036 -> RoadNode(1008036, "Коридор"),
    1008037 -> RoadNode(1008037, "Коридор"),
    1008038 -> RoadNode(1008038, "Коридор"),
    1008039 -> RoadNode(1008039, "Коридор"),
    1008040 -> RoadNode(1008040, "Коридор"),
    1008041 -> RoadNode(1008041, "Коридор"),
    1008042 -> RoadNode(1008042, "Коридор"),
    1008043 -> RoadNode(1008043, "Коридор"),
    1008044 -> RoadNode(1008044, "Коридор"),
    1008045 -> RoadNode(1008045, "Коридор"),
    1008046 -> RoadNode(1008046, "Коридор"),
    1008047 -> RoadNode(1008047, "Коридор"),
    1008048 -> RoadNode(1008048, "Коридор"),
    1008049 -> RoadNode(1008049, "Коридор"),
    1008050 -> RoadNode(1008050, "Коридор"),
    1008051 -> RoadNode(1008051, "Коридор"),
    1008052 -> RoadNode(1008052, "Коридор"),
    1008053 -> RoadNode(1008053, "Коридор"),
    1008054 -> RoadNode(1008054, "Коридор"),
    1008054 -> RoadNode(1008054, "Коридор"),
    1008055 -> ServiceNode(1008055, "Диваны", Set("посидеть")),
    1008056 -> ServiceNode(1008056, "Туалет М", Set("туалет")),
    1008057 -> ServiceNode(1008057, "Туалет Ж", Set("туалет")),
  )

  import navigate.domain.EnrichGraph.{EnrichIntForGraph, EnrichNodesToEdge, IdRange}

  implicit val idRange: IdRange = new IdRange(1008000)

  val graph: WeightedGraph = WeightedGraph() +
    (25 ~ 30 costs 4.meters) +
    (30 ~ 32 costs 8.meters) +
    (32 ~ 25 costs 4.meters) +
    (30 ~ 29 costs 4.meters) +
    (32 ~ 31 costs 4.meters) +
    (29 ~ 50 costs 3.meters) +
    (50 ~ 31 costs 3.meters) +
    (50 ~ 55 costs 2.meters) +
    (29 ~ 18 costs 2.meters) +
    (31 ~ 17 costs 2.meters) +
    (31 ~ 33 costs 6.meters) +
    (33 ~ 16 costs 2.meters) +
    (33 ~ 34 costs 3.meters) +
    (34 ~ 15 costs 2.meters) +
    (34 ~ 35 costs 5.meters) +
    (35 ~ 14 costs 2.meters) +
    (35 ~ 36 costs 5.meters) +
    (36 ~ 27 costs 2.meters) +
    (35 ~ 37 costs 2.meters) +
    (37 ~ 13 costs 2.meters) +
    (37 ~ 56 costs 2.meters) +
    (37 ~ 38 costs 5.meters) +
    (38 ~ 12 costs 2.meters) +
    (38 ~ 51 costs 2.meters) +
    (51 ~ 54 costs 2.meters) +
    (51 ~ 39 costs 2.meters) +
    (39 ~ 11 costs 2.meters) +
    (39 ~ 19 costs 2.meters) +
    (39 ~ 40 costs 3.meters) +
    (40 ~ 10 costs 2.meters) +
    (40 ~ 20 costs 2.meters) +
    (40 ~ 41 costs 3.meters) +
    (41 ~ 21 costs 2.meters) +
    (41 ~ 9 costs 2.meters) +
    (41 ~ 42 costs 3.meters) +
    (42 ~ 8 costs 2.meters) +
    (42 ~ 22 costs 2.meters) +
    (42 ~ 43 costs 3.meters) +
    (43 ~ 23 costs 2.meters) +
    (43 ~ 7 costs 2.meters) +
    (43 ~ 44 costs 3.meters) +
    (44 ~ 24 costs 2.meters) +
    (44 ~ 6 costs 2.meters) +
    (44 ~ 45 costs 3.meters) +
    (45 ~ 26 costs 3.meters) +
    (45 ~ 5 costs 2.meters) +
    (45 ~ 46 costs 2.meters) +
    (46 ~ 47 costs 2.meters) +
    (47 ~ 28 costs 2.meters) +
    (46 ~ 48 costs 3.meters) +
    (48 ~ 1 costs 2.meters) +
    (48 ~ 4 costs 2.meters) +
    (48 ~ 49 costs 3.meters) +
    (49 ~ 2 costs 2.meters) +
    (49 ~ 3 costs 2.meters)

  // + DirectedEdge(NodeStore(idRange.value + 25), NodeStore(idRange.value + 30), Distance(4))
}
