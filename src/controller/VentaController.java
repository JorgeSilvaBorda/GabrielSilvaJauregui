package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Producto;
import model.Vendedor;
import model.Venta;
import model.VentaProducto;

public class VentaController {

	//Insertar una venta
	public Venta insertarVenta(Venta venta) {
		//Primero insertar los datos de la venta
		Connection con = ConexionController.getConnection();
		int insertId = 0;
		try {
			//Obtenemos la fecha de la venta en objeto Date
			Date fechaVenta = java.sql.Date.valueOf(venta.getFecha());
			String sqlInsertVenta = "INSERT INTO VENTA(FECHA, IDVENDEDOR, TOTAL) VALUES(?, ?, ?)";

			//Trabajamos con un PreparedStatement en lugar de un Statement, para poder obtener el ID de INSERT de la venta.
			PreparedStatement st = con.prepareStatement(sqlInsertVenta, PreparedStatement.RETURN_GENERATED_KEYS);
			st.setDate(1, fechaVenta);
			st.setInt(2, venta.getVendedor().getId());
			st.setInt(3, venta.getTotal());

			int filasAfectadas = st.executeUpdate();

			if (filasAfectadas > 0) {
				ResultSet idGenerado = st.getGeneratedKeys();
				if (idGenerado.next()) {
					insertId = idGenerado.getInt(1);
				}
			}

			con.close();

			if (insertId > 0) {
				venta.setId(insertId);
				insertarDetalleVenta(venta);
				return venta;
			}
		} catch (Exception ex) {
			System.out.println("No se pudo insertar la boleta.");
			System.out.println(ex);
			ex.printStackTrace();
		}
		return null;
	}

	//Insertar el detalle de la venta
	private void insertarDetalleVenta(Venta venta) {
		List<VentaProducto> productosVenta = venta.getVentaProductos();
		for (int i = 0; i < productosVenta.size(); i++) {
			String queryDetalleVenta = "INSERT INTO VENTAPRODUCTO(IDVENTA, IDPRODUCTO, CANTIDAD, TOTAL) VALUES("
					+ venta.getId() + ", "
					+ productosVenta.get(i).getProducto().getId() + ", "
					+ productosVenta.get(i).getCantidad() + ", "
					+ productosVenta.get(i).getTotal() + ""
					+ ")";

			Connection con = ConexionController.getConnection();

			try {
				Statement st = con.createStatement();
				st.execute(queryDetalleVenta);
				con.close();
			} catch (Exception ex) {
				System.out.println("No se pudo insertar el detalle de la venta.");
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
	}

	//Obtener el detalle de una venta por ID de la venta
	public List<VentaProducto> getDetalleVentaID(int id) {
		String query = "SELECT\n"
				+ "	A.ID IDVENTA,\n"
				+ "	A.FECHA,\n"
				+ "	A.IDVENDEDOR,\n"
				+ "	A.TOTAL TOTALVENTA,\n"
				+ "	B.ID IDVENDEDOR,\n"
				+ "	B.NOMBRES,\n"
				+ "	B.RUT,\n"
				+ "	B.APPATERNO,\n"
				+ "	B.APMATERNO,\n"
				+ "	C.IDPRODUCTO,\n"
				+ "	C.CANTIDAD,\n"
				+ "	C.TOTAL SUBTOTAL,\n"
				+ "	D.NOMBRE,\n"
				+ "	D.PRECIO\n"
				+ "FROM\n"
				+ "	VENTA A INNER JOIN VENDEDOR B\n"
				+ "	ON A.IDVENDEDOR = B.ID INNER JOIN VENTAPRODUCTO C\n"
				+ "	ON A.ID = C.IDVENTA INNER JOIN PRODUCTO D\n"
				+ "	ON C.IDPRODUCTO  = D.ID "
				+ "WHERE A.ID = " + id;
		List<VentaProducto> ventasProductos = new ArrayList();
		Connection con = ConexionController.getConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				VentaProducto vp = new VentaProducto();
				vp.setIdVenta(rs.getInt("IDVENTA"));
				Producto p = new Producto();
				p.setId(rs.getInt("IDPRODUCTO"));
				p.setNombre(rs.getString("NOMBRE"));
				p.setPrecio(rs.getInt("PRECIO"));
				vp.setProducto(p);
				vp.setCantidad(rs.getInt("CANTIDAD"));
				vp.setTotal(rs.getInt("SUBTOTAL"));
				ventasProductos.add(vp);
			}
			con.close();
			return ventasProductos;
		} catch (Exception ex) {
			System.out.println("No se pudo obtener el detalle de una venta por ID.");
			System.out.println(ex);
			ex.printStackTrace();
		}

		return null;
	}

