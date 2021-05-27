public class Servidor {
	
	private double  t_ocioso;
	private double  t_ocupado;
	private int     c_atendidos;
	private boolean ocupado;
	
	public Servidor() {
		this.t_ocioso    = 0;
		this.t_ocupado   = 0;
		this.c_atendidos = 0;
		this.ocupado     = false;
	}
	
	public double getT_ocioso() {
		return t_ocioso;
	}
	public void setT_ocioso(double t_ocioso) {
		this.t_ocioso += t_ocioso;
	}
	public double getT_ocupado() {
		return t_ocupado;
	}
	public void setT_ocupado(double t_ocupado) {
		this.t_ocupado += t_ocupado;
	}
	public int getC_atendidos() {
		return c_atendidos;
	}
	public void setC_atendidos(int c_atendidos) {
		this.c_atendidos += c_atendidos;
	}
	public boolean isOcupado() {
		return ocupado;
	}
	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

}
