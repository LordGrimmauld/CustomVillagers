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

    public static List<File> findAllFiles(File path, String fileEnding) {
        List<File> fileList = new ArrayList<>();
        findJsonFiles(path, fileList, fileEnding);
        return fileList;
    }

    public static void findJsonFiles(File path, List<File> files, String fileEnding) {
        if (path.isDirectory()) {
            File[] next = path.listFiles();
            if (next == null)
                return;
            for (File file : next) {
                if (file.isDirectory()) {
                    findJsonFiles(file, files, fileEnding);
                } else {
                    if (file.getName().toLowerCase().endsWith(fileEnding)) {
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
        }
    }

    public static void checkFileStructure() {
        if(!ROOT.canWrite())
            return;
        if(!VILLAGER_ROOT.exists())
            VILLAGER_ROOT.mkdir();
        if(!VILLAGER_DIR.exists())
            VILLAGER_DIR.mkdir();
        if(!VILLAGER_LANG.exists())
            VILLAGER_LANG.mkdir();
        if(!VILLAGER_TEXTURES.exists())
            VILLAGER_TEXTURES.mkdir();
        if(!VILLAGER_SOUNDS.exists())
            VILLAGER_SOUNDS.mkdir();
    }
}
