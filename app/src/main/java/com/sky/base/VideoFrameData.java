package com.sky.base;

public class VideoFrameData {
    public int safeTexture;
    public int mixTexture;
    public int viewportWidth;
    public int viewportHeight;
    public boolean mirror;

    public VideoFrameData(int safeTexture, int mixTexture, int viewportWidth, int viewportHeight, boolean mirror) {
        this.safeTexture = safeTexture;
        this.mixTexture = mixTexture;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.mirror = mirror;
    }

    public VideoFrameData(int mixTexture, int viewportWidth, int viewportHeight, boolean mirror) {
        this.mixTexture = mixTexture;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.mirror = mirror;
    }
}
