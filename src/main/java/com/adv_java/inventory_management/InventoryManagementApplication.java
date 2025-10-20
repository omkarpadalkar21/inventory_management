package com.adv_java.inventory_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class InventoryManagementApplication {

    public static void main(String[] args) {
        TimeZone defaultTz = TimeZone.getTimeZone("Asia/Kolkata");
        TimeZone.setDefault(defaultTz);
        System.setProperty("user.timezone", "Asia/Kolkata");

        SpringApplication.run(InventoryManagementApplication.class, args);
    }

}
