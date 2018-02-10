package com.henu;

import com.henu.controller.MyWebSocket;
import com.henu.util.PicCrawler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TiebaApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(TiebaApplication.class, args);
		MyWebSocket.setApplicationContext(application);
		PicCrawler.setApplicationContext(application);
	}

}
