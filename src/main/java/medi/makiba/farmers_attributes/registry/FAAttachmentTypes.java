package medi.makiba.farmers_attributes.registry;

import java.util.Set;
import java.util.function.Supplier;


import medi.makiba.farmers_attributes.FarmersAttributes;
import medi.makiba.farmers_attributes.datacomponent.ZestyCulinaryRecord;
import medi.makiba.farmers_attributes.attribute.GreenThumb;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FAAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FarmersAttributes.MODID);

    public static final Supplier<AttachmentType<Integer>> CROUCH_BONEMEAL_COOLDOWN = ATTACHMENT_TYPES.register(
        "crouch_bonemeal_cooldown", () -> AttachmentType.builder(() -> 0).build()
    );

    public static final Supplier<AttachmentType<ZestyCulinaryRecord>> ZESTY_CULINARY = ATTACHMENT_TYPES.register(
        "zesty_culinary", () -> AttachmentType.builder(() -> new ZestyCulinaryRecord()).serialize(ZestyCulinaryRecord.CODEC).build()
    );

    public static final Supplier<AttachmentType<Set<BlockPos>>> GREEN_THUMB = ATTACHMENT_TYPES.register(
        "green_thumb", () -> AttachmentType.builder(() -> GreenThumb.newData()).serialize(GreenThumb.CODEC).build()
    );
}