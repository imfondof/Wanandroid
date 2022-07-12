package com.imfondof.wanandroid.ui.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.flexbox.FlexboxLayout;
import com.imfondof.wanandroid.R;
import com.imfondof.wanandroid.other.utils.SPUtils;
import com.imfondof.wanandroid.other.utils.ThinBoldSpan;
import com.imfondof.wanandroid.other.utils.ToastUtil;
import com.imfondof.wanandroid.ui.base.App;

import java.util.LinkedList;
import java.util.Queue;

import me.jingbin.library.adapter.BaseByViewHolder;
import me.jingbin.library.adapter.BaseRecyclerAdapter;

public class TreeAdapter extends BaseRecyclerAdapter<TreeItemBean> {
    private LayoutInflater mInflater = null;
    private Queue<TextView> mFlexItemTextViewCaches = new LinkedList<>();
    private boolean isSelect = false;
    private int selectedPosition = -1;
    private final Context context;

    public TreeAdapter(Context context) {
        super(R.layout.item_tree);
        this.context = context;
    }

    @Override
    protected void bindView(BaseByViewHolder<TreeItemBean> holder, TreeItemBean dataBean, int position) {
        if (dataBean != null) {
            TextView tvTreeTitle = holder.getView(R.id.tv_tree_title);
            FlexboxLayout flTree = holder.getView(R.id.fl_tree);
            String name = dataBean.getName();
            if (isSelect) {
                flTree.setVisibility(View.GONE);
                if (selectedPosition == position) {
                    name = name + "     ★★★";
                    tvTreeTitle.setTextColor(App.getInstance().getResources().getColor(R.color.colorTheme));
                } else {
                    tvTreeTitle.setTextColor(App.getInstance().getResources().getColor(R.color.colorContent));
                }
            } else {
                tvTreeTitle.setTextColor(App.getInstance().getResources().getColor(R.color.colorContent));
                flTree.setVisibility(View.VISIBLE);
                for (int i = 0; i < dataBean.getChildren().size(); i++) {
                    WxarticleItemBean childItem = dataBean.getChildren().get(i);
                    TextView child = createOrGetCacheFlexItemTextView(flTree);
                    child.setText(childItem.getName());
                    child.setOnClickListener(v ->
                                    ToastUtil.showToast("id is " + childItem.getId())
//                            CategoryDetailActivity.start(v.getContext(), childItem.getId(), dataBean)
                    );
                    flTree.addView(child);
                }
            }
            tvTreeTitle.setText(ThinBoldSpan.getDefaultSpanString(tvTreeTitle.getContext(), name));
        }
    }

    /**
     * 复用需要有相同的BaseByViewHolder，且HeaderView部分获取不到FlexboxLayout，需要容错
     */
    @Override
    public void onViewRecycled(@NonNull BaseByViewHolder<TreeItemBean> holder) {
        super.onViewRecycled(holder);
        FlexboxLayout fbl = holder.getView(R.id.fl_tree);
        if (fbl != null) {
            for (int i = 0; i < fbl.getChildCount(); i++) {
                mFlexItemTextViewCaches.offer((TextView) fbl.getChildAt(i));
            }
            fbl.removeAllViews();
        }
    }

    private TextView createOrGetCacheFlexItemTextView(FlexboxLayout fbl) {
        TextView tv = mFlexItemTextViewCaches.poll();
        if (tv != null) {
            return tv;
        }
        if (mInflater == null) {
            mInflater = LayoutInflater.from(fbl.getContext());
        }
        return (TextView) mInflater.inflate(R.layout.layout_tree_tag, fbl, false);
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
        if (isSelect) {
            selectedPosition = SPUtils.getInt("find_position", -1);
        }
    }

    public boolean isSelect() {
        return isSelect;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
