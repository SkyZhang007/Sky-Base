package com.sky.live.cam.cams;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.widget.ImageView;

import com.sky.live.cam.ImageSourceProvider;
import com.sky.live.cam.opengl.Constants;

public class CameraSourceImpl implements ImageSourceProvider<Integer> {
    private CameraProxy mCameraProxy;
    private GLSurfaceView glSurfaceView;
    private SurfaceTexture.OnFrameAvailableListener mFrameListener;

    private int mCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private boolean mCameraChanging;


    public CameraSourceImpl(Context context, GLSurfaceView glSurfaceView) {
        this.mCameraProxy =  new CameraProxy(context);
        this.glSurfaceView = glSurfaceView;
    }

    @Override
    public void open(Integer cameraId, SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener) {
        mFrameListener = onFrameAvailableListener;
        mCameraID = cameraId;
        mCameraProxy.openCamera(mCameraID, new CameraListener() {
            @Override
            public void onOpenSuccess() {
                onCameraOpen(mFrameListener);

            }

            @Override
            public void onOpenFail() {

            }
        });

    }

    private void onCameraOpen(final SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener){
        if (glSurfaceView == null) return;
        glSurfaceView.queueEvent(() -> {
            mCameraChanging = false;
            mCameraProxy.startPreview(onFrameAvailableListener);
        });

    }

    public void changeCamera(int cameraId, Runnable onOpenSuccess) {
        if (null == glSurfaceView) return;
        if (Camera.getNumberOfCameras() == 1
                || mCameraChanging) {
            return;
        }
        mCameraID = cameraId;
        mCameraChanging = true;
        if (glSurfaceView == null) return;
        glSurfaceView.queueEvent(() -> mCameraProxy.changeCamera(mCameraID, new CameraListener() {
            @Override
            public void onOpenSuccess() {
                if (glSurfaceView == null) return;
                glSurfaceView.queueEvent(() -> {
                    mCameraProxy.deleteTexture();
                    onCameraOpen(mFrameListener);
                    if (glSurfaceView != null) {
                        glSurfaceView.requestRender();
                    }
                    if (onOpenSuccess != null) {
                        onOpenSuccess.run();
                    }
                });
            }

            @Override
            public void onOpenFail() {

            }
        }));
    }

    @Override
    public void update() {
        if (mCameraChanging)return;
        mCameraProxy.updateTexture();



    }

    @Override
    public void close() {
        mCameraProxy.releaseCamera();

    }

    public void setPreferSize(int width, int height) {
        mCameraProxy.setPreviewSize(height, width);
    }

    @Override
    public float[] getFov() {
        return mCameraProxy.getFov();
    }

    @Override
    public Constants.TextureFormat getTextureFormat() {
        return Constants.TextureFormat.Texture_Oes;
    }

    @Override
    public int getTexture() {
        return mCameraProxy.getPreviewTexture();
    }

    @Override
    public int getWidth() {
        return mCameraProxy.getPreviewWidth();
    }

    @Override
    public int getHeight() {
        return mCameraProxy.getPreviewHeight();
    }

    @Override
    public int getOrientation() {
        return mCameraProxy.getOrientation();
    }

    @Override
    public boolean isFront() {
        return mCameraProxy.isFrontCamera();
    }

    @Override
    public long getTimestamp() {
        return mCameraProxy.getTimeStamp();
    }

    @Override
    public boolean isReady() {
        return (mCameraProxy != null && mCameraProxy.isCameraValid() && !mCameraChanging);
    }

    @Override
    public ImageView.ScaleType getScaleType() {
        return ImageView.ScaleType.CENTER_CROP;
    }

    public int getCameraID() {
        return mCameraID;
    }
}
