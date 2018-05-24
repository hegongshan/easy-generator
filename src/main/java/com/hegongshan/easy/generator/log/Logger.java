package com.hegongshan.easy.generator.log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Logger {
	
	private static java.util.logging.Logger log;
	private String sourceClassName = null;
	
	public Logger(Class<?> clazz) {
		log = java.util.logging.Logger.getLogger(clazz.getName());
		sourceClassName = clazz.getPackage().getName() + "." + clazz.getSimpleName();
		ConsoleHandler console = new ConsoleHandler();
		console.setFormatter(new Formatter(){

			@Override
			public String format(LogRecord record) {
				if(record.getSourceClassName() != null) {
					record.getMessage();
					StringBuffer sb = new StringBuffer();
					sb.append("[")
						.append(record.getLevel().getName())
						.append("][")
						.append(record.getSourceMethodName())
						.append("][")
						.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(Date.from(Instant.now())))
						.append("][")
						.append(record.getSourceClassName())
						.append("] - ")
						.append(record.getMessage());
					if(record.getThrown() != null) {
						sb.append(record.getThrown().getMessage());
					}
					return sb.append("\n").toString();
				}
				return null;
			}
			
			/*@Override
			public String getHead(Handler h) {
				return "[" + Constants.LOG_NAME + "]";
			}*/
			
		});
		log.addHandler(console);
		log.setUseParentHandlers(false);
	}
	
	private void log(Level level,String msg,Throwable thrown) {
		Throwable exception = new Throwable();
		StackTraceElement[] locations = exception.getStackTrace();

		String sourceMethodName = "unkown";
		if( locations != null && locations.length > 2 ) {
            StackTraceElement caller = locations[2];
            sourceMethodName = caller.getMethodName();
        }
		if(thrown == null) {
			log.logp(level, sourceClassName,sourceMethodName, msg);
		} else {
			log.logp(level, sourceClassName, sourceMethodName, msg, thrown);
		}
	}
	
	public void error(String msg,Throwable thrown) {
		log(Level.SEVERE,msg,thrown);
	}
	
	public void error(String msg) {
		log(Level.SEVERE,msg,null);
	}
	
	public void info(String msg) {
		log(Level.INFO,msg,null);
	}
	
	public void debug(String msg) {
		log(Level.CONFIG,msg,null);
	}
	
	public void warning(String msg) {
		log(Level.WARNING,msg,null);
	}

}
