package betx.apiservice.service.impl;

import betx.apiservice.model.Venue;
import betx.apiservice.repository.VenueRepository;
import betx.apiservice.service.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    VenueRepository venueRepository;

    @Override
    public Venue save(Venue venue) {

        return venueRepository.save(
                Venue
                        .builder()
                        .name(venue.getName())
                        .address(venue.getAddress())
                        .capacity(venue.getCapacity())
                        .city(venue.getCity())
                        .image(venue.getImage())
                        .build()
        );
    }
}
