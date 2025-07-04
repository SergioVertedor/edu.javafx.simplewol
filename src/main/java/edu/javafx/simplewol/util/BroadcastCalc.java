package edu.javafx.simplewol.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Clase de utilidad para calcular la dirección de broadcast
 */
public class BroadcastCalc {

  /**
   * Calcula la dirección de broadcast a partir de una dirección IP y una máscara de subred.
   * 
   * @param ipAddress
   * @param subnetMask
   * @return
   */
  public static String calculateBroadcastAddress(String ipAddress, String subnetMask) {
    byte[] ip = null;
    byte[] mask = null;
    String broadcastStr = null;

    try {
      // Convierte la dirección IP y la máscara de subred en un array de bytes
      // Utiliza InetAddress.getByName para obtener la representación de la dirección IP y la
      // máscara de subred
      ip = InetAddress.getByName(ipAddress).getAddress();
      mask = InetAddress.getByName(subnetMask).getAddress();
      byte[] broadcast = new byte[ip.length];

      for (int i = 0; i < ip.length; i++) {
        // Calcula la dirección de broadcast realizando una operación OR entre la dirección IP y el
        // complemento de la máscara de subred (ip[i] | ~mask[i])
        broadcast[i] = (byte) (ip[i] | ~mask[i]);
        broadcastStr = InetAddress.getByAddress(broadcast).getHostAddress();
      }
    } catch (UnknownHostException e) {
      // Si ocurre un error al convertir la dirección IP o la máscara de subred, lanza una
      // IllegalArgumentException con un mensaje descriptivo
      AlertHelper.showAlert(Constantes.ERROR_BROADCAST);
      return null;
    }
    // Si la dirección de broadcast se calcula correctamente, devuelve la dirección en formato
    // string
    return broadcastStr;
  }
}
