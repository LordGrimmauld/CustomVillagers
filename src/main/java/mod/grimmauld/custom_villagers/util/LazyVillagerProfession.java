package mod.grimmauld.custom_villagers.util;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LazyVillagerProfession {
    private final ResourceLocation id;
    private final LazyPointOfInterestType lazyPointOfInterestType;
    private final ResourceLocation workSoundId;
    private VillagerProfession villagerProfession = null;

    public LazyVillagerProfession(ResourceLocation id, LazyPointOfInterestType lazyPointOfInterestType, @Nullable ResourceLocation workSoundId) {
        this.id = id;
        this.lazyPointOfInterestType = lazyPointOfInterestType;
        this.workSoundId = workSoundId;
    }

    @Nonnull
    public VillagerProfession getOrCreate() {
        if (villagerProfession == null) {
            SoundEvent workSound = workSoundId != null && ForgeRegistries.SOUND_EVENTS.containsKey(workSoundId) ? ForgeRegistries.SOUND_EVENTS.getValue(workSoundId) : null;
            villagerProfession = new VillagerProfession(id.toString(), lazyPointOfInterestType.getOrCreate(), ImmutableSet.of(), ImmutableSet.of(), workSound).setRegistryName(id);
        }
        return villagerProfession;
    }
}
