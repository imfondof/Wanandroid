package com.imfondof.wanandroid.ui.find

import android.os.Bundle
import android.view.animation.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.imfondof.wanandroid.R
import com.imfondof.wanandroid.ui.base.BaseFragment


/**
 * 学习动画
 */
class AnimFrg : BaseFragment() {
    override fun setContent() = R.layout.frg_anim

    companion object {
        @JvmStatic
        fun newInstance(): Fragment? {
            val bundle = Bundle()
            val fragment = AnimFrg()
            fragment.arguments = bundle
            return fragment
        }
    }

    var scaleBtn: Button? = null
    var alphaBtn: Button? = null
    var rotateBtn: Button? = null
    var transBtn: Button? = null
    var setBtn: Button? = null
    var set2Btn: Button? = null


    lateinit var alphaAnim: AlphaAnimation

    var tv: TextView? = null

    override fun initView() {
        super.initView()
        scaleBtn = getView(R.id.btn_animation)
        alphaBtn = getView(R.id.btn_alpha)
        rotateBtn = getView(R.id.btn_rotate)
        transBtn = getView(R.id.btn_trans)
        setBtn = getView(R.id.btn_set)
        set2Btn = getView(R.id.btn_set2)

        tv = getView(R.id.tv)

        scaleBtn?.setOnClickListener {
            tv?.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.study_scale))
        }

        alphaAnim = AlphaAnimation(1.0f, 0.1f)
        alphaAnim.duration = 3000
        alphaAnim.fillBefore = true
        alphaBtn?.setOnClickListener {
            tv?.startAnimation(alphaAnim)
        }
        //        alphaBtn?.setOnClickListener {
//            tv?.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.study_alpha))
//        }


        rotateBtn?.setOnClickListener {
            tv?.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.study_rotate))
        }
        transBtn?.setOnClickListener {
            tv?.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.study_transe))
        }
        setBtn?.setOnClickListener {
            tv?.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.study_set))
        }
        set2Btn?.setOnClickListener {
            var alphaAnim = AlphaAnimation(1.0f, 0.1f);
            var scaleAnim = ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            var rotateAnim = RotateAnimation(0f, 720f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            var setAnim = AnimationSet(true);
            setAnim.addAnimation(alphaAnim);
            setAnim.addAnimation(scaleAnim);
            setAnim.addAnimation(rotateAnim);

            setAnim.duration = 3000;
            setAnim.fillAfter = true;

            tv?.startAnimation(setAnim)
        }
    }
}