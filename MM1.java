import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MM1 {

	private static String EVENTO_LLEGADA = "Llegada";
	private static String EVENTO_PARTIDA = "Partida";
	private static int    MEDIA_LLEGADA  = 5;
	private static int    MEDIA_SERVICIO = 4;
	
	private ArrayList<Evento> eventos;
	private Queue<Double>     cola;
	private Servidor          servidor;
	private Random 			  random;
	
	private double t_max_servicio;
	private double long_prom_cola;
	private double retraso_cola;
	private double utilizacion;
	private double reloj;
	
	public MM1(double t_max_servicio) {
		this.eventos  = new ArrayList<>();
		this.cola     = new LinkedList<>();
		this.servidor = new Servidor();
		this.random   = new Random();
		
		this.t_max_servicio = t_max_servicio;
		this.long_prom_cola = 0;
		this.retraso_cola   = 0;
		this.utilizacion	= 0;
		this.reloj 			= 0;
	}
	
	public void start() {
		Evento evento;
		String tipo_evento;
		
		// llega el primer cliente
		this.eventos.add(new Evento(EVENTO_LLEGADA, numeroAleatorio(MEDIA_LLEGADA)));
		
		// comienza la simulacion
		while (this.reloj < this.t_max_servicio) {
			
			// obtiene el evento mas proximo
			Collections.sort(this.eventos);
			
			evento      = this.eventos.get(0);
			tipo_evento = evento.getTipo();
			
			System.out.println(evento.getTipo() + " - " + evento.getT_ocurrencia() + " - " + evento.getT_inicio());
			
			if (tipo_evento.equals(EVENTO_LLEGADA)) {
				eventoArribo(evento);
			}
			else if(tipo_evento.equals(EVENTO_PARTIDA)) {
				eventoPartida(evento);
			}
			
			this.reloj = evento.getT_ocurrencia();
			this.eventos.remove(0);
			
		}
		
		// calcular estadisticas
		this.long_prom_cola /= this.reloj;
		this.retraso_cola   /= this.reloj;
		this.utilizacion     = this.servidor.getT_ocupado() / this.reloj;
		
		System.out.println("\nEventos que quedaron pendientes:");
		for (Evento event : this.eventos) {
			System.out.println(event.getTipo() + " - " + event.getT_ocurrencia() + " - " + event.getT_inicio());
		}
		System.out.println("\nReloj de simulacion: " + this.reloj);
		
	}
	
	public void eventoArribo(Evento evento) {
		
		Double t_llegada = evento.getT_ocurrencia();

		this.long_prom_cola += cola.size() * (t_llegada - this.reloj);
		
		// planificar el siguiente evento de arribo
		this.eventos.add(new Evento(EVENTO_LLEGADA, t_llegada + numeroAleatorio(MEDIA_LLEGADA)));
		
		if (servidor.isOcupado()) {
			this.cola.add(t_llegada);
		}
		else {
			
			// recolectar estadisticas
			this.servidor.setT_ocioso(t_llegada - this.reloj);
			
			this.servidor.setC_atendidos(1);
			this.servidor.setOcupado(true);
			
			// planificar salida para el cliente
			this.eventos.add(new Evento(EVENTO_PARTIDA, t_llegada + numeroAleatorio(MEDIA_SERVICIO), t_llegada));
			
		}
		
	}
	
	public void eventoPartida(Evento evento) {
		
		Double t_partida = evento.getT_ocurrencia();
		Double t_inicio  = evento.getT_inicio();
		
		this.servidor.setT_ocupado(t_partida - t_inicio);
		
		if (this.cola.isEmpty()) {
			this.servidor.setOcupado(false);
		}
		else {
			
			Double t_cola_inicio = cola.element();
			
			// recolectar estadisticas
			this.retraso_cola   += t_partida - t_cola_inicio;
			this.long_prom_cola += cola.size() * (t_partida - this.reloj);
			
			this.servidor.setC_atendidos(1);
			
			// planificar salida para el cliente
			this.eventos.add(new Evento(EVENTO_PARTIDA, t_partida + numeroAleatorio(MEDIA_SERVICIO), t_partida));
			
			// mover cada cliente un lugar hacia adelante
			this.cola.remove();
			
		}
		
	}
	
	public double numeroAleatorio(int media) {
		return - media * Math.log(1 - this.random.nextDouble());
	}
	
	public void mostrarEstadisticas() {
		System.out.println(
				"\n--------------- ESTADISTICAS ---------------\n" +
				"\nCant. clientes atendidos: " + this.servidor.getC_atendidos() +
				"\nCant. clientes en cola:   " + this.cola.size() +
				"\nLong. promedio de cola:   " + this.long_prom_cola +
				"\nRetraso en cola:          " + this.retraso_cola +
				"\n------------------------- " +
				"\nUtilizacion del srvidor:  " + this.utilizacion +
				"\nTiempo de servicio:       " + this.servidor.getT_ocupado() +
				"\nTiempo ocioso:            " + this.servidor.getT_ocioso()
				);
	}
	
	public static void main(String[] args) {
		
		MM1 mm1 = new MM1(480);
		
		mm1.start();
		mm1.mostrarEstadisticas();
		
	}
	
}
