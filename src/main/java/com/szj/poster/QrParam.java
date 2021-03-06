package com.szj.poster;

/**
 * @author shenggongjie
 * @date 2021/1/19 15:25
 */
public class QrParam {


    /**
     * 小程序码路径
     */
    private String qrPath;

    /**
     * x轴,最左坐标
     */
    private int x;

    /**
     * y轴,最上坐标
     */
    private int y;

    public String getQrPath() {
        return qrPath;
    }

    public void setQrPath(String qrPath) {
        this.qrPath = qrPath;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
