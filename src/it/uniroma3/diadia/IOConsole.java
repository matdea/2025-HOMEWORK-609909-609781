package it.uniroma3.diadia;

import java.util.Scanner;

public class IOConsole implements IO {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void mostraMessaggio(String messaggio) {
        System.out.println(messaggio);
    }

    @Override
    public String leggiRiga() {
        return scanner.nextLine();
    }
}

