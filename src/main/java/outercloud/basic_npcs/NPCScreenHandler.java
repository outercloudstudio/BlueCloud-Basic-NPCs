package outercloud.basic_npcs;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class NPCScreenHandler extends ScreenHandler {
    NPC npc;

    public NPCScreenHandler(int syncId, NPC npc) {
        this(BasicNPCs.NPC_SCREEN_HANDLER, syncId);

        this.npc = npc;
    }

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
}
