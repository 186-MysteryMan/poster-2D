package com.szj.poster;

import java.awt.*;

/**
 * @author shenggongjie
 * @date 2021/2/27 23:03
 */
public class PosterParam {
    /**
     * 背景图
     */
    private String backgroundImgPath;

    /**
     * 内容图
     */
    private String contentImgPath;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 昵称下方标题
     */
    private String constant;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 价格
     */
    private String price;
    /**
     * 小程序码坐标
     */
    private QrParam qrParam;
    /**
     * 第一个文本颜色
     */
    private Color firstColor;

    public String getBackgroundImgPath() {
        return backgroundImgPath;
    }

    public void setBackgroundImgPath(String backgroundImgPath) {
        this.backgroundImgPath = backgroundImgPath;
    }

    public String getContentImgPath() {
        return contentImgPath;
    }

    public void setContentImgPath(String contentImgPath) {
        this.contentImgPath = contentImgPath;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getConstant() {
        return constant;
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public QrParam getQrParam() {
        return qrParam;
    }

    public void setQrParam(QrParam qrParam) {
        this.qrParam = qrParam;
    }

    public Color getFirstColor() {
        return firstColor;
    }

    public void setFirstColor(Color firstColor) {
        this.firstColor = firstColor;
    }
}
