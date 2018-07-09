package com.rabobank.statment.batch.util.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.rabobank.statment.batch.model.StatementDTO;


/**
 * Class to map the columns from the input file to Statement DTO object
 * @author winston
 *
 */
public class StatementFieldSetMapper<T> implements FieldSetMapper<StatementDTO>
{
    @Override
    public StatementDTO mapFieldSet(FieldSet fieldSet) throws BindException {
    	StatementDTO statement = new StatementDTO();
    	statement.setReference(fieldSet.readInt( "reference" ) );
    	statement.setAccountNumber( fieldSet.readString( "accountNumber" ) );
    	statement.setDescription( fieldSet.readString( "description" ) );
    	statement.setStartBalance( fieldSet.readBigDecimal( "startBalance" ) );
    	statement.setMutation( fieldSet.readBigDecimal( "mutation" ) );
    	statement.setEndBalance( fieldSet.readBigDecimal( "endBalance" ) );
        return statement;
    }
}