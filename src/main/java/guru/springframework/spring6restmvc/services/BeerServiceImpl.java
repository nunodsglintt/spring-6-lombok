package guru.springframework.spring6restmvc.services;

import lombok.extern.slf4j.Slf4j;
import guru.springframework.spring6restmvc.models.Beer;
import guru.springframework.spring6restmvc.models.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {


    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Get Beer ID in service was called");
        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
