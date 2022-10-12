package com.thunv.ecommerceou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
//@ComponentScan({ "com.thunv.ecommerceou.controller",
//		"com.thunv.ecommerceou.configs",
//		"com.thunv.ecommerceou.database",
//		"com.thunv.ecommerceou.jwt",
//		"com.thunv.ecommerceou.repositories",
//		"com.thunv.ecommerceou.service",
//		"com.thunv.ecommerceou.social",
//		"com.thunv.ecommerceou.utils",
//		"com.thunv.ecommerceou.validator"
//})
public class EcommerceouApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceouApplication.class, args);
	}
}
