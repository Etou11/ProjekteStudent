package de.hsesslingen.sa;

import java.util.Scanner;

public final class Console {
    private Console() {
    }

    private static Scanner console = new Scanner(System.in);
    static String[] direction;

    public static String readLine() {
        return console.nextLine();
    }

    public static void writeLine(String message) {
        System.out.println(message);
    }
    
}
