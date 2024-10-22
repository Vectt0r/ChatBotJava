import java.io.*;
import java.net.*;

public class ChatBotClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 55555)) {
            System.out.println("Conectado ao ChatBot!");

            // Criar streams para comunicação
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            String mensagem;
            while (true) {
                System.out.print("Digite sua pergunta (ou 'Sair' para encerrar): ");
                mensagem = teclado.readLine();

                if (mensagem.equalsIgnoreCase("Sair")) {
                    break;
                }

                out.println(mensagem);

                String resposta = in.readLine();
                System.out.println("ChatBot: " + resposta);
            }
        } catch (IOException e) {
            System.err.println("Erro na comunicação " + e + " : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
