package com.rabobank.statment.batch.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.rabobank.statment.batch.model.StatementDTO;
import com.rabobank.statment.batch.service.StatementReportProcessorServiceImpl;
import com.rabobank.statment.batch.util.StringHeaderWriter;
import com.rabobank.statment.batch.util.exception.ValidationException;
import com.rabobank.statment.batch.util.mapper.StatementFieldSetMapper;

/**
 * Class that contains the Spring Batch configuration for reading the input
 * statement records, validates the records and write the failures onto a result
 * file.
 * 
 * @author Winston
 *
 */
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:system.properties")
public class BatchConfiguration {

	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

	@Autowired
	private Environment env;
	/**
	 * Method to read input CSV file containing customer statement records
	 * 
	 * @return the ItemReader - SpringBatch object
	 */
	@Bean
	public ItemReader<StatementDTO> csvFileReader() {
		String inputFile =  env.getProperty("inputCSVFile"); // "c://winston//records.csv";
		log.debug("Reading input file : " + inputFile);
		FlatFileItemReader<StatementDTO> reader = new FlatFileItemReader<StatementDTO>();
		reader.setResource(new FileSystemResource(inputFile));

		DefaultLineMapper<StatementDTO> lineMapper = new DefaultLineMapper<StatementDTO>();

		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames(
				new String[] { "reference", "accountNumber", "description", "startBalance", "mutation", "endBalance" });
		lineMapper.setFieldSetMapper(new StatementFieldSetMapper<StatementDTO>());
		lineMapper.setLineTokenizer(delimitedLineTokenizer);
		reader.setLinesToSkip(1);
		reader.setLineMapper(lineMapper);
		return reader;
	}

	/**
	 * Method to read input XML file containing customer statement records
	 * 
	 * @return the ItemReader - SpringBatch object
	 */
	@Bean
	public ItemReader<StatementDTO> xmlFileReader() {
		String inputFile = env.getProperty("inputXMLFile"); //"c://winston//records.xml";
		log.debug("Reading input file : " + inputFile);
		StaxEventItemReader<StatementDTO> xmlStaxEventItemReader = new StaxEventItemReader<StatementDTO>();
		Resource resource = new FileSystemResource(inputFile);
		xmlStaxEventItemReader.setFragmentRootElementName("record");
		Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
		studentMarshaller.setClassesToBeBound(StatementDTO.class);
		xmlStaxEventItemReader.setUnmarshaller(studentMarshaller);
		xmlStaxEventItemReader.setResource(resource);
		return xmlStaxEventItemReader;

	}

	/**
	 * Method to write the failure records out of input CSV file onto a result file
	 * 
	 * @return the ItemWriter - SpringBatch object
	 */
	@Bean
	public ItemWriter<StatementDTO> csvFailureFilewriter() {
		String outputFile = env.getProperty("CSVResponseFile"); //"c://winston//CSVFailureRecords.csv";
		log.debug("Writing cSV failures to result file : " + outputFile);
		FlatFileItemWriter<StatementDTO> writer = new FlatFileItemWriter<StatementDTO>();
		writer.setEncoding("UTF-8");
		writer.setResource(new FileSystemResource(outputFile));
		setHeader(writer);
		DelimitedLineAggregator<StatementDTO> delLineAgg = new DelimitedLineAggregator<StatementDTO>();
		delLineAgg.setDelimiter(",");
		BeanWrapperFieldExtractor<StatementDTO> fieldExtractor = new BeanWrapperFieldExtractor<StatementDTO>();
		fieldExtractor.setNames(new String[] { "reference", "description","errorMsg" });
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);
		return writer;
	}

	/**
	 * Method to write the failure records out of input XML file onto a result file
	 * 
	 * @return the ItemWriter - SpringBatch object
	 */
	@Bean
	public ItemWriter<StatementDTO> xmlFailureFilewriter() {

		String outputFile = env.getProperty("XMLResponseFile") ;// "c://winston//XMLFailureRecords.csv";
		log.debug("Writing cSV failures to result file : " + outputFile);
		FlatFileItemWriter<StatementDTO> writer = new FlatFileItemWriter<StatementDTO>();
		writer.setEncoding("UTF-8");
		writer.setResource(new FileSystemResource(outputFile));
		setHeader(writer);
		DelimitedLineAggregator<StatementDTO> delLineAgg = new DelimitedLineAggregator<StatementDTO>();
		delLineAgg.setDelimiter(",");
		BeanWrapperFieldExtractor<StatementDTO> fieldExtractor = new BeanWrapperFieldExtractor<StatementDTO>();
		fieldExtractor.setNames(new String[] { "reference", "description", "errorMsg" });
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);
		return writer;
	}

	@Bean
	public ItemProcessor<StatementDTO, StatementDTO> statementReportProcessor() {
		return new StatementReportProcessorServiceImpl();
	}

	@Bean
	public Job processStatement(JobBuilderFactory jobs, Step step1, Step step2) {
		return jobs.get("processStatement").flow(step1).next(step2).end().build();
	}

	/**
	 * Method written in SpringBatch step to read the csv input records and
	 * validates and write the failures onto csv file
	 * 
	 * @param stepBuilderFactory
	 * @param csvFileReader
	 * @param csvFailureFilewriter
	 * @param statementReportProcessor
	 * @return
	 */
	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<StatementDTO> csvFileReader,
			ItemWriter<StatementDTO> csvFailureFilewriter,
			ItemProcessor<StatementDTO, StatementDTO> statementReportProcessor) {
		return stepBuilderFactory.get("step1").<StatementDTO, StatementDTO>chunk(5).faultTolerant().skipLimit(50)
				.noRollback(ValidationException.class).skip(ValidationException.class).reader(csvFileReader)
				.processor(statementReportProcessor).writer(csvFailureFilewriter).build();
	}

	/**
	 * Method written in SpringBatch step to read the xml input records and
	 * validates and write the failures onto csv file
	 * 
	 * @param stepBuilderFactory
	 * @param xmlFileReader
	 * @param csvFailureFilewriter
	 * @param statementReportProcessor
	 * @return
	 */
	@Bean
	public Step step2(StepBuilderFactory stepBuilderFactory, ItemReader<StatementDTO> xmlFileReader,
			ItemWriter<StatementDTO> xmlFailureFilewriter,
			ItemProcessor<StatementDTO, StatementDTO> statementReportProcessor) {
		return stepBuilderFactory.get("step2").<StatementDTO, StatementDTO>chunk(5).faultTolerant().skipLimit(50)
				.noRollback(ValidationException.class).skip(ValidationException.class).reader(xmlFileReader)
				.processor(statementReportProcessor).writer(xmlFailureFilewriter).build();
	}

	/**
	 * Method to set the header in the response file
	 * 
	 * @param FlatFileItemWriter
	 *            - the writer reference used to set the header
	 * @return void
	 */
	public void setHeader(FlatFileItemWriter<StatementDTO> writer) {
		String fileHeader = "reference,description,errorMsg";
		StringHeaderWriter headerWriter = new StringHeaderWriter(fileHeader);
		writer.setHeaderCallback(headerWriter);
	}
}
