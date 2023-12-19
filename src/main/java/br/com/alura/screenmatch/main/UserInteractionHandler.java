package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.EpisodesData;
import br.com.alura.screenmatch.model.SeasonsData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumer;
import br.com.alura.screenmatch.service.DataConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        /* Esse trecho de código está usando a API de Stream do Java para processar uma lista de objetos "SeasonsData"
        * e, a partir dela, criar uma lista combinada de todos os objetos "EpisodesData" contidos em cada temporada.
        * Em resumo: esse trecho de código extrai todos os episódios de todas as temporadas presentes na lista
        * "seasonsDataList" e os armazena em uma nova lista chamada "episodesDataList". */
        List<EpisodesData> episodesDataList = seasonsDataList.stream()
                .flatMap(s -> s.episodes().stream())
                .collect(Collectors.toCollection(ArrayList::new));
                /* "collect(Collectors.toList())" é diferente de "toList()"! */
                //.collect(Collectors.toList());

        /* System.out.println("\n0s 10 melhores episódios da série " + serieName.toUpperCase() + " são: ");
        episodesDataList.stream()
                // Filtra e limpa os episódios sem avaliação (com avaliação N/A):
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro(N/A)" + e))
                // Compara os episódios pela avaliação em ordem DECRESCENTE:
                .sorted(Comparator.comparing(EpisodesData::rating).reversed())
                .peek(e -> System.out.println("Ordenação: " + e))
                // Limita o tamanho em 5:
                .limit(10)
                .peek(e -> System.out.println("Limite: " + e))
                .map(e -> e.title().toUpperCase())
                .peek(e -> System.out.println("Mapeamento: " + e))
                .forEach(System.out::println); */

        // Descobre as temporadas dos episódios:
        List<Episode> episodes = seasonsDataList.stream()
                .flatMap(s -> s.episodes().stream()
                        .map(e -> new Episode(s.number(), e))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("Digite o nome do episódio que você deseja procurar: ");
        var titleSnippet = scanner.nextLine();
        Optional<Episode> foundEpisode = episodes.stream()
                .filter(e -> e.getTitle().toLowerCase().contains(titleSnippet))
                // "findFirst" sempre segue a mesma ordem!
                .findFirst();

        if (foundEpisode.isPresent()) {
            System.out.println("Episódio encontrado: " + foundEpisode.get().getTitle());
            System.out.println("Temporada: " + foundEpisode.get().getSeason());
        } else {
            System.out.println("Episódio não encontrado!");
        }

        /* System.out.println("A partir de que ano você deseja ver os episódios?");
        var year = scanner.nextInt();
        scanner.nextLine();

        LocalDate localDate = LocalDate.of(year, 1, 1);

        // Formatador de data:
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodes.stream()
                // Diferente de null e posterior a data de busca:
                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(localDate))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getSeason()
                        + " Episódio: " + e.getTitle()
                        + " Data de lançamento: " + e.getReleaseDate().format(dtf)
                )); */

    }
}
