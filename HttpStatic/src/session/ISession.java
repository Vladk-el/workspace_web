package session;

import java.util.Map;

public interface ISession {

	public void setAttribute(String key, Object value);
	
	public Object getAttribute(String key);
	
	public Map<String, Object> getAttributes();
	
}
