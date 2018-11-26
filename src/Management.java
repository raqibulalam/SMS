import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;

public class Management extends JFrame {

	private JPanel contentPane;
	private JTable datatable;
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rst = null;
	private JScrollPane scrollPane;
	private JTextField nametf;
	private JTextField usernametf;
	private JTextField mailtf;
	private JPasswordField passtf;
	private JTextField contacttf;
	private JTextField agetf;
	private JRadioButton male;
	private JRadioButton female;
	private JTextField heighttf;
	private JComboBox addressCB;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;
	String v = null;
	String combo = null;
	String gen = null;
	private int E_ID = 0;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JScrollPane scrollPane_1;
	private JTextField searchtf;
	private JComboBox searchCB;
	private JLabel lblName;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblEmail;
	private JLabel lblContact;
	private JLabel lblHeight;
	private JLabel lblGender;
	private JLabel lblAge;
	private JLabel lblLocation;
	private JButton btnNewButton_1;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Management frame = new Management();
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
	public Management() {
		setResizable(false);
		design();
		conn = SQLconnection.ConnecrDB();
		loadtable();
		center();
	}

	private void loadtable() {
		try {
			String query = "select E_ID,Name,Username,Email,Contact,Gender,Address,Age,Height FROM EMPLOYEE";
			pst = conn.prepareStatement(query);
			rst = pst.executeQuery();
			datatable.setModel(DbUtils.resultSetToTableModel(rst));
			pst.close();
			rst.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private void addData() {
		try {
			String query = "INSERT INTO employee(Name,Username,Password,Email,Contact,Gender,Height,Age,Address) VALUES(?,?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(query);
			pst.setString(1, nametf.getText());
			pst.setString(2, usernametf.getText());
			pst.setString(3, passtf.getText());
			pst.setString(4, mailtf.getText());
			pst.setString(5, contacttf.getText());
			if (male.isSelected()) {
				v = male.getText().toString();
			} else if (female.isSelected()) {
				v = female.getText().toString();
			} else {
				JOptionPane.showMessageDialog(null, "Enter your Gender");
			}
			pst.setString(6, v);
			pst.setString(7, heighttf.getText());
			pst.setString(8, agetf.getText());
			pst.setString(9, addressCB.getSelectedItem().toString());
			pst.execute();
			pst.close();
			JOptionPane.showMessageDialog(null, "New Info added");
			loadtable();
			Reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadfield() {
		try {
			int row = datatable.getSelectedRow();
			String ID = (datatable.getModel().getValueAt(row, 0).toString());
			String query = "Select * FROM employee WHERE E_ID='" + ID + "'";
			pst = conn.prepareStatement(query);
			rst = pst.executeQuery();
			while (rst.next()) {
				E_ID = rst.getInt("E_ID");

				nametf.setText(rst.getString("Name"));
				usernametf.setText(rst.getString("username"));
				passtf.setText(rst.getString("Password"));
				mailtf.setText(rst.getString("Email"));
				contacttf.setText(rst.getString("Contact"));

				v = rst.getString("Gender");
				if (v.equals("Male")) {
					male.setSelected(true);
					female.setSelected(false);
				} else if (v.equals("Female")) {
					female.setSelected(true);
					male.setSelected(false);

				} else {
					System.out.println("Enter your Gender");
				}
				combo = rst.getString("Address");
				if (combo.equals("Dhaka")) {
					addressCB.setSelectedItem("Dhaka");
				} else if (combo.equals("Rajshahi")) {
					addressCB.setSelectedItem("Rajshahi");
				} else if (combo.equals("Khluna")) {
					addressCB.setSelectedItem("Khluna");
				} else if (combo.equals("Barishal")) {
					addressCB.setSelectedItem("Barishal");
				} else if (combo.equals("Sylet")) {
					addressCB.setSelectedItem("Sylet");
				} else if (combo.equals("Chittagong")) {
					addressCB.setSelectedItem("Chittagong");
				} else if (combo.equals("Rangpur")) {
					addressCB.setSelectedItem("Rangpur");
				} else {
					System.out.println("null");
				}

				agetf.setText(rst.getString("Age"));
				heighttf.setText(rst.getString("Height"));

			}
			pst.close();
			rst.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updatedata() {
		try {

			String sql = "Update employee SET Name='" + nametf.getText() + "',Username='" + usernametf.getText()
					+ "',Password='" + passtf.getText() + "',Email='" + mailtf.getText() + "',Contact='"
					+ contacttf.getText() + "',Gender='" + v + "',Height='" + heighttf.getText() + "',Age='"
					+ agetf.getText() + "',Address='" + addressCB.getSelectedItem().toString() + "' WHERE E_ID = '"
					+ E_ID + "'";
			pst = conn.prepareStatement(sql);
			pst.execute();

			JOptionPane.showMessageDialog(null, "Updated successfully");
			pst.close();
			rst.close();
			loadtable();
			Reset();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void deletedata() {
		try {
			int action=JOptionPane.showConfirmDialog(null,"Are you sure to Delete?", "Delete",JOptionPane.YES_NO_CANCEL_OPTION);
			if(action==0) {
				String query="DELETE FROM employee WHERE E_ID ='" + E_ID + "'";
				pst=conn.prepareStatement(query);
				pst.execute();
				JOptionPane.showMessageDialog(null, "Deleted Successfully");
				
				pst.close();
				loadtable();
				Reset();
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void searchData() {
		try {
			String select = (String) searchCB.getSelectedItem();
			String query="select E_ID,Name,Username,Email,Contact,Gender,Address,Age FROM EMPLOYEE WHERE " + select +" LIKE '" + searchtf.getText() + "%'"; 
			pst=conn.prepareStatement(query);
			//pst.setString(1, searchtf.getText());
			rst=pst.executeQuery();
			datatable.setModel(DbUtils.resultSetToTableModel(rst));
			pst.close();
			rst.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void Reset() {
		nametf.setText(null);
		usernametf.setText(null);
		passtf.setText(null);
		mailtf.setText(null);
		contacttf.setText(null);
		agetf.setText(null);
		heighttf.setText(null);
		buttonGroup.clearSelection();
		female.setSelected(false);
		addressCB.setSelectedItem("-");
		searchCB.setSelectedItem("-");
		searchtf.setText(null);
		
		
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
		

	private void design() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1070, 742);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(27, 96, 1011, 142);
		contentPane.add(scrollPane_1);

		scrollPane = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane);

		datatable = new JTable();
		datatable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				loadfield();

			}
		});
		scrollPane.setViewportView(datatable);

		nametf = new JTextField();
		nametf.setToolTipText("");
		nametf.setFont(new Font("Tahoma", Font.PLAIN, 22));
		nametf.setBounds(130, 303, 383, 42);
		contentPane.add(nametf);
		nametf.setColumns(10);

		usernametf = new JTextField();
		usernametf.setFont(new Font("Tahoma", Font.PLAIN, 22));
		usernametf.setBounds(130, 380, 383, 42);
		contentPane.add(usernametf);
		usernametf.setColumns(10);

		passtf = new JPasswordField();
		passtf.setFont(new Font("Tahoma", Font.PLAIN, 22));
		passtf.setBounds(130, 444, 383, 49);
		contentPane.add(passtf);

		mailtf = new JTextField();
		mailtf.setFont(new Font("Tahoma", Font.PLAIN, 22));
		mailtf.setBounds(130, 506, 383, 42);
		contentPane.add(mailtf);
		mailtf.setColumns(10);

		contacttf = new JTextField();
		contacttf.setFont(new Font("Tahoma", Font.PLAIN, 22));
		contacttf.setBounds(130, 561, 383, 42);
		contentPane.add(contacttf);
		contacttf.setColumns(10);

		agetf = new JTextField();
		agetf.setFont(new Font("Tahoma", Font.PLAIN, 22));
		agetf.setBounds(635, 303, 388, 42);
		contentPane.add(agetf);
		agetf.setColumns(10);

		male = new JRadioButton("Male");
		buttonGroup.add(male);
		male.setBounds(652, 380, 127, 25);
		contentPane.add(male);

		female = new JRadioButton("Female");
		buttonGroup.add(female);
		female.setBounds(859, 380, 127, 25);
		contentPane.add(female);

		heighttf = new JTextField();
		heighttf.setFont(new Font("Tahoma", Font.PLAIN, 22));
		heighttf.setText("");
		heighttf.setBounds(635, 439, 388, 49);
		contentPane.add(heighttf);
		heighttf.setColumns(10);

		addressCB = new JComboBox();
		addressCB.setFont(new Font("Tahoma", Font.BOLD, 22));
		addressCB.setModel(new DefaultComboBoxModel(new String[] {"-", "Dhaka", "Rajshahi", "Khluna", "Barishal", "Sylhet", "Chittagong", "Rangpur"}));
		addressCB.setBounds(635, 501, 388, 47);
		contentPane.add(addressCB);

		btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(30, 144, 255));
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addData();
			}
		});
		btnAdd.setBounds(357, 623, 147, 59);
		contentPane.add(btnAdd);

		btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(30, 144, 255));
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatedata();

			}
		});
		btnUpdate.setBounds(516, 623, 147, 59);
		contentPane.add(btnUpdate);

		btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(30, 144, 255));
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deletedata();
			}
			
		});
		btnDelete.setBounds(692, 623, 147, 59);
		contentPane.add(btnDelete);
		
		JLabel lblNewLabel = new JLabel("Search by");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 0, 85, 55);
		contentPane.add(lblNewLabel);
		
		searchtf = new JTextField();
		searchtf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				searchData();
			}
		});
		searchtf.setBounds(417, 13, 284, 42);
		contentPane.add(searchtf);
		searchtf.setColumns(10);
		
		searchCB = new JComboBox();
		searchCB.setFont(new Font("Tahoma", Font.BOLD, 22));
		searchCB.setModel(new DefaultComboBoxModel(new String[] {"-", "Name", "Username", "Email", "Contact", "Gender", "Address", "Age"}));
		searchCB.setBounds(91, 6, 291, 49);
		contentPane.add(searchCB);
		
		lblName = new JLabel("Name");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(12, 302, 106, 43);
		contentPane.add(lblName);
		
		lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(12, 380, 106, 43);
		contentPane.add(lblUsername);
		
		lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(12, 444, 106, 43);
		contentPane.add(lblPassword);
		
		lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(12, 506, 106, 43);
		contentPane.add(lblEmail);
		
		lblContact = new JLabel("Contact");
		lblContact.setForeground(Color.WHITE);
		lblContact.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblContact.setHorizontalAlignment(SwingConstants.CENTER);
		lblContact.setBounds(12, 560, 106, 43);
		contentPane.add(lblContact);
		
		lblHeight = new JLabel("Age");
		lblHeight.setForeground(Color.WHITE);
		lblHeight.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeight.setBounds(525, 303, 106, 43);
		contentPane.add(lblHeight);
		
		lblGender = new JLabel("Gender");
		lblGender.setForeground(Color.WHITE);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGender.setHorizontalAlignment(SwingConstants.CENTER);
		lblGender.setBounds(525, 379, 106, 43);
		contentPane.add(lblGender);
		
		lblAge = new JLabel("Height");
		lblAge.setForeground(Color.WHITE);
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAge.setHorizontalAlignment(SwingConstants.CENTER);
		lblAge.setBounds(525, 444, 106, 43);
		contentPane.add(lblAge);
		
		lblLocation = new JLabel("Location");
		lblLocation.setForeground(Color.WHITE);
		lblLocation.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocation.setBounds(525, 506, 106, 43);
		contentPane.add(lblLocation);
		
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.setBackground(new Color(30, 144, 255));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Reset();
			}
		});
		btnNewButton.setBounds(859, 622, 127, 60);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Logout");
		btnNewButton_1.setBackground(new Color(30, 144, 255));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				testJF tf=new testJF();
				tf.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton_1.setBounds(713, 13, 163, 42);
		contentPane.add(btnNewButton_1);
		
		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setIcon(new ImageIcon(Management.class.getResource("/image/1002401_photoshop-sports-backgrounds-download-hd-wallpapers-download_1600x1000_h.jpg")));
		lblNewLabel_1.setBounds(0, 0, 1066, 705);
		contentPane.add(lblNewLabel_1);
	}
}
