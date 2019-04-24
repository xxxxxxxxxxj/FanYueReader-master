package com.jack.reader.bean;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/22 16:18
 */
public class PageHomeBean {
    private int errno;
    private String msg;
    private PageHomeData data;

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

    public PageHomeData getData() {
        return data;
    }

    public void setData(PageHomeData data) {
        this.data = data;
    }

    public static class PageHomeData {
        private List<BannerBean.BannerData> banner;
        private List<PageHomeBooks> books;

        public List<BannerBean.BannerData> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean.BannerData> banner) {
            this.banner = banner;
        }

        public List<PageHomeBooks> getBooks() {
            return books;
        }

        public void setBooks(List<PageHomeBooks> books) {
            this.books = books;
        }

        public static class PageHomeBooks {
            private String id;
            private String title;
            private String summary;
            private String author;
            private String book_typeid;
            private String book_typename;
            private String status;
            private String readcount;
            private String created;
            private String uptime;
            private String content_secr;
            private String coverimg;

            public String getBook_typename() {
                return book_typename;
            }

            public void setBook_typename(String book_typename) {
                this.book_typename = book_typename;
            }

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

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getBook_typeid() {
                return book_typeid;
            }

            public void setBook_typeid(String book_typeid) {
                this.book_typeid = book_typeid;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getReadcount() {
                return readcount;
            }

            public void setReadcount(String readcount) {
                this.readcount = readcount;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getUptime() {
                return uptime;
            }

            public void setUptime(String uptime) {
                this.uptime = uptime;
            }

            public String getContent_secr() {
                return content_secr;
            }

            public void setContent_secr(String content_secr) {
                this.content_secr = content_secr;
            }

            public String getCoverimg() {
                return coverimg;
            }

            public void setCoverimg(String coverimg) {
                this.coverimg = coverimg;
            }
        }
    }
}
