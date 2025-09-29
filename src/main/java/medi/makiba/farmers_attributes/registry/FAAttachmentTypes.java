package medi.makiba.farmers_attributes.registry;

import java.util.function.Supplier;

import medi.makiba.farmers_attributes.FarmersAttributes;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FAAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FarmersAttributes.MODID);

    public static final Supplier<AttachmentType<Integer>> CROUCH_BONEMEAL_COOLDOWN = ATTACHMENT_TYPES.register(
        "crouch_bonemeal_cooldown", () -> AttachmentType.builder(() -> 0).build()
    );
}