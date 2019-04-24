package com.jack.reader.bean;

/**
 * Created by 山鸡 on 2017/9/9.
 */

public class FindImageBean {

    private String title;
    private int icon1ResId;
    private int icon2ResId;
    private String _id;

    public FindImageBean(String title, int iconResId) {
        this.title = title;
        this.icon1ResId = iconResId;
    }

    public FindImageBean(String title, int iconResId,int icon2ResId,String _id) {
        this.title = title;
        this.icon1ResId = iconResId;
        this.icon2ResId = icon2ResId;
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon1ResId() {
        return icon1ResId;
    }

    public void setIcon1ResId(int icon1ResId) {
        this.icon1ResId = icon1ResId;
    }

    public int getIcon2ResId() {
        return icon2ResId;
    }

    public void setIcon2ResId(int icon2ResId) {
        this.icon2ResId = icon2ResId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
