package com.thaumcraftaspectannotations.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import thaumcraft.api.aspects.Aspect;


@SideOnly(Side.CLIENT)
public final class AspectIconRenderer {

    private AspectIconRenderer() {
    }

    public static void renderAspect(Aspect aspect, int x, int y, int size, float alpha) {
        if (aspect == null || size <= 0 || alpha <= 0.0F) return;

        int color = aspect.getColor();
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;

        Minecraft.getMinecraft().getTextureManager().bindTexture(aspect.getImage());

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(x, y + size, 0.0D).tex(0.0D, 1.0D).color(red, green, blue, alpha).endVertex();
        buffer.pos(x + size, y + size, 0.0D).tex(1.0D, 1.0D).color(red, green, blue, alpha).endVertex();
        buffer.pos(x + size, y, 0.0D).tex(1.0D, 0.0D).color(red, green, blue, alpha).endVertex();
        buffer.pos(x, y, 0.0D).tex(0.0D, 0.0D).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
    }
}