package org.example;

import java.sql.*;
import java.util.Scanner;

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
    private static int userID;
    private static String username;

    private static int currentSC;
    public static void main(String[] args) throws SQLException {
        String host = "jdbc:sqlite:src/main/resources/aNIMENetwork";
        con = DriverManager.getConnection(host);
        int option;
        printInicial();
        while (true) {
            menu();
            option = getOption();
            if (option == 0) break;
            if (currentSC == 0) {
                switch (option) {
                    case 1: login();
                        break;
                    case 2: register();
                        break;
                }
            }
        }


    }
    private static int getOption(){
        Scanner sc = new Scanner(System.in);
        int option = -1;
        try {
            option = Integer.parseInt(sc.next());
            if ((currentSC == 0 && option > 3) || (currentSC == 1 && option > 6)){
                System.out.println("Opcion incorrecta");
            }
        }catch (IllegalArgumentException iae){
            System.out.println("Opción incorrecta");
        }
        return option;
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
        if(currentSC == 0) {
            System.out.println("-----------------------------------------------");
            System.out.println(" 0. EXIT | 1. LOGIN | 2. REGISTER");
            System.out.println("------------------------------------------------");
        } else {
            System.out.println("-------------------------------------------------");
            System.out.println(" 0. EXIT | 1. VER TODOS LOS ANIMES | 2. VER ANIMES DE UN GENERO | 3. LOGOUT " + username);
        }
    }

    private static void login() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Usuario: ");
        username = sc.nextLine();
        String sql = "SELECT * from usuarios where nombre = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1,username);
        ResultSet rs = pst.executeQuery();

        if (rs.next()){
            if(rs.getString("nombre").equals(username)) {
                userID = rs.getInt("id_usuario");
                currentSC = 1;
            }
        } else {
            System.out.println("Usuario no encontrado");
        }
    }
    private static void register() throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement st = null;
        System.out.println("Name: ");
        String name = sc.nextLine();

        System.out.println("Lastname: ");
        String lastname = sc.nextLine();

        String query = "INSERT INTO usuarios (nombre,apellidos) VALUES(?,?)";
        st = con.prepareStatement(query);
        st.setString(1,name);
        st.setString(2,lastname);
        st.executeUpdate();
    }
}