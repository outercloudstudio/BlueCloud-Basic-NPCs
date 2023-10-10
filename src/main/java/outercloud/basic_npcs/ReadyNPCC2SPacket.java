package outercloud.basic_npcs;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ReadyNPCC2SPacket implements FabricPacket {
    public static final PacketType<ReadyNPCC2SPacket> TYPE = PacketType.create(new Identifier("basic_npcs", "ready_npc_c2s"), ReadyNPCC2SPacket::new);

    public ReadyNPCC2SPacket() {}

    public ReadyNPCC2SPacket(PacketByteBuf buf) {}

    public void write(PacketByteBuf buf) {}

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
