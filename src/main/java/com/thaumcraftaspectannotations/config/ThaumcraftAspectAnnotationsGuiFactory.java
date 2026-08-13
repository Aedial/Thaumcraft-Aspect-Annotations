package com.thaumcraftaspectannotations.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import com.thaumcraftaspectannotations.Tags;


public class ThaumcraftAspectAnnotationsGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ThaumcraftAspectAnnotationsConfigGui(parentScreen);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    public static class ThaumcraftAspectAnnotationsConfigGui extends GuiConfig {

        public ThaumcraftAspectAnnotationsConfigGui(GuiScreen parentScreen) {
            super(
                parentScreen,
                getConfigElements(),
                Tags.MODID,
                false,
                false,
                I18n.format(Tags.MODID + ".config.title")
            );
        }

        private static List<IConfigElement> getConfigElements() {
            return new ArrayList<>(ConfigElement.from(ThaumcraftAspectAnnotationsConfig.class).getChildElements());
        }
    }
}