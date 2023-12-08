package Asta_Application2.util;


public enum ARTICOLO {
	
	GIOCONDA( 1503,
			"Un dipinto ricco di misteriLa \n\t"
			+ "Gioconda è un dipinto a olio su tavola \n\t"
			+ "eseguito da Leonardo da Vinci intorno al 1503. \n\t"
			+ "Misura 77 cm per 53 \n\t"
			+ "ed è oggi conservato al Musée du Louvre.",
			
			"olio su tela , quadro di legno di quercia",
			21.45
			),
	DAVID_MICHELANGELO( 1501,
			"Michelangelo scolpi il David da settembre del 1501 \n\t"
			+ " fino a maggio del 1504, dando come risultato questa icona \n\t"
			+ "del Rinascimento con dimensioni e proporzioni enormi.\n\t"
			+ " Il David è alto poco più di 5 metri e supera le cinque tonnellate.\n\t"
			+ " Il David è considerato l'apice del lavoro scultoreo di Michelangelo.",
			
			"MARMO",
			300.23
			
			),
	TIGRE_IMB( 2010,
			"Tigre imbalsamata da un grande cacciatore ,\n\t"
			+ " si dice che sia stata uccisa con un arco a punta velenosa\n\t",
			"tessuto animale , pelle animale",
			70.30
			),
	RABARAMA( 1696,
			"Scultura Rabarama della serie e voluto corredata di garanzia.",
			"RAME",
			6.00
			),
	DILLON_BOY( 2023, 
			"Uno degli artisti più influenti su Internet,\n\t"
			+ " Dillon Boy ha sviluppato il seguito in più rapida crescita nel mondo dell'arte online.\n\t"
			+ " Nel 2017 D-Boy è diventato uno degli artisti più ricercati sul World Wide Web\n\t",
			"RAME",
			2.00
			),
	ADAM_AND_EVE(
			2020, 
			"Roberto Dyrcz. "
			+ "Nato il 27 marzo 1972 a Zakopane. \n\t"
			+ "Studia all'Accademia di Belle Arti di Cracovia. \n\t"
			+ "Diploma presso la Facoltà di Scultura presso lo studio del prof. \n\t"
			+ "Józef Sękowski nel 2002.\n\t",
			"Bronzo patinato, legno patinato",
			3.00
			);
	
	
	
	private int epoca;
	private String materiale;
	private String descrizione;
	private double peso;
	
	ARTICOLO(int epoca,String descrizione,String materiale,double peso) {
		this.setEpoca(epoca);
		this.setDescrizione(descrizione);
		this.setMateriale(materiale);
		this.setPeso(peso);
	}
	
	
	//METODI GETTERS E SETTERS 
	public int getEpoca() {
		return epoca;
	}
	public void setEpoca(int epoca) {
		this.epoca = epoca;
	}
	public String getMateriale() {
		return materiale;
	}
	public void setMateriale(String materiale) {
		this.materiale = materiale;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	

}
