package HTTPD;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

	public static void main(String[] args) {
		try {
			if (args.length < 1) {
				throw new Exception("Le programme attend un argument: le fichier de configuration");
			}
			(new HttpServer(configReader(new File(args[0])))).launch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static Properties configReader(final File propertyFile) throws IOException, ConfigurationException {
		Properties configuration = new Properties();
		FileHelper.checkFileConfiguration(propertyFile);
		configuration.load(new FileInputStream(propertyFile));
		return configuration;
	}

}
