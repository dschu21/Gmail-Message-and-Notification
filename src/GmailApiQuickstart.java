import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class GmailApiQuickstart {

    private static String USER_NAME = "xxxxxxxxx";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "xxxxxxx"; // Gmail password
    private static String RECIPIENT = "xxxxxx", RECIPIENT1 = "xxxxxxxx@gmail.com";

    
    	static String from = USER_NAME;
        static String pass = PASSWORD;
        static String[] to = { RECIPIENT, RECIPIENT1}; // list of recipient email addresses
        static String subject = "Automatic Response";
        static String body = "BRUUUHHHH, your coffee is READAAAYYY! Why don't you huck on down and have a cup?";
        
     public static Scanner sc = new Scanner(System.in);

     
     
    public static void main(String[] args) {
        

        sendFromGmail(from, pass, to, subject, body);
    }

   
    private static void sendFromGmail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

       
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];
            
	          
            
            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            
            try{
				Thread.sleep(50000);
			}
			catch(InterruptedException e){
				 e.printStackTrace();
			}
            message.setSubject(subject);
           
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            setNotification();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

	private static void setNotification() {
		
		String message = body;
		String header = "From CoffeeMaker5000:";
		final JFrame frame = new JFrame();
		//frame.setUndecorated(true);
		frame.setTitle("New Email!");
		frame.setSize(300,125);
		frame.setAlwaysOnTop(true);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;
		JLabel headingLabel = new JLabel(header);
		headingLabel .setIcon(null); // --- use image icon you want to be as heading image.
		headingLabel.setOpaque(false);
		frame.add(headingLabel, constraints);
//		constraints.gridx++;
//		constraints.weightx = 0f;
//		constraints.weighty = 0f;
//		constraints.fill = GridBagConstraints.NONE;
//		constraints.anchor = GridBagConstraints.NORTH;
//		JButton closeButton = new JButton(new AbstractAction("x") {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				 frame.dispose();
//			}
//		});
//		closeButton.setMargin(new Insets(1, 4, 1, 4));
//		closeButton.setFocusable(false);
//		frame.add(closeButton, constraints);
		constraints.gridx = 0;
		constraints.gridy++;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;
		JLabel messageLabel = new JLabel("<HtMl>" + message);
		frame.add(messageLabel, constraints);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
		Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());// height of the task bar
		frame.setLocation(scrSize.width - frame.getWidth(), scrSize.height - toolHeight.bottom - frame.getHeight());
		new Thread(){
		      @Override
		      public void run() {
		           try {
		                  Thread.sleep(10000); // time after which pop up will be disappeared.
		                  frame.dispose();
		           } catch (InterruptedException e) {
		                  e.printStackTrace();
		           }
		      };
		}.start();
	}

}