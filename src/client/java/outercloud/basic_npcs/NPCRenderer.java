package outercloud.basic_npcs;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class NPCRenderer extends BipedEntityRenderer<NPC, BipedEntityModel<NPC>> {
    EntityRenderDispatcher entityRenderDispatcher;

    public NPCRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BipedEntityModel<>(ctx.getPart(BasicNPCsClient.NPC_MODEL_LAYER)), 0.5f);

        entityRenderDispatcher = ctx.getRenderDispatcher();
    }

    @Override
    public void render(NPC npc, float f, float delta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
//        super.render(npc, f, delta, matrixStack, vertexConsumerProvider, light);

//        matrixStack.translate(2f, 0f, 0f);
        entityRenderDispatcher.render(npc.renderEntity, 0, 0, 0, 0, delta, matrixStack, vertexConsumerProvider, light);
//        matrixStack.translate(-2f, 0f, 0f);
    }

    @Override
    public Identifier getTexture(NPC entity) {
        return new Identifier(entity.getTexture());
    }
}
