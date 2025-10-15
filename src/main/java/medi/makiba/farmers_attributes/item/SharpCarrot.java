package medi.makiba.farmers_attributes.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.SimpleTier;

public class SharpCarrot extends SwordItem {
    private static int BITE_SIZE = 80;
    private static Tier tier = new SimpleTier(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 250, 2.0f, 0.0f, 15, () -> Ingredient.of(Items.CARROT));
    private static FoodProperties foodProperties = (new FoodProperties.Builder()).nutrition(12).saturationModifier(0.6F).build();
    public SharpCarrot() {
        super(tier, new Item.Properties().attributes(
            createAttributes(tier, 3.5f, -2.4f)
        ));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        if (player.canEat(foodProperties.canAlwaysEat())) {
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return foodProperties.eatDurationTicks();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        livingEntity.eat(level, stack.copy(), foodProperties);
        if (stack.isDamageableItem()) {
            if (livingEntity == null || !livingEntity.hasInfiniteMaterials()){
                if (livingEntity instanceof ServerPlayer sp) {
                    CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(sp, stack, stack.getDamageValue() + BITE_SIZE);
                }
                int damage = stack.getDamageValue() + BITE_SIZE;
                stack.setDamageValue(damage);
                if (damage >= stack.getMaxDamage()) {
                    stack.shrink(1);
                }
            }
        }
        return stack;
    }
}
