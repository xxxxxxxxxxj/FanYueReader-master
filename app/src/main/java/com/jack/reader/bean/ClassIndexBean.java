package com.jack.reader.bean;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 11:29
 */
public class ClassIndexBean {
    private int errno;
    private String msg;
    private ClassIndexData data;

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

    public ClassIndexData getData() {
        return data;
    }

    public void setData(ClassIndexData data) {
        this.data = data;
    }

    public static class ClassIndexData {
        private List<BannerBean.BannerData.NewBanner> banner;
        private List<ClassIndexType> retbooktype;

        public List<BannerBean.BannerData.NewBanner> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean.BannerData.NewBanner> banner) {
            this.banner = banner;
        }

        public List<ClassIndexType> getRetbooktype() {
            return retbooktype;
        }

        public void setRetbooktype(List<ClassIndexType> retbooktype) {
            this.retbooktype = retbooktype;
        }

        public static class ClassIndexType {
            private String id;
            private String book_typename;
            private String img;
            private String num;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getBook_typename() {
                return book_typename;
            }

            public void setBook_typename(String book_typename) {
                this.book_typename = book_typename;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }
}
