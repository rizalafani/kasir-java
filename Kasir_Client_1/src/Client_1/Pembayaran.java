package Client_1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Pembayaran extends JDialog {
	private JPanel contentPane;
	private JTable table;
	private JScrollPane jsp;
	private JLabel lblTotalBayar;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblBayar;
	private JTextField textField_2;
	private JLabel lblKembali;
	private JLabel lblTerbilang;
	private JLabel lblSekian;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	public TableModel model;
	public int total;
	public MainForm main = null;
	public Pembayaran(JFrame frame,Boolean modal) {
		super(frame,modal);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				table.setModel(model);
				textField.setText(String.valueOf(total));
				lblSekian.setText(angkaToTerbilang(Long.parseLong(textField.getText()))+" Rupiah");
				textField_1.getDocument().addDocumentListener(new DocumentListener() {					
					@Override
					public void removeUpdate(DocumentEvent e) {
						OnChangeTextFieldBayar(textField_1.getText());
					}
					
					@Override
					public void insertUpdate(DocumentEvent e) {
						OnChangeTextFieldBayar(textField_1.getText());
					}
					
					@Override
					public void changedUpdate(DocumentEvent e) {
						OnChangeTextFieldBayar(textField_1.getText());
					}
				});
				
				textField_1.requestFocus();
			}
		});
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 674, 367);
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
		
		lblTotalBayar = new JLabel("Total Bayar");
		lblTotalBayar.setBounds(10, 279, 71, 14);
		panel.add(lblTotalBayar);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(86, 276, 145, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					if(Integer.parseInt(textField.getText()) <= Integer.parseInt(textField_1.getText())){
						textField_2.setText(String.valueOf(Integer.parseInt(textField_1.getText()) - Integer.parseInt(textField.getText())));
						label_3.setText(angkaToTerbilang(Long.parseLong(textField_2.getText()))+" Rupiah");
						textField_1.setEditable(false);
						textField_2.setEditable(false);
						DefaultTableModel models = (DefaultTableModel)main.table.getModel();
						models.getDataVector().removeAllElements();
						models.fireTableDataChanged();						
					}else{
						JOptionPane.showMessageDialog(null, "pembayaran kurang dari total beli", "Warning", JOptionPane.WARNING_MESSAGE);
						textField_1.requestFocus();
					}
				}
			}
		});
		textField_1.setColumns(10);
		textField_1.setBounds(86, 304, 145, 20);
		panel.add(textField_1);
		
		lblBayar = new JLabel("Bayar");
		lblBayar.setBounds(10, 307, 71, 14);
		panel.add(lblBayar);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(86, 332, 145, 20);
		panel.add(textField_2);
		
		lblKembali = new JLabel("Kembali ");
		lblKembali.setBounds(10, 335, 71, 14);
		panel.add(lblKembali);
		
		lblTerbilang = new JLabel("Terbilang :");
		lblTerbilang.setBounds(249, 279, 78, 14);
		panel.add(lblTerbilang);
		
		lblSekian = new JLabel("Sekian");
		lblSekian.setBounds(337, 279, 327, 14);
		panel.add(lblSekian);
		
		label = new JLabel("Terbilang :");
		label.setBounds(249, 307, 78, 14);
		panel.add(label);
		
		label_1 = new JLabel("Sekian");
		label_1.setBounds(337, 307, 327, 14);
		panel.add(label_1);
		
		label_2 = new JLabel("Terbilang :");
		label_2.setBounds(249, 335, 78, 14);
		panel.add(label_2);
		
		label_3 = new JLabel("Sekian");
		label_3.setBounds(337, 335, 327, 14);
		panel.add(label_3);
		setLocationRelativeTo(null);
	}
	
	private void OnChangeTextFieldBayar(String angka){
		try {
			label_1.setText(angkaToTerbilang(Long.parseLong(angka))+" Rupiah");
		} catch (Exception e) {
			
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
