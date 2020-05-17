package com.fabelio.test.controller;

import com.fabelio.test.model.Product;
import com.fabelio.test.model.Response;
import com.fabelio.test.model.SearchCriteria;
import com.fabelio.test.services.LinkService;
import com.fabelio.test.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(controllers = SearchController.class, secure = false)
public class SearchControllerTest {

    private MockMvc mvc;

    @Mock
    private ProductService productService;

    @Mock
    private LinkService linkService;

    @InjectMocks
    private SearchController searchController;

    @Before
    public void initialize() {
        mvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    public void searchUrl() throws Exception {
        String url = "/api/search";
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setUri("test.com");
        Mockito.when(productService.getProduct(Mockito.anyString())).thenReturn("SUCCESS");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(searchCriteria));
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        Response response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Response.class);
        assertThat(response.getMsg()).isNotNull();
    }

    @Test
    public void searchAllProducts() throws Exception {
        String url = "/api/search/products";
        Mockito.when(productService.getAllProducts()).thenReturn(new ArrayList<>());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isNotNull();
    }

    @Test
    public void searchProductById() throws Exception {
        String url = "/api/search/products/{id}";
        Mockito.when(productService.getProductById(Mockito.anyString()))
                .thenReturn(new Product("Test", "Deskripsi", "image.jpg", "2500000", new Date()));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url, "1");
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isNotNull();
    }

    @Test
    public void searchAllLinks() throws Exception {
        String url = "/api/search/links";
        Mockito.when(linkService.getAllLink()).thenReturn(new ArrayList<>());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isNotNull();
    }
}
