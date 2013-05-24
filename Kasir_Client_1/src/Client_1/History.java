package Client_1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;

import ListModels.ListTransaksi;
import TabelModels.TransaksiModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class History extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane jsp;
	private JButton btnDetail;
	private List<ListTransaksi> list;
	private ListTransaksi transaksi;
	private TableModel model;
	private DetailHistory detail_history;
	
	public History() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				SetModel(Connector.GetConnection().GetHistoryTransaksi(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			}
		});
		setTitle("Detail Transaksi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 645, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 614, 361);
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
		jsp.setBounds(10, 11, 599, 309);
		panel.add(jsp);
		contentPane.add(panel);
		
		btnDetail = new JButton("Detail");
		btnDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//JOptionPane.showMessageDialog(null, String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
					detail_history = new DetailHistory(GetThis(), true);
					detail_history.kode = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
					detail_history.setVisible(true);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		btnDetail.setBounds(520, 333, 89, 23);
		panel.add(btnDetail);
		setLocationRelativeTo(null);
	}

	private History GetThis(){
		return this;
	}
	
	private void SetModel(ResultSet hasil){
		try{
			list = new ArrayList<ListTransaksi>();
			while (hasil.next()) {
				transaksi = new ListTransaksi();
				transaksi.SetKode(hasil.getString("kode_pembelian"));
				transaksi.SetKasir(hasil.getString("nama_asli"));
				transaksi.SetTanggal(hasil.getString("tanggal"));
				list.add(transaksi);
			}
			model = new TransaksiModel(list);
			table.setModel(model);
		}catch (SQLException e) {
			System.out.println(e);
		}
	}
}
