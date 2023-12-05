package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.EpisodesData;
import br.com.alura.screenmatch.model.SeasonsData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumer;
import br.com.alura.screenmatch.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ApiConsumer apiConsumer = new ApiConsumer();
        var json = apiConsumer.fetchData("https://www.omdbapi.com/?t=supernatural&apikey=35a0af28");
        System.out.println(json);

        // Conversor de série:
        DataConverter dataConverter = new DataConverter();
        SeriesData seriesData = dataConverter.fetchData(json, SeriesData.class);
        System.out.println(seriesData);

        // Conversor de episódios:
        json = apiConsumer.fetchData("https://www.omdbapi.com/?t=supernatural&season=1&episode=8&apikey=35a0af28");
        EpisodesData episodesData = dataConverter.fetchData(json, EpisodesData.class);
        System.out.println(episodesData);

        // Conversor de temporadas:
        List<SeasonsData> seasonsDataList = new ArrayList<>();
        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            json = apiConsumer.fetchData("https://www.omdbapi.com/?t=supernatural&season=" + i + "&apikey=35a0af28");
            SeasonsData seasonsData = dataConverter.fetchData(json, SeasonsData.class);
            seasonsDataList.add(seasonsData);
        }
        seasonsDataList.forEach(System.out::println);
    }
}
