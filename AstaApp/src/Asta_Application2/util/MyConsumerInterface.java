package Asta_Application2.util;

import java.net.Socket;

@FunctionalInterface
public interface MyConsumerInterface {

	public void broadcast(String mess,Socket client_mittente);
		
}
