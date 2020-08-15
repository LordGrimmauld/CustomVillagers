package mod.grimmauld.custom_villagers;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CustomVillagers.MODID)
@SuppressWarnings("unused")
public class CustomVillagers {
    public static final String MODID = "custom_villagers";
    public static final String VERSION = "0.1";
    private static final Logger LOGGER = LogManager.getLogger(MODID);

    public CustomVillagers() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onVillagerProffesionsRegistry(final RegistryEvent.Register<VillagerProfession> villagerProfessionRegistryEvent) {
            LOGGER.info("HELLO from Register Professions");
        }
    }
}
