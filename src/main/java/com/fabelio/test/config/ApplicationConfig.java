package com.fabelio.test.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yoga.pramugia01
 */

@Configuration
@ComponentScan("com.fabelio.test")
@EntityScan("com.fabelio.test")
public class ApplicationConfig {

}
