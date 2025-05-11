package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.giocatore.Giocatore;



public class Partita {

	private Giocatore giocatore;
	private Labirinto labirinto;
	private boolean finita;

	public Partita() {
		this.labirinto = new Labirinto(); 
		this.giocatore = new Giocatore(); 
		this.finita = false;
	}

	public Giocatore getGiocatore() {
		return this.giocatore;
	}

	public Labirinto getLabirinto() {
		return this.labirinto;
	}


	public boolean vinta() {
		return this.labirinto.getStanzaCorrente() == this.labirinto.getStanzaVincente();
	}
	
	public boolean giocatoreIsVivo() {
	    return this.giocatore.getCfu() > 0;
	}



	public boolean isFinita() {
		return finita || vinta() || (this.giocatore.getCfu() == 0);
	}


	public void setFinita() {
		this.finita = true;
	}

	@Override
	public String toString() {
		return this.labirinto.getStanzaCorrente() + "\nCfu: " + this.giocatore.getCfu();
	}
}

