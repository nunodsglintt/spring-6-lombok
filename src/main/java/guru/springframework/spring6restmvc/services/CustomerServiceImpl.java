package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.models.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO c1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("John Wick")
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        CustomerDTO c2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Canelo Alvarez")
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        CustomerDTO c3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Chris Rock")
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        customerMap.put(c1.getId(), c1);
        customerMap.put(c2.getId(), c2);
        customerMap.put(c3.getId(), c3);
    }

    @Override
    public List<CustomerDTO> findCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> findCustomerById(UUID id) {
        log.debug("Entered findCustomerById in Service");
        return Optional.of(customerMap.get(id));


    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .version(customer.getVersion())
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
         customerMap.put(savedCustomer.getId(), savedCustomer);

         return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(id);

        existing.setCustomerName(customer.getCustomerName());
        existing.setLastModifiedAt(LocalDateTime.now());

        return Optional.of(existing);
    }

    @Override
    public Boolean deleteCustomerById(UUID id) {

        customerMap.remove(id);

        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID id, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(id);

        System.out.println(customer.toString());

        if(StringUtils.hasText(customer.getCustomerName())){
            existing.setCustomerName(customer.getCustomerName());
        }
        return Optional.of(existing);
    }
}
