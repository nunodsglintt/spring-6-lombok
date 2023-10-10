package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.models.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer c1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("John Wick")
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        Customer c2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Canelo Alvarez")
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        Customer c3 = Customer.builder()
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
    public List<Customer> findCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer findCustomerById(UUID id) {
        log.debug("Entered findCustomerById in Service");
        return customerMap.get(id);


    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
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
    public void updateCustomerById(UUID id, Customer customer) {
        Customer existing = customerMap.get(id);

        existing.setCustomerName(customer.getCustomerName());
        existing.setLastModifiedAt(LocalDateTime.now());


    }

    @Override
    public void deleteCustomerById(UUID id) {
        customerMap.remove(id);
    }

    @Override
    public void patchCustomerById(UUID id, Customer customer) {
        Customer existing = customerMap.get(id);

        System.out.println(customer.toString());

        if(StringUtils.hasText(customer.getCustomerName())){
            existing.setCustomerName(customer.getCustomerName());
        }
    }
}
