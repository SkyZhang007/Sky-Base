package com.sky.live.cam.render

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.sky.live.cam.ImageSourceProvider
import com.sky.live.cam.cams.CameraInterface
import com.sky.live.cam.cams.CameraSourceImpl
import com.sky.live.cam.model.ProcessInput
import com.sky.live.cam.model.ProcessOutput
import com.sky.live.cam.opengl.Constants
import com.sky.live.cam.utils.ImageUtil
import com.sky.live.cam.utils.OrientationSensor

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SelfCaptureRenderer(
    context: Context?,
    private var callback: RenderCallback?,
    private var previewView: GLSurfaceView?,
) : GLSurfaceView.Renderer {

    interface RenderCallback {
        fun onSurfaceCreated()
        fun onSurfaceChanged(width: Int, height: Int)
        fun onDrawFrame(texture: Int, width: Int, height: Int): Int
    }

    private var mImageUtil: ImageUtil? = null
    private var mImageSourceProvider: ImageSourceProvider<Int>
    private var mSurfaceWidth = 0
    private var mSurfaceHeight = 0
    var mCameraId = CameraInterface.CAMERA_FRONT

    init {
        mImageUtil = ImageUtil()
        mImageSourceProvider =
            CameraSourceImpl(
                context,
                previewView
            )
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        (mImageSourceProvider as CameraSourceImpl).setPreferSize(
            1280,
            720
        )
        mImageSourceProvider.open(mCameraId) {
            previewView?.requestRender()
        }

        callback?.onSurfaceCreated()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        if (width != 0 && height != 0) {
            mSurfaceWidth = width
            mSurfaceHeight = height
        }
        callback?.onSurfaceChanged(width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        if (!mImageSourceProvider.isReady) {
            return
        }
        if (mImageUtil == null) {
            return
        }

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        mImageSourceProvider.update()

        //将输入纹理转换出人脸为正的2D纹理
        val input: ProcessInput = transToPortrait()

        val output = ProcessOutput()
        output.setTexture(callback?.onDrawFrame(input.texture, input.width, input.height) ?: 0)
        output.setWidth(input.width)
        output.setHeight(input.height)

        //上屏
        drawOnScreen(output)
    }

    fun destroy() {
        mImageUtil?.release()
        mImageSourceProvider.close()
    }

    fun switchCamera(cameraId: Int) {
        (mImageSourceProvider as CameraSourceImpl).changeCamera(cameraId, null)
    }


    private fun transToPortrait(): ProcessInput {
        val processInput = ProcessInput()
        var mTextureWidth = mImageSourceProvider.width
        var mTextureHeight = mImageSourceProvider.height
        processInput.texture = mImageSourceProvider.texture

        processInput.setSensorRotation(OrientationSensor.getOrientation())
        if (mImageSourceProvider.orientation % 180 === 90 ||
            mImageSourceProvider.textureFormat !== Constants.TextureFormat.Texure2D
        ) {
            if (mImageSourceProvider.orientation % 180 === 90) {
                mTextureWidth = mImageSourceProvider.height
                mTextureHeight = mImageSourceProvider.width
            }
            val transition =
                ImageUtil.Transition().rotate(mImageSourceProvider.orientation.toFloat())
                    .flip(false, mImageSourceProvider.isFront)
            val texture = mImageUtil!!.transferTextureToTexture(
                mImageSourceProvider.texture,
                mImageSourceProvider.textureFormat,
                Constants.TextureFormat.Texure2D,
                mImageSourceProvider.width,
                mImageSourceProvider.height,
                transition
            )
            processInput.texture = texture
        }

        processInput.width = mTextureWidth
        processInput.height = mTextureHeight

        return processInput
    }

    private fun drawOnScreen(output: ProcessOutput) {
        if (!GLES20.glIsTexture(output.getTexture())) {
            return
        }

        val width = output.width
        val height = output.height

        val drawTransition = ImageUtil.Transition()
            .crop(
                mImageSourceProvider.scaleType,
                0,
                width,
                height,
                mSurfaceWidth,
                mSurfaceHeight
            )
        mImageUtil?.drawFrameOnScreen(
            output.getTexture(),
            Constants.TextureFormat.Texure2D,
            mSurfaceWidth,
            mSurfaceHeight,
            drawTransition.matrix
        )
    }
}