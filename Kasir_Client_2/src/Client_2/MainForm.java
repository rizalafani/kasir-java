package Client_2;

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

import javax.swing.JComboBox;

import ListModels.ListBarang;
import TabelModels.BarangModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JSeparator;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private ListBarang barang;
	private List<ListBarang> list;
	private TableModel model;
	private JTable table;
	private ManipulasiData m_barang;
	private int result;
	public MainForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					SetModel(Connector.GetConnection().GetDataBarang());
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
		
		JMenu Menu = new JMenu("Menu");
		menuBar.add(Menu);
		
		JMenuItem mntmTransaksi = new JMenuItem("Tambah Barang");
		mntmTransaksi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_barang = new ManipulasiData(GetThis(),true);
				m_barang.main = GetThis();
				m_barang.setVisible(true);
			}
		});
		Menu.add(mntmTransaksi);
		
		JSeparator separator = new JSeparator();
		Menu.add(separator);
		
		JMenuItem mntmAboutMe = new JMenuItem("About Me");
		mntmAboutMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JOptionPane.showMessageDialog(null, "Aplikasi Kasir (Client Server)\nOleh : Ahmad Rizal Afani\nhttps://calonpresident.blogspot.com","Informasi",JOptionPane.INFORMATION_MESSAGE);
				}catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		Menu.add(mntmAboutMe);
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
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 48, 763, 364);
		panel.add(scrollPane);
		
		JButton btnBarang = new JButton("Hapus");
		btnBarang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					if(table.getSelectedRow() >= 0){
						if(JOptionPane.showConfirmDialog(null, "yakin hapus "+String.valueOf(table.getValueAt(table.getSelectedRow(), 1))+" ?","Warning",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
							result = Connector.GetConnection().DeleteBarang(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
							if(result > 0){
								JOptionPane.showMessageDialog(null, "delete data sukses !");
								SetModel(Connector.GetConnection().GetDataBarang());
							}else{
								JOptionPane.showMessageDialog(null, "delete data gagal !");
							}
						}
					}else{
						JOptionPane.showMessageDialog(null, "Pilih dulu bro !!");
					}
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.toString());
				}				
			}
		});
		btnBarang.setBounds(694, 14, 79, 23);
		panel.add(btnBarang);
		
		JButton btnSimpan = new JButton("Edit");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(table.getSelectedRow() >= 0){
						m_barang = new ManipulasiData(GetThis(), true);
						m_barang.status = "lama";
						m_barang.main = GetThis();
						m_barang.kode = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
						m_barang.setVisible(true);
					}else{
						JOptionPane.showMessageDialog(null, "Pilih dulu bro !!");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		btnSimpan.setBounds(605, 14, 79, 23);
		panel.add(btnSimpan);
		setLocationRelativeTo(null);
	}
	
	private MainForm GetThis(){
		return this;
	}
	
	public void SetModel(ResultSet hasil){
		try {
			list = new ArrayList<ListBarang>();
			while(hasil.next()){
				barang = new ListBarang();
				barang.SetKodeBarang(hasil.getString("kode_barang"));
				barang.SetNamaBarang(hasil.getString("nama_barang"));
				barang.SetStok(hasil.getString("stok"));
				barang.SetHarga(hasil.getString("harga"));
				list.add(barang);
			}
			model = new BarangModel(list);
			table.setModel(model);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
