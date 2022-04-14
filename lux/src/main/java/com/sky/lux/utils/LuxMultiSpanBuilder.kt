package com.sky.lux.utils

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes

/**
 * Created by on 2020/4/21
 * 构造多样式多文本
 */
class LuxMultiSpanBuilder {

    private val mBuilder: SpannableStringBuilder = SpannableStringBuilder()

    fun append(builderLux: LuxSpanBuilder): LuxMultiSpanBuilder {
        mBuilder.append(builderLux.build())
        return this
    }

    fun append(text: CharSequence?): LuxMultiSpanBuilder {
        mBuilder.append(text ?: "")
        return this
    }

    fun appendSpace(): LuxMultiSpanBuilder {
        mBuilder.append(" ")
        return this
    }

    @JvmOverloads
    fun append(text: CharSequence?, @StyleRes appearance: Int,
               @StyleableRes colorList: Int = -1): LuxMultiSpanBuilder {
        val context = Lux.getAppContext()
        val ss = SpannableString(text ?: "")
        ss.setSpan(TextAppearanceSpan(context, appearance, colorList), 0, text?.length ?: 0,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBuilder.append(ss)
        return this
    }

    fun appendForegroundColor(text: CharSequence?, colorRes: Int): LuxMultiSpanBuilder {
        val ss = SpannableString(text ?: "")
        val color = colorRes.res2Color()
        ss.setSpan(ForegroundColorSpan(color), 0, text?.length ?: 0,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBuilder.append(ss)
        return this
    }

    fun append(text: CharSequence?, what: Any): LuxMultiSpanBuilder {
        val ss = SpannableString(text ?: "")
        ss.setSpan(what, 0, text?.length ?: 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBuilder.append(ss)
        return this
    }

    fun build(): SpannableStringBuilder {
        return mBuilder
    }
}

/**
 * 构造多样式文本，对整个字符串进行相交位置段的多样式构造
 */
class LuxSpanBuilder(text: CharSequence?) {

    private val mSpanString: SpannableString = SpannableString(text ?: "")

    @JvmOverloads
    fun foregroundColorSpanFrom(
        start: Int = 0, end: Int = mSpanString.length, color: Int): LuxSpanBuilder {
        mSpanString.setSpan(ForegroundColorSpan(color), start, end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    @JvmOverloads
    fun appearanceSpanFrom(start: Int = 0, end: Int = mSpanString.length,
                           appearance: Int, colorList: Int = -1): LuxSpanBuilder {
        val context = Lux.getAppContext()
        mSpanString.setSpan(TextAppearanceSpan(context, appearance, colorList), start, end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    @JvmOverloads
    fun styleSpanFrom(start: Int = 0, end: Int = mSpanString.length, style: Int): LuxSpanBuilder {
        mSpanString.setSpan(StyleSpan(style), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    @JvmOverloads
    fun clickableSpanFrom(
        start: Int = 0, end: Int = mSpanString.length,
        listener: View.OnClickListener?, color: Int): LuxSpanBuilder {
        mSpanString.setSpan(LuxClickableTextSpan(listener, color), start, end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    @JvmOverloads
    fun objectSpanFrom(start: Int = 0, end: Int = mSpanString.length, span: Any): LuxSpanBuilder {
        mSpanString.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun build(): SpannableString {
        return mSpanString
    }
}

class LuxClickableTextSpan(
    private var mListener: View.OnClickListener?,
    @ColorInt private var mColor: Int) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        ds.color = mColor
        ds.isUnderlineText = false
        ds.clearShadowLayer()
    }

    override fun onClick(widget: View) {
        mListener?.onClick(widget)
    }
}