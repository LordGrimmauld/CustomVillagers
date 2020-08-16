package mod.grimmauld.custom_villagers.util;

import mod.grimmauld.custom_villagers.CustomVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.FileInputStream;
import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class TextureHelper {
    private static final Minecraft MC = Minecraft.getInstance();
    private static final ArrayList<ResourceLocation> professionTextureLocations = new ArrayList<>();

    public static void loadProfessionTextures() {
        professionTextureLocations.forEach(resourceLocation -> MC.getTextureManager().deleteTexture(resourceLocation));
        professionTextureLocations.clear();
        FileHelper.findAllFiles(FileHelper.VILLAGER_TEXTURES, ".png").forEach(file -> {
            try {
                ResourceLocation resourceLocation = new ResourceLocation(CustomVillagers.MODID, "textures/entity/villager/profession/" + file.getName());
                professionTextureLocations.add(resourceLocation);
                MC.getTextureManager().registerTexture(resourceLocation, new DynamicTexture(NativeImage.read(new FileInputStream(file))));
            } catch (Exception e) {
                CustomVillagers.LOGGER.error(String.format("Could not load texture %s: %s", file.toString(), e.toString()));
            }
        });
    }
}
