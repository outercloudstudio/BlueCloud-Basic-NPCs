package outercloud.basic_npcs.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import outercloud.basic_npcs.BasicNPCs;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
//    @ModifyVariable(method = "tickFallFlying", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/LivingEntity;getFlag(I)Z"))
//    public boolean modify(boolean value) {
//        BasicNPCs.LOGGER.info("Modifier " + String.valueOf(value));
//
//        return value;
//    }

    @Inject(method = "tickFallFlying", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void tickFallFlying(CallbackInfo info, boolean bl) {
        LivingEntity instance = (LivingEntity)(Object)this;
        EntityMixin entityMixin = (EntityMixin) instance;

        if(!(instance instanceof ZombieEntity)) return;

        if(!instance.getWorld().isClient()) return;;

//        BasicNPCs.LOGGER.info(String.valueOf(instance.getWorld().isClient()));

        if(instance.getWorld().isClient()) entityMixin.invokeSetFlag(7, bl);

//        BasicNPCs.LOGGER.info("Local " + bl);
    }

    @Inject(method = "isFallFlying", at = @At("RETURN"), cancellable = true)
    private void isFallFlying(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(true);
    }
}
