public class Servidor {

	private double  tiempoOcioso;
	private double  tiempoOcupado;
	private int     cantidadAtendidos;
	private boolean ocupado;

	public Servidor() {
		this.tiempoOcioso      = 0;
		this.tiempoOcupado     = 0;
		this.cantidadAtendidos = 0;
		this.ocupado           = false;
	}

	public double getTiempoOcioso() {
		return tiempoOcioso;
	}
	public void setTiempoOcioso(double tiempoOcioso) {
		this.tiempoOcioso += tiempoOcioso;
	}
	public double getTiempoOcupado() {
		return tiempoOcupado;
	}
	public void setTiempoOcupado(double tiempoOcupado) {
		this.tiempoOcupado += tiempoOcupado;
	}
	public int getCantidadAtendidos() {
		return cantidadAtendidos;
	}
	public void setCantidadAtendidos(int cantidadAtendidos) {
		this.cantidadAtendidos += cantidadAtendidos;
	}
	public boolean isOcupado() {
		return ocupado;
	}
	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

}
