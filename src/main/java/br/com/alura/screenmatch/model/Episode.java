package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Não é um record por ser uma classe de negócio já que teremos métodos, getters, setters, validações...
public class Episode {
    private Integer season;
    private String title;
    private Integer number;
    private Double rating;
    private LocalDate releaseDate;

    public Episode(Integer seasonNumber, EpisodesData episodesData) {
        this.season = seasonNumber;
        this.title = episodesData.title();
        this.number = episodesData.number();

        try {
            this.rating = Double.valueOf(episodesData.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(episodesData.releaseDate());
        } catch (DateTimeParseException e) {
            this.releaseDate = null;
        }

    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return  "temporada=" + season +
                ", título='" + title + '\'' +
                ", número=" + number +
                ", avaliação=" + rating +
                ", data de lançamento=" + releaseDate;
    }
}
