package medi.makiba.farmers_attributes.mixin;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import medi.makiba.farmers_attributes.FarmersAttributes;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;

public class MixinPlugin implements IMixinConfigPlugin {
    private static final String FARMERS_DELIGHT_ID = "farmersdelight";
    private String ownPackage;
    private boolean farmersDelightInstalled;
    
    @Override
    public void onLoad(String mixinPackage)
    {
        ownPackage = mixinPackage;
        
        farmersDelightInstalled = isModLoaded(FARMERS_DELIGHT_ID);
        if (farmersDelightInstalled) {
            FarmersAttributes.LOGGER.info("Farmer's Delight detected, enabling compatibility mixins.");
        }
    }

    private static Boolean isModLoaded(String modid) {
        return ModList.get() != null
                ? ModList.get().isLoaded(modid)
                : LoadingModList.get().getModFileById(modid) != null;
    }
    
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
    {
        var mixinShortName = mixinClassName.substring(ownPackage.length() + 1);
        if (mixinShortName.startsWith(FARMERS_DELIGHT_ID)){
            return farmersDelightInstalled;
        }
        return true;
    }
    
    
    @Override
    public String getRefMapperConfig()
    {
        return null;
    }
    
    @Override
    public List<String> getMixins()
    {
        return null;
    }
    
    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }
    
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
    
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }

}
