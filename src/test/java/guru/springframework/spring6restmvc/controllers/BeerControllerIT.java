package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.models.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testPatchBeerByIdNotFound(){
        assertThrows(NotFoundException.class, ()->{
            beerController.patchBeerById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testPatchBeerById(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = BeerDTO.builder()
                .price(BigDecimal.valueOf(34.88))
                .build();

        ResponseEntity response = beerController.patchBeerById(beer.getId(), beerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer savedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(savedBeer.getPrice()).isEqualTo(beerDTO.getPrice());
    }

    @Test
    void testDeleteBeerByIdNotFound(){
        assertThrows(NotFoundException.class, ()->{
            beerController.deleteBeer(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteBeerFound(){
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity response = beerController.deleteBeer(beer.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(beerRepository.findById(beer.getId()).isEmpty());

    }

    @Test
    void testUpdateBeerNotFound(){
        assertThrows(NotFoundException.class, ()->{
           beerController.updateBeer(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateBeer(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        beerDTO.setBeerName("UPDATED BEER");

        ResponseEntity response = beerController.updateBeer(beer.getId(), beerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer savedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(savedBeer.getBeerName()).isEqualTo(beerDTO.getBeerName());


    }

    @Rollback
    @Transactional
    @Test
    void testSaveBeer(){
        BeerDTO beerDTO = BeerDTO
                .builder()
                .beerName("New Beer")
                .build();

        ResponseEntity response = beerController.handlePost(beerDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();

        String[] location = response.getHeaders().getLocation().getPath().split("/");
        // http:localhost:8080/  api   / v1  / beer / {beerId}
        System.out.println(location[4]);
        UUID uuid = UUID.fromString( location[4]);

        Beer beer = beerRepository.findById(uuid).get();

        assertThat(beer).isNotNull();
    }

    @Test
    void testBeerIsNotFound(){
        assertThrows(NotFoundException.class, ()->{
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    void testListBeerById(){
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerController.getBeerById(beer.getId());

        assertThat(beerDTO).isNotNull();
    }

    @Test
    void testListBeers(){
        List<BeerDTO> dtos = beerController.getBeers();

        assertThat(dtos.size()).isEqualTo(3);
    }
    @Rollback
    @Transactional
    @Test
    void testEmptyList(){
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.getBeers();

        assertThat(dtos.size()).isEqualTo(0);
    }
}