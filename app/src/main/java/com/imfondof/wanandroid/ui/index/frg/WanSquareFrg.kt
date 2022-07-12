package com.imfondof.wanandroid.ui.index.frg

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.imfondof.wanandroid.R
import com.imfondof.wanandroid.data.Resource
import com.imfondof.wanandroid.other.utils.InjectorUtil
import com.imfondof.wanandroid.other.view.webView.WebViewActivity
import com.imfondof.wanandroid.ui.base.BaseFragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

class WanSquareFrg : BaseFragment(), OnRefreshListener, OnLoadMoreListener {
    private lateinit var adapter: WanHomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SmartRefreshLayout
    private val starPage = 0
    var page = starPage
    private lateinit var viewModel: WanHomeViewModel
    override fun setContent() = R.layout.frg_wan_home

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, InjectorUtil.getHomeModelFactory()).get(WanHomeViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment? {
            val bundle = Bundle()
            val fragment = WanSquareFrg()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        recyclerView = getView(R.id.recycler_view)
        refreshLayout = getView(R.id.refresh_layout)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadMoreListener(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        adapter = WanHomeAdapter()
        recyclerView.adapter = adapter
        adapter.setNewData(viewModel.dataList)
        adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            if (!TextUtils.isEmpty(this.adapter.data[position].link)) {
                WebViewActivity.loadUrl(activity, this.adapter.data[position].link, this.adapter.data[position].title)
            }
            true
        }
    }

    override fun loadData() {
        super.loadData()
        getData()
    }

    private fun getData() {
        handleData(viewModel.getSquareArticle(page, null)) {
            viewModel.dataList.addAll(it.data.datas)
        }
    }

    private fun <T> handleData(liveData: LiveData<Resource<T>>, action: (T) -> Unit) = liveData.observe(this, Observer { result ->
        if (result?.status == Resource.LOADING) {
            showLoading()
        } else {
            refreshLayout.finishRefresh()
            refreshLayout.finishLoadMore()
            if (result?.data != null && result.status == Resource.SUCCESS) {
                dissmissLoding()
                action(result.data)
                adapter.notifyDataSetChanged()
            } else {
                dissmissLoding()
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show()
            }
        }
    })

    override fun onRefresh(refreshLayout: RefreshLayout) {
        viewModel.dataList?.clear()
        page = starPage
        getData()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        getData()
    }
}