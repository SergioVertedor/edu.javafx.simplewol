package edu.javafx.simplewol.util;

import java.util.ArrayList;
import java.util.List;
import edu.javafx.simplewol.model.MagicPacket;

public class InputValidator {

  public static List<String> errors = new ArrayList<>();

  public void validateIp(String ip) {
    String errorMessage = null;
    if (!ip.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
        + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")) {
      errors.add("IP address is not valid");
    }

  }

  public void validateMac(String mac) {
    if (!mac.matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")) {
      errors.add("MAC address is not valid");
    } ;
  }

  public void validatePort(String port) {
    if (!port.matches("^\\d+$")) {
      errors.add("Port number is not valid");
    }
  }

  public void validateSubnetMask(String subnetMask) {
    if (!subnetMask
        .matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")) {
      errors.add("Subnet mask is not valid");
    }
  }

  public void validateAlias(String alias) {
    if (!alias.matches("^[a-zA-Z0-9_ ]+$")) {
      errors.add("Alias is not valid");
    }
  }
}
