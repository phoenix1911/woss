package util;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.briup.util.Logger;

public class LoggerImpl implements Logger {
	// 获取 Logger对象
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LoggerImpl.class);
	private String logPro;

	public LoggerImpl() {
	}

	@Override
	public void init(Properties arg0) {
		System.out.println("logger Impl()");
		String logPro = arg0.getProperty("log-pro");
//		System.out.println(logPro);
		PropertyConfigurator.configure(logPro);

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

	public static void main(String[] args) throws Exception {

	}
}
