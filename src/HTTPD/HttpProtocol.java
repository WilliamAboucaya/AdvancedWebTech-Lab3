package HTTPD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

public class HttpProtocol {
	
	static enum HTTPMethod {
		GET, POST, PUT, DELETE
	}

	protected static HttpProtocolRequest readRequest(InputStream clientRequest) throws IOException, HTTPMethodException {
		BufferedReader request = new BufferedReader(new InputStreamReader(clientRequest));
		String line;

		String firstline = request.readLine();
		String[] requestSplitted = firstline.split(" ");
		
		switch(HTTPMethod.valueOf(requestSplitted[0])) {
			case DELETE:
				break;
			case GET:
				return new HttpProtocolGetRequest(requestSplitted[1], requestSplitted[2]);
			case POST:
				break;
			case PUT:
				break;
			default:
				throw new HTTPMethodException("This method can't be handled");
		}

		return null;
	}

	protected static void send400Message(PrintWriter pw) throws IOException {
		pw.println("HTTP/1.0 400 Bad Request");
		pw.println("Date: " + new Date().toString());
		pw.println("Server: JavaHttp/1.0");
		pw.println("Content-type: text/html");
		pw.println("");
		pw.println(
				"<HEAD><TITLE>Bad Request</TITLE></HEAD> <BODY><H1>Bad Request</H1><P>Votre navigateur Internet a envoye une requete que ce serveur ne peut pas traiter.</P></BODY>");
		pw.flush();
	}

	protected static void send404Message(PrintWriter pw, String path) throws IOException {
		pw.println("HTTP/1.0 404 Not found");
		pw.println("Date: " + new Date().toString());
		pw.println("Server: JavaHttp/1.0");
		pw.println("Content-type: text/html");
		pw.println("");
		pw.println("<HEAD> <TITLE> File not found </TITLE> </HEAD> <BODY> <H1> File not found </H1><P>The resource "
				+ path + " is not present on this server.</P></ BODY>");
		pw.flush();
	}
	
	protected static void send500Message(PrintWriter pw) throws IOException {
		pw.println("HTTP/1.0 500 Internal Server Error");
		pw.println("Date: " + new Date().toString());
		pw.println("Server: JavaHttp/1.0");
		pw.println("Content-type: text/html");
		pw.println("");
		pw.println("<HEAD> <TITLE> Internal Server Error </TITLE> </HEAD> <BODY> <H1> Internal Server Error </H1><P>Something went wrong...</P></ BODY>");
		pw.flush();
	}

	protected static void http200Header(PrintWriter pw, String mimeType) throws IOException {
		pw.println("HTTP/1.0 200 OK");
		pw.println("Date: " + new Date().toString());
		pw.println("Server: JavaHttp/1.0");
		pw.println("Content-type: " + mimeType);
		pw.println("");
		pw.flush();
	}
}
