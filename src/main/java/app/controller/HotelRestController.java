package app.controller;

import app.entity.Hotel;
import app.entity.QHotel;
import app.repository.HotelRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelRestController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getHotels(@RequestParam(value = "filter.maxPrice", required = false) Integer maxPrice) {
        if (maxPrice != null) {
            return hotelRepository.findByPricePerNightLessThan(maxPrice);
        }
        return hotelRepository.findAll();
    }

    @GetMapping("/v2")
    public List<Hotel> getHotelsV2(@RequestParam(value = "filter.maxPrice", required = false) Integer maxPrice,
                                   @RequestParam(value = "filter.country", required = false) String country) {
        QHotel hotel = QHotel.hotel;
        BooleanBuilder builder = new BooleanBuilder();

        if (maxPrice != null) {
            builder.and(hotel.pricePerNight.lt(maxPrice));
        }

        if (!StringUtils.isEmpty(country)) {
            builder.and(hotel.address.country.equalsIgnoreCase(country));
        }

        if (builder.getValue() == null) {
            return hotelRepository.findAll();
        }

        Iterator<Hotel> iterator = hotelRepository.findAll(builder.getValue()).iterator();
        List<Hotel> hotels = new ArrayList<>();
        while (iterator.hasNext()) {
            hotels.add(iterator.next());
        }
        return hotels;
    }

    @GetMapping("/{id}")
    public Hotel getHotel(@PathVariable String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    @PostMapping
    public void add(@RequestBody Hotel hotel) {
        hotelRepository.insert(hotel);
    }

    @PutMapping
    public void update(@RequestBody Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        hotelRepository.deleteById(id);
    }
}

