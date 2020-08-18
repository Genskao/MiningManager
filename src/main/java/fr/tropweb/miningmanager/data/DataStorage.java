package fr.tropweb.miningmanager.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.tropweb.miningmanager.engine.BlockEngine;
import fr.tropweb.miningmanager.pojo.BlockLite;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.HashSet;
import java.util.UUID;

public class DataStorage {
    private static final Gson JSON = new GsonBuilder().create();
    private static final String DATA_PATH = "/data/";

    private final Plugin plugin;
    private final String dataFolder;

    public DataStorage(Plugin plugin) {
        this.plugin = plugin;
        this.dataFolder = plugin.getDataFolder().getPath() + DATA_PATH;

        new File(dataFolder).mkdirs();
    }

    public HashSet<BlockLite> reload(boolean placed, boolean delete) {

        // list files on data folder
        final File[] files = new File(dataFolder).listFiles();

        // prepare list of BlockLite
        final HashSet<BlockLite> blockLites = new HashSet<>();

        // check all files
        for (int i = 0; i < files.length; i++) {

            // get file
            final File file = files[i];

            // don't play with json file
            if (!file.getAbsolutePath().endsWith(".json"))
                continue;

            // get BlockLite from file
            final BlockLite blockLite = loadJson(file);

            // maybe error, no data or data corrupted
            if (blockLite == null || blockLite.isEmpty()) {
                System.out.println(String.format("The file %s is corrupted.", file.getName()));
                continue;
            }

            // check if the block was the good status
            if (blockLite.getPlacedByPlayer() == placed) {

                // if it's not already in the list
                if (!BlockEngine.contains(blockLites, blockLite)) {
                    blockLites.add(blockLite);

                }

                // optional if the owner want delete double files (if exist)
                else if (delete) {
                    file.delete();
                }
            }

        }
        return blockLites;
    }

    public void saveBlockRemoved(BlockLite blockLite) {
        saveJson(blockLite);
    }

    public void saveBlockPlaced(BlockLite blockLite) {
        saveJson(blockLite);
    }

    public void deleteBlock(BlockLite blockLite) {
        final String data = this.getData(blockLite);
        final String name = this.getFilename(data);

        final File file = this.getFile(name);
        if (file.exists() && file.delete())
            System.out.println("The file %s has been deleted.");
        else
            System.out.println("The file %s has not been deleted.");
    }

    public BlockLite loadJson(File file) {
        try (final FileInputStream fileInputStream = new FileInputStream(file);
             final Reader inputStreamReader = new InputStreamReader(fileInputStream)) {
            return JSON.fromJson(inputStreamReader, BlockLite.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void saveJson(BlockLite blockLite) {
        final String data = this.getData(blockLite);
        final String name = this.getFilename(data);

        try (final FileOutputStream fileOutputStream = new FileOutputStream(this.getFile(name))) {
            fileOutputStream.write(data.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private File getFile(final String name) {
        return new File(dataFolder + name + ".json");
    }

    private String getFilename(final String data) {
        return UUID.nameUUIDFromBytes(data.getBytes()).toString();
    }

    private String getData(final BlockLite blockLite) {
        return JSON.toJson(blockLite);
    }
}
