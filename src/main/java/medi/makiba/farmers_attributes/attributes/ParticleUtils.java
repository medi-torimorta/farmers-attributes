package medi.makiba.farmers_attributes.attributes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class ParticleUtils {
    public static void spawnParticleInBlock(ServerPlayer player, BlockPos pos, int count, ParticleOptions particle) {
        ServerLevel level = player.serverLevel();
        BlockState blockstate = level.getBlockState(pos);
        double d1 = blockstate.isAir() ? 1.0 : blockstate.getShape(level, pos).max(Direction.Axis.Y);
        spawnParticles(player, pos, count, 0.5, d1, true, particle);
    }

    public static void spawnParticles(
        ServerPlayer player, BlockPos pos, int count, double xzSpread, double ySpread, boolean allowInAir, ParticleOptions particle
    ) {
        ServerLevel level = player.serverLevel();
        RandomSource randomsource = level.getRandom();

        for (int i = 0; i < count; i++) {
            double d0 = randomsource.nextGaussian() * 0.02;
            double d1 = randomsource.nextGaussian() * 0.02;
            double d2 = randomsource.nextGaussian() * 0.02;
            double d3 = 0.5 - xzSpread;
            double d4 = (double)pos.getX() + d3 + randomsource.nextDouble() * xzSpread * 2.0;
            double d5 = (double)pos.getY() + randomsource.nextDouble() * ySpread;
            double d6 = (double)pos.getZ() + d3 + randomsource.nextDouble() * xzSpread * 2.0;
            if (allowInAir || !level.getBlockState(BlockPos.containing(d4, d5, d6).below()).isAir()) {
                level.sendParticles(player, ParticleTypes.HAPPY_VILLAGER, false, d4, d5, d6, 2, d0, d1, d2, 0.5);
            }
        }
    }
}
