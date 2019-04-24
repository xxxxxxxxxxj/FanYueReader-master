package com.jack.reader.bean;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/4/22 11:44
 */
public class DefaultBean {
    private int errno;
    private String msg;
    private DefaultData data;

    public static class DefaultData {
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

    public DefaultData getData() {
        return data;
    }

    public void setData(DefaultData data) {
        this.data = data;
    }
}
