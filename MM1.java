import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MM1 {

	private static int MEDIA_LLEGADA  = 5;
	private static int MEDIA_SERVICIO = 4;

	private ArrayList<Evento> eventos;
	private Queue<Double>     cola;
	private Servidor          servidor;
	private Random 	          random;

	private double tiempoMaxServicio;
	private double longPromCola;
	private double retrasoCola;
	private double utilizacion;
	private double reloj;

	public MM1(double tiempoMaxServicio) {
		this.eventos  = new ArrayList<>();
		this.cola     = new LinkedList<>();
		this.servidor = new Servidor();
		this.random   = new Random();

		this.tiempoMaxServicio = tiempoMaxServicio;
		this.longPromCola      = 0;
		this.retrasoCola       = 0;
		this.utilizacion       = 0;
		this.reloj 	           = 0;
	}

	public void start() {
		Evento  evento;
		Eventos tipoEvento;

		// llega el primer cliente
		this.eventos.add(new Evento(Eventos.LLEGADA, numeroAleatorio(MEDIA_LLEGADA)));

		// comienza la simulacion
		while (this.reloj < this.tiempoMaxServicio) {

			// obtiene el evento mas proximo
			Collections.sort(this.eventos);

			evento     = this.eventos.get(0);
			tipoEvento = evento.getTipo();

			System.out.println(evento.getTipo() + " - " + evento.getTiempoOcurrencia() + " - " + evento.getTiempoInicio());

			switch (tipoEvento) {
			case LLEGADA:
				eventoArribo(evento);
				break;

			case PARTIDA:
				eventoPartida(evento);
				break;
			}

			this.reloj = evento.getTiempoOcurrencia();
			this.eventos.remove(0);

		}

		// calcular estadisticas
		this.longPromCola /= this.reloj;
		this.retrasoCola  /= this.reloj;
		this.utilizacion   = this.servidor.getTiempoOcupado() / this.reloj;

		System.out.println("\nEventos que quedaron pendientes:");
		for (Evento event : this.eventos) {
			System.out.println(event.getTipo() + " - " + event.getTiempoOcurrencia() + " - " + event.getTiempoInicio());
		}
		System.out.println("\nReloj de simulacion: " + this.reloj);

	}

	public void eventoArribo(Evento evento) {

		Double tiempoLlegada = evento.getTiempoOcurrencia();

		this.longPromCola += this.cola.size() * (tiempoLlegada - this.reloj);

		// planificar el siguiente evento de arribo
		this.eventos.add(new Evento(Eventos.LLEGADA, tiempoLlegada + numeroAleatorio(MEDIA_LLEGADA)));

		if (this.servidor.isOcupado()) {
			this.cola.add(tiempoLlegada);
		}
		else {

			// recolectar estadisticas
			this.servidor.setTiempoOcioso(tiempoLlegada - this.reloj);

			this.servidor.setCantidadAtendidos(1);
			this.servidor.setOcupado(true);

			// planificar salida para el cliente
			this.eventos.add(new Evento(Eventos.PARTIDA, tiempoLlegada + numeroAleatorio(MEDIA_SERVICIO), tiempoLlegada));

		}

	}

	public void eventoPartida(Evento evento) {

		Double tiempoPartida = evento.getTiempoOcurrencia();
		Double tiempoIinicio = evento.getTiempoInicio();

		this.servidor.setTiempoOcupado(tiempoPartida - tiempoIinicio);

		if (this.cola.isEmpty()) {
			this.servidor.setOcupado(false);
		}
		else {

			Double tiempoColaInicio = this.cola.element();

			// recolectar estadisticas
			this.retrasoCola  += tiempoPartida - tiempoColaInicio;
			this.longPromCola += this.cola.size() * (tiempoPartida - this.reloj);

			this.servidor.setCantidadAtendidos(1);

			// planificar salida para el cliente
			this.eventos.add(new Evento(Eventos.PARTIDA, tiempoPartida + numeroAleatorio(MEDIA_SERVICIO), tiempoPartida));

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
				"\nCant. clientes atendidos: " + this.servidor.getCantidadAtendidos() +
				"\nCant. clientes en cola:   " + this.cola.size() +
				"\nLong. promedio de cola:   " + this.longPromCola +
				"\nRetraso en cola:          " + this.retrasoCola +
				"\n------------------------- " +
				"\nUtilizacion del srvidor:  " + this.utilizacion +
				"\nTiempo de servicio:       " + this.servidor.getTiempoOcupado() +
				"\nTiempo ocioso:            " + this.servidor.getTiempoOcioso()
				);
	}

}
