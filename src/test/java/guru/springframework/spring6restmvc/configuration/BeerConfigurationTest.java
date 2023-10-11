package guru.springframework.spring6restmvc.configuration;

import guru.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerConfigurationTest {

    @Autowired
    BeerRepository beerRepository;

    BeerConfiguration beerConfiguration;

    @BeforeEach
    void setUp(){
        beerConfiguration = new BeerConfiguration(beerRepository);
    }

    @Test
    void TestRun() throws Exception{
        beerConfiguration.run();

        assertThat(beerRepository.count()).isEqualTo(3);
    }

}