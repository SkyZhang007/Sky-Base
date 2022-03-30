package com.sky.lux.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;


/**
 * created on 2020-03-23
 * description：lux统一屏幕相关工具类
 */
public class ScreenUtil {

    private static final String XIAOMI_FULLSCREEN_GESTURE = "force_fsg_nav_bar";
    private static final String HUAWAI_DISPLAY_NOTCH_STATUS = "display_notch_status";
    private static final String XIAOMI_DISPLAY_NOTCH_STATUS = "force_black";

    /**
     * @param context used to get system services
     * @return screenWidth in pixels
     */
    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 1080;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size.x;
    }

    /**
     * @param context used to fetch display metrics
     * @param dp      dp value
     * @return pixel value
     */
    public static int dp2px(Context context, int dp) {
        if (context == null) {
            return (dp * 3);
        }
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return Math.round(px);
    }

    public static float dp2px_f(Context context, float dp) {
        if (context == null) {
            return dp * 3;
        }
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());

    }

    public static int px2dp(Context context, float px) {
        return Math.round(px2dp_f(context, px));
    }

    public static float px2dp_f(Context context, float px) {
        final float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }

    /**
     * 获取ActionBar高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    /**
     * 获取是否全屏
     *
     * @return 是否全屏
     */
    public static boolean isTranslucent(View view) {
        Activity activity = getActivityFromView(view);
        if (activity == null) {
            return false;
        }
        return isTranslucent(activity);
    }

    public static boolean isTranslucent(Activity activity) {
        boolean ret = false;
        try {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility();
            ret = (flags & View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

            if (!ret) {//适配小米魅族
                ret = (activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Activity getActivityFromView(View view) {
        if (null != view) {
            Context context = view.getContext();
            return getActivityFromContext(context);
        }
        return null;
    }

    public static Activity getActivityFromContext(Context context) {
        if (context != null) {
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }

    public static boolean xiaomiNavigationGestureEnabled(Context context) {
        int val = Settings.Global.getInt(context.getContentResolver(), XIAOMI_FULLSCREEN_GESTURE, 0);
        return val != 0;
    }

    public static boolean huaweiIsNotchSetToShowInSetting(Context context) {
        // 0: 默认
        // 1: 隐藏显示区域
        int result = Settings.Secure.getInt(context.getContentResolver(), HUAWAI_DISPLAY_NOTCH_STATUS, 0);
        return result == 0;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 2160;
        }
        int screenHeight = getDisplayMetrics(context).heightPixels;
//        if (LuxDeviceUtil.isXiaomi() && xiaomiNavigationGestureEnabled(context)) {
//            screenHeight += getResourceNavHeight(context);
//        }
        return screenHeight;
    }

    /**
     * 获取 DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    private static int getResourceNavHeight(Context context) {
        // 小米4没有nav bar, 而 navigation_bar_height 有值
        int resourceId = context.getResources().getIdentifier(
                "navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }

    /**
     * 剔除挖孔屏等导致的不可用区域后的 height
     *
     * @param activity
     * @return
     */
    public static int getUsefulScreenHeight(Activity activity) {
        return getUsefulScreenHeight(activity, NotchHelper.hasNotch(activity));
    }

    public static int getUsefulScreenHeight(View view) {
        return getUsefulScreenHeight(view.getContext(), NotchHelper.hasNotch(view));
    }

    private static int getUsefulScreenHeight(Context context, boolean hasNotch) {
        int result = getRealScreenSize(context)[1];
        int orientation = context.getResources().getConfiguration().orientation;
        boolean isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT;
        if (!hasNotch) {
            if (isPortrait && DeviceUtil.isEssentialPhone()
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                // https://arstechnica.com/gadgets/2017/09/essential-phone-review-impressive-for-a-new-company-but-not-competitive/
                // 这里说挖孔屏是状态栏高度的两倍
                result -= 2 * StatusBarHelper.getStatusBarHeight(context);
            }
            return result;
        }
//        if (isPortrait) {
        // TODO vivo 设置-系统导航-导航手势样式-显示手势操作区域 打开的情况下，应该减去手势操作区域的高度，但无API
        // TODO vivo 设置-显示与亮度-第三方应用显示比例 选为安全区域显示时，整个 window 会移动，应该减去移动区域，但无API
        // TODO oppo 设置-显示与亮度-应用全屏显示-凹形区域显示控制 关闭是，整个 window 会移动，应该减去移动区域，但无API
//        }
        return result;
    }

    public static int[] getRealScreenSize(Context context) {
        // 切换屏幕导致宽高变化时不能用 cache，先去掉 cache
        return doGetRealScreenSize(context);
    }

    @SuppressLint("ObsoleteSdkInt")
    private static int[] doGetRealScreenSize(Context context) {
        int[] size = new int[2];
        int widthPixels, heightPixels;
        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
        try {
            // used when 17 > SDK_INT >= 14; includes window decorations (statusbar bar/menu bar)
            widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
            heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                // used when SDK_INT >= 17; includes window decorations (statusbar bar/menu bar)
                Point realSize = new Point();
                d.getRealSize(realSize);


                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }

    @TargetApi(17)
    public static boolean xiaomiIsNotchSetToShowInSetting(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), XIAOMI_DISPLAY_NOTCH_STATUS, 0) == 0;
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 取消全屏
     */
    public static void cancelFullScreen(Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 获取虚拟菜单的高度，若无则返回 0
     */
    public static int getNavMenuHeight(Context context) {
        if (!isNavMenuExist(context)) {
            return 0;
        }
        int resourceNavHeight = getResourceNavHeight(context);
        if (resourceNavHeight >= 0) {
            return resourceNavHeight;
        }

        // 小米 MIX 有nav bar, 而 getRealScreenSize(context)[1] - getScreenHeight(context) = 0
        return getRealScreenSize(context)[1] - getScreenHeight(context);
    }

    public static boolean isNavMenuExist(Context context) {
        // 通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }
}
