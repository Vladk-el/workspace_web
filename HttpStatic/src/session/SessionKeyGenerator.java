package session;

import java.sql.Timestamp;
import java.util.Random;
import java.util.Date;

public class SessionKeyGenerator {
	
	private final static Integer RANGE = 100000;

	public static String generateSessionKey(){
		
		Random random = new Random();
		
		return (new Timestamp(new Date().getTime())).getTime() + "." + random.nextInt(RANGE);
	}
}
