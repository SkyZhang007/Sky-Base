package com.sky.lux.component.keyboard.keyboard.interfaces.listener

import android.view.View

/**
 * preventing listeners that PanelSwitchHelper set these to view from being overwritten
 */
interface OnViewClickListener {
    fun onClickBefore(view: View?)
}

private typealias OnClickBefore = (view: View?) -> Unit

class OnViewClickListenerBuilder : OnViewClickListener {

    private var onClickBefore: OnClickBefore? = null

    override fun onClickBefore(view: View?) {
        onClickBefore?.invoke(view)
    }

    fun onClickBefore(onClickBefore: OnClickBefore) {
        this.onClickBefore = onClickBefore
    }
}