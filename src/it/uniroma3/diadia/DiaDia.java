package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Giocatore;

public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = "" +
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n" +
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n" +
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n" +
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n" +
			"Per conoscere le istruzioni usa il comando 'aiuto'.";

	static final private String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa"};

	private Partita partita;
	private IOConsole io;

	public DiaDia(IOConsole io) {
		this.io = io;
		this.partita = new Partita();
	}

	public void gioca() {
		String istruzione;

		this.io.mostraMessaggio(MESSAGGIO_BENVENUTO);
		do {
			istruzione = this.io.leggiRiga();
		} while (!processaIstruzione(istruzione));
	}

	private boolean processaIstruzione(String istruzione) {
		Comando comandoDaEseguire = new Comando(istruzione);

		if (comandoDaEseguire.getNome() == null)
			return false;

		switch (comandoDaEseguire.getNome()) {
			case "fine":
				this.fine();
				return true;
			case "vai":
				this.vai(comandoDaEseguire.getParametro());
				break;
			case "aiuto":
				this.aiuto();
				break;
			case "prendi":
				this.prendi(comandoDaEseguire.getParametro());
				break;
			case "posa":
				this.posa(comandoDaEseguire.getParametro());
				break;
			default:
				this.io.mostraMessaggio("Comando sconosciuto");
				break;
		}

		if (this.partita.vinta()) {
			this.io.mostraMessaggio("Hai vinto!");
			return true;
		} else if (this.partita.getGiocatore().getCfu() <= 0) {
			this.io.mostraMessaggio("Hai perso! Hai esaurito i tuoi CFU.");
			return true;
		}

		return false;
	}

	private void prendi(String nomeAttrezzo) {
		if (nomeAttrezzo == null) {
			this.io.mostraMessaggio("Che attrezzo vuoi prendere?");
			return;
		}

		Stanza stanzaCorrente = this.partita.getLabirinto().getStanzaCorrente();
		Attrezzo attrezzo = stanzaCorrente.getAttrezzo(nomeAttrezzo);

		if (attrezzo != null) {
			if (this.partita.getGiocatore().getBorsa().addAttrezzo(attrezzo)) {
				stanzaCorrente.removeAttrezzo(attrezzo);
				this.io.mostraMessaggio("Hai preso: " + nomeAttrezzo);
			} else {
				this.io.mostraMessaggio("La borsa è piena o troppo pesante.");
			}
		} else {
			this.io.mostraMessaggio("Attrezzo non trovato nella stanza.");
		}
	}

	private void posa(String nomeAttrezzo) {
		if (nomeAttrezzo == null) {
			this.io.mostraMessaggio("Che attrezzo vuoi posare?");
			return;
		}

		Attrezzo attrezzo = this.partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);

		if (attrezzo != null) {
			if (this.partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzo)) {
				this.io.mostraMessaggio("Hai posato: " + nomeAttrezzo);
			} else {
				this.io.mostraMessaggio("Non puoi posare l'attrezzo nella stanza.");
			}
		} else {
			this.io.mostraMessaggio("Attrezzo non presente nella borsa.");
		}
	}

	private void aiuto() {
		for (String comando : elencoComandi) {
			this.io.mostraMessaggio(comando + " ");
		}
		this.io.mostraMessaggio("");
	}

	private void vai(String direzione) {
		if (direzione == null) {
			this.io.mostraMessaggio("Dove vuoi andare?");
			return;
		}

		Stanza prossimaStanza = this.partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(direzione);

		if (prossimaStanza == null) {
			this.io.mostraMessaggio("Direzione inesistente");
		} else {
			this.partita.getLabirinto().setStanzaCorrente(prossimaStanza);
			int cfu = this.partita.getGiocatore().getCfu();
			this.partita.getGiocatore().setCfu(--cfu);
		}

		this.io.mostraMessaggio(partita.toString());
	}

	private void fine() {
		this.io.mostraMessaggio("Grazie di aver giocato!");
	}

	public static void main(String[] argc) {
		IOConsole io = new IOConsole();
		DiaDia gioco = new DiaDia(io);
		gioco.gioca();
	}
}
