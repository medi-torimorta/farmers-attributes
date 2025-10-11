package medi.makiba.farmers_attributes.compat.puffish_skills.implementation;

import medi.makiba.farmers_attributes.compat.puffish_skills.IPuffishSkillsCompat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.puffish.skillsmod.api.SkillsAPI;
import net.puffish.skillsmod.experience.source.builtin.BreakBlockExperienceSource;

public class PuffishSkillsCompat implements IPuffishSkillsCompat {

    @Override
    public void addBreakExp(ServerPlayer player, BlockState blockState, ItemStack tool) {
        SkillsAPI.updateExperienceSources(
		player,
		BreakBlockExperienceSource.class,
		experienceSource -> experienceSource.getValue(player, blockState, tool)
    );
    }
}
