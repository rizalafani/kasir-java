package TabelModels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ListModels.ListBarang;

public class BarangModel extends AbstractTableModel {
	public BarangModel(List<ListBarang> list) {
		this.list = list;
	}
	private List<ListBarang> list;
	
	@Override
	public int getRowCount() {
		return list.size();
	};
	
	@Override
	public int getColumnCount() {
		return 4;
	};
	
	public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).GetKodeBarang();
            case 1:
                return list.get(rowIndex).GetNamabarang();
            case 2:
                return list.get(rowIndex).GetHarga();
            case 3:
                return list.get(rowIndex).GetStok();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Kode Barang";
            case 1:
                return "Nama Barang";
            case 2:
                return "Harga";
            case 3:
                return "Stok";   
            default:
                return null;
        }
    }
}
