import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClientGUI extends JFrame{

	private static FlowLayout layout;
	private static JTextField serverip1,serverip2,serverip3,serverip4,port;
	static JLabel point;
	private static JFrame panel;
	private static JButton Ok;
	private static boolean portok = false;
	private static boolean ip1ok = false;
	private static boolean ip2ok = false;
	private static boolean ip3ok = false;
	private static boolean ip4ok = false;
	private static String serverip;
	private static int serverport;
	
	static void connectionPrompt(){
		
		JFrame panel = new JFrame();
		
		panel.setName("Connection to (?)");
		layout = new FlowLayout();
		panel.setLayout(layout);
		panel.setSize(300, 100);
		
		point = new JLabel("IP: ");
		panel.add(point);
		
		serverip1 = new JTextField();
		serverip1.setPreferredSize(new Dimension(30,20));
		serverip1.setDocument(new JTextFieldLimit(3));
		serverip1.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
				      char c = e.getKeyChar();
				      if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
				         e.consume();  // ignore event
				      }
				   }
				});
		serverip1.addKeyListener(new textFilter());
		panel.add(serverip1);
		
		point = new JLabel(".");
		panel.add(point);
			
		serverip2 = new JTextField();
		serverip2.setPreferredSize(new Dimension(30,20));
		serverip2.setDocument(new JTextFieldLimit(3));
		serverip2.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
				      char c = e.getKeyChar();
				      if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
				         e.consume();  // ignore event
				      }
				   }
				});
		serverip2.addKeyListener(new textFilter());
		panel.add(serverip2);
		
		point = new JLabel(".");
		panel.add(point);
		
		serverip3 = new JTextField();
		serverip3.setPreferredSize(new Dimension(30,20));
		serverip3.setDocument(new JTextFieldLimit(3));
		serverip3.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
				      char c = e.getKeyChar();
				      if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
				         e.consume();  // ignore event
				      }
				   }
				});
		serverip3.addKeyListener(new textFilter());
		panel.add(serverip3);
		
		point = new JLabel(".");
		panel.add(point);
		
		serverip4 = new JTextField();
		serverip4.setPreferredSize(new Dimension(30,20));
		serverip4.setDocument(new JTextFieldLimit(3));
		serverip4.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
				      char c = e.getKeyChar();
				      if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
				         e.consume();  // ignore event
				      }
				   }
				});
		serverip4.addKeyListener(new textFilter());
		panel.add(serverip4);
		
		point = new JLabel(" Port: ");
		panel.add(point);
		
		port = new JTextField();
		port.setPreferredSize(new Dimension(40,20));
		port.setDocument(new JTextFieldLimit(5));
		port.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
				      char c = e.getKeyChar();
				      if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
				         e.consume();  // ignore event
				      }
				   }
				});
		port.addKeyListener(new textFilter());
		panel.add(port);
		
		Ok = new JButton("OK");
		Ok.setPreferredSize(new Dimension(60,30));
		Ok.setEnabled(false);
		Ok.addActionListener(new buttonListener());
		panel.add(Ok);
		
		panel.setResizable(false);
		
		panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel.setVisible(true);
	}
	
	static class buttonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent act) {
			
			if(act.getSource() == Ok){
				serverip = serverip1.getText() + "."  + serverip2.getText() + "." + serverip3.getText() + "." + serverip4.getText();
				serverport = Integer.parseInt(port.getText());
				System.out.println("Attempting connection to : " + serverip + " : " + serverport);
				
			}
			
		}
				
	}
	
	
	static String getServerIP(){
		return serverip;
	}
	
	static int getServerPORT(){
		return serverport;
	}
	
	static class JTextFieldLimit extends PlainDocument {
		  private int limit;
		  JTextFieldLimit(int limit) {
		    super();
		    this.limit = limit;
		  }

		  JTextFieldLimit(int limit, boolean upper) {
		    super();
		    this.limit = limit;
		  }

		  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		    if (str == null)
		      return;

		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
		}
	
	static class textFilter implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
			
		}
		

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
int key = e.getKeyCode();
			
			JTextField source = (JTextField) e.getSource();
			
			
			if((key>=48)&&(key<=57) || (key == KeyEvent.VK_BACK_SPACE && source.getText().length() > 0)){
			
				if(source == port){
					
					if(Integer.parseInt(port.getText()) > 65535 || source.getText().length() == 0){
						port.setBackground(Color.red);
						portok = false;
					}else{
						port.setBackground(Color.WHITE);
						portok = true;
					}
					
				}else if(source == serverip1){
					
					if(Integer.parseInt(serverip1.getText()) > 255 || source.getText().length() == 0){
						serverip1.setBackground(Color.red);
						ip1ok = false;
					}else{
						serverip1.setBackground(Color.WHITE);
						ip1ok = true;
					}
					
				}else if(source == serverip2){
					
					if(Integer.parseInt(serverip2.getText()) > 255 || source.getText().length() == 0){
						serverip2.setBackground(Color.red);
						ip2ok = false;
					}else{
						serverip2.setBackground(Color.WHITE);
						ip2ok = true;
					}
					
				}else if(source == serverip3){	
					if(Integer.parseInt(serverip3.getText()) > 255 || source.getText().length() == 0){
						serverip3.setBackground(Color.red);
						ip3ok = false;
					}else{
						serverip3.setBackground(Color.WHITE);
						ip3ok = true;
					}
				}else if(source == serverip4){	
					if(Integer.parseInt(serverip4.getText()) > 255 || source.getText().length() == 0){
						serverip4.setBackground(Color.red);
						ip4ok = false;
					}else{
						serverip4.setBackground(Color.WHITE);
						ip4ok = true;
					}
				}
				
			}
			if(allOk()){
				Ok.setEnabled(true);
			}else{
				Ok.setEnabled(false);
			}
		}
		 
	}
	
	private static boolean allOk() {
		return portok && ip1ok && ip2ok && ip3ok && ip4ok;
	}
	
	
	
	
}
