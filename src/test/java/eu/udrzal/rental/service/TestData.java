package eu.udrzal.rental.service;

import eu.udrzal.rental.model.MovieDefinition;
import eu.udrzal.rental.model.MovieTape;
import eu.udrzal.rental.model.MovieType;

public class TestData {

    public static final MovieTape HP2 = new MovieTape().setMovieDefinition(new MovieDefinition().setTitle("HP2")
            .setType(MovieType.OLD));
    public static final MovieTape HP5 =
            new MovieTape().setMovieDefinition(new MovieDefinition().setTitle("HP5").setType(MovieType.REGULAR));
    public static final MovieTape HP7 = new MovieTape().setMovieDefinition(new MovieDefinition().setTitle("HP7")
            .setType(MovieType.NEW));
}
