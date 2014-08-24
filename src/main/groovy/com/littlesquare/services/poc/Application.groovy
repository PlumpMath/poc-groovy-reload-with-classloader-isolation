package com.littlesquare.services.poc

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan

/**
 * @author Adam Jordens (adam@jordens.org)
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {
    private static Logger LOG = LoggerFactory.getLogger(Application)

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args)
        LOG.info("Finished loading... ${ctx.beanDefinitionCount} beans loaded.")
    }
}
