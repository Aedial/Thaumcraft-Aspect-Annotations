package com.thaumcraftaspectannotations.client;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;

import com.thaumcraftaspectannotations.config.ThaumcraftAspectAnnotationsConfig;
import com.thaumcraftaspectannotations.config.ThaumcraftAspectAnnotationsConfig.OverlayPosition;


@SideOnly(Side.CLIENT)
public class ContainerAspectOverlayHandler {

    @SubscribeEvent
    public void onDrawForeground(GuiContainerEvent.DrawForeground event) {
        int overlaySize = ThaumcraftAspectAnnotationsConfig.getOverlaySize();
        float alpha = ThaumcraftAspectAnnotationsConfig.getAlpha();
        if (overlaySize <= 0 || alpha <= 0.0F) return;

        OverlayPosition position = ThaumcraftAspectAnnotationsConfig.getPosition();

        for (Slot slot : event.getGuiContainer().inventorySlots.inventorySlots) {
            if (!slot.isEnabled()) continue;

            Aspect aspect = getDisplayedAspect(slot.getStack());
            if (aspect == null) continue;

            int x = slot.xPos + position.getOffsetX(overlaySize);
            int y = slot.yPos + position.getOffsetY(overlaySize);
            AspectIconRenderer.renderAspect(aspect, x, y, overlaySize, alpha);
        }
    }

    private static Aspect getDisplayedAspect(ItemStack stack) {
        if (stack.isEmpty()) return null;

        if (!(stack.getItem() instanceof IEssentiaContainerItem)) return null;

        IEssentiaContainerItem containerItem = (IEssentiaContainerItem) stack.getItem();
        if (containerItem.ignoreContainedAspects()) return null;

        AspectList storedAspects = containerItem.getAspects(stack);
        if (storedAspects == null || storedAspects.size() <= 0) return null;

        Aspect[] aspects = storedAspects.getAspects();
        if (aspects.length <= 0) return null;

        // getAspects can provide null aspects
        int index = 0;
        while (index < aspects.length && aspects[index] == null) index++;
        return index < aspects.length ? aspects[index] : null;
    }
}