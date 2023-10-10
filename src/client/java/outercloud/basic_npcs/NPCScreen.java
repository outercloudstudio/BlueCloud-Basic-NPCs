package outercloud.basic_npcs;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class NPCScreen extends HandledScreen<NPCScreenHandler> {
//    ButtonWidget testButton = ButtonWidget.builder(Text.of("Test Button"), button -> {}).dimensions(0, 0, 64, 20).tooltip(Tooltip.of(Text.of("Test Tooltip"))).build();
    TextFieldWidget texturePathWidget;

    public NPCScreen(NPCScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.backgroundWidth = 248;
        this.backgroundHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        texturePathWidget = new TextFieldWidget(textRenderer, 0, 0, 128, 20, null);

        texturePathWidget.setFocusUnlocked(false);
        texturePathWidget.setEditableColor(-1);
        texturePathWidget.setUneditableColor(-1);
        texturePathWidget.setDrawsBackground(true);
        texturePathWidget.setMaxLength(50);
        texturePathWidget.setChangedListener(this::onTexturePathChanged);
        texturePathWidget.setText("Default Text");
        texturePathWidget.setEditable(true);

        this.addSelectableChild(texturePathWidget);
        this.setInitialFocus(texturePathWidget);

        addDrawable(texturePathWidget);
    }

    private void onTexturePathChanged(String path) {
       updateNPC();
    }

    private void updateNPC() {
        ClientPlayNetworking.send(new UpdateNPCC2SPacket(this.texturePathWidget.getText()));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.client.player.closeHandledScreen();

            return true;
        }

        if(this.texturePathWidget.isActive()) return this.texturePathWidget.keyPressed(keyCode, scanCode, modifiers);

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private int originX() {
        return (this.width - this.backgroundWidth) / 2;
    }

    private int originY() {
        return (this.height - this.backgroundHeight) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        texturePathWidget.setX(originX() + 6);
        texturePathWidget.setY(originY() + 18);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(new Identifier("textures/gui/demo_background.png"), originX(), originY(), 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, Text.of("NPC Config"), this.titleX, this.titleY, 4210752, false);
    }
}
