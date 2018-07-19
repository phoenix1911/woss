package util;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.briup.util.Logger;

public class LoggerImpl implements Logger {
	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LoggerImpl.class);
	private static String path = "src/file/log4j.properties";
	private static PropertyConfigurator propertyConfigurator;
	static {
		try {
			propertyConfigurator = new PropertyConfigurator();
			PropertyConfigurator.configure(path);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void init(Properties arg0) {

	}

	@Override
	public void debug(String arg0) {
		logger.debug(arg0);
	}

	@Override
	public void error(String arg0) {
		logger.error(arg0);
	}

	@Override
	public void fatal(String arg0) {
		logger.fatal(arg0);
	}

	@Override
	public void info(String arg0) {
		logger.info(arg0);
	}

	@Override
	public void warn(String arg0) {
		logger.warn(arg0);
	}

	public static void main(String[] args) {
		LoggerImpl loggerImpl = new LoggerImpl();
		loggerImpl.warn("3423423");
	}
}
