package outercloud.basic_npcs;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class UpdateNPCS2CPacket implements FabricPacket {
    public static final PacketType<UpdateNPCS2CPacket> TYPE = PacketType.create(new Identifier("basic_npcs", "update_npc_s2c"), UpdateNPCS2CPacket::new);
    public final String TexturePath;

    public UpdateNPCS2CPacket(String texturePath) {
        this.TexturePath = texturePath;
    }

    public UpdateNPCS2CPacket(PacketByteBuf buf) {
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
