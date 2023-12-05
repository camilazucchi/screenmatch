package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.service.ApiConsumer;

import java.util.Scanner;

public class UserInteractionHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final ApiConsumer apiConsumer = new ApiConsumer();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=35a0af28";


    // Método responsável por lidar com as interações com o usuário:
    public void showUserOptions() {
        // Printa no console para o usuário digitar o nome da série:
        System.out.println("Digite o nome da série para busca: ");
        // Recebe o nome da série na variável "serieName" como uma String ("scanner.nextLine()"):
        var serieName = scanner.nextLine();
        var json = apiConsumer.fetchData(ADDRESS + serieName.replace(" ", "+") + APIKEY);
    }
}
