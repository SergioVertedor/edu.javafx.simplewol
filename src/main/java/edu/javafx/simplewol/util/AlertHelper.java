package edu.javafx.simplewol.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

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

  public static String inputText(String message) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Entrada de texto");
    dialog.setHeaderText("Por favor ingresa un valor:");
    dialog.setContentText("Texto:");

    dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.length() > 20) {
        dialog.getEditor().setText(oldValue);
      }
    });

    Optional<String> resultado = dialog.showAndWait();
    return resultado.orElse(null);
  }
}
