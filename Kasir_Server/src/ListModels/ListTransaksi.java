package ListModels;

public class ListTransaksi {
	private String kode_beli,tanggal,kasir;
	public void SetKode(String kode_beli){
		this.kode_beli = kode_beli;
	}
	public String GetKode(){
		return this.kode_beli;
	}
	public void SetTanggal(String tanggal){
		this.tanggal = tanggal;
	}
	public String GetTanggal(){
		return this.tanggal;
	}
	public void SetKasir(String kasir){
		this.kasir = kasir;
	}
	public String GetKasir(){
		return this.kasir;			
	}
}
