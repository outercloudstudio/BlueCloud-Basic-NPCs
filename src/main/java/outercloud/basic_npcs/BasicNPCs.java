package outercloud.basic_npcs;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicNPCs implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("basic_npcs");

	public static final EntityType<outercloud.basic_npcs.NPC> NPC = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier("basic_npcs", "npc"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, outercloud.basic_npcs.NPC::new).build()
	);

	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(NPC, outercloud.basic_npcs.NPC.createMobAttributes());
	}
}