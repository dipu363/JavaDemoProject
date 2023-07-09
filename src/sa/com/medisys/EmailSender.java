package sa.com.medisys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSender extends TimerTask {
	
	private static final Logger log = LoggerFactory.getLogger(SendMail.class);
	private static final String TAG = "EmailSender :: {} ";
	Date date = new Date();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	String currentDate = simpleDateFormat.format(date);

	@Override
	public void run() {

		log.info("Proccessing......");
		// System.out.println(currentDate);
		new SendMail().createEmail(currentDate);
		//log.info("email sending complite");
	}

}
