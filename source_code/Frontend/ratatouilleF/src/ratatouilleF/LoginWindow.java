package ratatouilleF;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.FlowLayout;

public class LoginWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 720, 512);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setBackground(Color.decode("#FFFBF3"));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setPreferredSize(new Dimension(640, 150));
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBackground(Color.decode("#FFFBF3"));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.EAST);
		panel_1.setPreferredSize(new Dimension(150, 150));
		panel_1.setLayout(null);
		panel_1.setBackground(Color.decode("#FFFBF3"));
		
		JLabel label = new JLabel("New label");
		label.setIcon(new ImageIcon("C:\\Users\\alfre\\Desktop\\circles (1).png"));
		label.setBounds(0, 0, 150, 150);
		panel_1.add(label);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.NORTH);
		panel_2.setPreferredSize(new Dimension(640, 150));
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.setBackground(Color.decode("#FFFBF3"));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.WEST);
		panel_3.setPreferredSize(new Dimension(150, 1000));
		panel_3.setLayout(null);
		panel_3.setBackground(Color.decode("#FFFBF3"));
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\alfre\\Desktop\\circles.png"));
		lblNewLabel.setBounds(0, 0, 150, 150);
		panel_3.add(lblNewLabel);
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("../../imgs/circles.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
