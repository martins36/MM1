public class Evento implements Comparable<Evento>{

	private String tipo;
	private double t_ocurrencia;
	private double t_inicio;
	
	public Evento(String tipo, double t_ocurrencia) {
		this.tipo         = tipo;
		this.t_ocurrencia = t_ocurrencia;
	}
	
	public Evento(String tipo, double t_ocurrencia, double t_inicio) {
		this.tipo         = tipo;
		this.t_ocurrencia = t_ocurrencia;
		this.t_inicio	  = t_inicio;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public double getT_ocurrencia() {
		return t_ocurrencia;
	}
	public void setT_ocurrencia(double t_ocurrencia) {
		this.t_ocurrencia = t_ocurrencia;
	}
	
	public double getT_inicio() {
		return t_inicio;
	}

	public void setT_inicio(double t_inicio) {
		this.t_inicio = t_inicio;
	}

	@Override
	public int compareTo(Evento evento) {
		return Double.compare(this.t_ocurrencia, evento.t_ocurrencia);
    }
	
}
