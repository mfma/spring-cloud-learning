package com.mfma.eurekamasterserver.config;

import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EurekaStateChangeListener {
	@EventListener
	public void listen(EurekaInstanceCanceledEvent event) {
		log.error(event.getServerId() + "\t" + event.getAppName() + " 服务下线 ");
	}
	@EventListener
	public void listen(EurekaInstanceRegisteredEvent event) {
		InstanceInfo instanceInfo = event.getInstanceInfo();
		log.info(instanceInfo.getAppName() + " 进行注册 ");
	}
	@EventListener
	public void listen(EurekaInstanceRenewedEvent event) {
		log.info(event.getServerId() + "\t" + event.getAppName() + " 服务进行续约 ");
	}
	@EventListener
	public void listen(EurekaRegistryAvailableEvent event) {
		log.info(" 注册中心启动 ");
	}
	@EventListener
	public void listen(EurekaServerStartedEvent event) {
		log.info("Eureka Server启动 ");
	}
}
