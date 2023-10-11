package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.models.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class BeerServiceJPA implements BeerService {

    @Autowired
    private BeerRepository beerRepository;
    @Autowired
    private BeerMapper beerMapper;
    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository
                .findAll()
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDTO(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> reference = new AtomicReference<>();
            beerRepository.findById(id).ifPresentOrElse(presentBeer->{
                presentBeer.setBeerName(beer.getBeerName());
                presentBeer.setBeerStyle(beer.getBeerStyle());
                presentBeer.setPrice(beer.getPrice());
                presentBeer.setUpc(beer.getUpc());
                presentBeer.setQuantityOnHand(beer.getQuantityOnHand());
                reference.set(Optional.of(beerMapper.beerToBeerDTO(beerRepository.save(presentBeer))));
            }, ()->{
                reference.set(Optional.empty());
            });
            return reference.get();
    }

    @Override
    public Boolean deleteBeerById(UUID id) {
        if(beerRepository.existsById(id)){
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID id, BeerDTO beer) {

        AtomicReference<Optional<BeerDTO>> reference = new AtomicReference<>();
        beerRepository.findById(id).ifPresentOrElse(present ->{
            if(StringUtils.hasText(beer.getBeerName())){
                present.setBeerName(beer.getBeerName());
            }
            if(beer.getPrice() != null){
                present.setPrice(beer.getPrice());
            }
            if(beer.getQuantityOnHand() != null){
                present.setQuantityOnHand(beer.getQuantityOnHand());
            }
            if(beer.getBeerStyle() != null){
                present.setBeerStyle(beer.getBeerStyle());
            }
            if(StringUtils.hasText(beer.getUpc())){
                present.setUpc(beer.getUpc());
            }
            reference.set(Optional.of(beerMapper.beerToBeerDTO(beerRepository.save(present))));
        }, ()->{
            reference.set(Optional.empty());
        });

        return reference.get();
    }
}
