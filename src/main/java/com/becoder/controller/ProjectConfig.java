package com.becoder.controller;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
	public ModelMapper Model() {
		return new ModelMapper(); 
	}
}
