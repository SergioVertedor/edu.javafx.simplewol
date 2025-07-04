package edu.javafx.simplewol.controller;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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
import javafx.scene.control.cell.TextFieldTableCell;

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
  private TableColumn<MagicPacket, String> columnAlias;

  @FXML
  private TableColumn<MagicPacket, String> columnIp;

  @FXML
  private TableColumn<MagicPacket, String> columnMac;

  @FXML
  private TableColumn<MagicPacket, String> columnPort;

  @FXML
  private TableColumn<MagicPacket, String> columnSubnetmask;

  @FXML
  private TextField textFieldIp;

  @FXML
  private TextField textFieldMAC;

  @FXML
  private TextField textFieldMagicPacketPort;

  @FXML
  private TextField textFieldSubnetMask;

  /**
   * Elimina el MagicPacket seleccionado de la tabla y del archivo.
   * 
   * @param event
   */
  @FXML
  void deletePressed(ActionEvent event) {
    FileHandler fileHandler = new FileHandler();
    MagicPacket magicPacket = tabla.getSelectionModel().getSelectedItem();
    fileHandler.delete(magicPacket);
    filas.remove(magicPacket);
  }

  /**
   * Muestra un cuadro de diálogo para ingresar un alias y guarda el MagicPacket en el archivo.
   * 
   * @param event
   */
  @FXML
  void savePressed(ActionEvent event) {
    try {
      String alias = AlertHelper.inputText(Constantes.REQUEST_ALIAS);
      if (alias == null) {
        AlertHelper.showAlert(Constantes.ERROR_ALIAS);
        return;
      }
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
   * Envia el paquete mágico al dispositivo de destino.
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
          AlertHelper.showAlert(sb.toString());
          return;
        }
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
   * Configura la tabla de MagicPackets y sus propiedades.
   * 
   * @throws UnknownHostException
   */
  private void configTable() {
    columnAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
    columnIp.setCellValueFactory(new PropertyValueFactory<>("ip"));
    columnMac.setCellValueFactory(new PropertyValueFactory<>("mac"));
    columnSubnetmask.setCellValueFactory(new PropertyValueFactory<>("subnetMask"));
    columnPort.setCellValueFactory(new PropertyValueFactory<>("port"));
    columnAlias.setCellFactory(TextFieldTableCell.forTableColumn());
    columnIp.setCellFactory(TextFieldTableCell.forTableColumn());
    columnMac.setCellFactory(TextFieldTableCell.forTableColumn());
    columnSubnetmask.setCellFactory(TextFieldTableCell.forTableColumn());
    columnPort.setCellFactory(TextFieldTableCell.forTableColumn());
    columnAlias.setEditable(true);
    columnIp.setEditable(true);
    columnMac.setEditable(true);
    columnSubnetmask.setEditable(true);
    columnPort.setEditable(true);
    tabla.setItems(filas);
    tabla.setEditable(true);
    tabla.setPlaceholder(new Label(""));
    setColumnEvents();
    loadTableData();
  }

  /**
   * Setea los eventos de las columnas de la tabla.
   */
  private void setColumnEvents() {

    AtomicReference<MagicPacket> oldPacketRef = new AtomicReference<MagicPacket>();
    InputValidator inputValidator = new InputValidator();
    FileHandler fileHandler = new FileHandler();

    tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
      if (newSel != null) {
        oldPacketRef.set(new MagicPacket(newSel.getAlias(), newSel.getIp(), newSel.getSubnetMask(),
            newSel.getMac(), newSel.getPort(),
            BroadcastCalc.calculateBroadcastAddress(newSel.getIp(), newSel.getSubnetMask())));

        textFieldIp.setText(newSel.getIp());
        textFieldMAC.setText(newSel.getMac());
        textFieldSubnetMask.setText(newSel.getSubnetMask());
        textFieldMagicPacketPort.setText(String.valueOf(newSel.getPort()));
      }
    });


    columnAlias.setOnEditCommit(event -> {
      MagicPacket packet = event.getRowValue();
      if (inputValidator.validateAlias(event.getNewValue())) {
        AlertHelper.showAlert(Constantes.ERROR_ALIAS);
        return;
      }
      packet.setAlias(event.getNewValue());

      fileHandler.edit(oldPacketRef.get(), packet);
    });

    columnIp.setOnEditCommit(event -> {
      MagicPacket packet = event.getRowValue();
      oldPacketRef.set(new MagicPacket(packet.getAlias(), packet.getIp(), packet.getSubnetMask(),
          packet.getMac(), packet.getPort(),
          BroadcastCalc.calculateBroadcastAddress(packet.getIp(), packet.getSubnetMask())));

      packet.setIp(event.getNewValue());
      if (!inputValidator.validateIp(packet.getIp())) {
        AlertHelper.showAlert(Constantes.ERROR_IP);
        packet.setIp(oldPacketRef.get().getIp());
        tabla.refresh();
        return;
      }
      fileHandler.edit(oldPacketRef.get(), packet);

    });

    columnMac.setOnEditCommit(event -> {
      MagicPacket packet = event.getRowValue();
      packet.setMac(event.getNewValue());
      if (!inputValidator.validateMac(packet.getMac())) {
        AlertHelper.showAlert(Constantes.ERROR_MAC);
        return;
      }
      fileHandler.edit(oldPacketRef.get(), packet);

    });
    columnSubnetmask.setOnEditCommit(event -> {
      MagicPacket packet = event.getRowValue();
      packet.setSubnetMask(event.getNewValue());
      if (!inputValidator.validateSubnetMask(packet.getSubnetMask())) {
        AlertHelper.showAlert(Constantes.ERROR_SUBNET_MASK);
        return;
      }
      fileHandler.edit(oldPacketRef.get(), packet);

    });
    columnPort.setOnEditCommit(event -> {
      MagicPacket packet = event.getRowValue();
      packet.setPort(event.getNewValue());
      if (!inputValidator.validatePort(packet.getPort())) {
        AlertHelper.showAlert(Constantes.ERROR_PORT);
        return;
      }
      fileHandler.edit(oldPacketRef.get(), packet);
    });
  }

  /**
   * Carga los datos de la tabla desde el archivo.
   */
  private void loadTableData() {
    tableMagicPackets.forEach(magicPacket -> filas.add(magicPacket));
  }

  /**
   * Obtiene el objeto MagickPacket de los campos de texto.
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
