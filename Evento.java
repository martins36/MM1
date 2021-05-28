public class Evento implements Comparable<Evento>{

	private Eventos tipo;
	private double  tiempoOcurrencia;
	private double  tiempoInicio;

	public Evento(Eventos tipo, double tiempoOcurrencia) {
		this.tipo             = tipo;
		this.tiempoOcurrencia = tiempoOcurrencia;
	}

	public Evento(Eventos tipo, double tiempoOcurrencia, double tiempoInicio) {
		this.tipo             = tipo;
		this.tiempoOcurrencia = tiempoOcurrencia;
		this.tiempoInicio	  = tiempoInicio;
	}

	public Eventos getTipo() {
		return tipo;
	}
	public void setTipo(Eventos tipo) {
		this.tipo = tipo;
	}
	public double getTiempoOcurrencia() {
		return tiempoOcurrencia;
	}
	public void setTiempoOcurrencia(double tiempoOcurrencia) {
		this.tiempoOcurrencia = tiempoOcurrencia;
	}

	public double getTiempoInicio() {
		return tiempoInicio;
	}

	public void setTiempoInicio(double tiempoInicio) {
		this.tiempoInicio = tiempoInicio;
	}

	@Override
	public int compareTo(Evento evento) {
		return Double.compare(this.tiempoOcurrencia, evento.tiempoOcurrencia);
    }

}
