package TabelModels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ListModels.ListUser;

public class UserModel extends AbstractTableModel {
	public UserModel(List<ListUser> list) {
		this.list = list;
	}
	
	private List<ListUser> list;	

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
                return list.get(rowIndex).GetUsername();
            case 1:
                return list.get(rowIndex).GetNamaAsli();
            case 2:
                return list.get(rowIndex).GetPassword();
            case 3:
                return list.get(rowIndex).GetStatus();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Username";
            case 1:
                return "Nama Asli";
            case 2:
                return "Password";
            case 3:
                return "Status";  
            default:
                return null;
        }
    }
}
