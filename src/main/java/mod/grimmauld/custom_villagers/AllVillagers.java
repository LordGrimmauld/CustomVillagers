package mod.grimmauld.custom_villagers;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CustomVillagers.MODID)
@SuppressWarnings("unused")
public class AllVillagers {
    private static final ArrayList<PointOfInterestType> pointOfInterestTypes = new ArrayList<>();
    private static final ArrayList<VillagerProfession> villagerProfessions = new ArrayList<>();

    public static void addVillager(Block professionBlock, ResourceLocation id, @Nullable SoundEvent soundEvent) {
        PointOfInterestType pointOfInterestType = new PointOfInterestType(id.toString(), PointOfInterestType.getAllStates(professionBlock), 1, 1).setRegistryName(id);
        pointOfInterestTypes.add(pointOfInterestType);
        villagerProfessions.add(new VillagerProfession(id.toString(), pointOfInterestType, ImmutableSet.of(), ImmutableSet.of(), soundEvent).setRegistryName(id));
    }

    @SubscribeEvent
    public static void onVillagerProffesionsRegistry(final RegistryEvent.Register<VillagerProfession> villagerProfessionRegistryEvent) {
        villagerProfessions.forEach(villagerProfessionRegistryEvent.getRegistry()::register);
    }

    @SubscribeEvent
    public static void onVillagerPOIRegistry(final RegistryEvent.Register<PointOfInterestType> pointOfInterestTypeRegister) {
        pointOfInterestTypes.forEach(pointOfInterestTypeRegister.getRegistry()::register);
    }

    static void load() {
        addVillager(Blocks.CRAFTING_TABLE, new ResourceLocation(CustomVillagers.MODID, "arborist"), null);
    }
}
