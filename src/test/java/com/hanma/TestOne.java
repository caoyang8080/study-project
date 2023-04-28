package com.hanma;

import com.google.code.kaptcha.Producer;
import com.hanma.utils.KaptchaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 随机码
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReggieApplication.class)
public class TestOne {

    @Autowired
    private Producer checkCode;
    @Autowired
    private KaptchaConfig kaptchaConfig;

    @Test
    public void TestAdd1(){
        Producer producer = kaptchaConfig.kaptchaProducer();
        String text = producer.createText();
        System.out.println(text);
        BufferedImage image = producer.createImage(text);
        File file = new File("saved.png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
