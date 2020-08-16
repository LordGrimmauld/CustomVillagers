package mod.grimmauld.custom_villagers;

import com.google.gson.JsonObject;
import mod.grimmauld.custom_villagers.util.*;
import net.minecraft.client.gui.screen.inventory.MerchantScreen;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CustomVillagers.MODID)
public class AllVillagers {
    private static final ArrayList<LazyPointOfInterestType> pointOfInterestTypes = new ArrayList<>();
    private static final ArrayList<LazyVillagerProfession> villagerProfessions = new ArrayList<>();


    public static void addVillager(@Nullable ResourceLocation professionBlock, ResourceLocation id, @Nullable ResourceLocation workSoundId) {
        LazyPointOfInterestType pointOfInterestType = new LazyPointOfInterestType(id, professionBlock);
        pointOfInterestTypes.add(pointOfInterestType);
        villagerProfessions.add(new LazyVillagerProfession(id, pointOfInterestType, workSoundId));
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
        FileHelper.findAllFilesOfType(FileHelper.VILLAGER_DIR, ".json").forEach(file -> {
            JsonObject json = FileHelper.openAsJson(file);
            if (json == null)
                return;
            final ResourceLocation workSoundId = json.has("work_sound") ? new ResourceLocation(JSONUtils.getString(json, "work_sound")) : null;
            addVillager(new ResourceLocation(JSONUtils.getString(json, "profession_block")), new ResourceLocation(JSONUtils.getString(json, "id")), workSoundId);
        });
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unused")
    public static void textureReloadEvent(ModelRegistryEvent event) {
        TextureHelper.loadProfessionTextures();
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    @OnlyIn(Dist.CLIENT)
    public static void loadSoundsEvent(SoundLoadEvent event) {
        SoundHelper.loadWorkSounds();
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unused")
    public static void setupEvent(GuiOpenEvent event) {
        if (event.getGui() instanceof MerchantScreen)
            LangHelper.loadLangFiles();
    }
}
