package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.models.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testPatchByIdNotFound(){
        assertThrows(NotFoundException.class, ()->{
            customerController.patchCustomer(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testPatchById(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = CustomerDTO
                .builder()
                .customerName("New Customer Name")
                .build();
        ResponseEntity response = customerController.patchCustomer(customer.getId(), customerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(savedCustomer.getCustomerName()).isEqualTo(customerDTO.getCustomerName());
    }

    @Test
    void testDeleteByIdNotFound(){
        assertThrows(NotFoundException.class, ()->{
            customerController.deleteCustomer(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteById(){
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity response = customerController.deleteCustomer(customer.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }



    @Test
    void testUpdateCutomerByIdNotFound(){
        assertThrows(NotFoundException.class, ()->{
            customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    // check status code
    // check if object is persisted
    // check if properties are set properly
    @Rollback
    @Transactional
    @Test
    void testUpdateCustomerById(){
       Customer customer = customerRepository.findAll().get(0);

       CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
       customerDTO.setId(null);
       customerDTO.setVersion(null);
       customerDTO.setCustomerName("New Customer Name");

       ResponseEntity response = customerController.updateById(customer.getId(), customerDTO);
       assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

       Customer savedCustomer = customerRepository.findById(customer.getId()).get();

       assertThat(savedCustomer.getCustomerName()).isEqualTo(customerDTO.getCustomerName());

    }

    // check status code
    // check headers
    // check if location is set corretly
    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer(){
        CustomerDTO customerDTO = CustomerDTO
                .builder()
                .customerName("Joao dos Santos")
                .build();

        ResponseEntity response = customerController.createCustomer(customerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(response.getHeaders().getLocation()).isNotNull();

        UUID givenID = UUID.fromString(response.getHeaders().getLocation().getPath().split("/")[4]);
        Customer customer = customerRepository.findById(givenID).get();

        assertThat(customer.getId()).isEqualTo(givenID);
        assertThat(customer.getCustomerName()).isEqualTo(customerDTO.getCustomerName());


    }

    @Test
    void testNotFoundCustomerById(){

        assertThrows(NotFoundException.class, ()->{
            CustomerDTO customerDTO = customerController.findCustomerById(UUID.randomUUID());
        });

    }

    @Test
    void testFindCustomerById(){
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerController.findCustomerById(customer.getId());

        assertThat(customerDTO).isNotNull();

    }

    @Rollback
    @Transactional
    @Test
    void testListIsEmpty(){
        customerRepository.deleteAll();

        List<CustomerDTO> dtos = customerController.findCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testFindAllCustomers(){

        List<CustomerDTO> dtos = customerController.findCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

}