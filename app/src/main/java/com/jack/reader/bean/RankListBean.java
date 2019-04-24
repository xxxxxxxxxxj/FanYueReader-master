package com.jack.reader.bean;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 13:19
 */
public class RankListBean {
    private int errno;
    private String msg;
    private RankListData data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RankListData getData() {
        return data;
    }

    public void setData(RankListData data) {
        this.data = data;
    }

    public static class RankListData {
        private List<RankListType> list;

        public List<RankListType> getList() {
            return list;
        }

        public void setList(List<RankListType> list) {
            this.list = list;
        }

        public static class RankListType {
            private String id;
            private String title;
            private String imgurl;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }
        }
    }
}
