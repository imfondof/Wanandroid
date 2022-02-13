package com.imfondof.wanandroid.data.bean;

import java.util.List;

public class WanCollectSiteBean {

    /**
     * data : [{"desc":"","icon":"","id":3448,"link":"https://www.jianshu.com/p/c44d7a106302#!/xh","name":"文章汇总|学习Android的一点一滴 - 厘米姑娘 - 简书","order":0,"userId":12351,"visible":1}]
     * errorCode : 0
     * errorMsg :
     */

    private int errorCode;
    private String errorMsg;
    private List<DataBean> data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * desc :
         * icon :
         * id : 3448
         * link : https://www.jianshu.com/p/c44d7a106302#!/xh
         * name : 文章汇总|学习Android的一点一滴 - 厘米姑娘 - 简书
         * order : 0
         * userId : 12351
         * visible : 1
         */

        private String desc;
        private String icon;
        private int id;
        private String link;
        private String name;
        private int order;
        private int userId;
        private int visible;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }
    }
}
