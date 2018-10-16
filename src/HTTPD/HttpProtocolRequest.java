package HTTPD;

public interface HttpProtocolRequest {

	public String getResource();
	
	public String getProtocolVersion();
	
	public void setResource(String resource);
	
	public void setProtocolVersion(String resource);
	
	@Override
	public String toString();
}