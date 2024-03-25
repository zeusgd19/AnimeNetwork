package org.example;

import java.sql.*;
import java.util.Scanner;
import java.util.Stack;

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
            else {
                switch (option){
                    case 1: viewAllAnimes();
                        break;
                    case 2 : generoAnime();
                        break;
                    case 3 : addAnime();
                        break;
                    case 4 : logout();
                }
            }
        }

        con.close();

    }
    private static int getOption(){
        Scanner sc = new Scanner(System.in);
        int option = -1;
        try {
            option = Integer.parseInt(sc.next());
            if ((currentSC == 0 && option > 2) || (currentSC == 1 && option > 4)){
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
            System.out.println("-----------------------------------------------");
        } else {
            System.out.println("-------------------------------------------------------------------------------------------------------------");
            System.out.println(" 0. EXIT | 1. VER TODOS LOS ANIMES | 2. VER ANIMES DE UN GENERO | 3. AÑADIR UN ANIME | 4. LOGOUT " + username);
            System.out.println("-------------------------------------------------------------------------------------------------------------");
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

    private static void viewAllAnimes() throws SQLException {
        Statement st;
        String sql = "SELECT * FROM anime";
        st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            System.out.print(rs.getInt("ID") + "\t");
            System.out.println(rs.getString("nombre"));
        }
    }
    private static void generoAnime() throws SQLException {
        Scanner sc = new Scanner(System.in);
        PreparedStatement pt;
        ResultSet rs;
        System.out.println("¿QUÉ GENERO DE ANIME ESTÁS BUSCANDO?");
        String tipoGenero = sc.nextLine();
        String sql = "SELECT * FROM generoAnime WHERE genero = ?";
        pt = con.prepareStatement(sql);
        pt.setString(1, tipoGenero);
        rs = pt.executeQuery();
        int id_genero = 0;

        if (rs.next()){
            id_genero = rs.getInt("id_genero");
        } else {
            System.out.println("Ese genero no existe");
        }
        pt.close();
        rs.close();
        String sql2 = "SELECT * FROM anime WHERE id_genero = ?";
        pt = con.prepareStatement(sql2);
        pt.setInt(1, id_genero);
        rs = pt.executeQuery();
        while (rs.next()){
            System.out.print(rs.getInt("id") + " ");
            System.out.println(rs.getString("nombre"));
        }
    }

    private static void logout(){
        currentSC = 0;
        username = "";
        userID = -1;
    }
    private static void menuGenero(){
        if (currentSC == 2){
            System.out.println("1. Isekai | 2.Sci-Fi");
        }
    }
    private static void generoAnime2() throws SQLException {
        PreparedStatement pt;
        int option;
        option = getOption();
        while (true) {
            menuGenero();
            if (option == 2)
                break;
            if (currentSC == 2) {
                switch (option) {
                    case 1:
                        String sql = "SELECT * FROM generoAnime WHERE genero LIKE %isekai%";
                        break;
                    case 2:
                        String sql2 = "SELECT * FROM generoAnime WHERE genero LIKE %SCI-FI%";
                        break;
                }
            }
        }
    }
    /*
    private static void generoAnime3() throws SQLException {
        ;
        String sql = "SELECT * FROM generoAnime";
        pt = con.prepareStatement(sql);
        ResultSet rs = pt.executeQuery(sql);
        while (rs.next()){
            System.out.println(rs.getString("genero" + "\n"));
            System.out.println(rs.getInt("id_genero"));
        }

    }*/

    private static void addAnime() throws SQLException {
        String nombre;
        String genero;
        int id_genero;
        String query;
        PreparedStatement st;
        ResultSet rs;
        Scanner sc = new Scanner(System.in);
        System.out.println("Nombre del anime: ");
        nombre = sc.nextLine();

        System.out.println("Genero del anime: ");
        genero = sc.nextLine();

        rs = selectGenre(genero);

        if(rs.next()){
            id_genero = rs.getInt("id_genero");
            insertAnime(nombre, id_genero);
        } else {
            query = "INSERT INTO generoAnime (genero) VALUES (?)";
            st = con.prepareStatement(query);
            st.setString(1,genero);
            st.executeUpdate();
            st.close();

            rs = selectGenre(genero);
            id_genero = rs.getInt("id_genero");
            rs.close();

            insertAnime(nombre, id_genero);
        }
    }

    private static ResultSet selectGenre(String genero) throws SQLException {
        String query;
        ResultSet rs;
        PreparedStatement st;
        query = "SELECT * FROM generoAnime WHERE genero = ?";
        st = con.prepareStatement(query);
        st.setString(1, genero);
        rs = st.executeQuery();
        return rs;
    }

    private static void insertAnime(String nombre, int id_genero) throws SQLException {
        String query;
        PreparedStatement st;
        query = "INSERT INTO anime (nombre,id_genero) VALUES (?,?)";
        st = con.prepareStatement(query);
        st.setString(1, nombre);
        st.setInt(2, id_genero);
        st.executeUpdate();
        st.close();
    }
}