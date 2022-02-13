package com.imfondof.wanandroid.data.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;
import java.util.List;

public class SystemResult implements Serializable {
    public List<DataBean> data;
    public Integer errorCode;
    public String errorMsg;

    public static class DataBean extends SectionEntity<DataBean> implements Serializable {
        public List<DataBean> children;
        public Integer courseId;
        public Integer id;
        public String name;
        public Integer order;
        public Integer parentChapterId;
        public Boolean userControlSetTop;
        public Integer visible;

        public DataBean(boolean isHeader, String header) {
            super(isHeader, header);
        }

        public DataBean(DataBean dataBean) {
            super(dataBean);
        }
    }
}
