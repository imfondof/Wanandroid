package com.imfondof.wanandroid.other.ext

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView

//这里可能会出现找不到文字的情况，发生错误记得检查一下文字是否正确
class NoUnderlineSpan : UnderlineSpan() {
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.color = ds.linkColor
        ds.isUnderlineText = false
    }
}

fun TextView.colorText(colorText: String, color: String, click: () -> Unit) {
    val style = SpannableStringBuilder()
    val index = text.indexOf(colorText, 0)
    style.append(text)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            click()
        }
    }
    style.setSpan(clickableSpan, index, index + colorText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    val noUnderlineSpan = NoUnderlineSpan()
    style.setSpan(noUnderlineSpan, index, index + colorText.length, Spanned.SPAN_MARK_MARK)
    val foregroundColorSpan = ForegroundColorSpan(Color.parseColor(color))
    style.setSpan(foregroundColorSpan, index, index + colorText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    movementMethod = LinkMovementMethod.getInstance()
    text = style
}
