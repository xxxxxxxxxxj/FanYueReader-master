package com.jack.reader.bean;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/23 17:12
 */
public class NewBookListBean {
    private int errno;
    private String msg;
    private NewBookListData data;

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

    public NewBookListData getData() {
        return data;
    }

    public void setData(NewBookListData data) {
        this.data = data;
    }

    public static class NewBookListData {
        private List<PageHomeBean.PageHomeData.PageHomeBooks> list;

        public List<PageHomeBean.PageHomeData.PageHomeBooks> getList() {
            return list;
        }

        public void setList(List<PageHomeBean.PageHomeData.PageHomeBooks> list) {
            this.list = list;
        }
    }
}
