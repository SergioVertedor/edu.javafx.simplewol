module edu.sergiovertedor.simplewol {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.base;


  exports edu.javafx.simplewol.application; 
  exports edu.javafx.simplewol.controller;
  opens edu.javafx.simplewol.application to javafx.fxml;
  
  opens edu.javafx.simplewol.controller to javafx.fxml;
    opens edu.javafx.simplewol.model to javafx.base;  

}

