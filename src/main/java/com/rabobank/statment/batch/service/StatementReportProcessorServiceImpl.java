package com.rabobank.statment.batch.service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.rabobank.statment.batch.model.StatementDTO;

/**
 * 
 * SpringBatch ItemProcessor - a service class which process the each record from the input file and validates each row
 * @author wian0418
 *
 */
public class StatementReportProcessorServiceImpl implements ItemProcessor<StatementDTO, StatementDTO> {

	private static final Logger log = LoggerFactory.getLogger(StatementReportProcessorServiceImpl.class);

	private Set<StatementDTO> statementSet = new HashSet<StatementDTO>();

	/**
	 * Method to process the input records - overridden method of ItemProcessor interface
	 * @param statement - the statement object consists of current record columns value
	 */
	@Override
	public StatementDTO process(StatementDTO statement) {
		log.info("processing record : " + statement.getReference());

		if (isDuplicateReference(statementSet, statement) || !isValidEndBalance(statement)) {
			statementSet.add(statement);
			
			return statement;
		} else {
			statementSet.add(statement);
			return null;
		}
	}

	/**
	 * Method isDuplicateReference - used to check if the reference value is duplicated in the given record set 
	 * @param statementSet
	 * @param currentRecord -the statement object consists of current record columns value
	 * @return
	 */
	public boolean isDuplicateReference(Set<StatementDTO> statementSet, StatementDTO currentRecord) {
		// overridden hascode method in statement object to check the duplicate using reference column value
		if (statementSet.contains(currentRecord)) { 
			log.error("validation failed : Duplicate record found : " + currentRecord.getReference());
			currentRecord.setErrorMsg("Duplicate record");
			return true;
		}
		return false;
	}

	/**
	 * Method isValidEndBalance - used to validate the format of End balance value xx:yy 
	 * accept both positive & negative amount with 2 decimals.
	 * @param currentRecord - the statement object consists of current record columns value 
	 * @return
	 */
	public boolean isValidEndBalance(StatementDTO currentRecord) {
		Number number = null;
		Pattern pattern = Pattern.compile("^[-+]?\\d+\\.\\d{1,2}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(String.valueOf(currentRecord.getEndBalance()));
		if (matcher.matches())
			return true;
		log.error("validation failed : invalid end balance : " + currentRecord.getEndBalance() + " for reference : "
				+ currentRecord.getReference());
		currentRecord.setErrorMsg("invalid end balance");
		return false;

	}

}
