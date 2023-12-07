package Asta_Application2.util;

import java.net.Socket;
import java.util.TimerTask;
import Asta_Application2.Server;
import  Asta_Application2.ClientHandler;

public class TimerHandler<T> extends TimerTask {

	ClientHandler clientHandler; 
	T element;
	Server server_main;
	
	public TimerHandler(ClientHandler clientH ,T e, Server s){
		this.element = e;
		this.server_main = s;
		clientHandler = clientH;
	}
	
	@Override
	public void run() {
		
	if(server_main.connections.contains(clientHandler.client) & ( server_main.session_guns.contains(clientHandler.client) || server_main.session_articoli.contains(clientHandler.client)) )	{
		
		if(element instanceof Gun) {
			ClientHandler.broadCastSessionGuns.broadcast(
					"ARMA "+server_main.gun_in_vendita+" VENDUTA A "+clientHandler.username + " PER "+ server_main.prezzo_gun_in_vendita
					+" $", null);
			clientHandler.buget -= server_main.prezzo_gun_in_vendita;
			server_main.RemoveGunFromList();
			clientHandler.print.println("upbuget?"+clientHandler.buget);
			if(clientHandler.buget <= 0) {
				
			}
			if(server_main.guns.size() > 0) {
				ClientHandler.broadCastSessionGuns.broadcast("ARMA IN VENDITA ---> | "+ server_main.gun_in_vendita + " |" + "prezzo : "+server_main.prezzo_gun_in_vendita,null );
			}
	
		}else {
			ClientHandler.broadCastSessionArticoli.broadcast(
					"ARTICOLO "+server_main.articolo_in_vendita+" VENDUTA A "+clientHandler.username + " PER "+ server_main.prezzo_articolo_in_vendita
					+" $", null);
			clientHandler.buget -= server_main.prezzo_articolo_in_vendita;
			server_main.RemoveArticoloFromList();
			clientHandler.print.println("upbuget?"+clientHandler.buget);//invio al client la somma di buget rimanente
			if(clientHandler.buget <= 0) {
				
			}
			if(server_main.guns.size() > 0) {
				ClientHandler.broadCastSessionArticoli.broadcast("ARTICOLO IN VENDITA ---> | "+ server_main.articolo_in_vendita + " |" + "prezzo : "+server_main.prezzo_articolo_in_vendita,null );
			}
		}
	}else {
		if(element instanceof Gun) {
			ClientHandler.broadCastSessionGuns.broadcast("ARMA "+element+" ANCORA IN VENDITA "+clientHandler.username+" USCITO", null);;
		}else {
			ClientHandler.broadCastSessionArticoli.broadcast("L'ARTICOLO ANCORA IN VENDITA "+clientHandler.username+" USCITO"
					, null);
		}
		
	}
		
		
		
	}
	
	
	

}
