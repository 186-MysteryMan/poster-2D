package com.szj.poster;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 字体工具类
 *
 * @author 刘彦民
 * @date 2018/5/5
 */
public class FontUtil {
    /**
     * 默认字体
     */
    public static final int DEFAULT_FONT = 1;
    /**
     * PingFangSC字体
     */
    public static final int PINGFANG_FONT = 2;
    /**
     * PingFangSCBold字体
     */
    public static final int PINGFANG_BOLD_FONT = 3;
    /**
     * 方正兰亭特黑GBK
     */
    public static final int FZLTTH_GBK_FONT = 4;


    /**
     * 根据字体类型获取字体
     * @param type
     * @param size
     * @return
     */
    public static Font getFont(int type, float size) {
        // 字体路径
        String path = "";
        switch (type) {
            case DEFAULT_FONT:
                path = "font" + File.separator + "苹方黑体-准-简.ttf";
                break;
            case PINGFANG_FONT:
                path = "font" + File.separator + "苹方黑体-准-简.ttf";
                break;
            case PINGFANG_BOLD_FONT:
                path = "font" + File.separator + "苹方黑体-准-简.ttf";
                break;
            case FZLTTH_GBK_FONT:
                path = "font" + File.separator + "苹方黑体-准-简.ttf";
                break;
            default:
                path = "font" + File.separator + "苹方黑体-准-简.ttf";
        }

        InputStream inputStream = null;
        try {
            inputStream = Objects.requireNonNull(Objects.requireNonNull(Graphics2D.class.getClassLoader().getResource(path)).openStream());
            Font sPfBoldFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            sPfBoldFont = sPfBoldFont.deriveFont(size);
            return sPfBoldFont;
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
