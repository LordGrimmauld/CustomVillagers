package mod.grimmauld.custom_villagers.util;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class LazyPointOfInterestType {
    private final ResourceLocation blockId;
    private final ResourceLocation id;
    private PointOfInterestType pointOfInterestType = null;

    public LazyPointOfInterestType(ResourceLocation id, ResourceLocation blockId) {
        this.id = id;
        this.blockId = blockId;
    }

    @Nonnull
    public PointOfInterestType getOrCreate() {
        if (pointOfInterestType == null) {
            Block professionBlock = blockId != null && ForgeRegistries.BLOCKS.containsKey(blockId) ? ForgeRegistries.BLOCKS.getValue(blockId) : null;
            pointOfInterestType = new PointOfInterestType(id.toString(), professionBlock == null ? ImmutableSet.of() : PointOfInterestType.getAllStates(professionBlock), 1, 1).setRegistryName(id);
            PointOfInterestType.func_221052_a(pointOfInterestType);
        }
        return pointOfInterestType;
    }
}
