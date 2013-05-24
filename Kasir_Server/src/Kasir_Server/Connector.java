package Kasir_Server;

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
	public ResultSet GetUser(String username,String password){
		return GetData("select * from userapp where username = '"+username+"' and password = '"+password+"' and status = 'manager';");
	}
	
	public ResultSet GetUser(String username){
		return GetData("select * from userapp where username = '"+username+"';");
	}
	
	public ResultSet GetUser(){
		return GetData("select * from userapp;");
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
	
	public ResultSet GetTransaksi(){
		return GetData("select * from pembelian inner join userapp on pembelian.kode_user=userapp.kode_user;");
	}
	
	public int InsertUser(String username,String password,String nama,String status){
		return Manipulasidata("insert into userapp (username,password,nama_asli,status) values ('"+username+"','"+password+"','"+nama+"','"+status+"');");
	}
	
	public int UpdateUser(String username,String password,String nama,String status){
		return Manipulasidata("update userapp set password = '"+password+"', nama_asli = '"+nama+"', status = '"+status+"' where username = '"+username+"';");
	}
	
	public int DeleteUser(String username){
		return Manipulasidata("delete from userapp where username = '"+username+"';");
	}
	
	public ResultSet GetTransaksiDetail(String kode){
		return GetData("select pembelian_detail.kode_pembelian,pembelian_detail.kode_barang,barang.nama_barang,pembelian_detail.harga," +
				"pembelian_detail.jumlah from pembelian_detail inner join barang on barang.kode_barang=pembelian_detail.kode_barang " +
				"where pembelian_detail.kode_pembelian = '"+kode+"';");
	}
}
