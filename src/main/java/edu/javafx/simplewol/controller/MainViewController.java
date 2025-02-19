package edu.javafx.simplewol.controller;

import edu.javafx.simplewol.model.MagickPacket;
import edu.javafx.simplewol.util.AlertHelper;
import edu.javafx.simplewol.util.BroadcastCalc;
import edu.javafx.simplewol.util.InputValidator;
import edu.javafx.simplewol.util.WakeOnLan;
import java.net.UnknownHostException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainViewController {

  @FXML private Button buttonWakeUp;

  @FXML private TextField textFieldIp;

  @FXML private TextField textFieldMAC;

  @FXML private TextField textFieldMagicPacketPort;

  @FXML private TextField textFieldSubnetMask;

  @FXML
  void deletePressed(ActionEvent event) {}

  @FXML
  void savePressed(ActionEvent event) {}

  /**
   * Send the magic packet to the target device.
   *
   * @param event
   * @throws Exception
   */
  @FXML
  void wakeUpPressed(ActionEvent event) throws Exception {
    try {
      MagickPacket magickPacket = getMagickPacket();
      InputValidator.validateIp(magickPacket.getIp());
      InputValidator.validateMac(magickPacket.getMac());
      InputValidator.validatePort(magickPacket.getPort());
      InputValidator.validateSubnetMask(magickPacket.getSubnetMask());

      WakeOnLan.sendMagicPacket(
          magickPacket.getMac(), magickPacket.getBroadcastAddress(), magickPacket.getPort());
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
  private MagickPacket getMagickPacket() throws UnknownHostException {
    return new MagickPacket(
        textFieldIp.getText(),
        textFieldMAC.getText(),
        textFieldMagicPacketPort.getText(),
        textFieldSubnetMask.getText(),
        BroadcastCalc.calculateBroadcastAddress(
            textFieldIp.getText(), textFieldSubnetMask.getText()));
  }
}
