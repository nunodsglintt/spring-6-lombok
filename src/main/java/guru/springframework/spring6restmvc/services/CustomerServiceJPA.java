package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.models.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
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

@RequiredArgsConstructor
@Service
@Primary
public class CustomerServiceJPA implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public List<CustomerDTO> findCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> findCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDTO(customerRepository.findById(id).orElse(null)));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        return customerMapper.customerToCustomerDTO(customerRepository.save(customerMapper.customerDTOToCustomer(customer)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> reference = new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse(presentCustomer->{
                presentCustomer.setCustomerName(customer.getCustomerName());
                reference.set(Optional.of(customerMapper.customerToCustomerDTO(customerRepository.save(presentCustomer))));

            },()->{
            reference.set(Optional.empty());
        } );
        return  reference.get();
    }

    @Override
    public Boolean deleteCustomerById(UUID id) {
        if(!customerRepository.existsById(id)){
            return false;
        }
        customerRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID id, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> reference = new AtomicReference<>();
        customerRepository.findById(id).ifPresentOrElse(present->{
            if(StringUtils.hasText(customer.getCustomerName())){
                present.setCustomerName(customer.getCustomerName());
            }
            reference.set(Optional.of(customerMapper.customerToCustomerDTO(customerRepository.save(present))));
        }, ()->{
            reference.set(Optional.empty());
        });
        return reference.get();
    }
}
