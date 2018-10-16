package HTTPD;

import java.io.IOException;

public class HttpProtocolGetRequest implements HttpProtocolRequest{
	
	private String resource;
	private String protocolVersion;
	
	public HttpProtocolGetRequest(String resource, String protocolVersion) throws IOException {
		this.resource = resource;
		this.protocolVersion = protocolVersion;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	@Override
	public String toString() {
		return "GET " + resource + " " + protocolVersion;
	}
}
