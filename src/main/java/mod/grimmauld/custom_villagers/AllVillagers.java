package mod.grimmauld.custom_villagers;

import com.google.gson.JsonObject;
import mod.grimmauld.custom_villagers.util.FileHelper;
import mod.grimmauld.custom_villagers.util.LazyPointOfInterestType;
import mod.grimmauld.custom_villagers.util.LazyVillagerProfession;
import mod.grimmauld.custom_villagers.util.TextureHelper;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CustomVillagers.MODID)
public class AllVillagers {
    private static final ArrayList<LazyPointOfInterestType> pointOfInterestTypes = new ArrayList<>();
    private static final ArrayList<LazyVillagerProfession> villagerProfessions = new ArrayList<>();


    public static void addVillager(@Nullable ResourceLocation professionBlock, ResourceLocation id, @Nullable SoundEvent workSound) {
        LazyPointOfInterestType pointOfInterestType = new LazyPointOfInterestType(id, professionBlock);
        pointOfInterestTypes.add(pointOfInterestType);
        villagerProfessions.add(new LazyVillagerProfession(id, pointOfInterestType, workSound));
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onVillagerPOIRegistry(final RegistryEvent.Register<PointOfInterestType> pointOfInterestTypeRegistryEvent) {
        pointOfInterestTypes.forEach(lazyPointOfInterestType -> pointOfInterestTypeRegistryEvent.getRegistry().register(lazyPointOfInterestType.getOrCreate()));
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onVillagerProfessionsRegistry(final RegistryEvent.Register<VillagerProfession> villagerProfessionRegistryEvent) {
        villagerProfessions.forEach(lazyVillagerProfession -> villagerProfessionRegistryEvent.getRegistry().register(lazyVillagerProfession.getOrCreate()));
    }

    static void loadVillagers() {
        FileHelper.findAllFiles(FileHelper.VILLAGER_DIR, ".json").forEach(file -> {
            JsonObject json = FileHelper.openAsJson(file);
            if (json == null)
                return;

            addVillager(new ResourceLocation(JSONUtils.getString(json, "profession_block")), new ResourceLocation(JSONUtils.getString(json, "id")), null);
        });
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unused")
    public static void reloadEvent(ModelRegistryEvent event) {
        TextureHelper.loadProfessionTextures();
    }
}
