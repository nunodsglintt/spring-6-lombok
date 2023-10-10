package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.models.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController

public class CustomerController {
    @Autowired
    private CustomerService customerService;

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_ID_PATH = CUSTOMER_PATH + "/" + "{customerId}";

    @PatchMapping(CUSTOMER_ID_PATH)
    public ResponseEntity patchCustomer(@PathVariable("customerId") UUID id, @RequestBody Customer customer){
        customerService.patchCustomerById(id, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(CUSTOMER_ID_PATH)
    public ResponseEntity deleteCustomer(@PathVariable("customerId") UUID id){

        customerService.deleteCustomerById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping(CUSTOMER_ID_PATH)
    public ResponseEntity updateById(@PathVariable("customerId") UUID id,@RequestBody Customer customer){
        customerService.updateCustomerById(id, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity createCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @GetMapping(CUSTOMER_PATH)
    public List<Customer> findCustomers(){
        log.debug("Get all customers entered Controller");
        return customerService.findCustomers();
    }

    @GetMapping(CUSTOMER_ID_PATH)
    public Customer findCustomerById(@PathVariable("customerId") UUID id){
        log.debug("Get customer by Id entered Controller");
        return this.customerService.findCustomerById(id).orElseThrow(NotFoundException::new);
    }
}
