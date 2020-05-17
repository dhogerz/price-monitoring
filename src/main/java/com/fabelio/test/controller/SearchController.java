package com.fabelio.test.controller;

import com.fabelio.test.model.Response;
import com.fabelio.test.model.SearchCriteria;
import com.fabelio.test.services.ProductService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class SearchController {

    private ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
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

}
