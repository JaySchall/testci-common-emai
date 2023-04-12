package org.apache.commons.mail;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
public class EmailTest {

	//Creates email Dummy object used in every test
	private Email email;
	@Before
	public void Setup()
	{
		email = new EmailDummy();
	}
	
	//Testing adding a string to Bcc
	@Test
	public void addBccValidTest()
	{
		
		try {
			email.addBcc("valid@mail");
			List<InternetAddress> bccList = email.getBccAddresses();
			assertEquals("valid@mail",bccList.get(0).toString());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Testing adding an array of strings to Bcc
	@Test
	public void addBccArrayValidtest()
	{
		
		String[] bccArray = new String[]{"valid@mail"};
		try {
			email.addBcc(bccArray);
			List<InternetAddress> bccList = email.getBccAddresses();
			assertEquals(bccArray[0],bccList.get(0).toString());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Testing adding a string to Cc
	@Test
	public void addCcValidTest()
	{
		
		try {
			email.addCc("valid@mail");
			List<InternetAddress> CcList = email.getCcAddresses();
			assertEquals("valid@mail",CcList.get(0).toString());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Testing adding an array of strings to Cc
	@Test
	public void addCcArrayValidtest()
	{
		
		String[] CcArray = new String[]{"valid@mail"};
		try {
			email.addCc(CcArray);
			List<InternetAddress> CcList = email.getCcAddresses();
			assertEquals(CcArray[0],CcList.get(0).toString());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Tests adding a header
	@Test
	public void addHeaderValidTest()
	{
		
		email.addHeader("head", "val");
		//TODO
		assertEquals(1,email.headers.size());
	}
	
	//tests error thrown for empty header value
	@Test(expected = IllegalArgumentException.class)
	public void addHeaderInvalidValTest() throws IllegalArgumentException
	{
		email.addHeader("head", null);
	}
	
	//tests error thrown for empty header name 
	public void addHeaderInvalidNameTest() throws IllegalArgumentException
	{
		String s = null;
		email.addHeader(s, "val");
	}
	
	//tests adding reply email
	@Test
	public void addReplyToValidTest()
	{
		
		try {
			email.addReplyTo("valid@mail");
			List<InternetAddress> replyList = email.getReplyToAddresses();
			assertEquals("valid@mail",replyList.get(0).toString());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//tests building message with minimal values
	@Test
	public void buildMimeMessageValidTest()
	{
		
		try {
			email.setHostName("host");
			email.setFrom("me@mail");
			email.addTo("other@mail");
			email.buildMimeMessage();
			
		} catch (EmailException e) {
			System.out.println("what");
			e.printStackTrace();
		}
	
	}
	
	//tests error of building message when it already has been built
	@Test(expected = IllegalStateException.class)
	public void buildMimeMessageAlreadyBuiltTest()  throws IllegalStateException
	{
		
		try {
			email.setHostName("host");
			email.setFrom("me@mail");
			email.addTo("other@mail");
			email.buildMimeMessage();
			email.buildMimeMessage();
			
		} catch (EmailException e) {
			System.out.println("what");
			e.printStackTrace();
		}
		
	}
	
	//tests building message with Bcc and CC
	@Test
	public void buildMimeMessageValid2Test()
	{
		Map<String, String> m = new HashMap();
		m.put("header", "mid");
		try {
			email.setHostName("host");
		
			email.setFrom("me@mail");
			email.addBcc("other@mail");
			email.addCc("other@mail");
			email.setHeaders(m);
			
			email.buildMimeMessage();

			
		} catch (EmailException e) {
			System.out.println("what");
			e.printStackTrace();
		}
	
	}
	
	//tests not being able to connect
	@Test(expected = EmailException.class)
	public void buildMimeMessagePopFailTest() throws EmailException
	{

		email.setHostName("host");
		
		email.setFrom("me@mail");
		email.addBcc("other@mail");
		email.addReplyTo("repl@mail.com");
		email.setPopBeforeSmtp(true, "host", "user", "pass");
		email.buildMimeMessage();

			

	
	}
	
	//tests no from address error
	@Test(expected = EmailException.class)
	public void buildMimeMessageNoFromTest() throws EmailException
	{
		
			email.setHostName("host");
			email.buildMimeMessage();
		
	}
	
	//tests no from receiver error
	@Test(expected = EmailException.class)
	public void buildMimeMessageNoReceiverTest() throws EmailException
	{
		
			email.setHostName("host");
			email.setFrom("me@mail");
			email.buildMimeMessage();
		
	}
	
	//tests getting a set hostname
	@Test
	public void getHostNameValidTest()
	{
		
		email.setHostName("hostname");
		assertEquals("hostname",email.getHostName());
	}
	
	//test getting null hostname
	@Test
	public void getHostNameEmptyTest()
	{
		
		//email.setHostName("hostname");
		assertEquals(null,email.getHostName());
	}
	
	//tests getting hostname from a session
	@Test
	public void getHostNameSessionTest()
	{
		
		Properties p = new Properties();
		
		p.setProperty(EmailConstants.MAIL_HOST, "name");
		Session session = Session.getInstance(p);
		email.setMailSession(session);
		assertEquals("name",email.getHostName());
	}
	
	//tests a valid sent date
	@Test
	public void getSentDateValidTest()
	{
		
		Date date = new Date();
		email.setSentDate(date);
		assertEquals(date,email.getSentDate());
	}
	
	//tests a valid set from 
	@Test
	public void setFromValidTest()
	{
		
		try {
			email.setFrom("me@mail");
			assertEquals("me@mail",email.getFromAddress().toString());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//tests getting default socket timeout
	@Test
	public void socketTimeoutTest()
	{
		
		assertEquals(60000, email.getSocketConnectionTimeout());
	}
	
	//tests getting a null mail session
	@Test(expected = EmailException.class)
	public void getMailSessionInvalidTest() throws EmailException
	{
		email.getMailSession();
	}
	
	//tests getting a valid mail session
	@Test()
	public void getMailSessionSSLTest()
	{
		try {
			email.setHostName("host");
			email.setSSLOnConnect(true);
			Session s = email.getMailSession();
			assertEquals("javax.mail.Session@186978a6",s.toString());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
