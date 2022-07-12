package com.imfondof.wanandroid.ui.system

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imfondof.wanandroid.R
import com.imfondof.wanandroid.data.http.HttpClient
import com.imfondof.wanandroid.other.ext.id
import com.imfondof.wanandroid.ui.base.BaseFragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SystemFrg : BaseFragment(), OnRefreshListener {
    private var mTreeAdapter: TreeAdapter? = null

    private val titleTv: TextView by id(R.id.title_tv)
    private val systemRv: RecyclerView by id(R.id.recycler_view)
    private val smartRefresh: SmartRefreshLayout by id(R.id.refresh_layout)

    override fun setContent() = R.layout.frg_system

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            val bundle = Bundle()
            val fragment: Fragment = SystemFrg()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        titleTv.text = "体系"

        smartRefresh.setEnableLoadMore(false)
        smartRefresh.setOnRefreshListener(this)
        mTreeAdapter = TreeAdapter(activity)
        systemRv.layoutManager = LinearLayoutManager(activity)
        systemRv.adapter = mTreeAdapter
        getSystem()
    }

//    override fun loadData() {
//        super.loadData()
//        getSystem()
//    }

    private fun getSystem() {
        val call = HttpClient.Builder.getWanAndroidService().system
        call.enqueue(object : Callback<TreeBean> {
            override fun onResponse(call: Call<TreeBean>, response: Response<TreeBean>) {
                mTreeAdapter!!.setNewData(response.body()?.data)
                dissmissLoding()
            }

            override fun onFailure(call: Call<TreeBean>, t: Throwable) {
                dissmissLoding()
            }
        })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        getSystem()
    }
}