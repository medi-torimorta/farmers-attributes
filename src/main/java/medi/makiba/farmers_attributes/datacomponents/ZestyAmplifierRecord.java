package medi.makiba.farmers_attributes.datacomponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record ZestyAmplifierRecord(int value) {
    
    public static final Codec<ZestyAmplifierRecord> CODEC = RecordCodecBuilder.create(instance ->
    instance.group(
        Codec.INT.fieldOf("value").forGetter(ZestyAmplifierRecord::value)
    ).apply(instance, ZestyAmplifierRecord::new));

    public static final StreamCodec<ByteBuf, ZestyAmplifierRecord> STREAM_CODEC = StreamCodec.unit(new ZestyAmplifierRecord(-1));
}


