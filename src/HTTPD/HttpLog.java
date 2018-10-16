package HTTPD;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import org.apache.log4j.Logger;

public class HttpLog {

	private Logger log;
	
	public HttpLog(String logFile) throws FileNotFoundException {
		if(!new File(logFile).exists()) {
			throw new FileNotFoundException("Le fichier de registre indiqué n'existe pas");
		}
		System.setProperty("log", logFile);
		log = Logger.getLogger(HttpLog.class);
	}
	
	public synchronized void add(String address, String request, int status) {
		StringBuilder logEntry = new StringBuilder("[");
		logEntry.append(address)
				.append("] [")
				.append(new Date().toString())
				.append("] [")
				.append(request)
				.append("] [")
				.append(status)
				.append("]");
		
		log.info(logEntry.toString());
	}
}
