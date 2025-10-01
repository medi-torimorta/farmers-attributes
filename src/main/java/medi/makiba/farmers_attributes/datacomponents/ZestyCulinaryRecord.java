package medi.makiba.farmers_attributes.datacomponents;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ZestyCulinaryRecord(Map<String, Double> map) {
    public ZestyCulinaryRecord() {
        this(Map.of());
    }

    public ZestyCulinaryRecord(int output_slot, double value) {
        this(Map.of(String.valueOf(output_slot), value));
    }

    public Double getAttValueForSlot(int slot) {
        return map.getOrDefault(String.valueOf(slot), 0.0);
    }

    @Nullable
    public ZestyCulinaryRecord updatedWith(int output_slot, double value) {
        if (value < 1 && map.containsKey(String.valueOf(output_slot)) && map.size() == 1) {
            return null;
        }

        Map<String, Double> new_map = new HashMap<>(map);
        if (value < 1) {
            new_map.remove(String.valueOf(output_slot));
        } else {
            new_map.put(String.valueOf(output_slot), value);
        }
        //if map is empty, return null
        if (new_map.isEmpty()) {
            return null;
        }
        return new ZestyCulinaryRecord(new_map);
    }

    public static final Codec<ZestyCulinaryRecord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.unboundedMap(Codec.STRING, Codec.DOUBLE).fieldOf("outputslot_amps").forGetter(ZestyCulinaryRecord::map)
    ).apply(instance, ZestyCulinaryRecord::new));

}