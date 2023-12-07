package Asta_Application2;

import static java.lang.System.*;
import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.function.Predicate;

import Asta_Application2.util.*;
import Asta_Application2.util.MyConsumerInterface;
import Asta_Application2.util.TimerHandler;

public class ClientHandler implements Runnable {

	Server server_main;
	public Socket client;
	public String username;
	public PrintStream print;
	BufferedReader buffer;
	public int buget;
	public static MyConsumerInterface broadCastSessionGuns;
	public static MyConsumerInterface broadCastSessionArticoli;
    Predicate<String> test_IsInteger = (s) -> {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			print.println("INPUT NON VALIDO");
			return false;
		}
		return true;
	};

	public ClientHandler(Socket c, Server sm) {
		this.client = c;
		this.server_main = sm;
		
		//300
		try {
			print = new PrintStream(client.getOutputStream(), true);// auto flush = true
			buffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			server_main.connections.add(client);
			broadCastSessionGuns = (mess, client_m) -> {
				for (Socket client : server_main.session_guns) {

					if (!client.equals(client_m)) {
						try {
							PrintStream print = new PrintStream(client.getOutputStream(), true);
							print.println(mess);
						} catch (IOException e) {
						}
					}

				}
			};
			broadCastSessionArticoli = (mess, client_m) -> {
				for (Socket client : server_main.session_articoli) {

					if (!client.equals(client_m)) {
						try {
							PrintStream print = new PrintStream(client.getOutputStream(), true);
							print.println(mess);
						} catch (IOException e) {
						}
					}

				}
			};
		} catch (IOException e) {
			err.println("ERRORE APERTURA STREAMS CON CLIENT");
		}
	}

	@Override
	public void run() {

		try {
			String mess = buffer.readLine();
			this.username = mess.split("&")[0];
			this.buget = Integer.valueOf(mess.split("&")[1]);
			
		} catch (IOException e1) {
			err.println("ERRORE NELLA LETTURA USERNAME");
		}
		
		ShowOptions();
		
	}
	
	public void ShowOptions() {
		print.println("------MENU ASTE APERTE--------");
		print.println("1-GUNS ASTA");
		print.println("2-ARTICOLI ASTA");

		int choice = 0;
		
		while(!(choice == 1 || choice == 2)) {
			try {
				choice = MenuHandle();
			} catch (IOException e1) {
				err.println("ERRORE LETTURA IN MENU");
			}
		}
		
		if (choice == 1) {
			try {
				AstaGunsHandler();
			} catch (IOException e) {
				err.println("ERRORE DURANTE LA GESTIONE DELL'ASTA GUNS CON CLIENT");
			}
		} else if (choice == 2) {
			try {
				AstaArticoliHandler();
			} catch (Exception e) {
				err.println("ERRORE DURANTE LA GESTIONE DELL'ASTA GUNS CON CLIENT");
			}
		}

	}

	public int MenuHandle() throws IOException {
		int choice = 0;
		String m = buffer.readLine();
		if(this.test_IsInteger.test(m)) {
			choice = Integer.valueOf(m);
		}else {
			choice = 3; 
		}
		return choice;
	}

	public void AstaGunsHandler() throws IOException {

		server_main.session_guns.add(client);
		print.println("ARMA IN VENDITA ---> | " + server_main.gun_in_vendita + " |" + "prezzo : "
				+ server_main.prezzo_gun_in_vendita);
		broadCastSessionGuns.broadcast("\n----SESSION -> " + username + " SI E' UNITO ALL'ASTA -----", client);
		String mess = "";
		
		while ((!mess.equalsIgnoreCase("exit") || (test_IsInteger.test(mess)))) {
			
			if(server_main.guns.size() == 1 || server_main.guns.size() == 0) {
				try {
					Thread.sleep(server_main.DELAY+100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				print.println("\nARMI FINITE ARRIVEDERCI E GRAZIE PER AVER PARTECIPATO ! ");
				break;
			}
			mess = buffer.readLine();
			
			if(server_main.guns.size() == 0)
				continue;
			
			if (mess.equalsIgnoreCase("exit") || buget <= 0)
				break;
			
			while(mess.equalsIgnoreCase("info")) {
				ShowInfo(server_main.gun_in_vendita);
				mess = buffer.readLine();
			}
			
				while ((!test_IsInteger.test(mess)) ) {
					mess = buffer.readLine();
				}
			
			
				
				if (!server_main.UpdatePrice(Integer.valueOf(mess), true)) {
					print.println("$ no accepted $");
				} else {
					print.println("$ accepted $");
					broadCastSessionGuns.broadcast("@"+username + " :  HA PROPOSTO " + mess, client);
					server_main.timer_gun.cancel();
					TimerHandler<Gun> th = new TimerHandler<>(this, server_main.gun_in_vendita, server_main);
					server_main.timer_gun = new Timer();
					server_main.timer_gun.schedule(th, server_main.DELAY);

				}
		}
		
		String broad = "SESSION -> " + username + " E' USCITO DALLA SESSIONE!";
		broadCastSessionGuns.broadcast(broad, client);
		print.println("\n\n\n PREMI INVIO PER TORNARE AL MENU PRINCIPALE --> ");
		mess = buffer.readLine();
		
		out.println("CLIENT " + username + " USCITO");
		server_main.session_guns.remove(server_main.session_guns.indexOf(client));
		out.println("CLIENT IN SESSIONE GUN : " + server_main.session_guns);
		
		ShowOptions();


	}

	public void BroadCastAll(String mess) {
		for (Socket client : server_main.connections) {

			try {
				PrintStream print = new PrintStream(client.getOutputStream(), true);
				print.println(mess);
			} catch (IOException e) {
			}

		}
	}

	public void AstaArticoliHandler()throws Exception {
		
		
		server_main.session_articoli.add(client);
		print.println("ARTTICOLO IN VENDITA ---> | " + server_main.articolo_in_vendita + " |" + "prezzo : "
				+ server_main.prezzo_articolo_in_vendita);
		broadCastSessionArticoli.broadcast("\n----SESSION -> " + username + " SI E' UNITO ALL'ASTA -----", client);
		String mess = "";
		
		while ((!mess.equalsIgnoreCase("exit") || (test_IsInteger.test(mess)))) {
			
			if(server_main.articoli.size() == 1 || server_main.articoli.size() == 0) {
				try {
					Thread.sleep(server_main.DELAY+100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				print.println("\nARTICOLI FINITI ARRIVEDERCI E GRAZIE PER AVER PARTECIPATO ! ");
				break;
			}
			
			mess = buffer.readLine();
			if(server_main.articoli.size() == 0)
				continue;
			
			if (mess.equalsIgnoreCase("exit") || buget <= 0)
				break;
			
			while(mess.equalsIgnoreCase("info")) {
				ShowInfo(server_main.articolo_in_vendita);
				mess = buffer.readLine();
			}
			
				while ((!test_IsInteger.test(mess))) {
					mess = buffer.readLine();
				}
				if (!server_main.UpdatePrice(Integer.valueOf(mess), false)) {
					print.println("$ no accepted $");
				} else {
					print.println("$ accepted $");
					broadCastSessionArticoli.broadcast("@"+username + " :  HA PROPOSTO " + mess + "$", client);
					server_main.timer_articolo.cancel();
					TimerHandler<ARTICOLO> th = new TimerHandler<>(this, server_main.articolo_in_vendita, server_main);
					server_main.timer_articolo = new Timer();
					server_main.timer_articolo.schedule(th, server_main.DELAY);

				}
		}
		
		String broad = "SESSION -> " + username + " E' USCITO DALLA SESSIONE!";
		broadCastSessionArticoli.broadcast(broad, client);
		print.println("\n\n\n PREMI INVIO PER TORNARE AL MENU PRINCIPALE --> ");
		out.println("CLIENT " + username + " USCITO");
		server_main.session_articoli.remove(server_main.session_articoli.indexOf(client));
		out.println("CLIENT IN SESSIONE GUN : " + server_main.session_articoli);
		mess = buffer.readLine();
		ShowOptions();
		


	}
	
	public <T> void ShowInfo(T t) {
		
		if(t instanceof Gun gun) {
			print.println("\tINFO "+ gun.name()
			+ "\n\t"+ gun.getDescrizione()
			+ "\n\t"+ gun.getProiettile()
			+ "\n\t"+ gun.getState());
		}else if(t instanceof ARTICOLO art) {
			print.println("\tINFO "+ art.name()
			+ "\n\t"+ art.getDescrizione()
			+ "\n\tMATERIALE : "+ art.getMateriale()
			+ "\n\tEPOCA : "+ art.getEpoca()
			+ "\n\tPESO : "+ art.getPeso());
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	

}