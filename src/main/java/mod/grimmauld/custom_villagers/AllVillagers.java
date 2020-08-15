package mod.grimmauld.custom_villagers;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.block.Block;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CustomVillagers.MODID)
public class AllVillagers {
    private static final ArrayList<PointOfInterestType> pointOfInterestTypes = new ArrayList<>();
    private static final ArrayList<VillagerProfession> villagerProfessions = new ArrayList<>();

    public static void addVillager(@Nullable Block professionBlock, ResourceLocation id, @Nullable SoundEvent workSound) {
        PointOfInterestType pointOfInterestType = new PointOfInterestType(id.toString(), professionBlock == null ? ImmutableSet.of() : PointOfInterestType.getAllStates(professionBlock), 1, 1).setRegistryName(id);
        PointOfInterestType.func_221052_a(pointOfInterestType);
        pointOfInterestTypes.add(pointOfInterestType);
        villagerProfessions.add(new VillagerProfession(id.toString(), pointOfInterestType, ImmutableSet.of(), ImmutableSet.of(), workSound).setRegistryName(id));
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onVillagerProffesionsRegistry(final RegistryEvent.Register<VillagerProfession> villagerProfessionRegistryEvent) {
        villagerProfessions.forEach(villagerProfessionRegistryEvent.getRegistry()::register);
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onVillagerPOIRegistry(final RegistryEvent.Register<PointOfInterestType> pointOfInterestTypeRegister) {
        pointOfInterestTypes.forEach(pointOfInterestTypeRegister.getRegistry()::register);
    }

    static void load() {
        List<File> fileList = getVillagerFiles();
        JsonParser parser = new JsonParser();
        fileList.forEach(file -> {
            CustomVillagers.LOGGER.info(String.format("Loading villager from file %s", file.toString()));
            JsonObject json;
            try {
                json = parser.parse(new FileReader(file)).getAsJsonObject();
            } catch (FileNotFoundException e) {
                CustomVillagers.LOGGER.error(String.format("Could not open file %s: %s", file.toString(), e.toString()));
                return;
            }

            final Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JSONUtils.getString(json, "profession_block")));
            final ResourceLocation id = new ResourceLocation(JSONUtils.getString(json, "id"));

            addVillager(block, id, null);
        });
    }

    private static List<File> getVillagerFiles() {
        List<File> fileList = new ArrayList<>();
        findVillagerFiles(CustomVillagers.VILLAGER_DIR, fileList);
        return fileList;
    }

    private static void findVillagerFiles(File path, List<File> files) {
        if (path.isDirectory()) {
            File[] next = path.listFiles();
            if (next == null)
                return;
            for (File file : next) {
                if (file.isDirectory()) {
                    findVillagerFiles(file, files);
                } else {
                    if (file.getName().toLowerCase().endsWith(".json")) {
                        files.add(file);
                    }
                }
            }
        }
    }
}
