package Client_2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

public class ManipulasiData extends JDialog {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	public String status = "baru",kode;
	public MainForm main;
	private int result;
	public ManipulasiData(JFrame frame,boolean modal) {
		super(frame,modal);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					if(!status.equals("baru")){
						ResultSet hasil = Connector.GetConnection().GetDataBarang("where kode_barang = '"+kode+"';");
						hasil.last();
						textField.setText(hasil.getString("nama_barang"));
						textField_1.setText(hasil.getString("Harga"));
						textField_2.setText(hasil.getString("Stok"));
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 403, 197);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNamaBarang = new JLabel("Nama Barang");
		lblNamaBarang.setBounds(34, 38, 97, 14);
		contentPane.add(lblNamaBarang);
		
		textField = new JTextField();
		textField.setBounds(131, 35, 188, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(131, 63, 119, 20);
		contentPane.add(textField_1);
		
		JLabel lblHarga = new JLabel("Harga");
		lblHarga.setBounds(34, 66, 97, 14);
		contentPane.add(lblHarga);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(131, 91, 97, 20);
		contentPane.add(textField_2);
		
		JLabel lblStok = new JLabel("Stok");
		lblStok.setBounds(34, 94, 97, 14);
		contentPane.add(lblStok);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(!textField.getText().equals("")&&!textField_1.getText().equals("")&&!textField_2.getText().equals("")){
						if(status.equals("baru")){
							result = Connector.GetConnection().InsertBarang(textField.getText(), textField_1.getText(), textField_2.getText());
						}else{
							result = Connector.GetConnection().UpdateBarang(kode,textField.getText(), textField_1.getText(), textField_2.getText());
						}
						main.SetModel(Connector.GetConnection().GetDataBarang());
						dispose();
					}else{
						JOptionPane.showMessageDialog(null, "data input gak lengkap !");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		btnSimpan.setBounds(131, 122, 89, 23);
		contentPane.add(btnSimpan);
		setLocationRelativeTo(null);
	}

}
