package medi.makiba.farmers_attributes.datagen;

import medi.makiba.farmers_attributes.FarmersAttributes;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class FALangProviderBase extends LanguageProvider{
    private String modid;

    public FALangProviderBase(PackOutput output, String locale) {
        super(output, FarmersAttributes.MODID, locale);
        this.modid = FarmersAttributes.MODID;
    }


    protected void addConfigTitle(String name) {
        add(this.modid + ".configuration.title", name);
    }

    protected void addConfigLabelsCommon(String sectionName, String sectionTitle) {
        add(this.modid + ".configuration.section." + this.modid.replace("_", ".")  + ".common.toml", sectionName);
        add(this.modid + ".configuration.section." + this.modid.replace("_", ".") + ".common.toml.title", sectionTitle);
    }

    protected void addConfigLabelsServer(String sectionName, String sectionTitle) {
        add(this.modid + ".configuration.section." + this.modid.replace("_", ".") + ".server.toml", sectionName);
        add(this.modid + ".configuration.section." + this.modid.replace("_", ".") + ".server.toml.title", sectionTitle);
    }

    protected void addConfigGroup(String configName, String name) {
        add(this.modid + ".configuration." + configName, name);
    }

    protected void addConfig(String configName, String name) {
		add("config." + this.modid + "." + configName, name);
	}

    protected void addTag(String tagName, String name) {
        add("tag." + this.modid + "." + tagName, name);
    }

    protected void addAttribute(String attributeName, String name, String Description) {
        add("attributes." + this.modid + "." + attributeName, name);
        add("attributes." + this.modid + "." + attributeName + ".desc", Description);
    }

    protected void addEffect(String effectName, String name, String Description) {
        add("effect." + this.modid + "." + effectName, name);
        add("effect." + this.modid + "." + effectName + ".description", Description);
    }
}


