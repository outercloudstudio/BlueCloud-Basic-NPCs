package outercloud.basic_npcs;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class NPCScreen extends HandledScreen<NPCScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/generic_54.png");

    public NPCScreen(NPCScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 114 + 9 * 18;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;

        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, 9 * 18 + 17);
        context.drawTexture(TEXTURE, i, j + 9 * 18 + 17, 0, 126, this.backgroundWidth, 96);
    }
}
