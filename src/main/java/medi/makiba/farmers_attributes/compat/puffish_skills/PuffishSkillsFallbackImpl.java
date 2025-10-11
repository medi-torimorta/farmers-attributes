package medi.makiba.farmers_attributes.compat.puffish_skills;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class PuffishSkillsFallbackImpl implements IPuffishSkillsCompat {

    @Override
    public void addBreakExp(ServerPlayer player, BlockState blockState, ItemStack tool) {
        // NO-OP
    }
}
