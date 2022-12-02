package com.api.automation.util;

import com.api.automation.hooks.CustomHooks;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReportLogger {

	private static Logger logger = Logger.getLogger("LOGGER");
	private static final String LOG_FILE = "src/main/resources/log4j.properties";
	private static final ReportLogger reportLogger = new ReportLogger();
	public static ReportLogger getInstance() {
		return reportLogger;
	}
	private ReportLogger() {
		// configure log4j properties file
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(LOG_FILE));
			PropertyConfigurator.configure(props);
		} catch (FileNotFoundException e) {
			logger.error("Exception occured for error event: " + e);
		} catch (IOException e) {
			logger.error("Exception occured for error event : " + e);
		}

	}

	public static void setLoggerClassName(String classname) {
		logger = Logger.getLogger(classname);
	}

	/**
	 * @FunctionName : log
	 * @Description : Method logs information to log file.
	 * @return : void
	 * @param str
	 * @Comments
	 */
	public void log(String str) {
		logger.setLevel(Level.INFO);
		logger.info(str);
	}

	public void addStep(String stepName) {
		CustomHooks.logInfo(stepName);
	}

	/**
	 * @FunctionName : info
	 * @Description : Method logs information to log file.
	 * @return : void
	 * @param str
	 * @Comments
	 */
	public void info(String str) {
		logger.setLevel(Level.INFO);
		logger.info(str);
	}

	/**
	 * @FunctionName : warn
	 * @Description : Method logs warning event to log file for given step
	 * @return : void
	 * @param str
	 * @Comments
	 */
	public void warn(String str) {
		logger.setLevel(Level.WARN);
		logger.warn(str);
	}

	/**
	 * @FunctionName : fatal
	 * @Description : Method logs fatal event to log file for given step
	 * @return : void
	 * @param str
	 * @Comments
	 */
	public void fatal(String str) {
		logger.setLevel(Level.FATAL);
		logger.fatal(str);
	}

	/**
	 * @FunctionName : error
	 * @Description : Method logs error event to log file for given step
	 * @return : void
	 * @param str
	 * @Comments
	 */
	public void error(String str) {
		try {
			logger.setLevel(Level.ERROR);
			logger.error(str);
		} catch (Exception e) {
			logger.error("Exception occured for error event : " + e);
		}
	}

}
