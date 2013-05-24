package Client_1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;

import ListModels.ListBarang;
import TabelModels.BarangModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Barang extends JDialog {

	private JPanel contentPane;
	private ListBarang barang;
	private List<ListBarang> list;
	private TableModel model;
	private JTable table;
	private MainForm main;
	public Barang(JFrame frame, boolean modal) {
		super(frame, modal);
		main = (MainForm)frame;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				SetModel(Connector.GetConnection().GetDataBarang());
			}
		});
		setTitle("Form Barang");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 655, 368);
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
		scrollPane.setBounds(10, 11, 633, 322);
		panel.add(scrollPane);
		
		JButton btnPilih = new JButton("Pilih");
		btnPilih.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRowCount() > 0){
					Object obj = JOptionPane.showInputDialog("Beli berapa bro "+table.getValueAt(table.getSelectedRow(), 1)+" nya ? :D");
					if(obj != (null)){
						try {
							int jum = Integer.parseInt(obj.toString());
							if(jum > 0){
								ResultSet hasil_barang = Connector.GetConnection().GetDataBarang("where kode_barang = '"+table.getValueAt(table.getSelectedRow(), 0)+"';");	
								hasil_barang.last();
								if(Integer.parseInt(hasil_barang.getString("stok")) >= jum){
									DefaultTableModel model = (DefaultTableModel)main.table.getModel();
									model.addRow(
										new Object[]{
											table.getValueAt(table.getSelectedRow(), 0),
											table.getValueAt(table.getSelectedRow(), 1),
											table.getValueAt(table.getSelectedRow(), 2),
											table.getValueAt(table.getSelectedRow(), 3),
											jum
										});
									int i = Integer.parseInt(main.textField_1.getText()) + (Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)))*jum);
									main.textField_1.setText(String.valueOf(i));
									dispose();
								}else{
									JOptionPane.showMessageDialog(null, "barang yang dibeli stok nya kurang !!");
								}
							}else{
								JOptionPane.showMessageDialog(null, "gak boleh beli 0 !!");
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "input nya gak bener bro !!");
						}		
					}
				}else{
					JOptionPane.showMessageDialog(null, "pilih barang nya dulu bro !!");
				}		
			}
		});
		btnPilih.setBounds(556, 345, 89, 23);
		panel.add(btnPilih);
		setLocationRelativeTo(null);
	}
	
	private Barang GetThis(){
		return this;
	}
	
	private void SetModel(ResultSet hasil){
		try{
			list = new ArrayList<ListBarang>();
			while (hasil.next()) {
				barang = new ListBarang();
				barang.SetKodeBarang(hasil.getString("kode_barang"));
				barang.SetNamaBarang(hasil.getString("nama_barang"));
				barang.SetHarga(hasil.getString("harga"));
				barang.SetStok(hasil.getString("stok"));
				list.add(barang);
			}
			model = new BarangModel(list);
			table.setModel(model);
		}catch (SQLException e) {
			System.out.println(e);
		}
	}
}
