package com.sky.lux.component.keyboard.keyboard.view.panel

import androidx.annotation.IdRes
import com.sky.lux.component.keyboard.keyboard.interfaces.ViewAssertion

/**
 * 扩展panelView
 * 同时需要实现 ViewAssertion 校验 trigger 绑定的有效性，实现 IPanelView 应该是一个 ViewGroup
 */
interface IPanelView : ViewAssertion {

    @IdRes
    fun getBindingTriggerViewId(): Int

    fun isTriggerViewCanToggle(): Boolean

    fun isShowing():Boolean
}