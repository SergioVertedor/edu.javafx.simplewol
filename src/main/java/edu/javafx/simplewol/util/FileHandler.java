package edu.javafx.simplewol.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import edu.javafx.simplewol.model.MagicPacket;

public class FileHandler {

  private static File file;

  public FileHandler() {
    file = new File(Constantes.FILE_PATH);
  }

  /**
   * Creates a new file if it does not exist.
   */
  public void create() {
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Writes a MagicPacket object to a file.
   * 
   * @param magicPacket
   */
  public void write(MagicPacket magicPacket) {
    try (FileWriter writer = new FileWriter(Constantes.FILE_PATH, true)) {
      StringBuilder sb = new StringBuilder();
      sb.append(magicPacket.getAlias()).append(";").append(magicPacket.getIp()).append(";")
          .append(magicPacket.getSubnetMask()).append(";").append(magicPacket.getMac()).append(";")
          .append(magicPacket.getPort()).append("\n");
      writer.write(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the file line by line and creates MagicPacket objects.
   * 
   * @return
   */
  public List<MagicPacket> read() {
    List<MagicPacket> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;

      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(";");
        if (parts.length == 5) {
          String alias = parts[0];
          String ip = parts[1];
          String subnetMask = parts[2];
          String mac = parts[3];
          String port = parts[4];

          // Validate the input data
          InputValidator inputValidator = new InputValidator();
          inputValidator.validateIp(ip);
          inputValidator.validateMac(mac);
          inputValidator.validatePort(port);
          inputValidator.validateSubnetMask(subnetMask);
          inputValidator.validateAlias(alias);

          if (InputValidator.errors.isEmpty()) {
            // Create a MagicPacket object and add it to the list
            MagicPacket magicPacket = new MagicPacket(alias, ip, subnetMask, mac, port,
                BroadcastCalc.calculateBroadcastAddress(ip, subnetMask));
            magicPacket.setAlias(alias);
            lines.add(magicPacket);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      System.err.println("Validation error: " + e.getMessage());
    }
    return lines;
  }

  /**
   * Deletes the line from MagicPacket selected on table.
   */
  public void delete(MagicPacket magicPacket) {
    List<MagicPacket> lines = read();
    lines.remove(magicPacket);
    try (FileWriter writer = new FileWriter(file)) {
      for (MagicPacket line : lines) {
        StringBuilder sb = new StringBuilder();
        sb.append(line.getAlias()).append(";").append(line.getIp()).append(";")
            .append(line.getSubnetMask()).append(";").append(line.getMac()).append(";")
            .append(line.getPort()).append("\n");
        writer.write(sb.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
