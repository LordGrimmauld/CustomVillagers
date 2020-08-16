package mod.grimmauld.custom_villagers.util;

import com.google.gson.JsonObject;
import mod.grimmauld.custom_villagers.CustomVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.LanguageMap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LangHelper {
    private static final Minecraft MC = Minecraft.getInstance();
    private static final String testFlagId = new ResourceLocation(CustomVillagers.MODID, "dynamic_lang_loaded").toString();

    public static void loadLangFiles() {
        if(LanguageMap.getInstance().translateKey(testFlagId).equals("true"))
            return;
        Language lang = MC.getLanguageManager().getCurrentLanguage();
        String lang_code = lang != null ? lang.getCode() : "en_us";
        FileHelper.findAllFilesWithName(FileHelper.VILLAGER_LANG, lang_code + ".json").forEach(file -> {
            JsonObject jsonObject = FileHelper.openAsJson(file);
            if (jsonObject != null)
                jsonObject.entrySet().forEach(entry -> LanguageMap.getInstance().languageList.put((entry.getKey().startsWith("entity.minecraft.villager.") ? entry.getKey() : "entity.minecraft.villager." + entry.getKey()).replace(":", "."), entry.getValue().getAsString()));
        });
        LanguageMap.getInstance().languageList.put(testFlagId, "true");
    }
}
