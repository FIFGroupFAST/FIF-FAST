package co.id.fifgroup.notification.manager;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import co.id.fifgroup.notification.domain.NotificationMessage;

public class SendEmailManager {
	
	private static final Logger logger = LoggerFactory.getLogger(SendEmailManager.class);

	private JavaMailSender mailSender;
	
	private String emailAddressFrom;
	private String emailTo;
	private String emailDomain;
	
	public String getEmailAddressFrom() {
		return emailAddressFrom;
	}

	public void setEmailAddressFrom(String emailAddressFrom) {
		this.emailAddressFrom = emailAddressFrom;
	}
	
	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	
	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
		
	public void sendEmail(final NotificationMessage msg, final String emailAddressTo) {
		logger.debug("Send email to : " + emailAddressTo);
		final String staticAddressTo = !emailTo.equals("") ? emailTo : emailAddressTo.concat(emailDomain);
		logger.debug("Send email to : " + staticAddressTo);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setFrom(emailAddressFrom);
				message.setTo(staticAddressTo);
				message.setSubject(msg.getSubject());
				message.setText(msg.getContentMessage(), true);				
			}
		};
		try {
			this.mailSender.send(preparator);
		} catch (Exception e) {
			logger.error("Error occurred while sending email.");
			logger.error(e.getMessage(), e);
		}
	}
	
	// added by WLY, added for HCMS phase 2, send notification to external person use email
	public void sendEmailExternal(final NotificationMessage msg, final String emailAddressTo) {
		logger.debug("Send email to : " + emailAddressTo);
		final String staticAddressTo = !emailTo.equals("") ? emailTo : emailAddressTo;
		logger.debug("Send email to : " + staticAddressTo);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setFrom(emailAddressFrom);
				message.setTo(staticAddressTo);
				message.setSubject(msg.getSubject());
				message.setText(msg.getContentMessage(), true);				
			}
		};
		try {
			this.mailSender.send(preparator);
		} catch (Exception e) {
			logger.error("Error occurred while sending email.");
			logger.error(e.getMessage(), e);
		}
	}
	// end by WLY
	
	public void sendEmailWithAttachment(final NotificationMessage msg, final String emailAddressTo, final String urlFile) {
		logger.debug("Send email to : " + emailAddressTo);
		final String staticAddressTo = !emailTo.equals("") ? emailTo : emailAddressTo.concat(emailDomain);
		logger.debug("Send email to : " + staticAddressTo);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
				message.setFrom(emailAddressFrom);
				message.setTo(staticAddressTo);
				message.setSubject(msg.getSubject());
				message.setText(msg.getContentMessage(), true);
				message.addAttachment(getFileName(urlFile), new File(urlFile));
			}
		};
		try {
			this.mailSender.send(preparator);
		} catch (Exception e) {
			logger.error("Error occurred while sending email.");
			logger.error(e.getMessage(), e);
		}
	}
	
	private String getFileName(String fullPath) {
		if (fullPath != null && !fullPath.equals("")) {
			int lastIndex = fullPath.lastIndexOf(File.separatorChar);
			if (lastIndex != -1) {
				return fullPath.substring(lastIndex+1);
			} 
		}
		return fullPath;
	}
}
