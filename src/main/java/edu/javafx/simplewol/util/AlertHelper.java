package edu.javafx.simplewol.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertHelper {

  private AlertHelper() {
    throw new IllegalStateException("Utility class");
  }

  public static void showAlert(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Validation Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}