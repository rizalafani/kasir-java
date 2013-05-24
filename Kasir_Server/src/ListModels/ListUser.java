package ListModels;

public class ListUser {
	private String username,password,nama_asli,status;
	public void SetUsername(String username){
		this.username = username;
	}
	public String GetUsername(){
		return this.username;
	}
	public void SetPassword(String password){
		this.password = password;
	}
	public String GetPassword(){
		return this.password;
	}
	public void SetNamaAsli(String nama_asli){
		this.nama_asli = nama_asli;
	}
	public String GetNamaAsli(){
		return this.nama_asli;
	}
	public void SetStatus(String status){
		this.status = status;
	}
	public String GetStatus(){
		return this.status;
	}
}
