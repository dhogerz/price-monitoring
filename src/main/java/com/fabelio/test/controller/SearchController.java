package com.fabelio.test.controller;

import com.fabelio.test.model.Product;
import com.fabelio.test.model.Response;
import com.fabelio.test.model.SearchCriteria;
import com.fabelio.test.model.SearchLink;
import com.fabelio.test.services.LinkService;
import com.fabelio.test.services.ProductService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SearchController {

    private ProductService productService;
    private LinkService linkService;

    public SearchController(ProductService productService, LinkService linkService) {
        this.productService = productService;
        this.linkService = linkService;
    }

    @PostMapping("/api/search")
    public ResponseEntity<Response> searchUrl(@Valid @RequestBody SearchCriteria search, Errors errors) {

        Response result = new Response();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }

        String message = productService.getProduct(search.getUri());
        result.setMsg(message);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/search/products")
    public List<Product> searchAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/api/search/products/{id}")
    public Product searchProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/api/search/links")
    public List<SearchLink> searchAllLinks() {
        return linkService.getAllLink();
    }

}
