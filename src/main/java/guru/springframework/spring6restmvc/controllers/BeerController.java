package guru.springframework.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import guru.springframework.spring6restmvc.models.BeerDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import guru.springframework.spring6restmvc.services.BeerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
    private final BeerService beerService;

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_ID_PATH = BEER_PATH + "/{beerId}";



    @PatchMapping(BEER_ID_PATH)
    public ResponseEntity patchBeerById(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer){

        if(beerService.patchBeerById(id, beer).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_ID_PATH)
    public ResponseEntity deleteBeer(@PathVariable("beerId") UUID id){

        if(!beerService.deleteBeerById(id)){
            throw new NotFoundException();
        }


        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_ID_PATH)
    public ResponseEntity updateBeer(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer){
        if(beerService.updateBeerById(id, beer).isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@RequestBody BeerDTO beer){

        BeerDTO savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer"+ "/" +  savedBeer.getId().toString());

        return new ResponseEntity(headers ,HttpStatus.CREATED);
    }

    @GetMapping(value = BEER_PATH)
    public List<BeerDTO> getBeers(){
        return this.beerService.listBeers();
    }

    @GetMapping(value = BEER_ID_PATH)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID id){
        log.debug("Get Beer by ID in controller");

        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }
}
