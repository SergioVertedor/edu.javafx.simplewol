package edu.javafx.simplewol.model;

import java.net.UnknownHostException;

/** This class represents a magic packet that is sent to a target device to wake it up. */
public class MagickPacket {

  private String ip;
  private String subnetMask;
  private String mac;
  private String port;
  private String broadcastAddress;

  public MagickPacket(String ip, String subnetMask, String mac, String magicPacketPort, String broadcastAddress)
      throws UnknownHostException {
    this.ip = ip;
    this.subnetMask = subnetMask;
    this.mac = mac;
    this.port = magicPacketPort;
    this.broadcastAddress = broadcastAddress;
  }

  public String getIp() {
    return ip;
  }

  public String getSubnetMask() {
    return subnetMask;
  }

  public String getMac() {
    return mac;
  }

  public String getPort() {
    return port;
  }

  public String getBroadcastAddress() {
    return broadcastAddress;
  }
}
