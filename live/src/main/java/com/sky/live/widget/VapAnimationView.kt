package com.sky.live.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.tencent.qgame.animplayer.AnimConfig
import com.tencent.qgame.animplayer.AnimView
import com.tencent.qgame.animplayer.inter.IAnimListener
import java.io.File

/**
 * 类功能：
 * 类作者：Sky
 * 类日期：2022/4/1.
 **/
class VapAnimationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val TAG = "AnimationView"
        const val TYPE_MP4 = 0
    }

    private var animationType: Int? = 0
    private var loopCount: Int = 0
    private var animationLink: String? = null

    private val animView: AnimView by lazy {
        AnimView(context)
    }

    init {
        addView(animView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    fun setAnimationType(type: Int?) {
        animationType = type
    }

    fun setAnimationLink(link: String) {
        animationLink = link
    }

    fun start() {
        if (animationLink.isNullOrEmpty() || animationLink.isNullOrEmpty()) {
            return
        }
        startInner(animationLink!!)
    }

    private fun startInner(path: String) {
        animView.run {
            stopPlay()
            postDelayed({
                setAnimListener(object : IAnimListener {
                    override fun onFailed(errorType: Int, errorMsg: String?) {
                    }

                    override fun onVideoComplete() {
                    }

                    override fun onVideoDestroy() {
                    }

                    override fun onVideoRender(frameIndex: Int, config: AnimConfig?) {
                    }

                    override fun onVideoStart() {
                    }
                })

                setLoop(
                    if (loopCount <= 0) {
                        Int.MAX_VALUE
                    } else {
                        loopCount
                    }
                )
                startPlay(File(path))
            }, 100)
        }

    }

    fun setLoopCount(count: Int) {
        loopCount = count
    }

    fun stop() {
        animView.stopPlay()
    }

    fun release() {
        animView.stopPlay()
    }

}