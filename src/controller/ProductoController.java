package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Producto;

public class ProductoController {
	
	//Existe el producto por nombre
	public boolean existeProductoNombre(String nombre){
		String query = "SELECT COUNT(*) CANTIDAD FROM PRODUCTO WHERE NOMBRE = '" + nombre + "'";
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
			System.out.println("No se pudo verificar si el producto existe");
			System.out.println(ex);
			ex.printStackTrace();
		}
		
		return false;
	}
	
	//Crear producto
	public void insertProducto(Producto producto){
		String query = "INSERT INTO PRODUCTO(NOMBRE, PRECIO, CANTIDAD) VALUES("
				+ "'" + producto.getNombre() + "', "
				+ producto.getPrecio() + ", "
				+ producto.getCantidad() + ""
				+ ")";
		
		Connection con = ConexionController.getConnection();
		try{
			Statement st = con.createStatement();
			st.execute(query);
			con.close();
		}catch (Exception ex) {
			System.out.println("No se pudo insertar el producto.");
			System.out.println(ex);
			ex.printStackTrace();
		}
	}
	
	//Obtener un producto por ID
	public Producto getProductoID(int id){
		String query = "SELECT ID, NOMBRE, PRECIO, CANTIDAD FROM PRODUCTO";
		Connection con = ConexionController.getConnection();
		Producto producto = new Producto();
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				producto.setId(rs.getInt("ID"));
				producto.setNombre(rs.getString("NOMBRE"));
				producto.setPrecio(rs.getInt("PRECIO"));
				producto.setCantidad(rs.getInt("CANTIDAD"));
			}
			
			con.close();
		}catch (Exception ex) {
			System.out.println("No se pudo obtener el producto por ID");
			System.out.println(ex);
			ex.printStackTrace();
		}
		
		return producto;
	}
	
	//Obtener listado de productos
	public List<Producto> getListadoProductos(){
		String query = "SELECT ID, NOMBRE, PRECIO, CANTIDAD FROM PRODUCTO";
		Connection con = ConexionController.getConnection();
		List<Producto> productos = new ArrayList();
		
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				Producto producto = new Producto();
				producto.setId(rs.getInt("ID"));
				producto.setNombre(rs.getString("NOMBRE"));
				producto.setPrecio(rs.getInt("PRECIO"));
				producto.setCantidad(rs.getInt("CANTIDAD"));
				productos.add(producto);
			}
			con.close();
		}catch (Exception ex) {
			System.out.println("No se pudo obtener el listado de productos.");
			System.out.println(ex);
			ex.printStackTrace();
		}
		
		return productos;
	}
	
	//Editar un producto conociendo su ID
	public void editarProductoID(Producto producto){
		String query = "UPDATE PRODUCTO SET "
				+ "NOMBRE = '" + producto.getNombre() + "', "
				+ "PRECIO = " + producto.getPrecio() + ", "
				+ "CANTIDAD = " + producto.getCantidad() + " "
				+ "WHERE ID = " + producto.getId();
		
		Connection con = ConexionController.getConnection();
		
		try{
			Statement st = con.createStatement();
			st.execute(query);
			con.close();
		}catch (Exception ex) {
			System.out.println("No se pudo modificar el producto por ID.");
			System.out.println(ex);
			ex.printStackTrace();
		}
	}
	
	
}
