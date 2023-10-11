package outercloud.basic_npcs;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import outercloud.basic_npcs.mixins.client.ScreenMixin;
import outercloud.basic_npcs.mixins.client.TextureManagerMixin;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NPCScreen extends HandledScreen<NPCScreenHandler> {
    private ButtonWidget appearanceTabButton = ButtonWidget.builder(Text.of("Appearance"), widget -> {}).dimensions(0, 0, 70, 16).build();
    private TextFieldWidget texturePathWidget;
    TextWidget texturePathAutoCompletionWidget;

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

        texturePathWidget = new TextFieldWidget(textRenderer, 0, 32, 300, 16, Text.empty());
        texturePathWidget.setDrawsBackground(true);
        texturePathWidget.setEditable(true);
        texturePathWidget.setMaxLength(500);

        if(texturePathAutoCompletionWidget != null) remove(texturePathAutoCompletionWidget);

        texturePathAutoCompletionWidget = new TextWidget(0, 32 + 1, 0, 16, Text.empty(), textRenderer);
        texturePathAutoCompletionWidget.setTextColor(Colors.GRAY);
        addDrawable(texturePathAutoCompletionWidget);

        addDrawableChild(texturePathWidget);

        handler.textPathUpdatedEvent.add(path -> {
            texturePathWidget.setText(path);

            texturePathWidget.setChangedListener(this::onTexturePathChanged);
        });

        setInitialFocus(texturePathWidget);
    }

    private Set<Identifier> getAllEntityTextures() {
        return ((TextureManagerMixin)MinecraftClient.getInstance().getTextureManager()).getResourceContainer().findAllResources("textures", identifier -> identifier.getPath().startsWith("textures/entity/")).keySet();
    }

    private void onTexturePathChanged(String path) {
        String completion = "";

        List<Identifier> autoCompletions = allEntityTextures.stream().filter(identifier -> identifier.toString().startsWith(path)).toList();

        System.out.println(autoCompletions.size());

        if(autoCompletions.size() > 0 && path.length() <= autoCompletions.get(0).toString().length()) completion = autoCompletions.get(0).toString().substring(path.length());

        texturePathAutoCompletionWidget.setX(texturePathWidget.getX() + 4 + textRenderer.getWidth(path));
        texturePathAutoCompletionWidget.setWidth(textRenderer.getWidth(Text.of(completion)));
        texturePathAutoCompletionWidget.setMessage(Text.of(completion));

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

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        Iterator<Drawable> drawableIterator = ((ScreenMixin)this).getDrawables().iterator();

        while(drawableIterator.hasNext()) {
            Drawable drawable = drawableIterator.next();
            drawable.render(context, mouseX, mouseY, delta);
        }

        this.drawForeground(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {}
}
