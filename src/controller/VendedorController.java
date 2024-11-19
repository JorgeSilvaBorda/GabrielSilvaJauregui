package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Vendedor;

public class VendedorController {
	
	//Existe el vendedor por rut
	public boolean existeVendedorRut(String rut){
		String query = "SELECT COUNT(*) CANTIDAD FROM VENDEDOR WHERE RUT = '" + rut + "'";
		Connection con = ConexionController.getConnection();
		int cantidad = 0;
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				cantidad = rs.getInt("CANTIDAD");
			}
			con.close();
			return cantidad > 0;
		}catch (Exception ex) {
			System.out.println("No se pudo validar si el rut de vendedor ya se encuentra registrado en Base de Datos.");
			System.out.println(ex);
			ex.printStackTrace();
			return false;
		}
	}
	
	//Crear Vendedor
	public void insertVendedor(Vendedor vendedor){
		String query = "INSERT INTO VENDEDOR(RUT, NOMBRES, APPATERNO, APMATERNO, PASSWORD)VALUES("
				+ "'" + vendedor.getRut() + "', "
				+ "'" + vendedor.getNombres() + "', "
				+ "'" + vendedor.getApPaterno() + "', "
				+ "'" + vendedor.getApMaterno() + "', "
				+ "'" + vendedor.getPassword() + "'"
				+ ")";
		
		Connection con = ConexionController.getConnection();
		try{
			Statement st = con.createStatement();
			st.execute(query);
			con.close();
		}catch (Exception ex) {
			System.out.println("No se pudo insertar el vendedor en Base de Datos.");
			System.out.println(ex);
			ex.printStackTrace();
		}
	}
	
	//Obtener un vendedor por rut
	public Vendedor getVendedorPorRut(String rut){
		String query = "SELECT ID, RUT, NOMBRES, APPATERNO, APMATERNO, PASSWORD FROM VENDEDOR WHERE RUT = '" + rut + "'";
		Connection con = ConexionController.getConnection();
		Vendedor vendedor = null;
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				vendedor.setId(rs.getInt("ID"));
				vendedor.setRut(rs.getString("RUT"));
				vendedor.setNombres(rs.getString("NOMBRES"));
				vendedor.setApPaterno(rs.getString("APPATERNO"));
				vendedor.setApMaterno(rs.getString("APMATERNO"));
				vendedor.setPassword(rs.getString("PASSWORD"));
			}
			return vendedor;
		}catch (Exception ex) {
			System.out.println("No se pudo obtener el vendedor por Rut desde las base de datos.");
			System.out.println(ex);
			ex.printStackTrace();
		}
		return vendedor;
	}
	
	//Obtener un vendedor por ID
	public Vendedor getVendedorPorID(int id){
		String query = "SELECT ID, RUT, NOMBRES, APPATERNO, APMATERNO, PASSWORD FROM VENDEDOR WHERE ID = " + Integer.toString(id);
		Connection con = ConexionController.getConnection();
		Vendedor vendedor = null;
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				vendedor.setId(rs.getInt("ID"));
				vendedor.setRut(rs.getString("RUT"));
				vendedor.setNombres(rs.getString("NOMBRES"));
				vendedor.setApPaterno(rs.getString("APPATERNO"));
				vendedor.setApMaterno(rs.getString("APMATERNO"));
				vendedor.setPassword(rs.getString("PASSWORD"));
			}
			con.close();
			return vendedor;
		}catch (Exception ex) {
			System.out.println("No se pudo obtener el vendedor por Rut desde las base de datos.");
			System.out.println(ex);
			ex.printStackTrace();
		}
		return vendedor;
	}
	
	//Obtener listado de vendedores
	public List<Vendedor> getVendedores(){
		String query = "SELECT ID, RUT, NOMBRES, APPATERNO, APMATERNO, PASSWORD FROM VENDEDOR";
		Connection con = ConexionController.getConnection();
		List<Vendedor> vendedores = new ArrayList();
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				Vendedor v = new Vendedor();
				v.setId(rs.getInt("ID"));
				v.setRut(rs.getString("RUT"));
				v.setNombres(rs.getString("NOMBRES"));
				v.setApPaterno(rs.getString("APPATERNO"));
				v.setApMaterno(rs.getString("APMMATERNO"));
				v.setPassword(rs.getString("PASSWORD"));
				vendedores.add(v);
			}
			
			con.close();
			
		}catch (Exception ex) {
			System.out.println("No se pudo obtener el listado de vendedores.");
			System.out.println(ex);
			ex.printStackTrace();
		}
		
		return vendedores;
	}
	
	//Editar un vendedor conociendo su ID
	public void editarVendedorID(Vendedor vendedor){
		String query = "UPDATE VENDEDOR SET"
				+ "RUT = '" + vendedor.getRut() + "', "
				+ "NOMBRES = '" + vendedor.getNombres() + "', "
				+ "APPTERNO = '" + vendedor.getApPaterno() + "', "
				+ "APMATERNO = '" + vendedor.getApMaterno() + "', "
				+ "PASSWORD = '" + vendedor.getPassword() + "' "
				+ "WHERE ID = " + Integer.toString(vendedor.getId());
		Connection con = ConexionController.getConnection();
		try{
			Statement st = con.createStatement();
			st.executeUpdate(query);
		}catch (Exception ex) {
			System.out.println("No se pudo actualizar al vendedor por ID.");
			System.out.println(ex);
			ex.printStackTrace();
		}
	}
	
}
