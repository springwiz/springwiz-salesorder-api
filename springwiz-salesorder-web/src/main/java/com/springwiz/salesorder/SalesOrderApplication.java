package com.springwiz.salesorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The Class SalesOrderApplication.
 */
@SpringBootApplication
@ComponentScan("com.springwiz.salesorder")
public class SalesOrderApplication {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SalesOrderApplication.class, args);
    }
}