package com.bg.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

/**
 * 验证码图片
 * @author ctp
 * @date 2022/11/22 15:00
 */
public class VerifyCodeImg {
    private int width = 160;
    private int height = 40;
    private int lineCount = 20;
    private BufferedImage bImage;
    private StringBuffer code = new StringBuffer();
    private String str = "wertyupasdfghjkzxcvbnmQWERTYUPASDFGHJKLZXCVBNM2345678";

    public VerifyCodeImg() {
        bImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = bImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        Font font = new Font("", Font.BOLD, 24);
        g.setFont(font);
        g.setColor(Color.BLACK);

        g.drawRect(0, 0, width-1 , height-1);

        Random random = new Random();
        for (int i = 0; i < 4; i++) {

            char c = str.charAt(random.nextInt(str.length()));
            int top = random.nextInt(11) + 20;
            g.drawString(c + "", width/4*i + height/8, top);
            code.append(c);
        }

        for (int j = 0; j < lineCount; j++) {
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(height);
            int y1 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);

        }

    }

    public String getCode() {
        return code.toString();
    }

    public String getImg() {
        try(ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            // bImage --> stream
            ImageIO.write(bImage, "png", stream);
            // stream --> ByteArray --> Base64EncoderString
            String base64 = Base64.getEncoder().encodeToString(stream.toByteArray());
            stream.flush();
            return "data:image/png;base64," + base64;
        }catch (Exception e) {
            return "getImg error";
        }

    }
}
