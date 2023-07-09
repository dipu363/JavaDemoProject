package sa.com.medisys;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMail {
	private static final Logger log = LoggerFactory.getLogger(SendMail.class);
	private static final String TAG = "SendMail :: {} ";
	private String rootdirectory;
	private List<File> selectedFile = new ArrayList<>();
	private String msgSubject = "Lab Report";
	private String msgText = "Please Find below as attechment your daily lab report";

	final String USER_NAME = "giyasuddin636@gmail.com"; // User name of the Google(mail) account
	final String PASSSWORD = "oqzhmepgnozhybqx"; // Password of the Google(mail) account
	final String FROM_ADDRESS = "giyasuddin636@gmail.com"; // From address

	public void createEmail(String directory) {
		ReadDirectory readDirectory = new ReadDirectory();
		String rootPath = readDirectory.getDirectory();
		log.info(TAG,"rootPath : "+ rootPath);
		rootdirectory = rootPath+"\\"+directory;
		log.info(TAG,"Target Directory : "+ rootdirectory);
		File fileLocation = new File(rootdirectory );
		File[] files = fileLocation.listFiles();
		if(fileLocation.listFiles() != null) {

		// iterate the files array
		for (File file : files) {
			// check if the file
			if (file.isFile()) {
				log.info(TAG,"File - " + file.getName());// if found file
			} else if (file.isDirectory()) {
				log.info(TAG,"Directory folder- " + file.getName()); // if found folder
				String filName = file.getName();
				String emailAddress = file.getName().substring(11);
				log.info(TAG,emailAddress);
				getClientFile(filName);
				sendEmailMessage(emailAddress);

			}
		}
		}else {
			log.info(TAG,"Directory Not Found");
		}

	}

	public void getClientFile(String subPath) {
		// System.out.println("call next");
		selectedFile.clear();
		File clientAllFile = new File(rootdirectory+"\\"+subPath);
		File[] files = clientAllFile.listFiles();

		// iterate the files array
		for (File file : files) {
			// check if the file
			if (file.isFile()) {
				// System.out.println("File - " + file.getName());
				selectedFile.add(file);
				// System.out.println(selectedFile.toString());
			} else if (file.isDirectory()) {
				log.info("Folder:" + file.getName());
				/*
				 * String filName = file.getName(); String emailAddress =
				 * file.getName().substring(11); System.out.println(emailAddress);
				 * allMailId.clear(); allMailId.add(emailAddress); getClientFile(filName);
				 */
			} 
		}
		log.info(selectedFile.toString());
		// sendEmailMessage();

	}

	private void sendEmailMessage(String emailId) {

		// System.out.println(emailId);
		// System.out.println("email send successful");

		// Create email sending properties

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USER_NAME, PASSSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USER_NAME)); // Set from address of the email
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId)); // Set email
																								// recipient
			message.setSubject(msgSubject); // Set email message subject //set content
			// type of the email // if sent with attachment then no need this line; //
			message.setContent(msgText, "text/html");

			// Attachment
			Multipart multipart = new MimeMultipart(); // Content
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(msgText);
			multipart.addBodyPart(bodyPart); // File Attachment
			for (File file : selectedFile) {
				DataSource dataSource = new FileDataSource(file);
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.setDataHandler(new DataHandler(dataSource));
				mimeBodyPart.setFileName(file.getName());
				multipart.addBodyPart(mimeBodyPart);
			}
			message.setContent(multipart);
			Transport.send(message); // Send email message

			log.info("sent email successfully!");
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null, "email Sending failed :" + e);
			throw new RuntimeException(e);

		}

	}

}
