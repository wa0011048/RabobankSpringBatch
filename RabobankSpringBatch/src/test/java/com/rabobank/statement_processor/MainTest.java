package com.rabobank.statement_processor;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rabobank.statment.batch.main.Main;


/**
 * Test class to test the statement record file processing
 * @author wian0418
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest(classes = Main.class) 
@PropertySource("classpath:system.properties")
public class MainTest {
	
	private static final Logger log = LoggerFactory.getLogger(MainTest.class);
	
	@Autowired
	private Environment env;
	
	@Test
	public void validateCSVResponseGenerated() {		
		log.info("validating if the CSV response file is generated");
		File f =  new File(env.getProperty("CSVResponseFile"));		
		assertTrue(f.exists());	
	}
	
	
	@Test
	public void validateXMLResponseGenerated() {
		log.info("validating if the XML response file is generated");
		File f =  new File(env.getProperty("XMLResponseFile"));
		assertTrue(f.exists());		
	}
	
}
