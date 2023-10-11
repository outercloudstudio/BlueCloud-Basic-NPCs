package outercloud.basic_npcs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class NPC extends HostileEntity {
    private static final TrackedData<String> TEXTURE = DataTracker.registerData(NPC.class, TrackedDataHandlerRegistry.STRING);

    protected NPC(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TEXTURE, "textures/entity/zombie/zombie.png");
    }

    public String getTexture() {
        return this.dataTracker.get(TEXTURE);
    }

    public void setTexture(String path) {
        if(!Identifier.isValid(path)) {
            BasicNPCs.LOGGER.warn("Attempted to set invalid identifier as texture path. Cancelling to prevent a crash! " + path);

            return;
        }

        this.dataTracker.set(TEXTURE, path);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putString("Texture", getTexture());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.dataTracker.set(TEXTURE, nbt.getString("Texture"));
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(this.getWorld().isClient) return ActionResult.SUCCESS;

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerEntity) -> new NPCScreenHandler(syncId, this, playerEntity), this.getDisplayName()));

        return ActionResult.SUCCESS;
    }
}
