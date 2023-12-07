package Asta_Application2;

import Asta_Application2.util.*;
import java.net.*;
import java.io.*;
import static java.lang.System.*;
import java.util.*;
import java.util.function.Predicate;

//MANCANZE ---> 

//aggiustare il bug che quando il client esce venga riporatato
//AGGIUSTARE VARI BUGS CHE SI PRESENTANO
//ELIMINARE COSE INUTILI

public class Server {

	public List<Socket> connections = new ArrayList<>();//si puo' levare anche questa
	public List<Socket> session_guns = new ArrayList<>();
	public List<Socket> session_articoli = new ArrayList<>();
	public List<ARTICOLO> articoli;// array di armi
	public List<Gun> guns;// array di armi
	Timer timer_gun;
	final long DELAY = 10000;
	Timer timer_articolo;
	public ARTICOLO articolo_in_vendita;
	public Gun gun_in_vendita;
	public int prezzo_articolo_in_vendita;
	public int prezzo_gun_in_vendita;
	public ServerSocket server;
	int offerta_base;
	int port;

	public Server(int port) {
		this.port = port;
		this.articoli = new ArrayList<>(Arrays.asList(ARTICOLO.values()));
		this.guns = new ArrayList<>(Arrays.asList(Gun.values()));
		try {
			server = new ServerSocket(port);
			out.println("-------- SERVER IN ESECUZIONE NELLA PORTA " + this.port + " -------");
			GetNewArticoloInVendita();
			GetNewGunInVendita();
			this.timer_articolo = new Timer();
			this.timer_gun = new Timer();
			
		} catch (Exception e) {
			err.println("ERRORE NELLA CREAZIONE DEL SERVER NELLA PORTA " + this.port);
		}
	}

	public void acceptConnectionAndTaskHandler() {
		try {
			Socket client = server.accept();
			out.println("NUOVO CLIENT IN CONNESSIONE !");
			new Thread(new ClientHandler(client, this)).start();
		} catch (IOException e) {
			err.println("ERRORE NELLA CONNESSIONE CON CLIENT");
		}
	}

	public synchronized boolean UpdatePrice(int proposta, boolean isPriceGun) {

		boolean state = false;

		if (isPriceGun) {
			if (proposta > this.prezzo_gun_in_vendita) {
				this.prezzo_gun_in_vendita = proposta;
				state = true;
			}
		} else {
			if (proposta > this.prezzo_articolo_in_vendita) {
				this.prezzo_articolo_in_vendita = proposta;
				state = true;
			}
		}

		return state;

	}

	public synchronized void GetNewArticoloInVendita() {
		if(articoli.size() > 0) {
			this.articolo_in_vendita = this.articoli.get(new Random().nextInt(articoli.size()));
			this.prezzo_articolo_in_vendita = 0;
			out.println(this.articolo_in_vendita.name() + " IN VENDITA");
		}
	}

	public synchronized void GetNewGunInVendita() {
		if(guns.size() > 0) {
			this.gun_in_vendita = this.guns.get(new Random().nextInt(guns.size()));
			this.prezzo_gun_in_vendita = 0;
			out.println(this.gun_in_vendita.name() + " IN VENDITA");
		}
		
	}

	public synchronized void RemoveArticoloFromList() {
		this.articoli.remove(articoli.indexOf(this.articolo_in_vendita));
		out.println("VENDUTO " + this.articolo_in_vendita.name() + "\n" + articoli);
		GetNewArticoloInVendita();
	}

	public synchronized void RemoveGunFromList() {
		this.guns.remove(guns.indexOf(this.gun_in_vendita));
		out.println("VENDUTO " + this.gun_in_vendita.name() + "\n" + guns);
		GetNewGunInVendita();
	}

	public static void main(String[] args) throws Exception {

		Server server = new Server(9095);
		while (true) {
			server.acceptConnectionAndTaskHandler();
		}

	}

}
