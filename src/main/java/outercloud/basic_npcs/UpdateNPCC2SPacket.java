package outercloud.basic_npcs;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class UpdateNPCC2SPacket implements FabricPacket {
    public static final PacketType<UpdateNPCC2SPacket> TYPE = PacketType.create(new Identifier("basic_npcs", "update_npc"), UpdateNPCC2SPacket::new);
    public final String TexturePath;

    public UpdateNPCC2SPacket(String texturePath) {
        this.TexturePath = texturePath;
    }

    public UpdateNPCC2SPacket(PacketByteBuf buf) {
        this(buf.readString());
    }

    public void write(PacketByteBuf buf) {
        buf.writeString(this.TexturePath);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
