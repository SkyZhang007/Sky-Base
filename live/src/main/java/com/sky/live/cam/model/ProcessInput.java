package com.sky.live.cam.model;

import com.sky.live.cam.opengl.Constants;

/**
 * Created  on 2021/5/14 4:08 PM
 */
public class ProcessInput {
    private int texture;
    private int width;
    private int height;
    public Constants.Rotation sensorRotation;


    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Constants.Rotation getSensorRotation() {
        return sensorRotation;
    }

    public void setSensorRotation(Constants.Rotation sensorRotation) {
        this.sensorRotation = sensorRotation;
    }
}
