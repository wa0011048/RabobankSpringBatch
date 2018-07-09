package com.rabobank.statment.batch.service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.rabobank.statment.batch.model.StatementDTO;

public class StatementReportProcessorServiceImpl implements ItemProcessor<StatementDTO, StatementDTO> {

	private static final Logger log = LoggerFactory.getLogger(StatementReportProcessorServiceImpl.class);

	private Set<StatementDTO> statementSet = new HashSet<StatementDTO>();

	public StatementDTO process(StatementDTO statement) throws Exception {
		log.info("processing record : " + statement.getReference());

		if (isDuplicateReference(statementSet, statement) || !isValidEndBalance(statement)) {
			statementSet.add(statement);
			return statement;

			// return null;
		} else {
			statementSet.add(statement);
			return null;
		}
	}

	public boolean isDuplicateReference(Set<StatementDTO> statementSet, StatementDTO currentRecord) {
		if (statementSet.contains(currentRecord)) {
			log.error("Duplicate record found and ignoring this record : " + currentRecord.getReference());
			currentRecord.setErrorMsg("Duplicate record");
			return true;
		}
		return false;
	}

	public boolean isValidEndBalance(StatementDTO currentRecord) {

		Number number = null;
		Pattern pattern = Pattern.compile("^[-+]?\\d+\\.\\d{1,2}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(String.valueOf(currentRecord.getEndBalance()));
		if (matcher.matches())
			return true;
		log.error("invalid end balance : " + currentRecord.getEndBalance() + " for reference"
				+ currentRecord.getReference());
		currentRecord.setErrorMsg("invalid end balance");
		return false;

	}

}
