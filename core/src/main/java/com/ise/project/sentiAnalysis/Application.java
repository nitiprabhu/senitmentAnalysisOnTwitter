
package com.ise.project.sentiAnalysis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author Nitish
 */


@SpringBootApplication
public class Application {

    public static Processor processor;
    private static Logger logger = LoggerFactory.getLogger(Application.class);


    /**
     * This is the initialisation of project in which will load all the beans present
     * @param args Convention to initiate springBootApplication
     * @throws ClassNotFoundException
     */


    public static void main(String args[]) throws ClassNotFoundException {



        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        processor = (Processor) context.getBean("getProcessor");

        try {
            logger.info("Initialising....");
        } catch (Exception e) {
            logger.error("Exception {}, {}", e.getMessage(), e);
        }
    }
}