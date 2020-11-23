package app.controller;


import app.entity.Customer;
import app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("app/customers")
public class CustomerController {

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;


    @RequestMapping(value = "", method = RequestMethod.POST)
    private Customer insert(@RequestBody Customer customer) {
        return (customerRepository.insert(customer));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private Customer[] getAll() {
        return customerRepository.select();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Customer get(@PathVariable Integer id) {
        return (customerRepository.select(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private Customer put(@PathVariable Integer id, @RequestBody Customer customer) {
        return (customerRepository.update(id, customer));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    private Customer delete(@PathVariable Integer id) {
        return (customerRepository.delete(id));
    }
}
