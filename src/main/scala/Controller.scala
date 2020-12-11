import javafx.fxml.FXML
import javafx.scene.control.{Button, TextField}

import Restaurant._

import scala.collection.mutable.ListBuffer;

class Controller {

  private val rest = Restaurant()
  private var h: Int = 0
  private var clients = ListBuffer.empty[Client]

  @FXML
  private var textFieldReserva: TextField = _

  @FXML
  private var textFieldMesasDisponiveis: TextField = _

  @FXML
  private var textFieldNumeroClientes: TextField = _

  @FXML
  private var start: Button = _

  def startRest(): Unit={
    rest.addInfo()
    textFieldReserva.setText(rest.nReservations.toString)
    textFieldHora.setText(rest.hora)
    textFieldMesasDisponiveis.setText(rest.nAvailableTable.toString)
    textFieldNumeroClientes.setText(rest.nClient.toString)
  }

  @FXML
  private var reservaCliente: Button = _

  @FXML
  private var textFieldNome: TextField = _

  @FXML
  private var textFieldMesa: TextField = _

  @FXML
  private var textFieldHoraR: TextField = _

  def addReservation(): Unit ={
    val client1: Client = Client(textFieldNome.getText(), textFieldHora.getText(), textFieldMesa.getText(), rest)
    clients += client1
    client1.askForReservation()
    textFieldReserva.setText(rest.nReservations.toString)
  }

  @FXML
  private var passarHora: Button = _

  @FXML
  private var textFieldHora: TextField = _

  def passTimeClick(): Unit={
    textFieldHora.setText(rest.passTime(h))
    textFieldMesasDisponiveis.setText(rest.nAvailableTable.toString)
    textFieldNumeroClientes.setText(rest.nClient.toString)
    textFieldReserva.setText(rest.nReservations.toString)
    rest.nClient = 0
    rest.nAvailableTable = 10
    h += 1
  }

  @FXML
  private var maisUmCliente: Button = _

  def oneMoreClient(): Unit ={
    val client1: Client = Client(textFieldNome.getText(), textFieldHora.getText(),textFieldMesa.getText(), rest)
    clients += client1
    client1.goDinner()
    textFieldNumeroClientes.setText(rest.nClient.toString)
    textFieldReserva.setText(rest.nReservations.toString)
  }

  @FXML
  private var menosUmCliente: Button = _

  def oneLessClient(): Unit ={
    clients.head.makePayment()
    textFieldNumeroClientes.setText(rest.nClient.toString)
    textFieldReserva.setText(rest.nReservations.toString)
    clients = clients.filter((str) => !((str) == clients.head.name))
  }
}
