package ru.help.wanted.project.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ru/help/wanted/project/service"})
public class ServiceConfig {
}
