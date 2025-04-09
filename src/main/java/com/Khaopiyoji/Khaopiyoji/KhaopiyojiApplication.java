package com.Khaopiyoji.Khaopiyoji;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class KhaopiyojiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KhaopiyojiApplication.class, args);


	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();

	}



}
