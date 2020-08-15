package mod.grimmauld.custom_villagers;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(CustomVillagers.MODID)
public class CustomVillagers {
    public static final String MODID = "custom_villagers";
    public static final String VERSION = "0.1";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final File VILLAGER_DIR = new File("villagers");

    public CustomVillagers() {
        MinecraftForge.EVENT_BUS.register(AllVillagers.class);
        AllVillagers.load();
    }
}
