package exp17d.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exp17d.jpa.model.Customer;
import exp17d.jpa.repository.CustomerRepository;

@RestController
@RequestMapping("/rest/v1/customer")
public class CustomerRestController {
    @Autowired
    CustomerRepository repository;
       
    // NOTE ilker below is really WRONG thing to do from REST point of view - TODO deleteMe
    @RequestMapping("/save")
    public String process(){
        repository.save(new Customer("Jack", "Smith"));
        repository.save(new Customer("Adam", "Johnson"));
        repository.save(new Customer("Kim", "Smith"));
        repository.save(new Customer("David", "Williams"));
        repository.save(new Customer("Peter", "Davis"));
        return "Done";
    }
       
    @RequestMapping("/findall")
    public String findAll(){
        String result = "";
           
        for(Customer cust : repository.findAll()){
            result += cust.toString() + "</br>";
        }
           
        return result;
    }
       
    @RequestMapping("/findbyid")
    public String findById(@RequestParam("id") long id){
//      String result = repository.findOne(id).toString();
    	// NOTE ilker CrudRepository in SpringBoot 1.5.3.RELEASE had above findOne, but in 2.2.6.RELEASE it is replaced with below findById
        Optional<Customer> oCustomer = repository.findById(id);
        String result = oCustomer.isPresent() ? oCustomer.get().toString() : null;
        return result;
    }
       
    @RequestMapping("/findbylastname")
    public String fetchDataByLastName(@RequestParam("lastname") String lastName){
        String result = "";
           
        for(Customer cust: repository.findByLastName(lastName)){
            result += cust.toString() + "</br>"; 
        }
           
        return result;
    }
}