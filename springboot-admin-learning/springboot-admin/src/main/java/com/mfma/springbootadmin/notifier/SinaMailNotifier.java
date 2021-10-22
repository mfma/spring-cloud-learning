package com.mfma.springbootadmin.notifier;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
public class SinaMailNotifier extends AbstractStatusChangeNotifier {
	
	private String[] ignoreChanges = new String[]{"UNKNOWN:UP","DOWN:UP","OFFLINE:UP"};
	
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public SinaMailNotifier(InstanceRepository repository) {
		super(repository);
	}
	
	
	@Override
	protected boolean shouldNotify(InstanceEvent event, Instance instance) {
		log.debug("==============当前事件类型是["+event.getType()+"]==============");
		log.debug("==============当前状态是["+instance.getStatusInfo().getStatus()+"]==============");
//		if (!(event instanceof InstanceStatusChangedEvent)) {
//			return false;
//		} else{
//			InstanceStatusChangedEvent statusChange = (InstanceStatusChangedEvent)event;
//			String from = this.getLastStatus(event.getInstance());
//			String to = statusChange.getStatusInfo().getStatus();
//			return Arrays.binarySearch(this.ignoreChanges, from + ":" + to) < 0 && Arrays.binarySearch(this.ignoreChanges, "*:" + to) < 0 && Arrays.binarySearch(this.ignoreChanges, from + ":*") < 0;
//		}
		return super.shouldNotify(event,instance);
	}
	
	@Override
	protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
		log.debug("===================发送一次邮件==================");
		String text = "mfma test mail ";
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("flyingtothesun@sina.com");
		message.setTo("flyingtothesun@sina.com");
		message.setSubject(instance.getRegistration().getName() + "服务状态改变");
		message.setText(text);
		return Mono.fromRunnable(() -> {
			javaMailSender.send(message);
		});
	}
}
