package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {

	private int id;
	private LocalDate fecha;
	private Vendedor vendedor;
	private List<VentaProducto> ventaProductos;
	private int total;

	public Venta() {
	}

	public Venta(int id, LocalDate fecha, Vendedor vendedor, List<VentaProducto> ventaProductos, int total) {
		this.id = id;
		this.fecha = fecha;
		this.vendedor = vendedor;
		this.ventaProductos = ventaProductos;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public List<VentaProducto> getVentaProductos() {
		return ventaProductos;
	}

	public void setVentaProductos(List<VentaProducto> ventaProductos) {
		this.ventaProductos = ventaProductos;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
