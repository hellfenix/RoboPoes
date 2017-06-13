package com.robopoes.robotics.inject;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class ReleaseModule extends AbstractModule {

	@Override
	protected void configure() {
		bindConstant().annotatedWith(Names.named("serverPort")).to(1234);
	}
	
}
