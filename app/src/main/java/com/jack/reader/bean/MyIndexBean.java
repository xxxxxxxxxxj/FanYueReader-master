package com.jack.reader.bean;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 14:33
 */
public class MyIndexBean {
    private int errno;
    private String msg;
    private MyIndexData data;

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

    public MyIndexData getData() {
        return data;
    }

    public void setData(MyIndexData data) {
        this.data = data;
    }

    public static class MyIndexData {
        private PageHomeBean.PageHomeData.PageHomeBooks recommendBook;
        private MyIndexList list;

        public PageHomeBean.PageHomeData.PageHomeBooks getRecommendBook() {
            return recommendBook;
        }

        public void setRecommendBook(PageHomeBean.PageHomeData.PageHomeBooks recommendBook) {
            this.recommendBook = recommendBook;
        }

        public MyIndexList getList() {
            return list;
        }

        public void setList(MyIndexList list) {
            this.list = list;
        }

        public static class MyIndexList {
            private List<PageHomeBean.PageHomeData.PageHomeBooks> bookslist;
            private String bookcount;

            public List<PageHomeBean.PageHomeData.PageHomeBooks> getBookslist() {
                return bookslist;
            }

            public void setBookslist(List<PageHomeBean.PageHomeData.PageHomeBooks> bookslist) {
                this.bookslist = bookslist;
            }

            public String getBookcount() {
                return bookcount;
            }

            public void setBookcount(String bookcount) {
                this.bookcount = bookcount;
            }
        }
    }
}
