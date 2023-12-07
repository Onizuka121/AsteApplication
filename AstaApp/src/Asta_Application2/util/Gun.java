package Asta_Application2.util;

public enum Gun {
	
	AK_47("RUSSIA","7.62×39mm","arma d'assalto"),
	AK_74("RUSSSIA","5,45 × 39 mm","arma d'assalto"),
	AK_103("RUSSIA","7,62 × 39 mm" , "fucile d'assalto"),
	AK_19("RUSSIA","5.56×45mm","fucile d'assalto NATO assault rifle"),
	GLOCK_23("AUSTRIA","40 S&W","Pistola semiautomatica"),
	GLOCK_24("AUSTRIA","40 S&W","Pistola semiautomatica"),
	GLOCK_22C("AUSTRIA","40 S&W","Pistola semiautomatica"),
	GLOCK_31("AUSTRIA",".357 SIG","Pistola semiautomatica"),
	XM174("USA","40x46 mm","mounted automatic 40mm grenade launcher heavily based on the M1919A4 machine gun"),
	XM133_MINIGUN("USA","30x36mm","6-barreled Gatling-type machine-gun");
	
	private String state;
	private String proiettile;
	private String descrizione;
	
	private Gun(String state , String dim , String descri) {
		this.state = state;
		this.proiettile = dim;
		this.descrizione = descri;
	}
	
	public String getState() {
		return state;
	}

	public String getProiettile() {
		return proiettile;
	}
	public String getDescrizione() {
		return descrizione;
	}

	

}
