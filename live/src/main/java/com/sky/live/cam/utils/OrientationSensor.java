package com.sky.live.cam.utils;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;

import com.sky.live.cam.opengl.Constants;

public class OrientationSensor {

    private static OrientationEventListener mOrientationListener;
    private static int mOrientation;

    public static void start(Context context) {
        if (mOrientationListener != null) {
            return;
        }
        mOrientationListener = new OrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;
                }

                int newOrientation = ((orientation + 45) / 90 * 90) % 360;
                if (newOrientation != mOrientation) {
                    mOrientation = newOrientation;
                }
            }
        };

        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.enable();
        } else {
            mOrientationListener = null;
        }
    }

    public static void stop() {
        if (mOrientationListener != null) {
            mOrientationListener.disable();
        }
        mOrientationListener = null;
        mOrientation = 0;
    }

    public static int getSensorOrientation(){
        return mOrientation;
    }

    public static Constants.Rotation getOrientation() {
        switch (mOrientation) {
            case 90:
                return Constants.Rotation.CLOCKWISE_ROTATE_90;
            case 180:
                return Constants.Rotation.CLOCKWISE_ROTATE_180;
            case 270:
                return Constants.Rotation.CLOCKWISE_ROTATE_270;
            default:
                return Constants.Rotation.CLOCKWISE_ROTATE_0;
        }
    }
}
