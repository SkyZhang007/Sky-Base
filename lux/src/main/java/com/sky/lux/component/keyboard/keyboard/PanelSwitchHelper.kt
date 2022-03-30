package com.sky.lux.component.keyboard.keyboard

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.sky.lux.R
import com.sky.lux.component.keyboard.keyboard.interfaces.ContentScrollMeasurer
import com.sky.lux.component.keyboard.keyboard.interfaces.ContentScrollMeasurerBuilder
import com.sky.lux.component.keyboard.keyboard.interfaces.PanelHeightMeasurer
import com.sky.lux.component.keyboard.keyboard.interfaces.PanelHeightMeasurerBuilder
import com.sky.lux.component.keyboard.keyboard.interfaces.listener.*
import com.sky.lux.component.keyboard.keyboard.view.PanelSwitchLayout

/**
 * the helper of panel switching
 */
class PanelSwitchHelper private constructor(builder: Builder, showKeyboard: Boolean) {

    private val mPanelSwitchLayout: PanelSwitchLayout? = builder.panelSwitchLayout

    init {
        mPanelSwitchLayout?.run {
            setContentScrollOutsizeEnable(builder.contentScrollOutsideEnable)
            setScrollMeasurers(builder.contentScrollMeasurers)
            setPanelHeightMeasurers(builder.panelHeightMeasurers)
            bindListener(builder.viewClickListeners, builder.panelChangeListeners, builder.keyboardStatusListeners, builder.editFocusChangeListeners)
            bindWindow(builder.window)
        }

        if (showKeyboard) {
            mPanelSwitchLayout?.toKeyboardState(true)
        }
    }

    fun addSecondaryInputView(editText: EditText?){
        mPanelSwitchLayout?.getContentContainer()?.getInputActionImpl()?.addSecondaryInputView(editText)
    }

    fun removeSecondaryInputView(editText: EditText?){
        mPanelSwitchLayout?.getContentContainer()?.getInputActionImpl()?.removeSecondaryInputView(editText)
    }

    fun hookSystemBackByPanelSwitcher(): Boolean {
        return mPanelSwitchLayout?.hookSystemBackByPanelSwitcher() ?: false
    }

    fun isPanelState() = mPanelSwitchLayout?.isPanelState()

    fun isKeyboardState() = mPanelSwitchLayout?.isKeyboardState()

    fun isResetState() = mPanelSwitchLayout?.isResetState()

    /**
     * 设置内容滑动
     */
    fun setContentScrollOutsideEnable(enable: Boolean) {
        mPanelSwitchLayout?.setContentScrollOutsizeEnable(enable)
    }

    fun showKeyboard(){
        mPanelSwitchLayout?.toKeyboardState(true)
    }

    /**
     * 判断内容是否允许滑动
     */
    fun isContentScrollOutsizeEnable() = mPanelSwitchLayout?.isContentScrollOutsizeEnable()

    /**
     * 外部显示输入框
     */
    @JvmOverloads
    fun toKeyboardState(async: Boolean = false) {
        mPanelSwitchLayout?.toKeyboardState(async)
    }

    /**
     * 外部显示面板
     */
    fun toPanelState(@IdRes triggerViewId: Int) {
        mPanelSwitchLayout?.findViewById<View>(triggerViewId).let {
            it?.performClick()
        }
    }

    /**
     * 隐藏输入法或者面板
     */
    fun resetState() {
        mPanelSwitchLayout?.checkoutPanel(Constants.PANEL_NONE)
    }

    class Builder(window: Window?, root: View?) {
        internal var viewClickListeners: MutableList<OnViewClickListener> = mutableListOf()
        internal var panelChangeListeners: MutableList<OnPanelChangeListener> = mutableListOf()
        internal var keyboardStatusListeners: MutableList<OnKeyboardStateListener> = mutableListOf()
        internal var editFocusChangeListeners: MutableList<OnEditFocusChangeListener> = mutableListOf()
        internal var contentScrollMeasurers: MutableList<ContentScrollMeasurer> = mutableListOf()
        internal var panelHeightMeasurers: MutableList<PanelHeightMeasurer> = mutableListOf()
        internal var panelSwitchLayout: PanelSwitchLayout? = null
        internal var window: Window
        internal var rootView: View
        internal var logTrack = false
        internal var contentScrollOutsideEnable = true

        constructor(activity: Activity?) : this(activity?.window, activity?.window?.decorView?.findViewById<View>(
            R.id.content))
        constructor(fragment: Fragment?) : this(fragment?.activity?.window, fragment?.view)
        constructor(dialogFragment: DialogFragment) : this(dialogFragment.activity?.window, dialogFragment.view)

