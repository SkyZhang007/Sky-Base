package com.sky.lux.component.keyboard.keyboard.device

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.sky.lux.R
import com.sky.lux.component.keyboard.keyboard.Constants
import com.sky.lux.utils.ScreenUtil

/**
 * panel helper
 */
object PanelUtil {

    private var pHeight: Int = -1
    private var lHeight: Int = -1

    @JvmStatic
    fun clearData(context: Context?) {
        pHeight = -1
        lHeight = -1
        val sp = context?.getSharedPreferences(Constants.KB_PANEL_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sp?.edit()?.clear()?.apply()
    }

    @JvmStatic
    fun showKeyboard(context: Context?, view: View?): Boolean {
        view?.requestFocus()
        val mInputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        return mInputManager?.showSoftInput(view, 0) ?: true
    }

    @JvmStatic
    fun hideKeyboard(context: Context?, view: View?): Boolean {
        val mInputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        return mInputManager?.hideSoftInputFromWindow(view?.windowToken, 0) ?: true
    }

    @JvmStatic
    fun getKeyBoardHeight(context: Context?): Int {
        val isPortrait = isPortrait(context)
        if (isPortrait && pHeight != -1) {
            return pHeight
        }
        if (!isPortrait && lHeight != -1) {
            return lHeight
        }
        val sp = context?.getSharedPreferences(Constants.KB_PANEL_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val key = if (isPortrait) Constants.KEYBOARD_HEIGHT_FOR_P else Constants.KEYBOARD_HEIGHT_FOR_L
        val defaultHeight = ScreenUtil.dp2px(context,
            (if (isPortrait) Constants.DEFAULT_KEYBOARD_HEIGHT_FOR_P else Constants.DEFAULT_KEYBOARD_HEIGHT_FOR_L).toInt()
        )
        val result = sp?.getInt(key, defaultHeight)
        if (result != defaultHeight) {
            if (isPortrait) {
                pHeight = result ?: 0
            } else {
                lHeight = result ?: 0
            }
        }
        return result ?: 0
    }

    @JvmStatic
    fun isPanelHeightBelowKeyboardHeight(context: Context?, curPanelHeight: Int): Boolean = hasMeasuredKeyboard(context) && getKeyBoardHeight(context) > curPanelHeight

    @JvmStatic
    fun setKeyBoardHeight(context: Context?, height: Int): Boolean {

        if (getKeyBoardHeight(context) == height) {
            return false
        }
        val isPortrait = isPortrait(context)
        if (isPortrait && pHeight == height) {
            return false
        }
        if (!isPortrait && lHeight == height) {
            return false
        }
        val sp = context?.getSharedPreferences(Constants.KB_PANEL_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val key = if (isPortrait) Constants.KEYBOARD_HEIGHT_FOR_P else Constants.KEYBOARD_HEIGHT_FOR_L
        val result = sp?.edit()?.putInt(key, height)?.commit() ?: false
        if (result) {
            if (isPortrait) {
                pHeight = height
            } else {
                lHeight = height
            }
        }
        return result
    }

    @JvmStatic
    fun getLocationOnScreen(view: View?): IntArray {
        val contentViewLocationInScreen = IntArray(2)
        view?.getLocationOnScreen(contentViewLocationInScreen)
        return contentViewLocationInScreen
    }

    @JvmStatic
    fun contentViewCanDrawStatusBarArea(window: Window): Boolean {
        return getLocationOnScreen(window.decorView.findViewById(Window.ID_ANDROID_CONTENT))[1] == 0
    }

    /**
     * 对应 id 为 @Android：id/content 的 FrameLayout 所加载的布局。
     * 也就是我们 setContentView 的布局高度
     *
     * @param window
     * @return
     */
    @JvmStatic
    fun getContentViewHeight(window: Window): Int {
        return window.decorView.findViewById<View>(Window.ID_ANDROID_CONTENT).height
    }

    @JvmStatic
    fun getToolbarHeight(window: Window?): Int {
        if (window == null) {
            return 0
        }
        return window.decorView.findViewById<View>(Window.ID_ANDROID_CONTENT).top
    }

    fun getScreenRealHeight(window: Window?): Int {
        if (window == null) {
            return 0
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val wm = window.context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getRealMetrics(outMetrics)
            return outMetrics.heightPixels
        } else {
            getScreenHeightWithSystemUI(window)
        }
    }

    @JvmStatic
    fun getScreenHeightWithSystemUI(window: Window): Int {
        return window.decorView.height
    }

    @JvmStatic
    fun getScreenHeightWithoutNavigationBar(context: Context?): Int {
        if (context == null) {
            return 0
        }
        return context.resources.displayMetrics.heightPixels
    }

    @JvmStatic
    fun getScreenHeightWithoutSystemUI(window: Window?): Int {
        if (window == null) {
            return 0
        }
        val r = Rect()
        window.decorView.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top
    }


    @JvmStatic
    fun isFullScreen(activity: Activity): Boolean {
        return (activity.window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN
                == WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    @JvmStatic
    fun isFullScreen(window: Window?): Boolean {
        if (window == null) {
            return false
        }
        return (window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN
                == WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    @JvmStatic
    fun getStatusBarHeight(window: Window?): Int {
        if (window == null) {
            return 0
        }
        val frame = Rect()
        window.decorView.getWindowVisibleDisplayFrame(frame)
        return frame.top
    }


    @JvmStatic
    fun getNavigationBarHeight(context: Context?, window: Window?): Int {
        if (context == null || window == null) {
            return 0
        }
        
        val deviceNavigationHeight = getInternalDimensionSize(context.resources, Constants.NAVIGATION_BAR_HEIGHT_RES_NAME)
        //三星android9 OneUI2.0一下打开全面屏手势，导航栏实际高度比 deviceHeight 小，需要做兼容
        val manufacturer = if (Build.MANUFACTURER == null) "" else Build.MANUFACTURER.trim { it <= ' ' }
        if (manufacturer.toLowerCase().contains("samsung") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            window.decorView.rootWindowInsets?.let {
                val stableBottom = it.stableInsetBottom
                if (stableBottom < deviceNavigationHeight) {
                    return stableBottom;
                }
            }
        }
        return deviceNavigationHeight;
    }

    private fun getInternalDimensionSize(res: Resources, key: String): Int {
        var result = 0
        val resourceId = res.getIdentifier(key, Constants.DIMEN, Constants.ANDROID)
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId)
        }
        return result
    }

    @JvmStatic
    fun isPortrait(context: Context?): Boolean {
        if (context == null) {
            return true
        }
        return when (context.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                true
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                false
            }
            else -> {
                val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                val point = Point()
                display.getSize(point)
                point.x <= point.y
            }
        }
    }

    @JvmStatic
    @TargetApi(14)
    fun isNavigationBarShow(context: Context?, window: Window?): Boolean {
        return isNavBarVisible(context, window)
    }

    @JvmStatic
    fun isNavBarVisible(context: Context?, window: Window?): Boolean {

        if (context == null || window == null) {
            return false
        }
        
        var isVisible = false
        var viewGroup: ViewGroup? = window.decorView as? ViewGroup?
        viewGroup?.let {
            for (i in 0 until it.childCount) {
                var id: Int = it.getChildAt(i).id
                if (id != android.view.View.NO_ID) {
                    var resourceEntryName: String? = context.resources?.getResourceEntryName(id)
                    if ((("navigationBarBackground" == resourceEntryName) && it.getChildAt(i).visibility == android.view.View.VISIBLE)) {
                        isVisible = true
                    }
                }
            }
            if (!isVisible) {
                val navigationBarView: View? = it.findViewById(R.id.lux_immersion_navigation_bar_view)
                navigationBarView?.let { navigationBar ->
                    if (navigationBar.visibility == View.VISIBLE) {
                        isVisible = true
                    }
                }
            }
        }
        if (isVisible) {
            // 对于三星手机，android10以下非OneUI2的版本，比如 s8，note8 等设备上，导航栏显示存在bug："当用户隐藏导航栏时显示输入法的时候导航栏会跟随显示"，会导致隐藏输入之后判断错误
            // 这个问题在 OneUI 2 & android 10 版本已修复，
            val manufacturer = if (Build.MANUFACTURER == null) "" else Build.MANUFACTURER.trim { it <= ' ' }
            if (manufacturer.toLowerCase().contains("samsung")
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                try {
                    return Settings.Global.getInt(context.contentResolver, "navigationbar_hide_bar_enabled") == 0
                } catch (e: Exception) {
                    //nothing to do
                }
            }
            isVisible = !hasSystemUIFlag(window, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }
        return isVisible
    }

    fun hasSystemUIFlag(window: Window?, flag: Int): Boolean {
        if (window == null) {
            return false
        }
        return window.decorView.systemUiVisibility and flag == flag
    }


    internal fun hasMeasuredKeyboard(context: Context?): Boolean {
        getKeyBoardHeight(context)
        return pHeight != -1 || lHeight != -1
    }
}