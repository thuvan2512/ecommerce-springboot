package com.thunv.ecommerceou;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.storage.v2.Object;
import com.thunv.schedule.TaskSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;


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
public class EcommerceouApplication  {
	@Autowired
	private TaskSchedule taskSchedule;

	private static final Logger logger = LoggerFactory.getLogger(EcommerceouApplication.class);

	@PostConstruct
	public void executeScheduleTask() {
		Timer timer = new Timer();
		TimerTask timerTask1 = new TimerTask() {
			@Override
			public void run() {
				taskSchedule.scanAndBanExpiredAgent();
			};
		};
		timer.schedule(timerTask1, 0,7000);
	}


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
			logger.error(ex.getMessage());
		}
		SpringApplication.run(EcommerceouApplication.class, args);
	}
}
