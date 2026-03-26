package gabywald.crypto.data.ioput;

import java.util.List;

public interface IFileCryptoCreator {
	
	public void addPathAndContent(String path, String content);
	
	public void setPathAndContent(String path, String content);
	
	public void emptyPathAndContent();
	
	public List<String> getEncodedPath();
	
	public List<String> getEncodedCont();
}
