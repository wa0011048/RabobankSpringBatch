package com.rabobank.statment.batch.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main class used to execute the spring batch for reading & writing the files.
 * @author wian0418
 *
 */
@ComponentScan
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	/**
	 * Main method used to execute the spring batch for reading & writing the files.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SpringApplication.run(Main.class, args);
		} catch (Exception e) {
			log.error("System error occured : " + e.getMessage());
		}
	}
}
