package com.mfma.springbootadmin.config;


import com.mfma.springbootadmin.notifier.MyRemindingNotifier;
import com.mfma.springbootadmin.notifier.SinaMailNotifier;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;


@Configuration
public class NotifierConfig {
	
	@Autowired
	private InstanceRepository repository;
	
	@Bean
	public SinaMailNotifier sinaMailNotifier() {
		return new SinaMailNotifier(repository);
	}
	
	@Primary
	@Bean(initMethod = "start", destroyMethod= "stop")
	public RemindingNotifier remindingNotifier() {
		MyRemindingNotifier notifier = new MyRemindingNotifier(sinaMailNotifier(),repository);
		notifier.setReminderPeriod(Duration.ofMinutes(10));
		notifier.setCheckReminderInverval(Duration.ofSeconds(10));
		String[] test = {"DOWN","OFFLINE","UNKNOWN"};
		notifier.setReminderStatuses(test);
		return notifier;
	}
	
//	private final InstanceRepository repository;
//	private final ObjectProvider<List<Notifier>> otherNotifiers;
//
//	public NotifierConfig(InstanceRepository repository,
//								 ObjectProvider<List<Notifier>> otherNotifiers) {
//		this.repository = repository;
//		this.otherNotifiers = otherNotifiers;
//	}
//
//
//	@Bean
//	public SinaMailNotifier sinaMailNotifier() {
//		return new SinaMailNotifier(repository);
//	}
//
//	@Primary
//	@Bean(initMethod = "start", destroyMethod = "stop")
//	public RemindingNotifier remindingNotifier(InstanceRepository repository) {
//		RemindingNotifier notifier = new RemindingNotifier(sinaMailNotifier(), repository);
//		notifier.setReminderPeriod(Duration.ofSeconds(10));
//		notifier.setCheckReminderInverval(Duration.ofSeconds(10));
//		notifier.setEnabled(true);
//		return notifier;
//	}
}
