package mod.grimmauld.custom_villagers.util;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoundHelper {
    private static final Minecraft MC = Minecraft.getInstance();

    public static void loadWorkSounds() {
        FileHelper.findAllFiles(FileHelper.VILLAGER_SOUNDS, ".ogg").forEach(file -> {

        });

        /*
        FileHelper.findAllFiles(FileHelper.VILLAGER_TEXTURES, ".png").forEach(file -> {
            try {
                ResourceLocation resourceLocation = new ResourceLocation(CustomVillagers.MODID, "textures/entity/villager/profession/" + file.getName());
                professionTextureLocations.add(resourceLocation);
                MC.getTextureManager().registerTexture(resourceLocation, new DynamicTexture(NativeImage.read(new FileInputStream(file))));
            } catch (Exception e) {
                CustomVillagers.LOGGER.error(String.format("Could not load texture %s: %s", file.toString(), e.toString()));
            }
        });*/
    }
}
