package com.imfondof.wanandroid.other.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imfondof.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class SpanSizeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<SpanSizeModel> spanSizeModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span_size);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            SpanSizeModel sizeModel = new SpanSizeModel();
            sizeModel.setWeight((int)(1+Math.random()*(10-1+1)));
            sizeModel.setName("张三" + i);
            sizeModel.setContent("中国，是以华夏文明为源泉、中华文化为基础，并以汉族为主体民族的多民族国家，通用汉语、汉字，汉族与少数民族被统称为“中华民族”，又自称为炎黄子孙、龙的传人。" + i);
            spanSizeModelList.add(sizeModel);
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 12);
        /**
         * 通过设置权重来决定一行按钮的个数；
         */
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (0 <=position&&position<=3) {
                    return 12;
                } else if (4<=position&&position<=6) {
                    return 2;
                } else if (10<=position &&position<=11) {
                    return 4;
                } else if (13<=position&&position<=14) {
                    return 6;
                } else if(15==position){
                    return 12;
                }else if(16<=position&&position<=18){
                    return 4;
                }else {
                    return 3;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecordViewHolder> {

        @NonNull
        @Override
        public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecordViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_span_size, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
            holder.name.setText(spanSizeModelList.get(position).getName());
            holder.content.setText(spanSizeModelList.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return spanSizeModelList.size();
        }

        public class RecordViewHolder extends RecyclerView.ViewHolder {
            private TextView name;
            private TextView content;

            public RecordViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                content = (TextView) itemView.findViewById(R.id.content);
            }
        }
    }
}