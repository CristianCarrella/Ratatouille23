package ratatouilleF;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class FirstViews {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstViews window = new FirstViews();
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
	public FirstViews() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.decode("#FFFBF3"));
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#FFFBF3"));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		panel.getRootPane().setBackground(Color.decode("#FFFBF3"));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.decode("#FFFBF3"));
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setPreferredSize(new Dimension(640, 150));
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.decode("#FFFBF3"));
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Entra nell'app");
		panel_2.add(btnNewButton);
		btnNewButton.setPreferredSize(new Dimension(130, 25));
		btnNewButton.setBorder(new RoundedBorder(25));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.decode("#FFFBF3"));
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("APPLICAZIONE DESKTOP PER AMMINISTRATORI E SUPERVISORI");
		panel_3.add(lblNewLabel);
		frame.setBounds(100, 100, 720, 512);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
	}

}
