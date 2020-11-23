package app.controller;

import app.entity.Product;
import app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app/products")
public class ProductController {

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;


    @RequestMapping(value = "", method = RequestMethod.POST)
    private Product insert(@RequestBody Product product) {
        return (productRepository.insert(product));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private Product[] getAll() {
        return productRepository.select();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Product get(@PathVariable Integer id) {
        return (productRepository.select(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private Product put(@PathVariable Integer id, @RequestBody Product product) {
        return (productRepository.update(id, product));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    private Product delete(@PathVariable Integer id) {
        return (productRepository.delete(id));
    }
}
