package outercloud.basic_npcs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class BasicNPCsClient implements ClientModInitializer {
	public static final EntityModelLayer NPC_MODEL_LAYER = new EntityModelLayer(new Identifier("basic_npcs", "npc"), "main");

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(BasicNPCs.NPC, NPCRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(NPC_MODEL_LAYER, () -> TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0.0F), 64, 64));
	}
}