package paquete;

import java.awt.Color;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.Label;
import javax.swing.JLabel;

public class Principal extends JFrame {

	JPanel contentPane;
	IngresarPanel ingresarPanel;
	RetirarPanel retirarPanel;
	ListaPanel listaPanel;

	JPanel panel;
	static Principal frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Principal();
					frame.setResizable(false);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Principal() {

		initComponents();

		ingresarPanel = new IngresarPanel();
		ingresarPanel.setBounds(0, 0, 526, 690);
		getContentPane().add(ingresarPanel);
		ingresarPanel.setVisible(false);

		retirarPanel = new RetirarPanel();
		retirarPanel.setBounds(0, 0, 526, 690);
		getContentPane().add(retirarPanel);

		panel = new JPanel();
		panel.setBounds(0, 0, 526, 691);
		panel.setBackground(new java.awt.Color(65, 14, 51));
		contentPane.add(panel);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setLayout(null);

		Button btnIngresar = new Button("Ingresar");
		btnIngresar.setFont(new Font("Avenir", Font.PLAIN, 30));
		btnIngresar.setForeground(new java.awt.Color(65, 14, 51));

		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ingresarPanel.setVisible(true);
				retirarPanel.setVisible(false);
			}
		});
		btnIngresar.setBounds(75, 446, 160, 83);
		panel.add(btnIngresar);

		Button btnRetirar = new Button("Retirar");
		btnRetirar.setFont(new Font("Avenir", Font.PLAIN, 30));
		btnRetirar.setForeground(new java.awt.Color(65, 14, 51));
		btnRetirar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retirarPanel.setVisible(true);
				ingresarPanel.setVisible(false);

			}
		});
		btnRetirar.setBounds(280, 446, 160, 83);
		panel.add(btnRetirar);

		JLabel lblNewLabel = new JLabel("E.G.A.");
		lblNewLabel.setForeground(UIManager.getColor("Button.highlight"));
		lblNewLabel.setFont(new Font("Avenir", Font.PLAIN, 60));
		lblNewLabel.setBounds(179, 97, 175, 103);
		panel.add(lblNewLabel);

		JLabel lblParking = new JLabel("Parking");
		lblParking.setForeground(UIManager.getColor("Button.highlight"));
		lblParking.setFont(new Font("Avenir", Font.PLAIN, 94));
		lblParking.setBounds(105, 212, 321, 115);
		panel.add(lblParking);

		Button button = new Button("Admin");
		button.setFont(new Font("Avenir", Font.PLAIN, 15));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = JOptionPane.showInputDialog(null, "ADMIN", "LOGIN", JOptionPane.DEFAULT_OPTION);

				if (password.equals("****")) {
					Principal2 principal2 = new Principal2();
					principal2.setVisible(true);
					frame.setVisible(false);
				}

				else
					System.out.println("incorrecto");
			}
		});
		button.setBounds(399, 652, 117, 29);
		button.setForeground(new java.awt.Color(65, 14, 51));

		panel.add(button);
		retirarPanel.setVisible(false);

	}

	public void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(526, 713);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	}
}
