package edu.javafx.simplewol.controller;

import java.net.UnknownHostException;
import java.util.List;
import edu.javafx.simplewol.model.MagicPacket;
import edu.javafx.simplewol.util.AlertHelper;
import edu.javafx.simplewol.util.BroadcastCalc;
import edu.javafx.simplewol.util.Constantes;
import edu.javafx.simplewol.util.FileHandler;
import edu.javafx.simplewol.util.InputValidator;
import edu.javafx.simplewol.util.WakeOnLan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainViewController {

  private static List<MagicPacket> tableMagicPackets = null;

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
    FileHandler fileHandler = new FileHandler();
    MagicPacket magicPacket = tabla.getSelectionModel().getSelectedItem();
    fileHandler.delete(magicPacket);
    filas.remove(magicPacket);
  }

  @FXML
  void savePressed(ActionEvent event) {
    try {
      String alias = AlertHelper.inputText(Constantes.REQUEST_ALIAS);
      if (alias.length() > 0) {
        InputValidator inputValidator = new InputValidator();
        inputValidator.validateAlias(alias);
        inputValidator.validateIp(textFieldIp.getText());
        inputValidator.validateMac(textFieldMAC.getText());
        inputValidator.validateSubnetMask(textFieldSubnetMask.getText());
        inputValidator.validatePort(textFieldMagicPacketPort.getText());
        StringBuilder sb = new StringBuilder();
        for (String error : InputValidator.errors) {
          sb.append(error).append("\n");
        }
        if (InputValidator.errors.size() > 0) {
          AlertHelper.showAlert(sb.toString());
          return;
        }
      }
      MagicPacket magicPacket = getMagickPacket();
      magicPacket.setAlias(alias);
      filas.add(magicPacket);
      FileHandler fileHandler = new FileHandler();
      fileHandler.write(magicPacket);
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
      InputValidator inputValidator = new InputValidator();
      inputValidator.validateIp(magickPacket.getIp());
      inputValidator.validateMac(magickPacket.getMac());
      inputValidator.validatePort(magickPacket.getPort());
      if (InputValidator.errors.isEmpty()) {
        WakeOnLan.sendMagicPacket(magickPacket.getMac(), magickPacket.getBroadcastAddress(),
            magickPacket.getPort());
      } else {
        StringBuilder sb = new StringBuilder();
        for (String error : InputValidator.errors) {
          sb.append(error).append("\n");
        }
        AlertHelper.showAlert(sb.toString());
      }
    } catch (Exception e) {
      AlertHelper.showAlert(e.getMessage());
    }
  }

  @FXML
  public void initialize() {
    FileHandler fileHandler = new FileHandler();
    fileHandler.create();
    tableMagicPackets = fileHandler.read();
    configTable();
  }

  /**
   * Configure the table columns and their properties.
   */
  private void configTable() {
    columnAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
    columnIp.setCellValueFactory(new PropertyValueFactory<>("ip"));
    columnMac.setCellValueFactory(new PropertyValueFactory<>("mac"));
    columnSubnetmask.setCellValueFactory(new PropertyValueFactory<>("subnetMask"));
    columnPort.setCellValueFactory(new PropertyValueFactory<>("port"));
    tabla.setItems(filas);
    tabla.setPlaceholder(new Label(""));
    loadTableData();
  }

  /**
   * Load the table data from the file.
   */
  private void loadTableData() {
    for (MagicPacket magicPacket : tableMagicPackets) {
      filas.add(magicPacket);
    }
  }

  /**
   * Get the MagickPacket object from the text fields.
   *
   * @return
   * @throws UnknownHostException
   */
  private MagicPacket getMagickPacket() throws UnknownHostException {
    return new MagicPacket(Constantes.EMPTY_STRING, textFieldIp.getText(),
        textFieldSubnetMask.getText(), textFieldMAC.getText(), textFieldMagicPacketPort.getText(),
        BroadcastCalc.calculateBroadcastAddress(textFieldIp.getText(),
            textFieldSubnetMask.getText()));
  }
}
