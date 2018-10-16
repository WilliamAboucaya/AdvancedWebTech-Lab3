package HTTPD;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class HttpServer {
	protected int port;
	protected File webRoot;
	protected HttpLog log;

	public HttpServer(Properties config) throws ConfigurationException {
		try {
			log = new HttpLog("httplog.log");
			
			this.webRoot = new File(config.getProperty(ConfigurationParameters.WebRoot.toString()));
			FileHelper.checkFileConfiguration(webRoot);
			this.port = Integer.parseInt(config.getProperty(ConfigurationParameters.Port.toString()));
		} catch (ConfigurationException c) {
			throw c;
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	protected void launch() {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(this.port);

		} catch (Exception exception) {
			throw new Error("Fatal cannot open PORT " + this.port);
		}

		while (true) {
			Socket client = null;
			try {
				client = serverSocket.accept();

				(new HttpWorkerThread(client, webRoot, log)).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}