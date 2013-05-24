package TabelModels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ListModels.ListTransaksi;;

public class TransaksiModel extends AbstractTableModel{
	public TransaksiModel(List<ListTransaksi> list) {
		this.list = list;
	}
	private List<ListTransaksi> list;
	
	@Override
	public int getRowCount() {
		return list.size();
	};
	
	@Override
	public int getColumnCount() {
		return 3;
	};
	
	public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).GetKode();
            case 1:
                return list.get(rowIndex).GetKasir();
            case 2:
                return list.get(rowIndex).GetTanggal();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Kode Transaksi";
            case 1:
                return "Nama Kasir";
            case 2:
                return "Tanggal dan Jam";  
            default:
                return null;
        }
    }
}
