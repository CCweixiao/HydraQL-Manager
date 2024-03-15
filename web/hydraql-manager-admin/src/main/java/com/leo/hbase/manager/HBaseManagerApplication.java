package com.leo.hbase.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * 启动程序
 * 
 * @author leojie
 */
@EnableWebSocket
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HBaseManagerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(HBaseManagerApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  HBaseManager启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "  _   _ ____                 __  __                                   \n" +
                " | | | | __ )  __ _ ___  ___|  \\/  | __ _ _ __   __ _  __ _  ___ _ __ \n" +
                " | |_| |  _ \\ / _` / __|/ _ \\ |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|\n" +
                " |  _  | |_) | (_| \\__ \\  __/ |  | | (_| | | | | (_| | (_| |  __/ |   \n" +
                " |_| |_|____/ \\__,_|___/\\___|_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|   \n" +
                "                                                      |___/           \n");
    }
}