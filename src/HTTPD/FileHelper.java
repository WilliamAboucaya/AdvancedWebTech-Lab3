package HTTPD;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHelper {
	public static void checkFileConfiguration(final File file) throws ConfigurationException {
		if (!file.exists()) {
			throw new ConfigurationException(file + " does not exists");
		}
		if (!file.canRead()) {
			throw new ConfigurationException(file + " is not readable");

		}
	}

	public static void sendFile(InputStream in, OutputStream out) throws IOException {
		int b = -1;

		while ((b = in.read()) != -1) {
			out.write(b);
		}
		
		out.flush();
	}

}