        init {
            requireNotNull(window) { "PanelSwitchHelper\$Builder#build : window can't be null!please set value by call #Builder" }
            this.window = window
            requireNotNull(root) { "PanelSwitchHelper\$Builder#build : rootView can't be null!please set value by call #Builder" }
            this.rootView = root
        }

        /**
         * note: helper will set view's onClickListener to View ,so you should add OnViewClickListener for your project.
         *
         * @param listener
         * @return
         */
        fun addViewClickListener(listener: OnViewClickListener?): Builder {
            if (listener != null && !viewClickListeners.contains(listener)) {
                viewClickListeners.add(listener)
            }
            return this
        }

        fun addViewClickListener(function: OnViewClickListenerBuilder.() -> Unit): Builder {
            viewClickListeners.add(OnViewClickListenerBuilder().also(function))
            return this
        }

        fun addPanelChangeListener(listener: OnPanelChangeListener?): Builder {
            if (listener != null && !panelChangeListeners.contains(listener)) {
                panelChangeListeners.add(listener)
            }
            return this
        }

        fun addPanelChangeListener(function: OnPanelChangeListenerBuilder.() -> Unit): Builder {
            panelChangeListeners.add(OnPanelChangeListenerBuilder().also(function))
            return this
        }

        fun addKeyboardStateListener(listener: OnKeyboardStateListener?): Builder {
            if (listener != null && !keyboardStatusListeners.contains(listener)) {
                keyboardStatusListeners.add(listener)
            }
            return this
        }

        fun addKeyboardStateListener(function: OnKeyboardStateListenerBuilder.() -> Unit): Builder {
            keyboardStatusListeners.add(OnKeyboardStateListenerBuilder().also(function))
            return this
        }

        fun addEditTextFocusChangeListener(listener: OnEditFocusChangeListener?): Builder {
            if (listener != null && !editFocusChangeListeners.contains(listener)) {
                editFocusChangeListeners.add(listener)
            }
            return this
        }

        fun addEditTextFocusChangeListener(function: OnEditFocusChangeListenerBuilder.() -> Unit): Builder {
            editFocusChangeListeners.add(OnEditFocusChangeListenerBuilder().also(function))
            return this
        }

        fun addContentScrollMeasurer(function: ContentScrollMeasurerBuilder.() -> Unit): Builder {
            contentScrollMeasurers.add(ContentScrollMeasurerBuilder().also(function))
            return this
        }

        fun addContentScrollMeasurer(scrollMeasurer: ContentScrollMeasurer?): Builder {
            if (scrollMeasurer != null && !contentScrollMeasurers.contains(scrollMeasurer)) {
                contentScrollMeasurers.add(scrollMeasurer)
            }
            return this
        }

        fun addPanelHeightMeasurer(function: PanelHeightMeasurerBuilder.() -> Unit): Builder {
            panelHeightMeasurers.add(PanelHeightMeasurerBuilder().also(function))
            return this
        }

        fun addPanelHeightMeasurer(panelHeightMeasurer: PanelHeightMeasurer?): Builder {
            if (panelHeightMeasurer != null && !panelHeightMeasurers.contains(panelHeightMeasurer)) {
                panelHeightMeasurers.add(panelHeightMeasurer)
            }
            return this
        }

        fun contentScrollOutsideEnable(contentScrollOutsideEnable: Boolean): Builder {
            this.contentScrollOutsideEnable = contentScrollOutsideEnable
            return this
        }

        fun logTrack(logTrack: Boolean): Builder {
            this.logTrack = logTrack
            return this
        }

        @JvmOverloads
        fun build(showKeyboard: Boolean = false): PanelSwitchHelper {
            findSwitchLayout(rootView)
            requireNotNull(panelSwitchLayout) { "PanelSwitchHelper\$Builder#build : not found PanelSwitchLayout!" }
            return PanelSwitchHelper(this, showKeyboard)
        }

        private fun findSwitchLayout(view: View?) {
            if (view is PanelSwitchLayout) {
                require(panelSwitchLayout == null) { "PanelSwitchHelper\$Builder#build : rootView has one more panelSwitchLayout!" }
                panelSwitchLayout = view
                return
            }
            if (view is ViewGroup) {
                val childCount = view.childCount
                for (i in 0 until childCount) {
                    findSwitchLayout(view.getChildAt(i))
                }
            }
        }
    }
}