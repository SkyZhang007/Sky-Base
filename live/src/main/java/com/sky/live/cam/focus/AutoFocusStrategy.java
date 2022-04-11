package com.sky.live.cam.focus;

import android.hardware.Camera;

import androidx.annotation.NonNull;

/**
 * Created by yangcihang on 2018/2/22.
 */

class AutoFocusStrategy implements FocusStrategy {
    @Override
    public void focusCamera(@NonNull Camera camera, @NonNull Camera.Parameters parameters) {
        try {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            camera.setParameters(parameters);
            camera.autoFocus((success, camera1) -> {
                Camera.Parameters params = camera1.getParameters();
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                camera1.setParameters(params);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
