package com.robopoes.robotics.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.robopoes.robotics.car.RoboPoes;

public class ReleaseModule extends AbstractModule {

	@Override
	protected void configure() {
		bindConstant().annotatedWith(Names.named("serverPort")).to(1234);
		bind(RoboPoes.class).in(Singleton.class);
	}
	
}
