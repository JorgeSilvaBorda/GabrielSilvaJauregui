package model;

public class Vendedor {

	private int id;
	private String rut;
	private String nombres;
	private String apPaterno;
	private String apMaterno;
	private String password;

	public Vendedor() {
	}

	public Vendedor(int id, String rut, String nombres, String apPaterno, String apMaterno, String password) {
		this.id = id;
		this.rut = rut;
		this.nombres = nombres;
		this.apPaterno = apPaterno;
		this.apMaterno = apMaterno;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApPaterno() {
		return apPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	public String getApMaterno() {
		return apMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
