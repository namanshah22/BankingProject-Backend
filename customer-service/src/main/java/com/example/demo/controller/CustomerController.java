package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Add a new customer
    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    // Get a customer by Aadhaar ID
    @GetMapping("/{aadhaar}")
    public Customer getCustomerById(@PathVariable String aadhaar) {
        return customerService.getCustomerById(aadhaar);
    }

    // Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Delete a customer by Aadhaar ID
    @DeleteMapping("/{aadhaar}")
    public void deleteCustomer(@PathVariable String aadhaar) {
        customerService.deleteCustomer(aadhaar);
    }
    
    // Update an existing customer by Aadhaar ID
    @PutMapping("/{aadhaar}")
    public Customer updateCustomer(@PathVariable String aadhaar, @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(aadhaar, updatedCustomer);
    }
}
