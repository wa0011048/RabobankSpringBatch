package com.rabobank.statment.batch.util.adapter;

import java.math.BigDecimal;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Class to convert the input string from the file to BigDecimal format of JAVA object
 * @author wian0418
 *
 */
public class JaxbBigDecimalAdapter extends XmlAdapter<String, BigDecimal> {

	@Override
	public String marshal(BigDecimal obj) throws Exception {
		return obj.toString();
	}

	@Override
	public BigDecimal unmarshal(String obj) throws Exception {
		return new BigDecimal(obj.replaceAll(",", ""));
	}
}
