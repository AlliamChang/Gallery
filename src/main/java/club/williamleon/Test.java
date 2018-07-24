package club.williamleon;

import club.williamleon.util.MD5;

/**
 * Created by 53068 on 2018/4/24 0024.
 */
public class Test {

    public static void main(String[] args) {
        try {
            System.out.println(MD5.encrypt("abc"));
            System.out.println(MD5.encrypt("abc"));
            System.out.println(MD5.encrypt("abc"));
            System.out.println(MD5.encrypt("abc"));
            System.out.println(MD5.encrypt("abc"));
            System.out.println(MD5.encrypt("abc"));
//            System.out.println(MD5.equal("abc", "79d010e195502978037cddf2c42f4b90dd368916c31f97fde2d8be91174f7762"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
