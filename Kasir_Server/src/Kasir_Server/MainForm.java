package Kasir_Server;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.JComboBox;

import ListModels.ListBarang;
import ListModels.ListTransaksi;
import ListModels.ListUser;
import TabelModels.BarangModel;
import TabelModels.TransaksiModel;
import TabelModels.UserModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.DefaultComboBoxModel;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private JTable table_1;
	private JScrollPane jsp_1;
	private JTable table_2;
	private JScrollPane jsp_2;
	private JPanel panel_user;
	private JPanel panel_transaksi;
	private JPanel panel_barang;
	private JButton btnDetail_trans;
	private JButton btnSimpan;
	private JButton btnUbah;
	private JButton btnHapus;
	private JButton btnCari;
	private JButton btnBersih;
	private JTable table;
	private JTextField txt_username;
	private JTextField txt_password;
	private JTextField txt_nama;
	private int result;
	private JComboBox cmb_status;
	private ResultSet hasil;
	/*table component*/
	private ListBarang barang;
	private List<ListBarang> list_barang;
	private TableModel modelbarang;
	private ListTransaksi transaksi;
	private List<ListTransaksi> list_transaksi;
	private TableModel modeltransaksi;
	private ListUser user;
	private List<ListUser> list_user;
	private TableModel modeluser;
	/*forms*/
	TransaksiDetail trans_detail;
	BarangDetail barang_detail;
	private JMenuItem mntmAboutMe;
	public MainForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					SetModelTransaksi(Connector.GetConnection().GetTransaksi());
					SetModelBarang(Connector.GetConnection().GetDataBarang());
					SetModelUser(Connector.GetConnection().GetUser());
					btnBersih.setEnabled(false);
					btnUbah.setEnabled(false);
					btnHapus.setEnabled(false);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		setTitle("Main Form");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 809, 494);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		mntmAboutMe = new JMenuItem("About Me");
		mntmAboutMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JOptionPane.showMessageDialog(null, "Aplikasi Kasir (Client Server)\nOleh : Ahmad Rizal Afani\nhttps://calonpresident.blogspot.com","Informasi",JOptionPane.INFORMATION_MESSAGE);
				}catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		mnMenu.add(mntmAboutMe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 773, 455);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 11, 773, 429);
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		jsp_1 = new JScrollPane(table_1);
		jsp_1.setBounds(10, 5, 748, 354);
		panel_transaksi = new JPanel();
		panel_transaksi.setLayout(null);
		panel_transaksi.add(jsp_1);
		tabbedPane.add(panel_transaksi);
		
		btnDetail_trans = new JButton("Detail");
		btnDetail_trans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(table_1.getSelectedRow() >= 0){
						trans_detail = new TransaksiDetail(GetThis(),true);
						trans_detail.kode = table_1.getValueAt(table_1.getSelectedRow(), 0).toString();
						trans_detail.setVisible(true);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		btnDetail_trans.setBounds(669, 370, 89, 23);
		panel_transaksi.add(btnDetail_trans);
		tabbedPane.setTitleAt(0, "Transaksi");
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		jsp_2 = new JScrollPane(table_2);
		jsp_2.setBounds(10, 5, 748, 351);
		panel_barang = new JPanel();
		panel_barang.setLayout(null);
		panel_barang.add(jsp_2);
		tabbedPane.add(panel_barang);
		
		JButton btn_detail_barang = new JButton("Detail");
		btn_detail_barang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(table_2.getSelectedRow() >= 0){
						barang_detail = new BarangDetail(GetThis(),true);
						barang_detail.kode = table_2.getValueAt(table_2.getSelectedRow(), 0).toString();
						barang_detail.setVisible(true);
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}				
			}
		});
		btn_detail_barang.setBounds(669, 367, 89, 23);
		panel_barang.add(btn_detail_barang);
		tabbedPane.setTitleAt(1, "Barang");
		panel_user = new JPanel();		
		panel_user.setLayout(null);
		tabbedPane.add(panel_user);
		
		JScrollPane scrollPane = new JScrollPane((Component) null);
		scrollPane.setBounds(10, 171, 748, 230);
		panel_user.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(23, 25, 85, 14);
		panel_user.add(lblUsername);
		
		txt_username = new JTextField();
		txt_username.setBounds(130, 22, 139, 20);
		panel_user.add(txt_username);
		txt_username.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(23, 53, 85, 14);
		panel_user.add(lblPassword);
		
		txt_password = new JTextField();
		txt_password.setColumns(10);
		txt_password.setBounds(130, 50, 176, 20);
		panel_user.add(txt_password);
		
		JLabel lblNamaLengkap = new JLabel("Nama Lengkap");
		lblNamaLengkap.setBounds(23, 81, 85, 14);
		panel_user.add(lblNamaLengkap);
		
		txt_nama = new JTextField();
		txt_nama.setColumns(10);
		txt_nama.setBounds(130, 78, 271, 20);
		panel_user.add(txt_nama);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(23, 109, 85, 14);
		panel_user.add(lblStatus);
		
		cmb_status = new JComboBox();
		cmb_status.setModel(new DefaultComboBoxModel(new String[] {"Kasir", "Manager", "Gudang"}));
		cmb_status.setBounds(130, 106, 119, 20);
		panel_user.add(cmb_status);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(!txt_nama.getText().equals("")&&!txt_password.getText().equals("")&&!txt_username.getText().equals("")){
						hasil = Connector.GetConnection().GetUser(txt_username.getText());
						if(!hasil.last()){
							result = Connector.GetConnection().InsertUser(txt_username.getText(), txt_password.getText(), txt_nama.getText(), cmb_status.getSelectedItem().toString());
							if(result > 0){
								SetModelUser(Connector.GetConnection().GetUser());
								JOptionPane.showMessageDialog(null, "Sukses tambah User !");
								txt_nama.setText("");
								txt_password.setText("");
								txt_username.setText("");
								cmb_status.setSelectedIndex(0);
							}else{
								JOptionPane.showMessageDialog(null, "Gagal tambah User!");
							}
						}else{
							JOptionPane.showMessageDialog(null, "Username telah dipakai !");
						}						
					}else{
						JOptionPane.showMessageDialog(null, "Input gak lengkap !");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		btnSimpan.setBounds(130, 137, 89, 23);
		panel_user.add(btnSimpan);
		
		btnUbah = new JButton("Ubah");
		btnUbah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!txt_password.getText().equals("") && !txt_nama.getText().equals("")){
						result = Connector.GetConnection().UpdateUser(txt_username.getText(), txt_password.getText(), txt_nama.getText(), cmb_status.getSelectedItem().toString());
						if(result > 0){
							SetModelUser(Connector.GetConnection().GetUser());
							txt_nama.setText("");
							txt_password.setText("");
							txt_username.setText("");
							cmb_status.setSelectedIndex(0);
							txt_username.setEnabled(true);
							btnBersih.setEnabled(false);
							btnCari.setEnabled(true);
							btnSimpan.setEnabled(true);
							btnUbah.setEnabled(false);
							btnHapus.setEnabled(false);
							JOptionPane.showMessageDialog(null, "Update data sukses !");
						}else{
							JOptionPane.showMessageDialog(null, "Update data gagal!");
						}
					}else{
						JOptionPane.showMessageDialog(null, "Input gak lengkap !");
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		btnUbah.setBounds(229, 137, 89, 23);
		panel_user.add(btnUbah);
		
		btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(JOptionPane.showConfirmDialog(null, "Yakin hapus data ini ? ","Warning",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
						result = Connector.GetConnection().DeleteUser(txt_username.getText());
						if(result > 0){
							SetModelUser(Connector.GetConnection().GetUser());
							txt_nama.setText("");
							txt_password.setText("");
							txt_username.setText("");
							cmb_status.setSelectedIndex(0);
							txt_username.setEnabled(true);
							btnBersih.setEnabled(false);
							btnCari.setEnabled(true);
							btnSimpan.setEnabled(true);
							btnUbah.setEnabled(false);
							btnHapus.setEnabled(false);
							JOptionPane.showMessageDialog(null, "Delete data sukses !");
						}else{
							JOptionPane.showMessageDialog(null, "Delete data gagal !");
						}
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		btnHapus.setBounds(328, 137, 89, 23);
		panel_user.add(btnHapus);
		
		btnCari = new JButton("Cari");
		btnCari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(!txt_username.getText().equals("")){
						hasil = Connector.GetConnection().GetUser(txt_username.getText());
						if(hasil.last()){
							hasil.beforeFirst();
							txt_username.setEnabled(false);
							while (hasil.next()) {
								txt_nama.setText(hasil.getString("nama_asli"));
								txt_password.setText(hasil.getString("password"));
								cmb_status.setSelectedItem(hasil.getString("status"));
							}
							txt_username.setEnabled(false);
							btnBersih.setEnabled(true);
							btnCari.setEnabled(false);
							btnSimpan.setEnabled(false);
							btnUbah.setEnabled(true);
							btnHapus.setEnabled(true);
						}else{
							JOptionPane.showMessageDialog(null, "Data yang anda cari tidak ada !!");
						}
					}else{
						JOptionPane.showMessageDialog(null, "Masukkan dulu data yang anda cari !!");
					}					
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		});
		btnCari.setBounds(314, 21, 89, 23);
		panel_user.add(btnCari);
		
		btnBersih = new JButton("Bersih");
		btnBersih.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_nama.setText("");
				txt_password.setText("");
				txt_username.setText("");
				txt_username.setEnabled(true);
				btnBersih.setEnabled(false);
				btnCari.setEnabled(true);
				btnSimpan.setEnabled(true);
				btnUbah.setEnabled(false);
				btnHapus.setEnabled(false);
				cmb_status.setSelectedIndex(0);
			}
		});
		btnBersih.setBounds(316, 49, 89, 23);
		panel_user.add(btnBersih);
		tabbedPane.setTitleAt(2, "Pengguna");
		panel.add(tabbedPane);
		setLocationRelativeTo(null);
	}
	
	private MainForm GetThis(){
		return this;
	}
	
	private void SetModelTransaksi(ResultSet hasil){
		try {
			list_transaksi = new ArrayList<ListTransaksi>();
			while (hasil.next()) {	
				transaksi = new ListTransaksi();
				transaksi.SetKode(hasil.getString("kode_pembelian"));
				transaksi.SetKasir(hasil.getString("nama_asli"));
				transaksi.SetTanggal(hasil.getString("tanggal"));
				list_transaksi.add(transaksi);
			}
			modeltransaksi = new TransaksiModel(list_transaksi);
			table_1.setModel(modeltransaksi);
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	private void SetModelBarang(ResultSet hasil){
		try {
			list_barang = new ArrayList<ListBarang>();
			while (hasil.next()) {
				barang = new ListBarang();
				barang.SetKodeBarang(hasil.getString("kode_barang"));
				barang.SetNamaBarang(hasil.getString("nama_barang"));
				barang.SetHarga(hasil.getString("harga"));
				barang.SetStok(hasil.getString("stok"));
				list_barang.add(barang);
			}
			modelbarang = new BarangModel(list_barang);
			table_2.setModel(modelbarang);
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	private void SetModelUser(ResultSet hasil){
		try {
			list_user = new ArrayList<ListUser>();
			while (hasil.next()) {
				user = new ListUser();
				user.SetUsername(hasil.getString("username"));
				user.SetNamaAsli(hasil.getString("nama_asli"));
				user.SetPassword(hasil.getString("password"));
				user.SetStatus(hasil.getString("status"));
				list_user.add(user);
			}
			modeluser = new UserModel(list_user);
			table.setModel(modeluser);
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
}
