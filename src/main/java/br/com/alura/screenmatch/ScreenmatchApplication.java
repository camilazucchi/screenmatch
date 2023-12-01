package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumer;
import br.com.alura.screenmatch.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

        DataConverter dataConverter = new DataConverter();
        SeriesData seriesData = dataConverter.fetchData(json, SeriesData.class);
        System.out.println(seriesData);
    }
}
