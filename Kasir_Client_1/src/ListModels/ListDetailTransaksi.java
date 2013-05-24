package ListModels;

public class ListDetailTransaksi {
	private String kodebarang,namabarang,harga,jumlah;
	public void SetKodeBarang(String kodebarang){
		this.kodebarang = kodebarang;
	}
	public String GetKodeBarang(){
		return this.kodebarang;
	}
	public void SetNamaBarang(String namabarang){
		this.namabarang = namabarang;
	}
	public String GetNamaBarang(){
		return this.namabarang;
	}
	public void SetHarga(String harga){
		this.harga = harga;
	}
	public String GetHarga(){
		return this.harga;
	}
	public void SetJumlah(String jumlah){
		this.jumlah = jumlah;
	}
	public String GetJumlah(){
		return this.jumlah;
	}
}
