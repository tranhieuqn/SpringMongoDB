package app.repository;

import app.entity.Address;
import app.entity.Hotel;
import app.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DBSeeder implements CommandLineRunner {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public void run(String... strings) throws Exception {
        Hotel marriot = new Hotel("Marriot", 120,
                new Address("Paris", "France"),
                Arrays.asList(new Review("John", 8, false),
                              new Review("Mary", 7, true)));

        Hotel ibis = new Hotel("Ibis", 90,
                new Address("Bucharest", "Romania"),
                Arrays.asList(new Review("Teddy", 9, true)));

        Hotel sofitel = new Hotel("Sofitel", 200,
                new Address("Rome", "Italy"), new ArrayList<>());

        hotelRepository.deleteAll();

        List<Hotel> hotels = Arrays.asList(marriot, ibis, sofitel);
        hotelRepository.saveAll(hotels);
    }
}
