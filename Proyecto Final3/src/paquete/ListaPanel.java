package paquete;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.mysql.cj.xdevapi.Statement;
import com.toedter.calendar.JDateChooser;

import Conexion.Conexion;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import javax.swing.JButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ListaPanel extends JPanel implements Vehiculo {
	private JTextField txtPlaca;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public ListaPanel() {
		initComponets();

	}

	public void initComponets() {
		setBackground(new java.awt.Color(65, 14, 51));
		setBounds(0, 0, 905, 410);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Placa:");
		lblNewLabel.setForeground(UIManager.getColor("Button.highlight"));
		lblNewLabel.setFont(new Font("Avenir", Font.PLAIN, 25));
		lblNewLabel.setBounds(129, 48, 93, 26);
		add(lblNewLabel);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setForeground(UIManager.getColor("Button.highlight"));
		lblFecha.setFont(new Font("Avenir", Font.PLAIN, 25));
		lblFecha.setBounds(334, 48, 93, 26);
		add(lblFecha);

		txtPlaca = new JTextField();
		txtPlaca.setFont(new Font("Avenir", Font.PLAIN, 21));
		txtPlaca.setBounds(129, 83, 148, 33);
		add(txtPlaca);
		txtPlaca.setColumns(10);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(334, 80, 140, 39);
		dateChooser.setBackground(new java.awt.Color(65, 14, 51));
		add(dateChooser);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 165, 838, 202);
		add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Placa", "Propietario", "E-mail", "Tipo de Vehiculo",
																				"Hora Entrada", "Hora Salia", "Pago" }) {
});

		TableColumnModel columnModel = table.getColumnModel();

		columnModel.getColumn(0).setPreferredWidth(5);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Avenir", Font.PLAIN, 19));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String fecha = "";

				DefaultTableModel modelo = (DefaultTableModel) table.getModel();
				modelo.setRowCount(0);

				if (dateChooser.getDate() != null) {

					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

					java.util.Date date = new java.util.Date();
					java.util.Date sqlDate = new java.sql.Date(date.getTime());

					sqlDate = dateChooser.getDate();
					fecha = dateFormat.format(sqlDate);

				}

				try {

					/*Class.forName("com.mysql.cj.jdbc.Driver");
					java.sql.Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ITLA",
							"root", "BreakingBad3");
							
					java.sql.Statement statement = conexion.createStatement();*/
					
					Connection conexion = Conexion.getConnection1();
					java.sql.Statement statement = conexion.createStatement();

					String query = "SELECT * FROM vehiculos WHERE placa LIKE '%" + txtPlaca.getText()
							+ "%' AND horaentrada LIKE '" + fecha + "%' ";
					System.out.println(query);
					ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(query);

					resultSet.first();

					do {
						String horasalida = resultSet.getString(7);
						String pago = resultSet.getString(8);

						if (horasalida == null) {
							horasalida = "Sigue en Parking";
							pago = "0";
						} else {

							horasalida = resultSet.getString(7);
							pago = resultSet.getString(8);
						}
						String[] fila = { resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), horasalida,
								pago };

						modelo.addRow(fila);

					} while (resultSet.next());

				}

				catch (SQLException e1) {

					e1.printStackTrace();
					Logger.getLogger(ListaPanel.class.getName()).log(Level.SEVERE, null, e1);
				}

			}
		});
		btnBuscar.setBounds(680, 83, 130, 37);
		add(btnBuscar);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBounds(17, 155, 861, 223);
		add(btnNewButton);
		BorderFactory.createLineBorder(Color.black);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBounds(125, 77, 156, 47);
		add(btnNewButton_1);

		txtPlaca.setBorder(BorderFactory.createEmptyBorder());
	}

	@Override
	public void RegistrarVehiculo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RetirarVehiculo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void BuscarVehiculo() {
		// TODO Auto-generated method stub
		
	}
}
