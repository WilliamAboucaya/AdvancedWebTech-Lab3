package HTTPD;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLConnection;

public class HttpWorkerThread extends Thread {
	private Socket client;
	private File webRoot;
	private HttpLog log;

	public HttpWorkerThread(Socket client, File webRoot, HttpLog log) {
		super();
		this.client = client;
		this.webRoot = webRoot;
		this.log = log;
	}

	public void run() {
		try {
			InputStream in = client.getInputStream();
			BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
			
			HttpProtocolRequest request = HttpProtocol.readRequest(in);

			File requested = new File(webRoot, request.getResource());
			
			if (requested.isDirectory()) {
				requested = new File(requested, "index.html");
			}

			if (!requested.exists()) {
				HttpProtocol.send404Message(new PrintWriter(out), request.getResource());
				log.add(client.getInetAddress().toString().replace("/", ""), request.toString(), 404);
				throw new HTTPRequestException("Resource not found " + requested);
			}
			HttpProtocol.http200Header(new PrintWriter(out),
					URLConnection.guessContentTypeFromName(requested.getName()));
			FileHelper.sendFile(new BufferedInputStream(new FileInputStream(requested)), new BufferedOutputStream(out));
			
			log.add(client.getInetAddress().toString().replace("/", ""), request.toString(), 200);
			
		} catch (HTTPRequestException httpE) {
			
		} catch (HTTPMethodException e) {
			try {
				HttpProtocol.send400Message(new PrintWriter(client.getOutputStream()));
			} catch (Exception e1) {

			}
		} catch (Exception e) {
			try {
				HttpProtocol.send500Message(new PrintWriter(client.getOutputStream()));
				e.printStackTrace();
			} catch (Exception e1) {

			}
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				
			}
		}
	}
}
