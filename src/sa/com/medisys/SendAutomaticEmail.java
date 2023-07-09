package sa.com.medisys;

import java.util.Timer;

public class SendAutomaticEmail {

	public static void main(String[] args) {
		Timer timer = new Timer();
		EmailSender es = new EmailSender();
		timer.schedule(es,1000);//one time task;
		//timer.schedule(es,10000, 60000*5);//repeated task in a prieod time
		

	}

}
