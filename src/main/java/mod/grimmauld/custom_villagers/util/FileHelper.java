package mod.grimmauld.custom_villagers.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mod.grimmauld.custom_villagers.CustomVillagers;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    private static final File ROOT = new File("/");
    private static final File VILLAGER_ROOT = new File("villagers");
    public static final File VILLAGER_DIR = new File("villagers/villagers");
    public static final File VILLAGER_SOUNDS = new File("villagers/sounds");
    public static final File VILLAGER_TEXTURES = new File("villagers/textures");
    public static final File VILLAGER_LANG = new File("villagers/lang");

    private static final JsonParser parser = new JsonParser();

    public static List<File> findAllFilesOfType(File path, String fileEnding) {
        List<File> fileList = new ArrayList<>();
        findFilesOfType(path, fileList, fileEnding);
        return fileList;
    }

    private static void findFilesOfType(File path, List<File> files, String fileEnding) {
        if (path.isDirectory()) {
            File[] next = path.listFiles();
            if (next == null)
                return;
            for (File file : next) {
                if (file.isDirectory()) {
                    findFilesOfType(file, files, fileEnding);
                } else {
                    if (file.getName().toLowerCase().endsWith(fileEnding)) {
                        files.add(file);
                    }
                }
            }
        }
    }

    public static List<File> findAllFilesWithName(File path, String name) {
        List<File> fileList = new ArrayList<>();
        findFilesWithName(path, fileList, name);
        return fileList;
    }

    private static void findFilesWithName(File path, List<File> files, String name) {
        if (path.isDirectory()) {
            File[] next = path.listFiles();
            if (next == null)
                return;
            for (File file : next) {
                if (file.isDirectory()) {
                    findFilesOfType(file, files, name);
                } else {
                    if (file.getName().equals(name)) {
                        files.add(file);
                    }
                }
            }
        }
    }

    @Nullable
    public static JsonObject openAsJson (File path) {
        CustomVillagers.LOGGER.info(String.format("Loading json file %s", path.toString()));
        try {
            return parser.parse(new FileReader(path)).getAsJsonObject();
        } catch (FileNotFoundException e) {
            CustomVillagers.LOGGER.error(String.format("Could not open file %s: %s", path.toString(), e.toString()));
            return null;
        } catch (IllegalStateException e) {
            CustomVillagers.LOGGER.error(String.format("Could not read content of file %s as json: %s", path.toString(), e.toString()));
            return null;
        }
    }

    public static void checkFileStructure() {
        boolean successful = true;
        if(ROOT.canWrite()) {
            if (!VILLAGER_ROOT.exists())
                successful = VILLAGER_ROOT.mkdir();
            if (!VILLAGER_DIR.exists())
                successful &= VILLAGER_DIR.mkdir();
            if (!VILLAGER_LANG.exists())
                successful &= VILLAGER_LANG.mkdir();
            if (!VILLAGER_TEXTURES.exists())
                successful &= VILLAGER_TEXTURES.mkdir();
            if (!VILLAGER_SOUNDS.exists())
                successful &= VILLAGER_SOUNDS.mkdir();
        } else {
            successful = false;
        }

        if (!successful)
            CustomVillagers.LOGGER.warn("Could not validate or create custom villager file structure");
    }
}
