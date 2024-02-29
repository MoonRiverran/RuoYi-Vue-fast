package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableCaching
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
//        try{
//            String password = "%?Buru1jiaN";
//            System.out.println("明文密码: " + password);
//            String[] keyPair = ConfigTools.genKeyPair(512);
//            //私钥
//            String privateKey = keyPair[0];
//            //公钥
//            String publicKey = keyPair[1];
//            //用私钥加密后的密文
//            password = ConfigTools.encrypt(privateKey, password);
//
//            System.out.println("privateKey:" + privateKey);
//            System.out.println("publicKey:" + publicKey);
//
//            System.out.println("password:" + password);
//
//            String decryptPassword = ConfigTools.decrypt(publicKey, password);
//            System.out.println("解密后:" + decryptPassword);
//        }catch (Exception e){
//        }
    }
}
