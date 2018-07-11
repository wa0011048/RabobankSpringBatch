package com.rabobank.statementprocessor; 
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rabobank.statment.batch.main.Main;
import com.rabobank.statment.batch.model.StatementDTO;
import com.rabobank.statment.batch.service.StatementReportProcessorServiceImpl;


/**
 * Test class to test the statement record file processing
 * @author wian0418
 *
 */
@Category(MainTest.class)
@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest(classes = Main.class) 
@PropertySource("classpath:system.properties")
public class MainTest {
	
	private static final Logger log = LoggerFactory.getLogger(MainTest.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	StatementReportProcessorServiceImpl statementReportProcessorService;
	
	/**
	 * Test case1 - Check if the response file is generated after the completion of job
	 */
	@Test
	public void testValidateCSVResponseGenerated() {		
		log.info(" Test case 1:  validating if the CSV response file is generated");
		File f =  new File(env.getProperty("CSVResponseFile"));		
		assertTrue(f.exists());	
	}
	
	
	/**
	 * Test case2 - Check if the response file is generated after the completion of job
	 */
	@Test
	public void testValidateXMLResponseGenerated() {
		log.info(" Test case 2:  validating if the XML response file is generated");
		File f =  new File(env.getProperty("XMLResponseFile"));
		assertTrue(f.exists());		
	}
	
	/**
	 * Test case3 - validates if the duplicate record is being identified
	 * if the method returns the statement object, it shows it is a duplicate record
	 */
	@Test
	public void testIfDuplicateRecordIdenfied() {
		log.info(" Test case 3:  Testing if the reference is duplicate  ");
		StatementDTO statement = new StatementDTO(194261,"NL91RABO0315273637","Clothes from Jan Bakker",
				BigDecimal.valueOf(21.6),BigDecimal.valueOf(-41.83),BigDecimal.valueOf(-20.23));
		assertNotNull(statementReportProcessorService.process(statement));			
	}
	
	/**
	 * Test case 4 - validates if the duplicate record is being identified
	 * if the method returns null, it shows it is a valid record
	 */
	@Test
	public void testIfNonDuplicateRecordProcessed() {
		log.info(" Test case 4:   Testing if the reference is not duplicate, it should be processed successfully");
		StatementDTO statement = new StatementDTO(194267,"NL91RABO0315273637","Clothes from Jan Bakker",
				BigDecimal.valueOf(21.6),BigDecimal.valueOf(-41.83),BigDecimal.valueOf(-20.23));
		assertNull(statementReportProcessorService.process(statement));
	}
	
	/**
	 * Test case 5 - validates if the  EndBalance is invalid format - no decimal split
	 * if the method returns the statement object (not null), it shows it is a invalid end balance
	 */
	@Test
	public void testIfInvalidEndBalance() {
		log.info(" Test case 5: Testing if the EndBalance is invalid ");
		StatementDTO statement = new StatementDTO(194268,"NL91RABO0315273637","Clothes from Jan Bakker",
				BigDecimal.valueOf(21.6),BigDecimal.valueOf(-41.83),BigDecimal.valueOf(-2234));
		assertNotNull(statementReportProcessorService.process(statement));
	}
	
	/**
	 * Test case 6 - validates if the  EndBalance is invalid format - 3 decimals
	 * if the method returns the statement object (not null), it shows it is a invalid end balance
	 */
	@Test
	public void testIfInvalidEndBalance2() {
		log.info(" Test case 6 : Testing if the EndBalance is invalid ");
		StatementDTO statement = new StatementDTO(194269,"NL91RABO0315273637","Clothes from Jan Bakker",
				BigDecimal.valueOf(21.6),BigDecimal.valueOf(-41.83),BigDecimal.valueOf(-22.834));
		assertNotNull(statementReportProcessorService.process(statement));
	}
	
}
