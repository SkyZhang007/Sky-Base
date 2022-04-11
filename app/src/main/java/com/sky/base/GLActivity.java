package com.sky.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sky.live.cam.ImageSourceProvider;
import com.sky.live.cam.cams.CameraSourceImpl;
import com.sky.live.cam.render.SelfCaptureRenderer;
import com.sky.live.cam.utils.ImageUtil;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import static android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY;

/**
 * 类功能：
 * 类作者：Sky
 * 类日期：2022/4/7.
 **/
public class GLActivity extends FragmentActivity implements SurfaceTexture.OnFrameAvailableListener, View.OnClickListener {

    protected Context mContext;
    protected GLSurfaceView mSurfaceView;
    protected ImageSourceProvider mImageSourceProvider;
    protected volatile boolean mIsPaused = false;
    private SelfCaptureRenderer mSelfCaptureRenderer;
    private ImageUtil mImageUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mImageUtil = new ImageUtil();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gl);

        initView();
        customBySrcConfig();
    }

    private void initView() {
        mSurfaceView = findViewById(R.id.glview);
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo ci = am.getDeviceConfigurationInfo();
        if (ci.reqGlEsVersion >= 0x30000) {
            mSurfaceView.setEGLContextClientVersion(3);
        } else {
            mSurfaceView.setEGLContextClientVersion(2);
        }

        mSelfCaptureRenderer = new SelfCaptureRenderer(this, new SelfCaptureRenderer.RenderCallback() {
            @Override
            public void onSurfaceCreated() {

            }

            @Override
            public void onSurfaceChanged(int width, int height) {

            }

            @Override
            public int onDrawFrame(int textureId, int width, int height) {
                // 准备帧缓冲区纹理对象
//                int dstTexture = mImageUtil.prepareTexture(width, height);
                return textureId;//dstTexture
            }
        }, mSurfaceView);
        mSurfaceView.setRenderer(mSelfCaptureRenderer);
        mSurfaceView.setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    private void customBySrcConfig() {
        mImageSourceProvider = new CameraSourceImpl(getApplicationContext(), mSurfaceView);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (null != mSurfaceView) {
            mSurfaceView.requestRender();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mSurfaceView) return;
        //  {zh} 确保在setRender之后调用，否则会出现NPE崩溃  {en} Make sure to call after setRender, otherwise NPE crash will occur
        mSurfaceView.onResume();
        mIsPaused = false;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (null == mSurfaceView) return;
        mIsPaused = true;
        mSurfaceView.onPause();
        mSurfaceView.queueEvent(new Runnable() {
            @Override
            public void run() {
                mImageSourceProvider.close();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSurfaceView = null;
        mSelfCaptureRenderer.destroy();
    }
}
