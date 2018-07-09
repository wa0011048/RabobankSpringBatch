package com.rabobank.statment.batch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabobank.statment.batch.main.BatchConfiguration;

public class PropertyReader {

	private static final Logger log = LoggerFactory.getLogger(PropertyReader.class);

	static Properties prop = new Properties();

	/**
	 * get the property value for a given key
	 * @param key
	 * @return
	 */
	public static String getPropertyValue(String key) {
		if (prop.size() == 0)
			loadProperties();
		return prop.getProperty(key);
	}
	
	public static void main(String a[]) {
		loadProperties();
	}

	/**
	* load the properties from the file into cache - static object
	*/
	public static void loadProperties() {

		InputStream input = null;

		try {

			input = new FileInputStream(new File("system.properties"));
			prop.load(input);
			// get the properties value
			prop.getProperty("inputCSVFile");
			prop.getProperty("inputXMLFile");
			prop.getProperty("XMLResponseFile");
			prop.getProperty("CSVResponseFile");

		} catch (IOException io) {
			log.error("Failed to load the property file" + io.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error("Failed to load the property file" + e.getMessage());
				}
			}

		}
	}

}
