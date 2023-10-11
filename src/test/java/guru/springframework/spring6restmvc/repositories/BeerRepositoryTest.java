package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;



    @Test
    void testSaveBear(){
        Beer savedBeer = beerRepository.save(Beer.
                builder().beerName("My beer").
                build());

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getBeerName()).isNotNull();
    }

}