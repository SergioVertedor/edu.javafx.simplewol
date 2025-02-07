module edu.sergiovertedor.simplewol {
  requires javafx.controls;
  requires javafx.fxml;


  exports edu.javafx.simplewol.application;
  opens edu.javafx.simplewol.application to javafx.fxml;
  exports edu.javafx.simplewol.controller;
  opens edu.javafx.simplewol.controller to javafx.fxml;
}