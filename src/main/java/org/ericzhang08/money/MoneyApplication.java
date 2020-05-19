package org.ericzhang08.money;

import org.ericzhang08.money.fp.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class MoneyApplication implements CommandLineRunner {
    private final Application fpApplication;

    MoneyApplication( Application fpApplication) {
        this.fpApplication = fpApplication;
    }

    public static void main(String[] args) {
        SpringApplication.run(MoneyApplication.class, args);
    }

    @Override
    public void run(String... args) {
        var listingPrice = new BigDecimal(100);

        System.out.println();

        System.out.println(fpApplication.getClass().getName());
        System.out.println("100:" + fpApplication.run(listingPrice));

        var listingPrice1 = new BigDecimal(110);

        System.out.println();

        System.out.println(fpApplication.getClass().getName());
        System.out.println("110:" + fpApplication.run(listingPrice1));
    }
}
