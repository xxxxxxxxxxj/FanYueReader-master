package com.jack.reader.bean;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/24 11:11
 */
public class ChapterInfoBean {
    private int errno;
    private String msg;
    private ChapterInfoData data;

    /**
     *   "url": "com.haotang.book",
     "target": 1,
     "img": "http://img.tangseng.shop/other/banner01.jpg"
     */
    public static class ChapterInfoData{
        private  String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

    public ChapterInfoData getData() {
        return data;
    }

    public void setData(ChapterInfoData data) {
        this.data = data;
    }
}
