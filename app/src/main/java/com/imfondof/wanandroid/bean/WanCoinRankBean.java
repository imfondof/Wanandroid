package com.imfondof.wanandroid.bean;

import java.util.List;

/**
 * wanandroid积分排行
 */
public class WanCoinRankBean {

    /**
     * data : {"curPage":1,"datas":[{"coinCount":13318,"level":134,"rank":1,"userId":20382,"username":"g**eii"}],"offset":0,"over":false,"pageCount":1182,"size":30,"total":35435}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * curPage : 1
         * datas : [{"coinCount":13318,"level":134,"rank":1,"userId":20382,"username":"g**eii"}]
         * offset : 0
         * over : false
         * pageCount : 1182
         * size : 30
         * total : 35435
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;



        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * coinCount : 13318
             * level : 134
             * rank : 1
             * userId : 20382
             * username : g**eii
             */

            private int coinCount;
            private int level;
            private int rank;
            private int userId;
            private String username;

            @Override
            public String toString() {
                return "DatasBean{" +
                        "coinCount=" + coinCount +
                        ", level=" + level +
                        ", rank=" + rank +
                        ", userId=" + userId +
                        ", username='" + username + '\'' +
                        '}';
            }

            public int getCoinCount() {
                return coinCount;
            }

            public void setCoinCount(int coinCount) {
                this.coinCount = coinCount;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
