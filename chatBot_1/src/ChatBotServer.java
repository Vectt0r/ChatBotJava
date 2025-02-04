import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ChatBotServer {
    // Dicionário de perguntas e respostas
    private static HashMap<String, String> respostas; // mapear respostas

    public static void inicializarRespostas() { // processamento de perguntas e respostas
        respostas = new HashMap<>();
        respostas.put("tudo bem?", "Sim, tudo ótimo!");
        respostas.put("quais filmes hoje?", "Hoje, está em cartaz o filme da Patrulha Canina.");
        respostas.put("quanto custa o aerolin?", "O Aerolin custa R$29,00.");
        respostas.put("sair", "Obrigado por utilizar nosso sistema!");
    }

    public static void main(String[] args) {
        inicializarRespostas();

        try (ServerSocket serverSocket = new ServerSocket(55555)) {
            System.out.println("ChatBot iniciado e aguardando conexões...");

            while (true) { // Mantém o servidor em execução
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Cliente conectado: " + socket.getInetAddress());

                    // Criar streams para comunicação
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    String perguntaCliente;
                    while ((perguntaCliente = in.readLine()) != null) {
                        // Converter a pergunta do cliente para minúsculas
                        perguntaCliente = perguntaCliente.toLowerCase().trim();
                        System.out.println("Cliente: " + perguntaCliente);
                    
                        // Se a pergunta for "sair", encerra a conexão e volta a ouvir
                        if (perguntaCliente.equals("sair")) {
                            out.println(respostas.get("sair"));
                            break; // Sai do loop da conexão atual
                        }
                    
                        // Se a pergunta for "desconectar", encerra a aplicação
                        if (perguntaCliente.equals("desconectar")) {
                            out.println("A aplicação será encerrada. Obrigado por utilizar nosso sistema!");
                            System.exit(0); // Encerra a aplicação
                        }
                    
                        // Responder à pergunta
                        String resposta = respostas.getOrDefault(perguntaCliente, "Desculpe, não entendi sua pergunta. Pode reformular?");
                        out.println(resposta);
                    }
                    System.out.println("Cliente desconectado.");
                } catch (IOException e) {
                    System.err.println("Erro ao tratar cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}
