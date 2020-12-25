package app.controller;


import app.entity.Order;
import app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = { "http://localhost:8888" })
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    @Qualifier("orderRepository")
    private OrderRepository orderRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    private Order insert(@RequestBody Order order) {
        return (orderRepository.insert(order));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private Order[] getAll() {
        return orderRepository.select();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Order get(@PathVariable Integer id) {
        return (orderRepository.select(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private Order put(@PathVariable Integer id, @RequestBody Order order) {
        return (orderRepository.update(id, order));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    private Order delete(@PathVariable Integer id) {
        return (orderRepository.delete(id));
    }


}
