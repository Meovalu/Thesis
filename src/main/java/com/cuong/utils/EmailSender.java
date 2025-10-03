package com.cuong.utils;


import java.net.URI;
import java.net.URL;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

public class EmailSender {

    public static void sendEmail(URI reportPath) {

    	try {
    		ImageHtmlEmail email = new ImageHtmlEmail();
    		String path = reportPath.toString();
    		path = path.replace("\\", "/");
    		URL url = new URI(path).toURL();
    		email.setDataSourceResolver(new DataSourceUrlResolver(url));
    		email.setHostName("smtp.googlemail.com"); 
    		email.setSmtpPort(465);
    		email.setAuthenticator(new DefaultAuthenticator("hoangduynguyen01022001@gmail.com","xidhwspfpfltkpsl")); 
    		email.setSSLOnConnect(true);
    		email.setFrom("hoangduynguyen01022001@gmail.com"); //Sender
    		email.setSubject("Test Results");
    		email.setMsg("Please find attached Report....");
    		email.addTo("oanh14f@gmail.com"); 
    		email.attach(url, "Test report", "Please check report..."); 
    		email.send(); // send the email 
    	} catch(Exception e) { 
    		e.printStackTrace(); 
    	}
    }

} 
