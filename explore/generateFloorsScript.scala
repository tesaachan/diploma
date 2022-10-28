object Main extends App {

  def generate(idPrefix: Int, room: Int): Unit = {
    println(s"10080$idPrefix -> ServiceNode(1008021, \"Б8$room\"),")
  }

  //(22 to 22 + 2).zip(List(49, 50, 51)).foreach { case (id, room) =>
  //  generate(id, room)
  //}

  //LazyList.from(1008029).take(26).toList.map { id =>
  //  println(s"$id -> RoadNode($id, \"Коридор\"),")
  //  id
  //}

  @scala.annotation.tailrec
  def readPrintLoop(acc: List[String] = Nil): Unit = {
    val s = scala.io.StdIn.readLine().split(" ")
    s(0) match {
      case "print" => acc.reverse.foreach(print); readPrintLoop(acc)
      case "clean" => readPrintLoop()
      case "exit"  => ()
      case _       =>
        val result = s"(${s(0)} ~ ${s(1)} costs ${s(2)}.meters) +\n"
        println(result)
        readPrintLoop(result :: acc)
    }
  }
  readPrintLoop()
}