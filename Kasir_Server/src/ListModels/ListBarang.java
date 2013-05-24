package ListModels;

public class ListBarang {
	private String kode_barang,nama_barang,harga,stok;
	public void SetKodeBarang(String kode_barang){
		this.kode_barang = kode_barang;
	}
	public String GetKodeBarang(){
		return this.kode_barang;
	}
	public void SetNamaBarang(String nama_barang){
		this.nama_barang = nama_barang;
	}
	public String GetNamabarang(){
		return this.nama_barang;
	}
	public void SetHarga(String harga){
		this.harga = harga;
	}
	public String GetHarga(){
		return this.harga;
	}
	public void SetStok(String stok){
		this.stok = stok;
	}
	public String GetStok(){
		return this.stok;
	}
}
