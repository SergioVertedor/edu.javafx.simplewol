package edu.javafx.simplewol.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BroadcastCalc {

    private BroadcastCalc() {
    throw new IllegalStateException("Utility class");
  }

  public static String calculateBroadcastAddress(String ipAddress, String subnetMask)
      throws UnknownHostException {
    byte[] ip = InetAddress.getByName(ipAddress).getAddress();
    byte[] mask = InetAddress.getByName(subnetMask).getAddress();
    byte[] broadcast = new byte[ip.length];

    for (int i = 0; i < ip.length; i++) {
      broadcast[i] = (byte) (ip[i] | ~mask[i]);
    }

    return InetAddress.getByAddress(broadcast).getHostAddress();
  }
}
