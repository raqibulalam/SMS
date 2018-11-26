
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class testJF extends JFrame {

	private JPanel contentPane;
	Connection conn=null;
	PreparedStatement pst=null;
	ResultSet rst=null;
	
	
	private JTextField UsernameTF;
	private JPasswordField passwordTf;
	private JLabel Clocklabel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testJF frame = new testJF();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public testJF() {
		setResizable(false);
		design();
		conn=SQLconnection.ConnecrDB();
		clock();
		center();
	}
	private void Login() {
		try {
			String query = "SELECT * FROM EMPLOYEE WHERE Username=? AND Password=?";
			pst=conn.prepareStatement(query);
			pst.setString(1,UsernameTF.getText());
			pst.setString(2, passwordTf.getText());
			rst=pst.executeQuery();
			
			if(rst.next())
			{
				JOptionPane.showMessageDialog(null, "Login Successfull");
				Management m= new Management();
				m.setVisible(true);
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Login Failed");
			}
			pst.close();
			rst.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void clock() {
		Thread clock=new Thread()
		{
			public void run() {
				try {
					for(;;) {
						Calendar cal=new GregorianCalendar();
						SimpleDateFormat form=new SimpleDateFormat("dd-mm-yy, hh:mm:ss a");
						Date date=cal.getTime();
						String timeString=form.format(date);
						Clocklabel.setText(timeString);
						sleep(100);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		clock.start();
	}
	public void center() {
		Dimension screensize,framesize;
		int x,y;
		screensize=Toolkit.getDefaultToolkit().getScreenSize();
		framesize =getSize();
		x=(screensize.width-framesize.width)/2;
		y=(screensize.height-framesize.height)/2;
		setLocation(x,y);
	}
	
	private void design()
	{
		conn=SQLconnection.ConnecrDB();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 817, 609);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(UsernameTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Enter username");
				}else if(passwordTf.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Enter your Passwoard");
				}else {
					Login();	
				}
				
			}
		});
		btnLogin.setBackground(new Color(240, 255, 255));
		btnLogin.setFont(new Font("Stencil", Font.PLAIN, 26));
		btnLogin.setBounds(260, 317, 316, 56);
		contentPane.add(btnLogin);
		
		JButton btnSignup = new JButton("Signup");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Signup s=new Signup();
				s.setVisible(true);
				dispose();
			}
		});
		btnSignup.setBackground(new Color(240, 255, 255));
		btnSignup.setFont(new Font("Stencil", Font.PLAIN, 24));
		btnSignup.setBounds(260, 409, 318, 62);
		contentPane.add(btnSignup);
		
		UsernameTF = new JTextField();
		UsernameTF.setBackground(new Color(240, 255, 255));
		UsernameTF.setHorizontalAlignment(SwingConstants.CENTER);
		UsernameTF.setFont(new Font("Tahoma", Font.PLAIN, 23));
		UsernameTF.setBounds(216, 82, 498, 69);
		contentPane.add(UsernameTF);
		UsernameTF.setColumns(10);
		
		passwordTf = new JPasswordField();
		passwordTf.setBackground(new Color(240, 255, 255));
		passwordTf.setHorizontalAlignment(SwingConstants.CENTER);
		passwordTf.setFont(new Font("Tahoma", Font.PLAIN, 23));
		passwordTf.setBounds(216, 181, 498, 69);
		contentPane.add(passwordTf);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(testJF.class.getResource("/image/Login-icon.png")));
		lblNewLabel.setBounds(120, 488, 86, 69);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(testJF.class.getResource("/image/signup-icon (1).png")));
		label.setBounds(120, 409, 86, 61);
		contentPane.add(label);
		
		JLabel lblNewLabel_3 = new JLabel("User name");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setIcon(new ImageIcon(testJF.class.getResource("/image/Office-Customer-Male-Light-icon.png")));
		lblNewLabel_3.setBounds(57, 96, 120, 69);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setIcon(new ImageIcon(testJF.class.getResource("/image/Key-icon.png")));
		lblNewLabel_1.setBounds(57, 201, 127, 56);
		contentPane.add(lblNewLabel_1);
		
		Clocklabel = new JLabel("Clock");
		Clocklabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 27));
		Clocklabel.setHorizontalAlignment(SwingConstants.CENTER);
		Clocklabel.setBounds(92, 13, 649, 56);
		contentPane.add(Clocklabel);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setFont(new Font("Stencil", Font.PLAIN, 24));
		btnExit.setBackground(new Color(240, 255, 255));
		btnExit.setBounds(258, 495, 318, 62);
		contentPane.add(btnExit);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(testJF.class.getResource("/image/App-login-manager-icon.png")));
		lblNewLabel_2.setBounds(120, 317, 72, 56);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setIcon(new ImageIcon(testJF.class.getResource("/image/200902.jpg")));
		lblNewLabel_4.setBounds(0, 0, 808, 570);
		contentPane.add(lblNewLabel_4);
	}
}
