package edu.javafx.simplewol.controller;

import java.net.UnknownHostException;
import edu.javafx.simplewol.model.MagicPacket;
import edu.javafx.simplewol.util.AlertHelper;
import edu.javafx.simplewol.util.BroadcastCalc;
import edu.javafx.simplewol.util.Constantes;
import edu.javafx.simplewol.util.InputValidator;
import edu.javafx.simplewol.util.WakeOnLan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainViewController {

  private ObservableList<MagicPacket> filas = FXCollections.observableArrayList();

  @FXML
  private Button buttonDelete;

  @FXML
  private Button buttonSave;

  @FXML
  private Button buttonWakeUp;

  @FXML
  private TableView<MagicPacket> tabla;

  @FXML
  private TableColumn<?, ?> columnAlias;

  @FXML
  private TableColumn<?, ?> columnIp;

  @FXML
  private TableColumn<?, ?> columnMac;

  @FXML
  private TableColumn<?, ?> columnPort;

  @FXML
  private TableColumn<?, ?> columnSubnetmask;

  @FXML
  private TextField textFieldIp;

  @FXML
  private TextField textFieldMAC;

  @FXML
  private TextField textFieldMagicPacketPort;

  @FXML
  private TextField textFieldSubnetMask;

  @FXML
  void deletePressed(ActionEvent event) {

  }

  @FXML
  void savePressed(ActionEvent event) {
    try {
      String alias = AlertHelper.inputText(Constantes.REQUEST_ALIAS);
      if (alias.length() > 0) {
        InputValidator.validateAlias(alias);
      }
      MagicPacket magicPacket = getMagickPacket();
      magicPacket.setAlias(alias);
      filas.add(magicPacket);
    } catch (Exception e) {
      AlertHelper.showAlert(e.getMessage());
    }
  }

  /**
   * Send the magic packet to the target device.
   *
   * @param event
   * @throws Exception
   */
  @FXML
  void wakeUpPressed(ActionEvent event) throws Exception {
    try {
      MagicPacket magickPacket = getMagickPacket();
      InputValidator.validateIp(magickPacket.getIp());
      InputValidator.validateMac(magickPacket.getMac());
      InputValidator.validatePort(magickPacket.getPort());
      WakeOnLan.sendMagicPacket(magickPacket.getMac(), magickPacket.getBroadcastAddress(),
          magickPacket.getPort());
    } catch (Exception e) {
      AlertHelper.showAlert(e.getMessage());
    }
  }

  /**
   * Get the MagickPacket object from the text fields.
   *
   * @return
   * @throws UnknownHostException
   */
  private MagicPacket getMagickPacket() throws UnknownHostException {
    return new MagicPacket(textFieldIp.getText(), textFieldSubnetMask.getText(),
        textFieldMAC.getText(), textFieldMagicPacketPort.getText(), BroadcastCalc
            .calculateBroadcastAddress(textFieldIp.getText(), textFieldSubnetMask.getText()));
  }


  @FXML
  public void initialize() {
    columnAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
    columnIp.setCellValueFactory(new PropertyValueFactory<>("ip"));
    columnMac.setCellValueFactory(new PropertyValueFactory<>("mac"));
    columnSubnetmask.setCellValueFactory(new PropertyValueFactory<>("subnetMask"));
    columnPort.setCellValueFactory(new PropertyValueFactory<>("port"));
    tabla.setItems(filas);
  }



}
