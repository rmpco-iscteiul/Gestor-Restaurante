import Restaurant.Restaurant

case class Client(name: String, time: String, table: String, res: Restaurant) {

  def askForReservation(): Unit = {
    println(name + " " + time + " " + table)
    res.verifySpace(time, table, name)
  }

  def goDinner(): Unit ={
    res.verifyCurrentSpace(table, time, name)
  }

  def makePayment(): Unit = {
    res.payTable(table, time, name)
  }
}
