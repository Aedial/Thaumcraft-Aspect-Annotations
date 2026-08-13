package com.thaumcraftaspectannotations.config;

import java.io.File;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.thaumcraftaspectannotations.Tags;


@Mod.EventBusSubscriber(modid = Tags.MODID)
@Config(modid = Tags.MODID, name = Tags.MODID, category = "")
@Config.LangKey(Tags.MODID + ".config.title")
public final class ThaumcraftAspectAnnotationsConfig {

    private static final double MIN_SCALE = 0.0625D;
    private static final double MAX_SCALE = 1.0D;
    private static final double DEFAULT_SCALE = 0.5D;
    private static final double MIN_ALPHA = 0.0D;
    private static final double MAX_ALPHA = 1.0D;
    private static final String CATEGORY_OVERLAY = "overlay";

    @Config.Name(CATEGORY_OVERLAY)
    @Config.LangKey(Tags.MODID + ".config.category.overlay")
    public static OverlayCategory overlay = new OverlayCategory();

    private ThaumcraftAspectAnnotationsConfig() {
    }

    public static void init(File configFile) {
        syncConfig();
    }

    public static float getScale() {
        return (float) overlay.scale;
    }

    public static int getOverlaySize() {
        return Math.max(1, Math.round(16.0F * getScale()));
    }

    public static OverlayPosition getPosition() {
        if (overlay.position == null) return OverlayPosition.TOP_RIGHT;

        return overlay.position;
    }

    public static float getAlpha() {
        return (float) overlay.alpha;
    }

    private static void syncConfig() {
        ConfigManager.sync(Tags.MODID, Config.Type.INSTANCE);

        boolean changed = false;

        double normalizedScale = clamp(overlay.scale, MIN_SCALE, MAX_SCALE);
        if (normalizedScale != overlay.scale) {
            overlay.scale = normalizedScale;
            changed = true;
        }

        double normalizedAlpha = clamp(overlay.alpha, MIN_ALPHA, MAX_ALPHA);
        if (normalizedAlpha != overlay.alpha) {
            overlay.alpha = normalizedAlpha;
            changed = true;
        }

        if (overlay.position == null) {
            overlay.position = OverlayPosition.TOP_RIGHT;
            changed = true;
        }

        if (changed) {
            ConfigManager.sync(Tags.MODID, Config.Type.INSTANCE);
        }
    }

    private static double clamp(double value, double minValue, double maxValue) {
        if (value < minValue) return minValue;

        if (value > maxValue) return maxValue;

        return value;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (!Tags.MODID.equals(event.getModID())) return;

        syncConfig();
    }

    public static class OverlayCategory {

        @Config.LangKey(Tags.MODID + ".config.scale")
        @Config.RangeDouble(min = MIN_SCALE, max = MAX_SCALE)
        public double scale = DEFAULT_SCALE;

        @Config.LangKey(Tags.MODID + ".config.position")
        public OverlayPosition position = OverlayPosition.TOP_RIGHT;

        @Config.LangKey(Tags.MODID + ".config.alpha")
        @Config.RangeDouble(min = MIN_ALPHA, max = MAX_ALPHA)
        public double alpha = 1.0D;
    }

    public enum OverlayPosition {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        CENTER;

        public int getOffsetX(int overlaySize) {
            if (this == TOP_RIGHT || this == BOTTOM_RIGHT) return 16 - overlaySize;

            if (this == CENTER) return (16 - overlaySize) / 2;

            return 0;
        }

        public int getOffsetY(int overlaySize) {
            if (this == BOTTOM_LEFT || this == BOTTOM_RIGHT) return 16 - overlaySize;

            if (this == CENTER) return (16 - overlaySize) / 2;

            return 0;
        }
    }
}