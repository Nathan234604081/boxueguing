package cn.edu.gdmec.android.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 23460 on 2017/12/27.
 */

public class MD5Utils {
    public  static  String md5(String text) { //调用MD5方法，括号为传进来的文本参数
        try {
            MessageDigest digest = MessageDigest.getInstance("md5"); //通过MessageDigest类获取它静态对象，传入MD5算法。
            byte[] result = digest.digest(text.getBytes());//通过digest方法获取文本字节
            StringBuilder sb = new StringBuilder();//16进制转换
            for (byte b : result){//16进制转换
                int number = b & 0xff;//
                String hex = Integer.toHexString(number);
                if (hex.length() == 1){
                    sb.append("0"+hex);//在字节为1位时进行拼接
                }else{
                    sb.append(hex);
                }
            }
            return  sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
