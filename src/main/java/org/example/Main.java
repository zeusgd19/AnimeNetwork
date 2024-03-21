package org.example;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private static java.sql.Connection con;
    public static void main(String[] args) throws SQLException {
        String host = "jdbc:sqlite:src/main/resources/aNIMENetwork";
        con = DriverManager.getConnection(host);
        printInicial();
        menu();
    }



    private static void printInicial() {
        System.out.println( ANSI_GREEN + "    _    _   _ ___ __  __ _____  __   __                 \n" +
                "   / \\  | \\ | |_ _|  \\/  | ____| \\ \\ / /                 \n" +
                "  / _ \\ |  \\| || || |\\/| |  _|    \\ V /                  \n" +
                " / ___ \\| |\\  || || |  | | |___    | |                   \n" +
                "/_/___\\_\\_|_\\_|___|_|__|_|_____|___|_|___    _           \n" +
                " / ___| ____| \\ | | ____|  _ \\ / _ \\/ ___|  | |__  _   _ \n" +
                "| |  _|  _| |  \\| |  _| | |_) | | | \\___ \\  | '_ \\| | | |\n" +
                "| |_| | |___| |\\  | |___|  _ <| |_| |___) | | |_) | |_| |\n" +
                " \\____|_____|_| \\_|_____|_| \\_\\\\___/|____/  |_.__/ \\__, |\n" +
                "                            __                __   |___/ \n" +
                " _______ _ __  _   _ ___   / /______  _ __ ___\\ \\        \n" +
                "|_  / _ \\ '_ \\| | | / __| | |_  / _ \\| '__/ _ \\| |       \n" +
                " / /  __/ |_) | |_| \\__ \\ | |/ / (_) | | | (_) | |       \n" +
                "/___\\___| .__/ \\__,_|___/ | /___\\___/|_|  \\___/| |       \n" +
                "        |_|                \\_\\                /_/        " + ANSI_RESET);
    }

    private static void menu(){
        System.out.println("-----------------------------------------------");
        System.out.println(" 0. EXIT | 1. LOGIN | 2. REGISTER");
        System.out.println("------------------------------------------------");
    }
}