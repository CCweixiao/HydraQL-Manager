package com.hydraql.manager.core.hbase;

/**
 * @author leojie 2024/2/23 15:17
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(HBaseVersion.matches("1.2.0", "1.2.1"));
        System.out.println(HBaseVersion.matches("1.2.0", "1.3.1"));
        System.out.println(HBaseVersion.matches("1.4.12", "1.4.3"));
    }
}
