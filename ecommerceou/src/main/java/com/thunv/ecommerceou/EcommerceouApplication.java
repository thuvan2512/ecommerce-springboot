package com.thunv.ecommerceou;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.storage.v2.Object;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
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
		try {
			ClassLoader classLoader = EcommerceouApplication.class.getClassLoader();
			File file = new File(Objects.requireNonNull(classLoader.getResource("ServiceAccountKey.json")).getFile());
			FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://ecom-notify-db-default-rtdb.firebaseio.com")
					.build();
			FirebaseApp.initializeApp(options);
		}catch (Exception ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
//		System.out.println("abc");
//		Timer t = new Timer();
//		TimerTask tt = new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println("Task is on");
//			};
//		};
//		t.schedule(tt, 0,3000);
		SpringApplication.run(EcommerceouApplication.class, args);
	}
}
