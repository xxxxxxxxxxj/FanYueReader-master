package com.jack.reader.bean;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/24 11:03
 */
public class ChapterBean {
    private int errno;
    private String msg;
    private ChapterData data;

    /**
     * "url": "com.haotang.book",
     * "target": 1,
     * "img": "http://img.tangseng.shop/other/banner01.jpg"
     */
    public static class ChapterData {
        private List<NewChapter> chapters;
        private String ismybook;

        public List<NewChapter> getChapters() {
            return chapters;
        }

        public void setChapters(List<NewChapter> chapters) {
            this.chapters = chapters;
        }

        public String getIsmybook() {
            return ismybook;
        }

        public void setIsmybook(String ismybook) {
            this.ismybook = ismybook;
        }

        public static class NewChapter {
            private String c;
            private String t;

            public String getC() {
                return c;
            }

            public void setC(String c) {
                this.c = c;
            }

            public String getT() {
                return t;
            }

            public void setT(String t) {
                this.t = t;
            }
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

    public ChapterData getData() {
        return data;
    }

    public void setData(ChapterData data) {
        this.data = data;
    }
}
