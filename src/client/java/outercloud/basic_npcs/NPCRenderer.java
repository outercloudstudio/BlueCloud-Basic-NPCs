package outercloud.basic_npcs;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Identifier;

public class NPCRenderer extends BipedEntityRenderer<NPC, BipedEntityModel<NPC>> {
    public NPCRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BipedEntityModel<>(ctx.getPart(BasicNPCsClient.NPC_MODEL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(NPC entity) {
        return new Identifier("minecraft", "textures/entity/zombie/zombie.png");
    }
}
