package Kasir_Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import ListModels.ListDetailTransaksi;
import TabelModels.DetailTransaksiModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDetail extends JDialog {
	private JPanel contentPane;
	private JTable table;
	private JScrollPane jsp;
	private JLabel lblTotalBayar;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblBayar;
	private JLabel lblTerbilang;
	private JLabel lblSekian;
	public String kode;
	private ListDetailTransaksi detail;
	private List<ListDetailTransaksi> list;
	private TableModel model;
	private ResultSet hasil;
	private int total = 0;
	public TransaksiDetail(JFrame frame,boolean modal) {
		super(frame,modal);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					textField_1.setText(kode);
					SetModel(Connector.GetConnection().GetTransaksiDetail(kode));
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 674, 342);
		panel.setLayout(null);
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		jsp = new JScrollPane(table);
		jsp.setBounds(10, 5, 654, 247);
		panel.add(jsp);
		contentPane.add(panel);
		
		lblTotalBayar = new JLabel("Total Bayar :");
		lblTotalBayar.setBounds(10, 279, 71, 14);
		panel.add(lblTotalBayar);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(86, 276, 145, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(86, 304, 145, 20);
		panel.add(textField_1);
		
		lblBayar = new JLabel("Kode Beli");
		lblBayar.setBounds(10, 307, 71, 14);
		panel.add(lblBayar);
		
		lblTerbilang = new JLabel("Terbilang :");
		lblTerbilang.setBounds(249, 279, 68, 14);
		panel.add(lblTerbilang);
		
		lblSekian = new JLabel("Sekian");
		lblSekian.setBounds(327, 279, 337, 14);
		panel.add(lblSekian);
		setLocationRelativeTo(null);
	}
	
	private void SetModel(ResultSet hasil){
		try {
			list = new ArrayList<ListDetailTransaksi>();
			total = 0;
			while (hasil.next()) {
				detail = new ListDetailTransaksi();
				detail.SetKodeBarang(hasil.getString("kode_barang"));
				detail.SetNamaBarang(hasil.getString("nama_barang"));
				detail.SetHarga(hasil.getString("harga"));
				detail.SetJumlah(hasil.getString("jumlah"));
				list.add(detail);
				total += (Integer.parseInt(hasil.getString("harga"))*Integer.parseInt(hasil.getString("jumlah")));
			}
			textField.setText(String.valueOf(total));
			lblSekian.setText(angkaToTerbilang(Long.parseLong(textField.getText()))+" Rupiah");
			model = new DetailTransaksiModel(list);
			table.setModel(model);
		}catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	private static String angkaToTerbilang(Long angka){
		String[] angkaTerbilang={"","Satu","Dua","Tiga","Empat","Lima","Enam","Tujuh","Delapan","Sembilan","Sepuluh","Sebelas"};
		if(angka < 12)
		return angkaTerbilang[angka.intValue()];
		if(angka >=12 && angka <= 19)
		return angkaTerbilang[angka.intValue() % 10] + " Belas";
		if(angka >= 20 && angka <= 99)
		return angkaToTerbilang(angka / 10) + " Puluh " + angkaTerbilang[angka.intValue() % 10];
		if(angka >= 100 && angka <= 199)
		return "Seratus " + angkaToTerbilang(angka % 100);
		if(angka >= 200 && angka <= 999)
		return angkaToTerbilang(angka / 100) + " Ratus " + angkaToTerbilang(angka % 100);
		if(angka >= 1000 && angka <= 1999)
		return "Seribu " + angkaToTerbilang(angka % 1000);
		if(angka >= 2000 && angka <= 999999)
		return angkaToTerbilang(angka / 1000) + " Ribu " + angkaToTerbilang(angka % 1000);
		if(angka >= 1000000 && angka <= 999999999)
		return angkaToTerbilang(angka / 1000000) + " Juta " + angkaToTerbilang(angka % 1000000);
		if(angka >= 1000000000 && angka <= 999999999999L)
		return angkaToTerbilang(angka / 1000000000) + " Milyar " + angkaToTerbilang(angka % 1000000000);
		if(angka >= 1000000000000L && angka <= 999999999999999L)
		return angkaToTerbilang(angka / 1000000000000L) + " Triliun " + angkaToTerbilang(angka % 1000000000000L);
		if(angka >= 1000000000000000L && angka <= 999999999999999999L)
		return angkaToTerbilang(angka / 1000000000000000L) + " Quadrilyun " + angkaToTerbilang(angka % 1000000000000000L);
		return "";
	}
}
