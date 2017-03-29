package com.alvorecer.venus.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.alvorecer.venus.service.CadasterClientService;

@Configuration
@ComponentScan(basePackageClasses = CadasterClientService.class)
public class ServiceConfig {

}
