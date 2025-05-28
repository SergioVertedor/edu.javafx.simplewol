package edu.javafx.simplewol.model;

import java.util.Objects;

/** This class represents a magic packet that is sent to a target device to wake it up. */
public class MagicPacket {

  private String alias;
  private String ip;
  private String subnetMask;
  private String mac;
  private String port;
  private String broadcastAddress;

  public MagicPacket(String alias, String ip, String subnetMask, String mac, String magicPacketPort,
      String broadcastAddress) {
    this.alias = alias;
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

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void setSubnetMask(String subnetMask) {
    this.subnetMask = subnetMask;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public void setBroadcastAddress(String broadcastAddress) {
    this.broadcastAddress = broadcastAddress;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    MagicPacket that = (MagicPacket) o;
    return Objects.equals(alias, that.alias) && Objects.equals(ip, that.ip)
        && Objects.equals(subnetMask, that.subnetMask) && Objects.equals(mac, that.mac)
        && Objects.equals(port, that.port);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, ip, subnetMask, mac, port);
  }

}
