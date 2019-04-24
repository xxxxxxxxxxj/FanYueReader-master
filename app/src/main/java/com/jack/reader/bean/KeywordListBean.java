package com.jack.reader.bean;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 18:34
 */
public class KeywordListBean {
    private int errno;
    private String msg;
    private KeywordListData data;

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

    public KeywordListData getData() {
        return data;
    }

    public void setData(KeywordListData data) {
        this.data = data;
    }

    public static class KeywordListData {
        private List<Keyword> list;

        public List<Keyword> getList() {
            return list;
        }

        public void setList(List<Keyword> list) {
            this.list = list;
        }

        public static class Keyword {
            private String id;
            private String hotkeyword;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getHotkeyword() {
                return hotkeyword;
            }

            public void setHotkeyword(String hotkeyword) {
                this.hotkeyword = hotkeyword;
            }
        }
    }
}
