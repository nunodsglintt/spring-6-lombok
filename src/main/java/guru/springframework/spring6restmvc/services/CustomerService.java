package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.models.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> findCustomers();

    Customer findCustomerById(UUID id);

    Customer saveCustomer(Customer customer);

    void updateCustomerById(UUID id, Customer customer);

    void deleteCustomerById(UUID id);

    void patchCustomerById(UUID id, Customer customer);
}
