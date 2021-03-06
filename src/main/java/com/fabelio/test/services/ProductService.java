package com.fabelio.test.services;

import com.fabelio.test.model.Product;
import com.fabelio.test.model.SearchLink;
import com.fabelio.test.repository.LinkRepository;
import com.fabelio.test.repository.ProductRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private static final String PROPERTY = "property";

    private ProductRepository productRepository;
    private LinkRepository linkRepository;

    public ProductService(ProductRepository productRepository, LinkRepository linkRepository) {
        this.productRepository = productRepository;
        this.linkRepository = linkRepository;
    }

    public String getProduct(String uri) {
        String result = null;

        boolean isCorrectUri = isFabelioHost(uri);

        if (isCorrectUri) {
            try {
                Document document = Jsoup.connect(uri).get();
                if (document.body() != null) {
                    Elements metaTags = document.getElementsByTag("meta");
                    Elements p = document.getElementsByTag("p");
                    Element element = p.get(2);

                    String productName = metaTags.stream().filter(x -> x.attr(PROPERTY).equals("og:title")).map(x -> x.attr("content")).findAny().orElse("Not Found");
                    String price = metaTags.stream().filter(x -> x.attr(PROPERTY).equals("product:price:amount")).map(x -> x.attr("content")).findAny().orElse("Not Found");
                    String image = metaTags.stream().filter(x -> x.attr(PROPERTY).equals("og:image")).map(x -> x.attr("content")).findAny().orElse("Not Found");
                    String description = element.text();

                    logger.info("product name   : {}", productName);
                    logger.info("price          : {}", price);
                    logger.info("image          : {}", image);
                    logger.info("description    : {}", description);

                    productRepository.save(new Product(productName, description, image, price, new Date()));
                    result = "Success";
                }

            } catch (IOException ioe) {
                logger.error("URL can't be found");
                result = "Failed";
            }
        }

        return result;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(product -> products.add(product));
        return products;
    }

    public Product getProductById(String id) {
        logger.info("product id   : {}", id);
        return productRepository.findOne(Long.valueOf(id));
    }

    private boolean isFabelioHost(String uri) {
        if (uri == null || uri.isEmpty())
            return false;
        else {
            linkRepository.save(new SearchLink(uri, new Date()));
            try {
                URI url = new URI(uri);
                logger.info("URL Host Name  : {}", url.getHost());
                return url.getHost().equals("fabelio.com");
            } catch (URISyntaxException ue) {
                logger.error("URL can't be found");
                return false;
            }
        }
    }
}
