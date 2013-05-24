package Client_2;

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
		return GetData("select * from userapp where username = '"+username+"' and password = '"+password+"' and status = 'gudang';");
	}
	
	public ResultSet GetDataBarang(){
		return GetData("select * from barang order by stok;");
	}
	
	public ResultSet GetDataBarang(String where){
		return GetData("select * from barang "+where+";");
	}
	
	public int InsertBarang(String nama,String harga,String stok){
		return Manipulasidata("insert into barang (nama_barang,harga,stok) values ('"+nama+"','"+harga+"','"+stok+"');");
	}
	
	public int UpdateBarang(String kode,String nama,String harga,String stok){
		return Manipulasidata("update barang set nama_barang='"+nama+"', harga='"+harga+"',stok='"+stok+"' where kode_barang = '"+kode+"';");
	}
	
	public int DeleteBarang(String kode){
		return Manipulasidata("delete from barang where kode_barang = '"+kode+"'");
	}
}
