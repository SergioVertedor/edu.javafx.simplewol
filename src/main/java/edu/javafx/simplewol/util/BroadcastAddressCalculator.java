package edu.javafx.simplewol.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BroadcastAddressCalculator {

  public static String calculateBroadcastAddress(String ipAddress, String subnetMask) throws UnknownHostException {
    byte[] ip = InetAddress.getByName(ipAddress).getAddress();
    byte[] mask = InetAddress.getByName(subnetMask).getAddress();
    byte[] broadcast = new byte[ip.length];

    for (int i = 0; i < ip.length; i++) {
      broadcast[i] = (byte) (ip[i] | ~mask[i]);
    }

    return InetAddress.getByAddress(broadcast).getHostAddress();
  }

  public static void main(String[] args) {
    try {
      String ipAddress = "192.168.1.10";
      String subnetMask = "255.255.255.0";
      String broadcastAddress = calculateBroadcastAddress(ipAddress, subnetMask);
      System.out.println("Broadcast Address: " + broadcastAddress);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }
}