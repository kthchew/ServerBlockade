package io.github.kthchew.serverblockade;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ServerBlockadeConfig {
    private boolean whitelist;
    private final ArrayList<String> serverAddressList;
    public static final String CONFIG_LOCATION = "./config/ServerBlockade.json";

    private ServerBlockadeConfig() {
        this.whitelist = false;
        this.serverAddressList = new ArrayList<>();
    }

    public static ServerBlockadeConfig load(File file) {
        ServerBlockadeConfig config = new ServerBlockadeConfig();
        if (file.exists()) {
            try {
                JsonObject configFromFile = (JsonObject) JsonParser.parseReader(new FileReader(file));
                config.whitelist = configFromFile.get("whitelist").getAsBoolean();
                for (JsonElement address : configFromFile.get("serverAddressList").getAsJsonArray()) {
                    config.serverAddressList.add(address.getAsString());
                }
            } catch (Exception e) {
                ServerBlockade.LOGGER.warn("The config file could not be read.");
                e.printStackTrace();
            }
        } else {
            try {
                config.save(new File(CONFIG_LOCATION));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return config;
    }

    public void save(File file) {
        try {
            JsonWriter writer = new JsonWriter(new FileWriter(ServerBlockadeConfig.CONFIG_LOCATION));
            writer.setIndent("    ");
            writer.beginObject();
            writer.name("whitelist").value(this.whitelist);
            writer.name("serverAddressList").beginArray();
            for (String address : serverAddressList) {
                writer.value(address);
            }
            writer.endArray();
            writer.endObject();
            writer.close();
        } catch (IOException e) {
            ServerBlockade.LOGGER.error("Failed to save config file.");
            e.printStackTrace();
        }
    }

    public boolean isServerAllowed(String serverAddress) {
        boolean isInList = serverAddressList.contains(serverAddress);
        return whitelist ? isInList : !isInList;
    }
}
