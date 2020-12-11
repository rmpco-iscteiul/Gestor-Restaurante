import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Restaurant {

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Restaurant], args: _*)
  }

  case class Restaurant() extends Application {

    var reservations = ListBuffer.empty[(String, String, String)]
    var tableList = ListBuffer.empty[Table]
    val horario: List[String] = List("1200", "1230", "1300", "1330", "1400", "1430", "1500", "1900", "1930", "2000", "2030", "2100", "2130", "2200")
    var hora: String = "1200"

    var nClient: Int = 0
    var nAvailableTable: Int = 10
    var nReservations: Int = 0

    def addInfo(): Unit = {
      val bufferedSource1 = Source.fromFile("tableList.txt")
      for (line <- bufferedSource1.getLines) {
        val linha: Array[String] = line.split(' ')
        var table = new Table(linha(0), this)
        tableList += table
      }
      bufferedSource1.close

      val bufferedSource = Source.fromFile("reservations.txt")
      for (line <- bufferedSource.getLines) {
        val linha: Array[String] = line.split(' ')
        val a: (String, String, String) = (linha(0), linha(1), linha(2))
        reservations += a
        nReservations += 1
      }
      bufferedSource.close
      removeTimeFromTables(reservations, tableList)
    }

    def verifySpace(time: String, table: String, name: String): Boolean = {
      for (a <- 1 to reservations.length) {
        if (table.equals(reservations(a)._1) && time.equals(reservations(a)._3)) {
          return false
        } else {
          val a: (String, String, String) = (table, name, time)
          reservations += a
          nReservations += 1
          return true
        }
      }
      false
    }

    def verifyCurrentSpace(time: String, table: String, name: String): String={
      for (t <- tableList) {
        if(!t.isOccupied) {
          t.isOccupied = true
          val a: (String, String, String) = (table, name, time)
          reservations += a
          nReservations += 1
          nClient += 1
          return "check"
        }
      }
      "check"
    }

    def payTable(table: String, time: String, name: String): String = {
      for (r <- reservations) {
        for (t: Table <- tableList) {
            if(t.getNTable() == table) {
              t.scheduled :+ time
              reservations = reservations.filter((str) => !((str) == (r._1, r._2, r._3)))
              nReservations -= 1
              nClient -= 1
              return "check"
            }
        }
      }
      "check"
    }

    def removeTimeFromTables(reservations: ListBuffer[(String, String, String)], tableList: ListBuffer[Table]): Unit = {
      if (tableList.nonEmpty && reservations.nonEmpty) {
        for (i <- 0 to 9) {
          if (reservations.head._1.equals(tableList(i).getNTable())) {
            tableList(i).removeTime(reservations.head._3)
          }
        }
        removeTimeFromTables(reservations.tail, tableList)
      }
    }

    def checkTablesStatus(listBuffer: ListBuffer[Table]): Unit = {
      if (listBuffer.nonEmpty) {
        listBuffer.head.verifyStatus()
        if(listBuffer.head.isOccupied == true)
          nClient += 1
        checkTablesStatus(listBuffer.tail)
      }
    }

    def passTime(h: Int): String = {
      if (horario.nonEmpty || h <= horario.length) {
        checkTablesStatus(tableList)
        hora = horario(h)
        return horario(h)
      }
      ""
    }

    override def start(primaryStage: Stage): Unit = {
      primaryStage.setTitle("Restaurante")
      val fxmlLoader = new FXMLLoader(getClass.getResource("controller.fxml"))
      val mainViewRoot: Parent = fxmlLoader.load()
      val scene = new Scene(mainViewRoot)
      primaryStage.setScene(scene)
      primaryStage.show()
    }
  }
}