


package Kasir_Server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BarangDetail extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	public String kode;
	private ResultSet hasil;
	public BarangDetail(JFrame frame,boolean modal) {
		super(frame,modal);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					hasil = Connector.GetConnection().GetDataBarang("where kode_barang = '"+kode+"'");
					while (hasil.next()) {
						textField.setText(hasil.getString("kode_barang"));
						textField_1.setText(hasil.getString("nama_barang"));
						textField_2.setText(hasil.getString("harga"));
						textField_3.setText(hasil.getString("stok"));
					}
				} catch (SQLException ex) {
					System.out.print(ex);
				}
			}
		});
		setBounds(100, 100, 486, 216);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblKodeBarang = new JLabel("Kode Barang");
			lblKodeBarang.setBounds(52, 35, 85, 14);
			contentPanel.add(lblKodeBarang);
		}
		{
			textField = new JTextField();
			textField.setEditable(false);
			textField.setBounds(147, 32, 69, 20);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			textField_1 = new JTextField();
			textField_1.setEditable(false);
			textField_1.setColumns(10);
			textField_1.setBounds(146, 60, 260, 20);
			contentPanel.add(textField_1);
		}
		{
			JLabel lblNamaBarang = new JLabel("Nama Barang");
			lblNamaBarang.setBounds(52, 63, 85, 14);
			contentPanel.add(lblNamaBarang);
		}
		{
			textField_2 = new JTextField();
			textField_2.setEditable(false);
			textField_2.setColumns(10);
			textField_2.setBounds(146, 86, 131, 20);
			contentPanel.add(textField_2);
		}
		{
			JLabel lblHarga = new JLabel("Harga");
			lblHarga.setBounds(52, 89, 85, 14);
			contentPanel.add(lblHarga);
		}
		{
			textField_3 = new JTextField();
			textField_3.setEditable(false);
			textField_3.setColumns(10);
			textField_3.setBounds(146, 112, 91, 20);
			contentPanel.add(textField_3);
		}
		{
			JLabel lblStok = new JLabel("Stok");
			lblStok.setBounds(52, 115, 85, 14);
			contentPanel.add(lblStok);
		}
		
		setLocationRelativeTo(null);
	}

}
