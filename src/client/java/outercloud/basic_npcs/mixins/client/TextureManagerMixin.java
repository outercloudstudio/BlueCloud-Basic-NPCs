package outercloud.basic_npcs.mixins.client;

import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextureManager.class)
public interface TextureManagerMixin {
    @Accessor
    ResourceManager getResourceContainer();
}
