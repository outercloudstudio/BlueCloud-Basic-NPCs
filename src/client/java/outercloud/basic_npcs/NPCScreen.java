package outercloud.basic_npcs;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import outercloud.basic_npcs.mixins.client.ScreenMixin;
import outercloud.basic_npcs.mixins.client.TextureManagerMixin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NPCScreen extends HandledScreen<NPCScreenHandler> {
    private ButtonWidget appearanceTabButton = ButtonWidget.builder(Text.of("Appearance"), widget -> {}).dimensions(0, 0, 70, 16).build();
    private TextFieldWidget texturePathWidget;
    private ChatInputSuggestor texturePathInputSuggestor;

    private ButtonWidget behaviourTabButton = ButtonWidget.builder(Text.of("Behaviour"), widget -> {}).dimensions(70, 0, 70, 16).build();

    private Set<Identifier> allEntityTextures;

    public NPCScreen(NPCScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        addDrawableChild(appearanceTabButton);
        addDrawableChild(behaviourTabButton);

        allEntityTextures = getAllEntityTextures();

        ClientPlayNetworking.send(new ReadyNPCC2SPacket());
    }

    @Override
    protected void init() {
        super.init();

        if(texturePathWidget != null) remove(texturePathWidget);

        texturePathWidget = new TextFieldWidget(textRenderer, 0, 32, 300, 16, Text.of("Loading..."));
        texturePathWidget.setDrawsBackground(true);
        texturePathWidget.setEditable(true);
        texturePathWidget.setMaxLength(500);

        addDrawableChild(texturePathWidget);

        handler.textPathUpdatedEvent.add(path -> {
            texturePathWidget.setText(path);

            texturePathWidget.setChangedListener(this::onTexturePathChanged);
        });

        setInitialFocus(texturePathWidget);

        texturePathInputSuggestor = new ChatInputSuggestor(client, this, this.texturePathWidget, this.textRenderer, true, true, 0, 7, false, Integer.MIN_VALUE);
        texturePathInputSuggestor.setWindowActive(true);
        texturePathInputSuggestor.refresh();
    }

    private Set<Identifier> getAllEntityTextures() {
        return ((TextureManagerMixin)MinecraftClient.getInstance().getTextureManager()).getResourceContainer().findAllResources("textures", identifier -> identifier.getPath().startsWith("textures/entity/")).keySet();
    }


    private void onTexturePathChanged(String path) {
        texturePathInputSuggestor.refresh();

//        Stream<Identifier> autoCompletions = allEntityTextures.stream().filter(identifier -> identifier.toString().startsWith(path));
//
//        autoCompletions.forEach(identifier -> {
//            texturePathAutoCompletionWidget.setMessage(Text.of(identifier.toString()));
//        });

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

        if (texturePathInputSuggestor.keyPressed(keyCode, scanCode, modifiers)) return true;

        if(this.texturePathWidget.isActive()) return this.texturePathWidget.keyPressed(keyCode, scanCode, modifiers);

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.texturePathInputSuggestor.mouseScrolled(verticalAmount) ? true : super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.texturePathInputSuggestor.mouseClicked(mouseX, mouseY, button) ? true : super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        Iterator<Drawable> drawableIterator = ((ScreenMixin)this).getDrawables().iterator();

        while(drawableIterator.hasNext()) {
            Drawable drawable = drawableIterator.next();
            drawable.render(context, mouseX, mouseY, delta);
        }

        this.drawForeground(context, mouseX, mouseY);

        this.texturePathInputSuggestor.render(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {}
}