	//Obtener una venta por ID
	public Venta getVentaID(int id) {
		String query = "SELECT \n"
				+ "	A.ID IDVENTA,\n"
				+ "	A.FECHA,\n"
				+ "	A.IDVENDEDOR,\n"
				+ "	A.TOTAL TOTALVENTA,\n"
				+ "	B.RUT,\n"
				+ "	B.NOMBRES,\n"
				+ "	B.APPATERNO,\n"
				+ "	B.APMATERNO\n"
				+ "FROM\n"
				+ "	VENTA A INNER JOIN VENDEDOR B\n"
				+ "	ON A.IDVENDEDOR = B.ID "
				+ "WHERE A.ID = " + id;
		Venta venta = new Venta();
		Vendedor vendedor = new Vendedor();

		Connection con = ConexionController.getConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				venta.setId(rs.getInt("IDVENTA"));
				venta.setFecha(rs.getDate("FECHA").toLocalDate());
				venta.setTotal(rs.getInt("TOTALVENTA"));

				vendedor.setId(rs.getInt("IDVENDEEDOR"));
				vendedor.setRut(rs.getString("RUT"));
				vendedor.setNombres(rs.getString("NOMBRES"));
				vendedor.setApPaterno(rs.getString("APPATERNO"));
				vendedor.setApMaterno(rs.getString("APMATERNO"));

				venta.setVendedor(vendedor);
			}

			con.close();
			return venta;
		} catch (Exception ex) {
			System.out.println("No se pudo obtener la venta por ID.");
			System.out.println(ex);
			ex.printStackTrace();
		}

		return null;
	}

	//Obtener el listado de ventas
	public List<Venta> obtenerListadoVentas() {
		String query = "SELECT \n"
				+ "	A.ID IDVENTA,\n"
				+ "	A.FECHA,\n"
				+ "	A.IDVENDEDOR,\n"
				+ "	A.TOTAL TOTALVENTA,\n"
				+ "	B.RUT,\n"
				+ "	B.NOMBRES,\n"
				+ "	B.APPATERNO,\n"
				+ "	B.APMATERNO\n"
				+ "FROM\n"
				+ "	VENTA A INNER JOIN VENDEDOR B\n"
				+ "	ON A.IDVENDEDOR = B.ID";

		Connection con = ConexionController.getConnection();
		List<Venta> ventas = new ArrayList();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Venta venta = new Venta();
				Vendedor vendedor = new Vendedor();

				vendedor.setId(rs.getInt("IDVENDEDOR"));
				vendedor.setRut(rs.getString("RUT"));
				vendedor.setNombres(rs.getString("NOMBRES"));
				vendedor.setApPaterno(rs.getString("APPATERNO"));
				vendedor.setApMaterno(rs.getString("APMATERNO"));

				venta.setId(rs.getInt("IDVENTA"));
				venta.setFecha(rs.getDate("FECHA").toLocalDate());
				venta.setVendedor(vendedor);
				venta.setTotal(rs.getInt("TOTALVENTA"));

				ventas.add(venta);
			}

			con.close();
			return ventas;
		} catch (Exception ex) {
			System.out.println("No se pudo obtener el listado de ventas.");
			System.out.println(ex);
			ex.printStackTrace();
		}

		return ventas;
	}

	//Obtener el listado de ventas por ID Vendedor
	public List<Venta> obtenerListadoVentasIdVendedor(int idVendedor) {
		String query = "SELECT \n"
				+ "	A.ID IDVENTA,\n"
				+ "	A.FECHA,\n"
				+ "	A.IDVENDEDOR,\n"
				+ "	A.TOTAL TOTALVENTA,\n"
				+ "	B.RUT,\n"
				+ "	B.NOMBRES,\n"
				+ "	B.APPATERNO,\n"
				+ "	B.APMATERNO\n"
				+ "FROM\n"
				+ "	VENTA A INNER JOIN VENDEDOR B\n"
				+ "	ON A.IDVENDEDOR = B.ID "
				+ "WHERE B.ID = " + idVendedor;

		Connection con = ConexionController.getConnection();
		List<Venta> ventas = new ArrayList();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Venta venta = new Venta();
				Vendedor vendedor = new Vendedor();

				vendedor.setId(rs.getInt("IDVENDEDOR"));
				vendedor.setRut(rs.getString("RUT"));
				vendedor.setNombres(rs.getString("NOMBRES"));
				vendedor.setApPaterno(rs.getString("APPATERNO"));
				vendedor.setApMaterno(rs.getString("APMATERNO"));

				venta.setId(rs.getInt("IDVENTA"));
				venta.setFecha(rs.getDate("FECHA").toLocalDate());
				venta.setVendedor(vendedor);
				venta.setTotal(rs.getInt("TOTALVENTA"));

				ventas.add(venta);
			}

			con.close();
			return ventas;
		} catch (Exception ex) {
			System.out.println("No se pudo obtener el listado de ventas.");
			System.out.println(ex);
			ex.printStackTrace();
		}

		return ventas;
	}
}
