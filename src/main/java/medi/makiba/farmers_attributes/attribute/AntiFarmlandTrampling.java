package medi.makiba.farmers_attributes.attribute;

import medi.makiba.farmers_attributes.FAConfig;
import medi.makiba.farmers_attributes.registry.FAAttributes;
import net.minecraft.server.level.ServerPlayer;

public class AntiFarmlandTrampling {
    public static boolean checkCancelTrample(ServerPlayer player){
        return FAConfig.FORCE_ANTI_FARMLAND_TRAMPLING.get() || player.getAttributeValue(FAAttributes.ANTI_FARMLAND_TRAMPLING) != 0;
    }
}
