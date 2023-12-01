package br.com.alura.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiConsumer {

    public String fetchData(String address) {
        /* Cria uma nova instância de "HttpClient": */
        HttpClient client = HttpClient.newHttpClient();

        try {
            /* Cria uma nova instância de "HttpRequest" configurada com a URI fornecida: */
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).build();
            /* Envia a requisição e obtém a resposta, armazenando-a na variável "response": */
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            /* Retorna o corpo da resposta como uma string: */
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
