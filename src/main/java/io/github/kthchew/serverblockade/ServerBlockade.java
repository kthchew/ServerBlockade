package io.github.kthchew.serverblockade;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ServerBlockade implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static ServerBlockadeConfig CONFIG;

    @Override
    public void onInitialize() {
        CONFIG = ServerBlockadeConfig.load(new File(ServerBlockadeConfig.CONFIG_LOCATION));
    }
}
