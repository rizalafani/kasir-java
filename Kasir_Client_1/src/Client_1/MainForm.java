package Client_1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.ScrollPaneConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MainForm extends JFrame {

	private JPanel contentPane;
	public Barang pesanan = null;
	private JTextField textField;
	private JLabel lblDate;
	public JTable table;
	private Pembayaran bayar;
	private Barang barang;
	private String username,kode_user;
	public JTextField textField_1;
	public MainForm(final String username,final String kode_user) {
		this.username = username;
		this.kode_user = kode_user;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				Date now = new Date();
				lblDate.setText("Date : "+now.getDate()+"/"+now.getMonth()+"/"+now.getYear());
			}
		});
		setTitle("Main Form");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 809, 494);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu Menu = new JMenu("Menu");
		menuBar.add(Menu);
		
		JMenuItem mntmTransaksi = new JMenuItem("Transaksi");
		mntmTransaksi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new History().setVisible(true);
			}
		});
		Menu.add(mntmTransaksi);
		
		JSeparator separator = new JSeparator();
		Menu.add(separator);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("About Me");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					JOptionPane.showMessageDialog(null, "Aplikasi Kasir (Client Server)\nOleh : Ahmad Rizal Afani\nhttps://calonpresident.blogspot.com","Informasi",JOptionPane.INFORMATION_MESSAGE);
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		Menu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 783, 423);
		contentPane.add(panel);
		panel.setLayout(null);
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Kode Barang", "Nama Barang", "Harga", "Stok", "Jumlah"
			}){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
	            return false;
	        }}
		);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 84, 763, 328);
		panel.add(scrollPane);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(25, 23, 32, 14);
		panel.add(lblLogin);
		
		textField = new JTextField();
		textField.setBounds(67, 20, 167, 20);
		textField.setText(username);
		textField.setEnabled(false);
		panel.add(textField);
		textField.setColumns(10);
		
		lblDate = new JLabel("Date : ");
		lblDate.setBounds(668, 23, 105, 14);
		panel.add(lblDate);
		
		JButton btnBarang = new JButton("Barang");
		btnBarang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					new Barang(GetThis(), true).setVisible(true);			
				}catch (Exception e) {
					System.out.println(e);
				}				
			}
		});
		btnBarang.setBounds(694, 50, 79, 23);
		panel.add(btnBarang);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(table.getRowCount() > 0){
						int result = Connector.GetConnection().InsertPembelian(kode_user);
						if(result == 1){
							ResultSet h = Connector.GetConnection().GetLastPembelian();
							h.last();
							int total = 0;
							for(byte i = 0; i<table.getRowCount(); i++){
								Connector.GetConnection().InsertDetailPembelian(h.getString("kode_pembelian"), String.valueOf(table.getValueAt(i, 0)),
										String.valueOf(table.getValueAt(i, 2)),String.valueOf(table.getValueAt(i, 4)));
								total += total+(Integer.parseInt(String.valueOf(table.getValueAt(i, 2)))*Integer.parseInt(String.valueOf(table.getValueAt(i, 4))));
								ResultSet hasil_stok = Connector.GetConnection().GetDataBarang("where kode_barang = '"+String.valueOf(table.getValueAt(i, 0))+"'");
								hasil_stok.last();
								int sisa = Integer.parseInt(hasil_stok.getString("stok"))-Integer.parseInt(String.valueOf(table.getValueAt(i, 4)));
								Connector.GetConnection().UpdateStok(String.valueOf(table.getValueAt(i, 0)), String.valueOf(sisa));
							}							
							bayar = new Pembayaran(GetThis(),true);
							bayar.model = table.getModel();
							bayar.total = total;
							bayar.main = GetThis();
							bayar.setVisible(true);
						}
					}else{
						JOptionPane.showMessageDialog(null, "Belum ada barang yang dibeli !","Informasi",JOptionPane.INFORMATION_MESSAGE);
					}
				}catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		btnSimpan.setBounds(536, 50, 79, 23);
		panel.add(btnSimpan);
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(table.getSelectedRow() >= 0){
						int i = Integer.parseInt(textField_1.getText()) - (Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)))*Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 4))));
						textField_1.setText(String.valueOf(i));
						DefaultTableModel d_model = (DefaultTableModel)table.getModel();
						d_model.removeRow(table.getSelectedRow());
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		btnHapus.setBounds(615, 50, 79, 23);
		panel.add(btnHapus);
		
		textField_1 = new JTextField();
		textField_1.setText("0");
		textField_1.setEnabled(false);
		textField_1.setColumns(10);
		textField_1.setBounds(67, 53, 167, 20);
		panel.add(textField_1);
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setBounds(25, 56, 32, 14);
		panel.add(lblTotal);
		setLocationRelativeTo(null);
	}
	
	private MainForm GetThis(){
		return this;
	}
}
