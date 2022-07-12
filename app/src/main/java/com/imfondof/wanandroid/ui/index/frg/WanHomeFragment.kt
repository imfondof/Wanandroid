package com.imfondof.wanandroid.ui.index.frg

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

/**
 * @Description:
 * @CreateDate: 2022/4/12 17:26
 * @Version: 1.0
 */
class WanHomeFragment : BaseFragment(), OnRefreshListener, OnLoadMoreListener {
    private lateinit var mAdapter: WanHomeAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: SmartRefreshLayout
    private val starPage = 0
    private var page = starPage
    private lateinit var viewModel: WanHomeViewModel
    override fun setContent() = R.layout.frg_wan_home

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, InjectorUtil.getHomeModelFactory()).get(WanHomeViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        super.initView()
        mRecyclerView = getView(R.id.recycler_view)
        mRefreshLayout = getView(R.id.refresh_layout)
        mRefreshLayout.setOnRefreshListener(this)
        mRefreshLayout.setOnLoadMoreListener(this)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        mAdapter = WanHomeAdapter()
        mRecyclerView.adapter = mAdapter
        mAdapter.setNewData(viewModel.dataList)
        mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            if (view.id == R.id.article) {
                if (!TextUtils.isEmpty(mAdapter.data[position].link)) {
                    WebViewActivity.loadUrl(activity, mAdapter.data[position].link, mAdapter.data[position].title)
                }
            }
            true
        }
    }

    override fun loadData() {
        super.loadData()
        getData()
    }

    private fun getData() {
        handleData(viewModel.getHomeArticle(page, null)) {
            viewModel.dataList.addAll(it.data.datas)
        }
    }

    private fun <T> handleData(liveData: LiveData<Resource<T>>, action: (T) -> Unit) = liveData.observe(this, Observer { result ->
        if (result?.status == Resource.LOADING) {
            showLoading()
        } else {
            mRefreshLayout.finishRefresh()
            mRefreshLayout.finishLoadMore()
            if (result?.data != null && result.status == Resource.SUCCESS) {
                dissmissLoding()
                action(result.data)
                mAdapter.notifyDataSetChanged()
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