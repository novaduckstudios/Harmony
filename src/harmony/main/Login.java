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
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Login extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static DiscordAPI api;
	private JTextField textField;
	private JPasswordField passwordField;
	static JLabel lblStatus;
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
		setTitle("Harmony - Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 342, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(100, 76, 225, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(100, 107, 225, 20);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnNewButton.setBounds(100, 138, 225, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblHarmony = new JLabel("harmony");
		lblHarmony.setVerticalAlignment(SwingConstants.TOP);
		lblHarmony.setFont(new Font("Segoe UI Light", Font.PLAIN, 25));
		lblHarmony.setBounds(10, 11, 127, 34);
		contentPane.add(lblHarmony);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(44, 79, 46, 14);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(27, 110, 63, 14);
		contentPane.add(lblPassword);
		
		lblStatus = new JLabel("status");
		lblStatus.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
		lblStatus.setText("Waiting...");
		lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStatus.setBounds(147, 26, 178, 14);
		contentPane.add(lblStatus);
	}
}
