package com.Khaopiyoji.Khaopiyoji;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class KhaopiyojiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KhaopiyojiApplication.class, args);

	}

	public class RazorpayConfig {

		@Bean
		public RazorpayClient razorpayClient() throws RazorpayException {
			// Replace these with your actual Razorpay key ID and key secret
			return new RazorpayClient("rzp_test_fx1Fp2EzCD904Q", "Rat4zWVfY0MYGg1lwsNMgPln");
		}
	}

}
