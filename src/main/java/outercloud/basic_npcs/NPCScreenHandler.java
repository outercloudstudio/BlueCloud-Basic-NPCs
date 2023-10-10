package outercloud.basic_npcs;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.function.Consumer;

public class NPCScreenHandler extends ScreenHandler {
    public ArrayList<Consumer<String>> textPathUpdatedEvent = new ArrayList<>();

    private NPC npc;
    private PlayerEntity player;

    // Only run on server
    public NPCScreenHandler(int syncId, NPC npc, PlayerEntity player) {
        this(BasicNPCs.NPC_SCREEN_HANDLER, syncId);

        this.npc = npc;
        this.player = player;

        textPathUpdatedEvent.add(path -> {
            this.npc.setTexture(path);
        });
    }

    // Only run on client
    public NPCScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(BasicNPCs.NPC_SCREEN_HANDLER, syncId);
    }

    public NPCScreenHandler(ScreenHandlerType<NPCScreenHandler> type, int syncId) {
        super(type, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    // Called when server receives client ready packet
    public void updateNpc(PlayerEntity player) {
        BasicNPCs.LOGGER.info("Ready: " + npc.getTexture());

        ServerPlayNetworking.send((ServerPlayerEntity) player, new UpdateNPCS2CPacket(npc.getTexture()));
    }

    // Called when client updates npc
    public void updateNpc(UpdateNPCC2SPacket packet) {
        BasicNPCs.LOGGER.info("Update from client: " + packet.TexturePath);

        textPathUpdatedEvent.forEach(response -> response.accept(packet.TexturePath));
    }

    // Called when server sends update packet
    public void updateNpc(UpdateNPCS2CPacket packet) {
        BasicNPCs.LOGGER.info("Update from server: " + packet.TexturePath);

        textPathUpdatedEvent.forEach(response -> response.accept(packet.TexturePath));
    }
}
