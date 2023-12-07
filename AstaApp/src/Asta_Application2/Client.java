package Asta_Application2;
//C:\Users\q\git\JavaServerClient\ServiceConsonantiVocaliServer-Client2\src\Asta_Application2
import java.net.*;
import java.io.*;
import static java.lang.System.*;
import java.util.*;
import java.util.function.Predicate;
public class Client {
	
	
	Socket socket;
	String username;
	PrintStream print;
	boolean connected;
	int buget;
	Predicate<String> test_IsInteger = (s) -> {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	};
	
	
	
	Client(String sa ,int po,String u,int b) throws UnknownHostException, IOException{
			this.buget = b;
			this.username = u;
			this.socket = new Socket(sa,po);
			this.print = new PrintStream(this.socket.getOutputStream());
			print.println(username + "&"+ buget);
			connected = true;
			new Thread(new ReadBroadCast(this)).start();
		
		
	}
	
	public void execute() {
		String respo = "";
		while(!"exit".equalsIgnoreCase(respo) & buget > 0) {
				respo = new Scanner(System.in).nextLine();
				if(test_IsInteger.test(respo)) {
					int p = Integer.valueOf(respo);
					if(p <= buget) {
						print.println(respo);
					}else {
						out.println("BUGET INSUFFICENTE PER QUESTA PROPOSTA");
					}
				}else {
					print.println(respo);
				}
				
		}
		connected = false;
		print.close();
		out.println("sei uscito!");
		
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public static void main(String... args) throws IOException {
		
		//aggiunta della impostazione username prima di collegarsi
		//cosi che il client dopo la connesione mandi il suo username al server
		out.print("USERNAME : ");
		String username = new Scanner(System.in).nextLine();
		out.print("BUGET INIZIALE: ");
		int buget = new Scanner(System.in).nextInt();
		Client client = new Client("192.168.0.189", 9095,username,buget);
		client.execute();	
		
	}
	
	

}

class ReadBroadCast implements Runnable{
	
	Client client;
	
	ReadBroadCast(Client c){
		this.client = c;
	}
	
	@Override
	public void run() {
		
		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
			String in = "";
			while(client.isConnected()) {
				in = buffer.readLine();
				if(in.startsWith("upbuget?")) {
					in = in.substring(8);
					client.buget = Integer.valueOf(in);
					out.println("----> BUGET : "+ in);
					if(client.buget <= 0) {
						out.println("BUGET FINITO PREMI | INVIO PER TERMINARE");
						break;
					}	
				}else 
					out.println(in);
			}
		} catch (IOException e1) {
			
		}
		
	}
	
}














