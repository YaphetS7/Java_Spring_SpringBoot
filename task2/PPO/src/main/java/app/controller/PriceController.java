package app.controller;

import app.entity.Price;
import app.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app/prices")
public class PriceController {

    @Autowired
    @Qualifier("priceRepository")
    private PriceRepository priceRepository;


    @RequestMapping(value = "", method = RequestMethod.POST)
    private Price insert(@RequestBody Price price) {
        System.out.println(price.getPrice());
        return (priceRepository.insert(price));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private Price[] getAll() {
        return priceRepository.select();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Price get(@PathVariable Integer id) {
        return (priceRepository.select(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private Price put(@PathVariable Integer id, @RequestBody Price price) {
        return (priceRepository.update(id, price));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    private Price delete(@PathVariable Integer id) {
        return (priceRepository.delete(id));
    }

}
