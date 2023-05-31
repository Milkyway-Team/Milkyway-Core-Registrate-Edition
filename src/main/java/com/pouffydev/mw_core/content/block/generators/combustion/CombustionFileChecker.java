package com.pouffydev.mw_core.content.block.generators.combustion;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CombustionFileChecker {
    public static String minecraftDir = Minecraft.getInstance().gameDirectory.getAbsolutePath();

    // Get the path to the combustion_sources folder
    public static String combustionSourcesPath = minecraftDir + "/data/mw_core/combustion_sources/";

    // Access the folder
    public static File combustionSourcesFolder = new File(combustionSourcesPath);
    public static boolean checkJsonFile(String filename) {
            // Get the path to the json file
            String jsonPath = minecraftDir + "/data/mw_core/combustion_sources/" + filename;

            // Read the contents of the file
            String jsonContents = "";
            try {
                jsonContents = new String(Files.readAllBytes(Paths.get(jsonPath)));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Parse the json contents and check for the required fields
            JsonObject jsonObject = new Gson().fromJson(jsonContents, JsonObject.class);
            if (!jsonObject.has("Block") || !jsonObject.has("Blockstate") || !jsonObject.has("Speed")) {
                return false;
            }

            // Check the types of the fields
            if (!(jsonObject.get("Block") instanceof JsonPrimitive) ||
                    !(jsonObject.get("Blockstate") instanceof JsonPrimitive) ||
                    !(jsonObject.get("Speed") instanceof JsonPrimitive)) {
                return false;
            }

            if (!(((JsonPrimitive) jsonObject.get("Block")).isString()) ||
                    !(((JsonPrimitive) jsonObject.get("Blockstate")).isString()) ||
                    !(jsonObject.get("Speed").isJsonPrimitive() && jsonObject.get("Speed").getAsJsonPrimitive().isNumber())) {
                return false;
            }

            return true;
        }
}
