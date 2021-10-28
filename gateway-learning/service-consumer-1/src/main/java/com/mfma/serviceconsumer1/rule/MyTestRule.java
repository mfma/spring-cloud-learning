package com.mfma.serviceconsumer1.rule;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

/**
 * @author mfma
 */
@Slf4j
public class MyTestRule implements IRule {
	
	private ILoadBalancer iLoadBalancer;
	private Random random = new Random();
	
	@Override
	public Server choose(Object o) {
		List<Server> servers = iLoadBalancer.getAllServers();
		log.debug("当前的random:",random.toString());
		return servers.get(random.nextInt(2));
	}
	
	@Override
	public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
		this.iLoadBalancer = iLoadBalancer;
	}
	
	@Override
	public ILoadBalancer getLoadBalancer() {
		return this.iLoadBalancer;
	}
	
}
