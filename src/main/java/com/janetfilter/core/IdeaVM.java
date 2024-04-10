
package com.janetfilter.core;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author maple
 * Created Date: 2024/4/7 19:40
 * Description:
 */

public class IdeaVM {
    public static void main(String[] args) throws Exception {
        System.out.println("IdeaVM.main");

        while (true) {
            System.out.println("请输入：");
            String line = new Scanner(System.in).nextLine();
            if (line.startsWith("modPow")) {
                String[] strArr = line.split(" ");
                BigInteger result = modPow(new BigInteger(strArr[1]), new BigInteger(strArr[2]), new BigInteger(strArr[3]));
                System.out.println("modPow结果：" + result);
            }

            if (line.equals("q")) {
                break;
            }
        }

        Thread.sleep(10000000000L);
    }

    private static BigInteger modPow(BigInteger base, BigInteger exponent, BigInteger m) {
        return base.modPow(exponent, m);
    }
}
