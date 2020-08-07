package paquete;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Conexion.Conexion;

public class RetirarPanel extends JPanel implements Vehiculo {

	JTextField txtPlacaR;
	RetirarPanel retirarPanel;


	public RetirarPanel() {

		setBackground(new java.awt.Color(65, 14, 51));

		setBounds(0, 0, 526, 690);
		
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Placa:");
		lblNewLabel.setForeground(UIManager.getColor("Button.highlight"));
		lblNewLabel.setFont(new java.awt.Font("Avenir", java.awt.Font.PLAIN, 40));
		lblNewLabel.setBounds(158, 190, 159, 46);
		add(lblNewLabel);

		JButton btnRetirar = new JButton("Retirar");
		btnRetirar.setFont(new java.awt.Font("Avenir", java.awt.Font.PLAIN, 30));
		btnRetirar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RetirarVehiculo();
				
			}

		});
		btnRetirar.setBounds(169, 501, 198, 73);
		add(btnRetirar);

		txtPlacaR = new JTextField();
		txtPlacaR.setFont(new java.awt.Font("Avenir", java.awt.Font.PLAIN, 20));
		txtPlacaR.setBounds(158, 248, 244, 46);
		add(txtPlacaR);
		txtPlacaR.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("<");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new java.awt.Font("Lucida Grande", java.awt.Font.PLAIN, 41));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		lblNewLabel_1.setIcon(null);
		lblNewLabel_1.setBounds(6, 6, 42, 40);
		add(lblNewLabel_1);
		
		JButton btnNewButton_1_1 = new JButton("");
		btnNewButton_1_1.setBounds(151, 245, 257, 53);
		add(btnNewButton_1_1);
		txtPlacaR.setBorder(BorderFactory.createEmptyBorder());
		btnRetirar.setForeground(new java.awt.Color(65, 14, 51));

	}

	

	@Override
	public void RegistrarVehiculo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RetirarVehiculo() {
		txtPlacaR.requestFocus();
		Double valorPagar = 0.0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		String fechaHora = dateFormat.format(sqlDate);

		try {
			

			Connection conexion = Conexion.getConnection1();
			java.sql.Statement stat = conexion.createStatement();

			String query = "SELECT horaentrada, tipovehiculo FROM vehiculos WHERE placa='" 
							+ txtPlacaR.getText() + "' AND estado='Disponible'";
			
			
			ResultSet rs = ((java.sql.Statement) stat).executeQuery(query);

			rs.first();
			
			
			String horaSalidaString = rs.getString(1);
			java.util.Date horaSalida = dateFormat.parse(horaSalidaString);

			int minutosACobrar = (int) (sqlDate.getTime() - horaSalida.getTime()) / 60000;

			System.out.println(minutosACobrar);

			if (rs.getString(2).equals("Automovil")) {
				valorPagar = minutosACobrar * 2.0;

			} else if (rs.getString(2).equals("Motocicleta")) {
				valorPagar = minutosACobrar * 1.0;
			}

			System.out.println("Valor a pagar por " + rs.getString(2) + " = " + valorPagar);

			
			stat.executeUpdate("UPDATE vehiculos SET horasalida = '" + fechaHora
					+ "' , estado='No Disponible' , valor='" + valorPagar + "' WHERE placa='"
					+ txtPlacaR.getText() + "' AND estado='Disponible'  ");

			
			int respuesta = JOptionPane.showConfirmDialog(null,
					"Valor a pagar: $" + valorPagar + "\n Desea Recibor un Recibo?", "Salida de Vehiculo",
					JOptionPane.YES_NO_OPTION);

			if (respuesta == JOptionPane.YES_OPTION) {

				try {
					Document document = new Document(PageSize.A6);
					try {
						PdfWriter.getInstance(document,
								new FileOutputStream(new File("/Users/Eban/Desktop/ejemplo.pdf")));
					} catch (FileNotFoundException fileNotFoundException) {
						System.out.println("No such file was found to generate the PDF "
								+ "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
					}

			
					
					java.sql.Statement statement = conexion.createStatement();
					ResultSet resultSet = statement
							.executeQuery("SELECT * FROM vehiculos WHERE placa='" + txtPlacaR.getText() + "'");

					
					if (resultSet.next()) {

						Chunk espacioChunk = new Chunk("  ");
						Paragraph espacioParagraph = new Paragraph(espacioChunk);

						document.open();
						Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f, Font.BOLD);
						Chunk tituloChunk = new Chunk("RECIBO DEL PARKING", f);
						Paragraph paraTitulo = new Paragraph(tituloChunk);
						document.add(paraTitulo);
						document.add(espacioParagraph);

						Chunk c5 = new Chunk("FECHA Y HORA: " + resultSet.getString("horasalida"));
						Paragraph p5 = new Paragraph(c5);
						document.add(p5);

						Chunk c = new Chunk("PLACA: " + resultSet.getString("placa"));
						Paragraph p = new Paragraph(c);
						document.add(p);

						Chunk c2 = new Chunk("PROPIETARIO: " + resultSet.getString("propietario"));
						Paragraph p2 = new Paragraph(c2);
						document.add(p2);

						Chunk c3 = new Chunk("VALOR PAGADO: " + resultSet.getString("valor"));
						Paragraph p3 = new Paragraph(c3);
						document.add(p3);

						Chunk c4 = new Chunk("TIPO VEHICULO: " + resultSet.getString("tipovehiculo"));
						Paragraph p4 = new Paragraph(c4);
						document.add(p4);

						document.add(espacioParagraph);
						document.add(espacioParagraph);
						document.add(espacioParagraph);
						document.add(espacioParagraph);
						document.add(espacioParagraph);

						document.add(new Chunk(
								"MUCHAS GRACIAS POR PREFERIRNOS " + resultSet.getString("propietario")));

						document.close();
						System.out.println("Your PDF file has been generated!(¡Se ha generado tu hoja PDF!");

					}

					conexion.close();

				} catch (DocumentException documentException) {
					System.out
							.println("The file not exists (Se ha producido un error al generar un documento): "
									+ documentException);
				}

			} else if (respuesta == JOptionPane.NO_OPTION) {
				txtPlacaR.setText("");
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "El vehiculo no se encuentra en el parking");
			Logger.getLogger(RetirarPanel.class.getName()).log(Level.SEVERE, null, ex);

		} catch (ParseException ex) {
			Logger.getLogger(RetirarPanel.class.getName()).log(Level.SEVERE, null, ex);
		}

		
	}

	@Override
	public void BuscarVehiculo() {
		// TODO Auto-generated method stub
		
	}
}
