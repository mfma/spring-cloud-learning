package com.mfma.springbootadmin;

import com.mfma.springbootadmin.notifier.SinaMailNotifier;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

@EnableDiscoveryClient
@EnableAdminServer
@SpringBootApplication
public class SpringbootAdminApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootAdminApplication.class, args);
	}
	


}
