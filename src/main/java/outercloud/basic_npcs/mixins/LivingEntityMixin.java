package outercloud.basic_npcs.mixins;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityMixin {
    @Invoker("updateLimbs")
    public void invokeUpdateLimbs(float deltaMovement);
}
