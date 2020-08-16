package mod.grimmauld.custom_villagers.util;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LazyVillagerProfession {
    private final ResourceLocation id;
    private final LazyPointOfInterestType lazyPointOfInterestType;
    private final SoundEvent workSound;
    private VillagerProfession villagerProfession = null;

    public LazyVillagerProfession(ResourceLocation id, LazyPointOfInterestType lazyPointOfInterestType, @Nullable SoundEvent workSound) {
        this.id = id;
        this.lazyPointOfInterestType = lazyPointOfInterestType;
        this.workSound = workSound;
    }

    @Nonnull
    public VillagerProfession getOrCreate() {
        if (villagerProfession == null)
            villagerProfession = new VillagerProfession(id.toString(), lazyPointOfInterestType.getOrCreate(), ImmutableSet.of(), ImmutableSet.of(), workSound).setRegistryName(id);
        return villagerProfession;
    }
}
