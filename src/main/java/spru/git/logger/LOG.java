package spru.git.logger;

import java.time.Instant;
import java.util.Date;


public class LOG {
	
	public static void debug(String message){
		System.out.println( Date.from(Instant.now()).toString() + ":\t" + message);
	}

}
