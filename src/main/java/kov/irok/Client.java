package kov.irok;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Client implements ConnectionListener{

    Scanner scanner = new Scanner(System.in);
    private Connection connection;
    private final static Logger logger = Logger.getLogger(Connection.class);

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        try {
            connection = new Connection(this,"127.0.0.1",8000 );
            showWelcomeMessage();
            while (true){
                String message;
                message = scanner.nextLine();
                connection.sendString(parseCommand(message));
            }
        } catch (IOException e) {
            System.out.println("Connection exception: " + e);
        }
    }

    private String parseCommand(String message) {
        if (message.equals("/register")){
            return showRegisterInfo();
        }else if (message.equals("/login")){
            return showLoginInfo();
        }else{
            return message;
        }
    }

    private void showWelcomeMessage() {
        System.out.println("Welcome to chat!!!");
        System.out.println("For register new user print   \"/register\"");
        System.out.println("For login user print          \"/login\"");
        System.out.println("For leave chat print          \"/leave\"");
        System.out.println("For exit print                \"/exit\"");

    }

    private String showRegisterInfo() {
        String [] strings = new String[5];
        strings[0] = "register";
        System.out.println("Enter \"agent\" for register agent or \"client\" for register client");
        strings[1] = scanner.nextLine();
        while (!(strings[1].equals("agent") || strings[1].equals("client"))){
            System.out.println("Enter \"agent\" for register agent or \"client\" for register client");
            strings[1] = scanner.nextLine();
        }
        System.out.println("Enter your name");
        strings[2] = scanner.nextLine();
        System.out.println("Enter your login");
        strings[3] = scanner.nextLine();
        System.out.println("Enter your password");
        strings[4] = scanner.nextLine();
        return StringUtils.join(Arrays.asList(strings), ":;");
    }

    private String showLoginInfo() {
        String [] strings = new String[3];
        strings[0] = "login";
        System.out.println("Enter your login");
        strings[1] = scanner.nextLine();
        System.out.println("Enter your password");
        strings[2] = scanner.nextLine();
        return StringUtils.join(strings, ":;");
    }

    @Override
    public void onConnectionReady(Connection connection) {
        logger.info("Connection to server ready");
    }

    @Override
    public void onReceiveString(Connection connection, String string) {
        System.out.println(string);
    }

    @Override
    public void onDisconnect(Connection connection) {
        logger.warn("Connection to server interrupted");
    }

    @Override
    public void onException(Connection connection, Exception e) {
        logger.error("Connection exception: " + e);
    }
}