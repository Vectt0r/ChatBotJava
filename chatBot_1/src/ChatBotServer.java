import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ChatBotServer {
    // Dicionário de perguntas e respostas
    private static HashMap<String, String> respostas; //mapear respostas
    public static void inicializarRespostas() { //processamento de perguntas e respostas
        respostas = new HashMap<>();
        respostas.put("Tudo bem?", "Sim, tudo ótimo!");
        respostas.put("Quais filmes hoje?", "Hoje, está em cartaz o filme da Patrulha Canina.");
        respostas.put("Quanto custa o Aerolin?", "O Aerolin custa R$29,00.");
        respostas.put("Sair", "Obrigado por utilizar nosso sistema!");    }

    public static void main(String[] args) {
        inicializarRespostas();

        try (ServerSocket serverSocket = new ServerSocket(55555)) {
            System.out.println("ChatBot iniciado e aguardando conexões...");

            while (true) {
                // Aceita uma conexão de um cliente
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                // Cria streams para comunicação
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                java.lang.String perguntaCliente;
                while ((perguntaCliente = in.readLine()) != null) {
                    System.out.println("Cliente: " + perguntaCliente);

                    // Se a pergunta for "Sair", encerra a conexão
                    if (perguntaCliente.equalsIgnoreCase("Sair")) {
                        out.println(respostas.get("Sair"));
                        break;
                    }
                    // Responder à pergunta
                    String resposta = respostas.getOrDefault(perguntaCliente, "Desculpe, não entendi sua pergunta. Pode reformular?");
                    out.println(resposta);
                }
                System.out.println("Cliente desconectado.");
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

