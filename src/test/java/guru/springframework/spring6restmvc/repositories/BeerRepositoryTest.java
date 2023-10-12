package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.models.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeerNameTooLong(){

        assertThrows(ConstraintViolationException.class, ()->{
            Beer savedBeer = beerRepository.save(Beer.
                    builder()
                    .beerName("My beer 12222222222122222222221222222222212222222222122222222221222222222212222222222122222222221222222222212222222222")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .price(new BigDecimal(32.22))
                    .upc("32323")
                    .build());

            beerRepository.flush();
        })
        ;
    }


    @Test
    void testSaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.
                builder()
                .beerName("My beer")
                .beerStyle(BeerStyle.PALE_ALE)
                .price(new BigDecimal(32.22))
                .upc("32323")
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getBeerName()).isNotNull();
    }

}