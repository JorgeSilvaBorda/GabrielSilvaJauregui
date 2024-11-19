package model;

public class VentaProducto {

	private int idVenta;
	private Producto producto;
	private int cantidad;
	private int total;

	public VentaProducto() {
	}

	public VentaProducto(int idVenta, Producto producto, int cantidad, int total) {
		this.idVenta = idVenta;
		this.producto = producto;
		this.cantidad = cantidad;
		this.total = total;
	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
