package com.thunv.ecommerceou;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.JsonObject;
import com.google.storage.v2.Object;
import com.thunv.schedule.TaskSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("type", "service_account");
			jsonObject.addProperty("project_id", "ecom-notify-db");
			jsonObject.addProperty("private_key_id", "6af6a51c5ebb032ee80d57c577325e35c57bed9b");
			jsonObject.addProperty("private_key", "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDExctx3JxKSQwW\n4KL0g7CPE+i16DvAGAFYQIuoFJYd/aKgIEv8FJOox9wJsj0WofUnps/fxzA0Hkqz\n8rbagcr3lXbGREbj7w+l5KV/NQdTfAzMtG554jlsOQgZpFtZ+VW8fT3B50wsQHEZ\nhyFTZWkk37N0xTRmYMNDk7pKX/t6/7aEyJbY73BoeLpiuM4rDwFlkyW1gcQHKVtK\n3R/F2HbMP0ur0f8+GsMTl7RjSsv0nzPkGQZD2M1TtqBHQwS/Tvd7cMTR/lKPL2QH\n0gBGBPzYvnkZFZ+lZq8ywxbpTnEh6hR/yaiz7NZTPVQQ77jiq7iOBHa3z22IJiNC\n7o3I+PIhAgMBAAECggEAAKdq4r3txcwuemq8lekUYpRZjBW5kZzOz9YWlLxdR5wc\nwNEcqYnAuCFSapSVYgRvp/Ykp5juEMVHQKf6Sjl/VQFx2ay3StmD/PxYnbatl54g\nXYONwLFTGShLHekzPcY/rgLBTFo+QUElUxU1M9WQ4vTMoBMp9ntkuL42YroFECKF\nD4Xana7BsUs4aPYwLDfpoLYeJkP/Pa4JJX0mNyJcNVdT6xqlDJtiLEWJPWOYs8Kz\nyntlEdlHMuDtDgne4ACPpopvsZv1HmR1Cq3JIKzsFygTWglffnuJYwm+YrhMnbyl\nmbyU7tNwArZMgmV05YQ2hkLKM5CoHC4F+TXi7nF/kQKBgQD1V0s0RkM8Okcxxkyd\n8ov5kpEmK8Kz6DYeFDCsC1QJ93FLcFoDEgG2gt1JUFRvZSIqT5Fne1Zwaf2ejICW\nO+Dl+oqG2d8J768DbRDK73DDaCQMWl41AmPobgZi/r1o4tqIQqJW/9/z6OVXo23T\nI4/YYWpsKsp8UVeKBnJ4TrUosQKBgQDNUlGpV9YAQpH24J9zZbwkG0uekGfca1CW\nb4yy3OFQUD6ZsSWt3UiKgoaLMImvExcEg+4uCIXVRdbDCdBqsjld2mppG9191f3G\nCV1QH1qKl2oBqKKqWO3rxtxhGUnlwI0hkX02VrDjHvrNQkfjkw1uDTuxP/toQsVP\nSIyqWyC8cQKBgQDdmH0KuDPeYF0RriRhvUOEpkeIDJqN2L2YqW/U1V6QqfigxNfo\nUglOCiFJKM861kPnp3qT1ykZ3AIBhLJX/7mAv60DZCI6XUzccVRrl51oQdXdenoA\nsyn+M/J8083MwKnVzSnIHtDvA3qyj6CQxlwUetie3n+JjgbdWjlmROEH4QKBgEnf\nwHoJnHnjedk14KkIhbUIDSE0G+p2wjweTqVcS8U12hA6aLAx2hPb+sTB5ggT+kgp\nn9z4PA8MXcU3FsIM9g8Ksxa2a+8Gu5Qxcl2NqmoLKJ0IK97WuGQI3Ooiyy/bI+xc\nCI0wi9xsHRkVLAB5vG9IW53ew3oz7AImvFXfC9xBAoGBANx7fYg+kAppyE2zwwO/\nQDS3JJWkq2Bh6STt43jPisTBuZJMHBXqnJ+cE2N9jw7OZbd8qonCUWNupg5k/KZu\nmmT9JVR4xKn28z/VBJvoLkwIyk9vHXaeptoIsUp4xr9RC6XcE6hTsT/TiMxYzTaS\n+GmSH+pDho5cn2sO3EPQQxJu\n-----END PRIVATE KEY-----\n".replace("\\n", "\n"));
			jsonObject.addProperty("client_email", "firebase-adminsdk-6k26y@ecom-notify-db.iam.gserviceaccount.com");
			jsonObject.addProperty("client_id", "111050444070438364551");
			jsonObject.addProperty("auth_uri", "https://accounts.google.com/o/oauth2/auth");
			jsonObject.addProperty("token_uri", "https://oauth2.googleapis.com/token");
			jsonObject.addProperty("auth_provider_x509_cert_url", "https://www.googleapis.com/oauth2/v1/certs");
			jsonObject.addProperty("client_x509_cert_url", "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-6k26y%40ecom-notify-db.iam.gserviceaccount.com");
			InputStream serviceAccount = new ByteArrayInputStream(jsonObject.toString().getBytes());
//			ClassLoader classLoader = EcommerceouApplication.class.getClassLoader();
//			File file = new File(Objects.requireNonNull(classLoader.getResource("ServiceAccountKey.json")).getFile());
//			FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
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
