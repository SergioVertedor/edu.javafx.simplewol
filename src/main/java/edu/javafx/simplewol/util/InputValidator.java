package edu.javafx.simplewol.util;

import edu.javafx.simplewol.model.MagicPacket;

public class InputValidator {

  private InputValidator() {
    throw new IllegalStateException("Utility class");
  }

 public static void validateIp(String ip) {
    if (!ip.matches(
        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")) {
      throw new IllegalArgumentException("Invalid IP address");
    }
  }

  public static void validateMac(String mac) {
    if (!mac.matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")) {
      throw new IllegalArgumentException("Invalid MAC address");
    }
  }

  public static void validatePort(String port) {
    if (!port.matches("^\\d+$")) {
      throw new IllegalArgumentException("Invalid port number");
    }
  }

  public static void validateSubnetMask(String subnetMask) {
    if (!subnetMask.matches(
        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")) {
      throw new IllegalArgumentException("Invalid subnet mask");
    }
 }

  public static void validateAlias(String alias) {
    if (!alias.matches("^[a-zA-Z0-9_]+$")) {
      throw new IllegalArgumentException("Invalid alias");
    }
  }
}
