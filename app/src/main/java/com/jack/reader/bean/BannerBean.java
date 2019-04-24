package com.jack.reader.bean;

import java.util.List;

/**
 * Created by 山鸡 on 2017/9/9.
 */

public class BannerBean {
    private int errno;
    private String msg;
    private List<BannerData> data;

    /**
     *   "url": "com.haotang.book",
     "target": 1,
     "img": "http://img.tangseng.shop/other/banner01.jpg"
     */
    public static class BannerData{
        private  String linkurl;
        private String imgurl;
        private int jumptype;

        public int getType() {
            return jumptype;
        }

        public void setType(int type) {
            this.jumptype = type;
        }

        public String getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }

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

    public List<BannerData> getData() {
        return data;
    }

    public void setData(List<BannerData> data) {
        this.data = data;
    }
}
