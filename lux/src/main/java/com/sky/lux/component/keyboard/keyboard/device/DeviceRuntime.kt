package com.sky.lux.component.keyboard.keyboard.device

import android.content.Context
import android.content.res.Configuration
import android.view.Window

/**
 * 保存当前设备信息跟随用户操作更改信息
 */
class DeviceRuntime(val context: Context?, val window: Window?) {

    var deviceInfoP: DeviceInfo? = null
    var deviceInfoL: DeviceInfo? = null

    var isNavigationBarShow: Boolean = false
    var isPortrait: Boolean = false
    var isPad: Boolean = false
    var isFullScreen: Boolean = false

    init {
        if (context != null) {
            isPad = (context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
        }
        isPortrait = PanelUtil.isPortrait(context)
        isNavigationBarShow = PanelUtil.isNavigationBarShow(context, window)
        isFullScreen = PanelUtil.isFullScreen(window)
    }

    fun getDeviceInfoByOrientation(cache: Boolean = false): DeviceInfo {
        isPortrait = PanelUtil.isPortrait(context)
        isNavigationBarShow = PanelUtil.isNavigationBarShow(context, window)
        isFullScreen = PanelUtil.isFullScreen(window)

        if (cache) {
            if (isPortrait && deviceInfoP != null) {
                return deviceInfoP!!
            } else if (!isPortrait && deviceInfoL != null) {
                return deviceInfoL!!
            }
        }

        val navigationBarHeight = PanelUtil.getNavigationBarHeight(context, window)
        val statusBarHeight = PanelUtil.getStatusBarHeight(window)
        //以这种方式计算出来的toolbar，如果和statusBarHeight一样，则实际上就是statusBar的高度，大于statusBar的才是toolBar的高度。
        var toolbarH = PanelUtil.getToolbarHeight(window)
        if (toolbarH == statusBarHeight) {
            toolbarH = 0
        }
        val screenHeight = PanelUtil.getScreenRealHeight(window)
        val screenWithoutSystemUIHeight = PanelUtil.getScreenHeightWithoutSystemUI(window)
        val screenWithoutNavigationHeight = PanelUtil.getScreenHeightWithoutNavigationBar(context)

        return if (isPortrait) {
            deviceInfoP = DeviceInfo(window, true,
                    statusBarHeight, navigationBarHeight, toolbarH,
                    screenHeight, screenWithoutSystemUIHeight, screenWithoutNavigationHeight)
            deviceInfoP!!
        } else {
            deviceInfoL = DeviceInfo(window, false,
                    statusBarHeight, navigationBarHeight, toolbarH,
                    screenHeight, screenWithoutSystemUIHeight, screenWithoutNavigationHeight)
            deviceInfoL!!
        }

    }
}