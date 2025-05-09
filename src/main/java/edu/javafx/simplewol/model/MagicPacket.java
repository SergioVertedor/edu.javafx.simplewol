package edu.javafx.simplewol.model;

import java.net.UnknownHostException;
import java.util.Objects;

/** This class represents a magic packet that is sent to a target device to wake it up. */
public class MagicPacket {

  private String alias;
  private String ip;
  private String subnetMask;
  private String mac;
  private String port;
  private String broadcastAddress;

  public MagicPacket(String ip, String subnetMask, String mac, String magicPacketPort,
      String broadcastAddress) {
    this.ip = ip;
    this.subnetMask = subnetMask;
    this.mac = mac;
    this.port = magicPacketPort;
    this.broadcastAddress = broadcastAddress;
  }

  public String getAlias() {
    return alias;
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

  public void setAlias(String alias) {
    this.alias = alias;
  }

}
