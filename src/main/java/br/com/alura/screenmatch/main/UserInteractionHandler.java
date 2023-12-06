package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.EpisodesData;
import br.com.alura.screenmatch.model.SeasonsData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumer;
import br.com.alura.screenmatch.service.DataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInteractionHandler {
    private Scanner scanner = new Scanner(System.in);
    private ApiConsumer apiConsumer = new ApiConsumer();
    private DataConverter dataConverter = new DataConverter();

    // Temporadas:
    private List<SeasonsData> seasonsDataList = new ArrayList<>();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=35a0af28";


    // Método responsável por lidar com as interações com o usuário:
    public void showUserOptions() {
        // Printa no console para o usuário digitar o nome da série:
        System.out.println("Digite o nome da série para busca: ");
        // Recebe o nome da série na variável "serieName" como uma String ("scanner.nextLine()"):
        var serieName = scanner.nextLine();
        // Concatena o endereço:
        var json = apiConsumer.fetchData(ADDRESS + serieName.replace(" ", "+") + APIKEY);
        // Conversor:
        SeriesData seriesData = dataConverter.fetchData(json, SeriesData.class);
        System.out.println(seriesData);

        // Temporadas:
        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            json = apiConsumer.fetchData(ADDRESS + serieName.replace(" ", "+") + "&season="
                    + i + APIKEY);
            SeasonsData seasonsData = dataConverter.fetchData(json, SeasonsData.class);
            seasonsDataList.add(seasonsData);
        }
        seasonsDataList.forEach(System.out::println);

        /* Essa linha de código está usando expressões lambda e o método "forEach" para percorrer uma lista de objetos
        * "SeasonData" e, para cada temporada, está percorrendo a lista de episódios associada a essa temporada
        * ("s.episodes()"), imprimindo o títlo de cada episódio no console. */
        seasonsDataList.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));
    }
}
