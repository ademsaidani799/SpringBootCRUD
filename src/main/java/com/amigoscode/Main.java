package com.amigoscode;


import jakarta.websocket.server.PathParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@SpringBootApplication
@SpringBootApplication
@RestController
@RequestMapping("/api/v1/customers")
public class Main {
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);

    }

    @GetMapping()
    public List<Customer> getAllCustomer(){
        List<Customer> list = this.customerRepository.findAll();
        return list;
    }

    record NewRequest(String name, String email, int age){}
    @PostMapping
    public void addNewUser(@RequestBody NewRequest req){
        Customer c = new Customer();
        c.setAge(req.age());
        c.setEmail(req.email());
        c.setName(req.name());
        customerRepository.save(c);

    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") int id){
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@RequestBody NewRequest req , @PathVariable("customerId") int id){

        customerRepository.findById(id).map(c->{
            c.setName(req.name());
            c.setEmail(req.email());
            c.setAge(req.age());
        return customerRepository.save(c);
        });
    }

}
