package edu.javafx.simplewol.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Clase que envía un Magic Packet a través de la red para encender un equipo remoto mediante Wake-on-LAN.
 */
public class WakeOnLan {
  private static final int PORT = 9; // Puerto estándar para Magic Packets

  /**
   * Método que envía un Magic Packet a través de la red para encender un equipo remoto mediante Wake-on-LAN.
   * @param macAddress
   * @param broadcastIP
   * @throws Exception
   */
  public static void sendMagicPacket(String macAddress, String broadcastIP) throws Exception {
    byte[] macBytes = getMacBytes(macAddress);
    byte[] magicPacket = new byte[102];

    // Se prepara cabecera para MagicPacket, 6 bytes de 0xFF
    for (int i = 0; i < 6; i++) {
      magicPacket[i] = (byte) 0xFF;
    }

    // Se prepara el cuerpo para MagicPacket el cual consta de 16 repeticiones de la MAC
    for (int i = 6; i < magicPacket.length; i += macBytes.length) {
      System.arraycopy(macBytes, 0, magicPacket, i, macBytes.length);
    }

    // Resuelve la dirección IP de broadcast
    InetAddress address = InetAddress.getByName(broadcastIP);

    /**
     * Se envía el MagicPacket con la dirección MAC y la dirección IP de broadcast usando un
     * DatagramPacket que se envía a través de un DatagramSocket usando UDP (User Datagram
     * Protocol).*
     */
    DatagramPacket packet = new DatagramPacket(magicPacket, magicPacket.length, address, PORT);
    DatagramSocket socket = new DatagramSocket();

    try {
      socket.send(packet);
      System.out.println("Magic Packet enviado a " + macAddress + " vía " + broadcastIP);
    } catch (Exception e) {
      throw new Exception("Error al enviar el Magic Packet", e);
    } finally {
      socket.close();
    }
  }

  private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
    String[] hex = macStr.split("[:\\-]");
    if (hex.length != 6) {
      throw new IllegalArgumentException("Dirección MAC no válida");
    }

    byte[] macBytes = new byte[6];
    for (int i = 0; i < 6; i++) {
      macBytes[i] = (byte) Integer.parseInt(hex[i], 16);
    }
    return macBytes;
  }

  public static void main(String[] args) {
    try {
      sendMagicPacket("04:42:1A:E9:46:19", "192.168.1.255"); // Cambia la MAC y la IP de broadcast
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
