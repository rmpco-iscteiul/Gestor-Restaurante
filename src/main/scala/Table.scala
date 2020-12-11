import Restaurant.Restaurant

class Table(nTable: String, res: Restaurant){

  var scheduled = Array.ofDim[String](20)
  scheduled = Array("1200", "1230", "1300", "1330", "1400", "1430", "1500", "1900", "1930", "2000", "2030", "2100", "2130", "2200")
  var isOccupied: Boolean = false

  def verifyStatus(): Unit = {
    if (scheduled.contains(res.hora)) {
      isOccupied = false
      println(res.hora)
      println(nTable + " " + isOccupied)
    } else {
      isOccupied = true
      res.nAvailableTable -= 1
      println(res.hora)
      println(nTable + " " + isOccupied)
    }
  }

  def removeTime(time: String): Unit= {
    if (scheduled.contains(time)) {
      println(scheduled.length + " " + nTable)
      scheduled = scheduled.filter(str => !(str == time))
      println(scheduled.length + " " + nTable)
    }
  }

  def getNTable() : String={
    nTable
  }
}