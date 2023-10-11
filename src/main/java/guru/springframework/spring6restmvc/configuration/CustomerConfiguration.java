package guru.springframework.spring6restmvc.configuration;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.models.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
@RequiredArgsConstructor
@Component
public class CustomerConfiguration implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
        Customer c1 = Customer.builder()
                .customerName("John Wick")
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        Customer c2 = Customer.builder()
                .customerName("Canelo Alvarez")
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        Customer c3 = Customer.builder()
                .customerName("Chris Rock")
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        customerRepository.save(c1);
        customerRepository.save(c2);
        customerRepository.save(c3);
    }
}
