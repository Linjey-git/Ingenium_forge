package com.linjey.ingenium.screen;

import com.linjey.ingenium.ModIngenium;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LightningInfusionerScreen extends AbstractContainerScreen<LightningInfusionerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ModIngenium.MOD_ID, "textures/gui/lightning_infusioner_gui.png");

    public LightningInfusionerScreen(LightningInfusionerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        int left_pixel = this.getGuiLeft();
        int top_pixel = this.getGuiTop();
        if (menu.isLightningStorm()) {
            this.blit(pPoseStack, left_pixel + 27, top_pixel + 32,176, 0, 28, 36);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
