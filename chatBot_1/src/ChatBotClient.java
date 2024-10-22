import java.io.*;
import java.net.*;

public class ChatBotClient {
    public static void main(String[] args) {
        String host = "localhost"; // ou o IP do servidor
        int port = 55555; // Porta do servidor

        while (true) { // Loop para permitir reconexão
            try (Socket socket = new Socket(host, port)) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

                String userInput;

                System.out.println("Conectado ao ChatBot! Digite suas perguntas ou 'sair' para desconectar.");

                while (true) {
                    System.out.print("Você: ");
                    userInput = consoleInput.readLine();

                    // Enviar a pergunta para o servidor
                    out.println(userInput);

                    // Receber e imprimir a resposta do servidor
                    String response = in.readLine();
                    System.out.println("ChatBot: " + response);

                    // Verificar se o usuário quer sair
                    if ("sair".equalsIgnoreCase(userInput)) {
                        break; // Encerra o loop e desconecta
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao conectar ao servidor: " + e.getMessage());
                break; // Sai do loop se houver um erro de conexão
            }
        }
        System.out.println("Cliente encerrado.");
    }
}
