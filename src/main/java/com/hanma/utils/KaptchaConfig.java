package com.hanma.utils;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码的形式可以在下面改，这里是生成四位数字＋字母的形式。
 * 生成验证码的配置
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public Producer kaptchaProducer() {
        Properties properties = new Properties();
        //设置图片边框
        properties.setProperty("kaptcha.border", "yes");
        //设置图片边框为蓝色
        properties.setProperty("kaptcha.border.color", "blue");
        //设置验证码的宽度
        properties.setProperty("kaptcha.image.width", "100");
        //设置宽度
        properties.setProperty("kaptcha.image.height", "40");
        // 文字间隔
        properties.put("kaptcha.textproducer.char.space", "5");
        //如果需要去掉干扰线
        properties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        // 字体
        properties.put("kaptcha.textproducer.font.names", "Arial,Courier,cmr10,宋体,楷体,微软雅黑");
        //设置字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "20");
        //背景颜色渐变开始
        properties.put("kaptcha.background.clear.from", "127,255,212");
        //背景颜色渐变结束
        properties.put("kaptcha.background.clear.to", "240,255,255");
        //设置字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        //限定验证码中的字符
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        //设置验证码的长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //将配置装载到一个实例中
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        //将配置传入实例
        defaultKaptcha.setConfig(new Config(properties));
        return defaultKaptcha;
    }
}

