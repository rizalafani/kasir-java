package Client_1;

import java.sql.*;

import javax.jws.soap.SOAPBinding.Use;

public class Connector {
	public Connector() {
		try{
			url = "jdbc:mysql://localhost:3306/kasir";
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url,"root","");				
		}catch (SQLException e) {
			System.out.println(e);
		}catch (ClassNotFoundException e) {
			System.out.println(e);
		}
	}
	
	private static Connector c_connection = null;
	private Connection connection;
	private ResultSet hasil;
	private Statement stat;
	private String url;	
	
	/* core eksekusi sql */
	private ResultSet GetData(String query){
		try{
			stat = connection.createStatement();
			return stat.executeQuery(query);
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	private int Manipulasidata(String query){
		try {
			stat = connection.createStatement();
			return stat.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	/*pake singleton pattern buat hemat memory*/
	public static Connector GetConnection(){
		try{
			if(c_connection == null){
				c_connection = new Connector();
			}
			return c_connection;
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/* function query */	
	public ResultSet GetUserLogin(String username,String password){
		return GetData("select * from userapp where username = '"+username+"' and password = '"+password+"' and status = 'kasir';");
	}
	
	public ResultSet GetDataBarang(){
		return GetData("select * from barang;");
	}
	
	public ResultSet GetDataBarang(String where){
		return GetData("select * from barang "+where+";");
	}
	
	public int InsertPembelian(String username){
		return Manipulasidata("insert into pembelian(kode_user,tanggal) values ('"+username+"',now());");
	}
	
	public int InsertDetailPembelian(String kode_beli,String kode_barang,String harga,String jumlah){
		return Manipulasidata("insert into pembelian_detail values ('"+kode_beli+"','"+kode_barang+"','"+harga+"','"+jumlah+"');");
	}
	
	public ResultSet GetLastPembelian(){
		return GetData("select * from pembelian order by kode_pembelian desc limit 1;");
	}
	
	public ResultSet GetHistoryTransaksi(String tanggal){
		return GetData("select * from pembelian inner join userapp on pembelian.kode_user=userapp.kode_user where SUBSTRING(tanggal,1,10) = '"+tanggal+"';");
	}
	
	public int UpdateStok(String kode,String stok){
		return Manipulasidata("update barang set stok='"+stok+"' where kode_barang = '"+kode+"';");
	}
	
	public ResultSet GetHistoryTransaksiDetail(String kode){
		return GetData("select pembelian_detail.kode_pembelian,pembelian_detail.kode_barang,barang.nama_barang,pembelian_detail.harga," +
				"pembelian_detail.jumlah from pembelian_detail inner join barang on barang.kode_barang=pembelian_detail.kode_barang " +
				"where pembelian_detail.kode_pembelian = '"+kode+"';");
	}
}
