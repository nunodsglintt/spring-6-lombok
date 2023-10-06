package guru.springframework.spring6restmvc.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import guru.springframework.spring6restmvc.models.Beer;
import org.springframework.stereotype.Controller;
import guru.springframework.spring6restmvc.services.BeerService;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID id){
        log.debug("Get Beer by ID in controller");

        return beerService.getBeerById(id);
    }
}
