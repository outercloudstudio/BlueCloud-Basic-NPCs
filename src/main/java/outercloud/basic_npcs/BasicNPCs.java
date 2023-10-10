package outercloud.basic_npcs;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicNPCs implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("basic_npcs");

	public static final EntityType<NPC> NPC = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier("basic_npcs", "npc"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, outercloud.basic_npcs.NPC::new).dimensions(new EntityDimensions(0.5F, 2, true)).build()
	);

	public static final ScreenHandlerType<NPCScreenHandler> NPC_SCREEN_HANDLER = new ScreenHandlerType<NPCScreenHandler>(NPCScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(NPC, outercloud.basic_npcs.NPC.createMobAttributes());

		Registry.register(Registries.SCREEN_HANDLER, new Identifier("basic_npcs", "npc"), NPC_SCREEN_HANDLER);

		ServerPlayNetworking.registerGlobalReceiver(UpdateNPCC2SPacket.TYPE, (packet, player, sender) -> {
			if(!(player.currentScreenHandler instanceof  NPCScreenHandler)) {
				LOGGER.warn("Tried to update NPC that player is no long interacting with!");

				return;
			}

			NPCScreenHandler screenHandler = (NPCScreenHandler) player.currentScreenHandler;
			screenHandler.updateNpc(packet);
		});
	}
}