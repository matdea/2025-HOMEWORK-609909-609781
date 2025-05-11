package it.uniroma3.diadia;

import java.util.Scanner;

public class IOConsole {
	private Scanner scanner = new Scanner(System.in);

	public void mostraMessaggio(String msg) {
		System.out.println(msg);
	}

	public String leggiRiga() {
		return this.scanner.nextLine();
	}
}
