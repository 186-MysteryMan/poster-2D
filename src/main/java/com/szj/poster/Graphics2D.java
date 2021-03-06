package com.szj.poster;
import sun.awt.image.BufferedImageGraphicsConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 使用java原生的Graphics2D画图
 * <p>
 * 注意：step方法是每步执行的结果，必须按顺序执行，只有有了上一步的结果之后才能执行下一步，因为下一步的背景图是上一步的结果图（后一步依赖前一步）。
 * 本例子实现的功能:
 * 1、背景图上加图片
 * 2、画圆角
 * 3、画圆形头像
 * 4、图片上写字
 * <p>
 *
 * @author 刘彦民
 * @date 2018/5/5
 */
public class Graphics2D {
    /**
     * 像素扩大的倍数
     */
    private static final float FACTOR = 2.0f;
    private static final int FFX = (int) (446 * FACTOR);
    private static final int SFF = (int) (755 * FACTOR);
    private static final int SX = (int) (76 * FACTOR);
    private static final int TZ = (int) (20 * FACTOR);
    private static final int OE = (int) (18 * FACTOR);
    private static final int TN = (int) (39 * FACTOR);
    /**
     * 所有步骤的集合
     */
    public static InputStream drawImage(PosterParam posterParam) {
        String avatarUrl = posterParam.getAvatarUrl();
        String backgroundImgPath = posterParam.getBackgroundImgPath();
        String constant = posterParam.getConstant();
        String contentImgPath = posterParam.getContentImgPath();
        String goodsName = posterParam.getGoodsName();
        String nickname = posterParam.getNickname();
        String price = posterParam.getPrice();
        Color firstColor = posterParam.getFirstColor();
        QrParam qrParam = posterParam.getQrParam();
        String qrPath = qrParam.getQrPath();
        int x = qrParam.getX();
        int y = qrParam.getY();
        ByteArrayOutputStream os = null;
        InputStream is = null;
        try {
            BufferedImage img = new BufferedImage(FFX, SFF, BufferedImage.TYPE_INT_RGB);
            BufferedImageGraphicsConfig config = BufferedImageGraphicsConfig.getConfig(img);
            img =config.createCompatibleImage(FFX, SFF, Transparency.TRANSLUCENT);
            java.awt.Graphics2D g = img.createGraphics();

            // 画头像
            BufferedImage bg = ImageIO.read(Objects.requireNonNull(Graphics2D.class.getClassLoader().getResource(backgroundImgPath)));
            BufferedImage image = null;
            if (avatarUrl != null) {
                BufferedImage avatar = ImageIO.read(new URL(avatarUrl));

                // 画头像圆角，根据需要是否使用 BufferedImage.TYPE_INT_ARGB
                image = new BufferedImage(SX, SX, BufferedImage.TYPE_INT_ARGB);
                Ellipse2D.Double shape = new Ellipse2D.Double(0, 0,SX, SX);
                java.awt.Graphics2D g2 = image.createGraphics();
                image = g2.getDeviceConfiguration().createCompatibleImage(SX, SX, Transparency.TRANSLUCENT);
                g2 = image.createGraphics();
                g2.setComposite(AlphaComposite.Clear);
                g2.fill(new Rectangle(FFX, SFF));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
                g2.setClip(shape);
                // 使用 setRenderingHint 设置抗锯齿
                g2 = image.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillRoundRect(0, 0,SX, SX, SX, SX);
                g2.setComposite(AlphaComposite.SrcIn);
                g2.drawImage(avatar,0, 0, 160, 160, null);
                g2.dispose();
            }

            g.drawImage(bg.getScaledInstance(FFX, SFF, Image.SCALE_SMOOTH), 0, 0, null);
            if (image != null) {
                g.drawImage(image.getScaledInstance(SX, SX,Image.SCALE_SMOOTH), TZ, OE,null);
                image.getGraphics().dispose();
            }
            g.setColor(Color.WHITE);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    0.8f));
            g.fillRoundRect(TZ, (int) (105 * FACTOR), (int) (405 * FACTOR), (int) (632 * FACTOR),40, 40);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            // 画昵称
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            if (nickname != null && !"".equals(nickname)) {
                g.setColor(firstColor);
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 32.0f));
                g.drawString(nickname, (int) (104 * FACTOR), (int) (45 * FACTOR));
            }

            // 画昵称下面的内容
            if (constant != null && !"".equals(constant)) {
                g.setColor(Color.white);
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 32.0f));
                g.drawString(constant, (int) (104 * FACTOR), (int) (70 * FACTOR));
            }


            // 商品图
            ImageIcon bufImg = new ImageIcon(new URL(contentImgPath));
            Image image1 = bufImg.getImage();
            BufferedImage contentImage = toBufferedImage(image1);
            g.setBackground(Color.white);
            g.drawImage(contentImage.getScaledInstance((int) (368 * FACTOR),(int) (368 * FACTOR), Image.SCALE_SMOOTH), TN, (int) (126 * FACTOR), Color.white, new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return true;
                }
            });
            contentImage.getGraphics().dispose();
            //生成小程序码
            BufferedImage qrCode = ImageIO.read(new URL(qrPath));
            g.drawImage(qrCode.getScaledInstance((int) (150 * FACTOR),(int) (150 * FACTOR), Image.SCALE_SMOOTH), (int) (x * FACTOR), (int) (y * FACTOR), null, new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return true;
                }
            });
            qrCode.getGraphics().dispose();
            // 画商品名称
            if (goodsName != null && !"".equals(goodsName)) {
                g.setColor(new Color(33, 33, 33, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 32.0f));
                drawTextNewLine(g, goodsName, TN, (int) (635 * FACTOR), (int) (25 * FACTOR), (int) (200 * FACTOR), new Color(33, 33, 33, 128), 40, 3, (int) (110 * FACTOR), null);
            }

            // 画价格
            if (price != null && !"".equals(price)) {
                g.setColor(new Color(246, 88, 25, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 46.0f));
                g.drawString(price, TN, (int) (530 * FACTOR));
            }

            g.dispose();
            os = new ByteArrayOutputStream();
            ImageIO.write(img, "png", os);
            is = new ByteArrayInputStream(os.toByteArray());
            return is;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseStreamUtil.close(is,os,"生成海报关流异常");
        }
        return null;
    }

    /**
     * image File 转 BufferedImage 的依赖函数
     *
     * @param image image
     * @return BufferedImage
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
    /**
     * 绘制海报文字(换行)
     *
     * @param graphics     画笔
     * @param text         文本
     * @param width        位置：x
     * @param height       位置：y
     * @param lineHeight   单行行高
     * @param lineWidth    单行行宽
     * @param color        文本颜色
     * @param limitLineNum 限制行数
     * @param msyhBoldFont 字体
     * @return int 行数
     */
    public static int drawTextNewLine(java.awt.Graphics2D graphics, String text, int width, int height, int lineHeight,
                                      int lineWidth, Color color, int textSize, int limitLineNum, int backgroundWidth, Font msyhBoldFont) {

        Font font = graphics.getFont();
        msyhBoldFont = font;
        graphics.setFont(font);
        graphics.setPaint(color);
        FontRenderContext frc = graphics.getFontRenderContext();
        graphics.getFontRenderContext();
        Rectangle2D stringBounds = font.getStringBounds(text, frc);
        double fontWidth = stringBounds.getWidth();
        List<String> lineList = new ArrayList<String>();
        int lineCharCountSub = 0;

        // 不满一行
        if (fontWidth <= lineWidth) {
            lineList.add(text);
            width = (backgroundWidth - Double.valueOf(fontWidth).intValue()) / 2 + backgroundWidth / 2 + 57;
        } else {
            width = (backgroundWidth - lineWidth) / 2 + backgroundWidth / 2 + 57;
            // 输出文本宽度,这里就以画布宽度作为文本宽度测试
            int textWidth = lineWidth;
            // 文本长度是文本框长度的倍数
            double bs = fontWidth / textWidth;
            // 每行大概字数
            int lineCharCount = (int) Math.ceil(text.length() / bs);
            lineCharCountSub = lineCharCount;
            int beginIndex = 0;
            while (beginIndex < text.length()) {
                int endIndex = beginIndex + lineCharCount;
                if (endIndex >= text.length()) {
                    endIndex = text.length();
                }
                String lineStr = text.substring(beginIndex, endIndex);
                Rectangle2D tempStringBounds = msyhBoldFont.getStringBounds(lineStr, frc);
                int tags = 1;
                while (tempStringBounds.getWidth() > textWidth) {
                    lineStr = lineStr.substring(0, lineStr.length() - tags);
                    tempStringBounds = msyhBoldFont.getStringBounds(lineStr, frc);
                }
                lineList.add(lineStr);
                beginIndex = beginIndex + lineStr.length();
            }
        }

        // Color.BLACK 。字体颜色
        graphics.setPaint(color);
        if (lineHeight == 0) {
            lineHeight = 35;
        }
        int lineNum = lineList.size();
        if (limitLineNum != 0 && lineNum > limitLineNum) {
            lineNum = limitLineNum;
        }
        // 绘制 换行文字
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < lineNum; i++) {
            String lineStr = lineList.get(i);
            if (lineNum >= 2 && i == lineNum - 1) {
                if (lineStr.length() >= lineCharCountSub - 3) {
                    lineStr = lineStr.substring(0, lineStr.length() - 2) + "...";
                }
            }
            graphics.drawString(lineStr, width, height + (i + 1) * lineHeight);
            graphics.drawString(lineStr, width, height + (i + 1) * lineHeight);
        }
        return lineNum;
    }
}
