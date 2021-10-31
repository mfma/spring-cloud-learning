package com.mfma.springbootadmin.notifier;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceDeregisteredEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.Notifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class MyRemindingNotifier extends RemindingNotifier {
	
	private String[] reminderStatuses = {"DOWN", "OFFLINE"};
	
	public MyRemindingNotifier(Notifier delegate, InstanceRepository repository) {
		super(delegate, repository);
	}
	
	@Override
	protected boolean shouldEndReminder(InstanceEvent event) {
		log.debug("==============当前的事件类型是[" + event.getType() + "]==============");
		if (event instanceof InstanceDeregisteredEvent) {
			return false;
		}
		if (event instanceof InstanceStatusChangedEvent) {
			return Arrays.binarySearch(this.reminderStatuses, ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus()) < 0;
		}
		return false;
	}
	
	@Override
	protected boolean shouldStartReminder(InstanceEvent event) {
//		if ((event instanceof InstanceStatusChangedEvent)||(event instanceof InstanceDeregisteredEvent)) {
//			return Arrays.binarySearch(this.reminderStatuses,
//					((InstanceStatusChangedEvent) event).getStatusInfo().getStatus()) >= 0;
//		}
		return true;
	}
}
