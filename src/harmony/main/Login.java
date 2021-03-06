package harmony.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Toolkit;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Login extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static DiscordAPI api;
	private JTextField textField;
	private JPasswordField passwordField;
	static JLabel lblStatus;
	private JButton button;
	static Properties prop = new Properties();
	static OutputStream output = null;
	static InputStream input = null;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {

					try {
			            // Set System L&F
			        UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
				    } 
				    catch (UnsupportedLookAndFeelException e) {
				       // handle exception
				    }
				    catch (ClassNotFoundException e) {
				       // handle exception
				    }
				    catch (InstantiationException e) {
				       // handle exception
				    }
				    catch (IllegalAccessException e) {
				       // handle exception
				    }
					Login frame = new Login();
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
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/harmony/main/harmony-heart-icon-79964.png")));
		setTitle("Harmony - Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 223);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			textField.setText(prop.getProperty("email"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		textField.setBounds(66, 76, 225, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(66, 107, 225, 20);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("Forgot Password");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("https://support.discordapp.com/hc/en-us/articles/218410947-I-forgot-my-Password-Where-can-I-set-a-new-one-"));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(168, 138, 123, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblHarmony = new JLabel("harmony");
		lblHarmony.setVerticalAlignment(SwingConstants.TOP);
		lblHarmony.setFont(new Font("Segoe UI Light", Font.PLAIN, 31));
		lblHarmony.setBounds(10, 11, 127, 42);
		contentPane.add(lblHarmony);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(10, 79, 46, 14);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(-7, 110, 63, 14);
		contentPane.add(lblPassword);
		
		lblStatus = new JLabel("status");
		lblStatus.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
		lblStatus.setText("Waiting...");
		lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStatus.setBounds(111, 33, 178, 14);
		contentPane.add(lblStatus);
		
		button = new JButton("Login");
		button.setFont(new Font("Tahoma", Font.BOLD, 11));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					output = new FileOutputStream("config.properties");

					// set the properties value
					prop.setProperty("email", textField.getText());

					// save properties to project root folder
					prop.store(output, null);

				} catch (IOException io) {
					io.printStackTrace();
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

				}
				lblStatus.setText("Attempting connection...");
				api = Javacord.getApi(textField.getText(), passwordField.getText());
				api.connect(new FutureCallback<DiscordAPI>() {
					  @Override
					  public void onSuccess(final DiscordAPI api) {
						  System.out.println("Connected to Discord!");
						  lblStatus.setText("Getting servers...");
						  try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						  System.out.println(api.getServers());
						  Servers.main();
						  setVisible(false);
						  dispose();
					  }

					  @Override
					  public void onFailure(Throwable t) {
						  System.out.println("FAILED!");
						  lblStatus.setText("Failed, wrong login?");
						  api.disconnect();
					    t.printStackTrace();
					  }
					});
			}
		});
		button.setBounds(66, 138, 92, 23);
		contentPane.add(button);
		
		JCheckBox chckbxRememberEmail = new JCheckBox("Remember E-Mail");
		chckbxRememberEmail.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {

					output = new FileOutputStream("config.properties");

					// set the properties value
					prop.setProperty("email", "");

					// save properties to project root folder
					prop.store(output, null);

				} catch (IOException io) {
					io.printStackTrace();
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

				}
			}
		});
		if(!textField.getText().isEmpty()){
			chckbxRememberEmail.setSelected(true);
			passwordField.grabFocus();
			
		}
		chckbxRememberEmail.setBounds(178, 168, 113, 23);
		contentPane.add(chckbxRememberEmail);
	}
}
