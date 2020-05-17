package com.fabelio.test.services;

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

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private static final String PROPERTY = "property";

    public String getProduct(String uri) {
        String result = null;
        if (uri != null || uri.isEmpty())
            logger.info("URL            : {}", uri);

        try {
            URI url = new URI(uri);
            logger.info("URL Host Name  : {}", url.getHost());
        } catch (URISyntaxException ue) {
            logger.error("URL can't be found");
            result = "Failed";
        }

        System.out.println("test");

        try {
            Document document = Jsoup.connect(uri).get();
            if (document.body() != null) {
                Elements metaTags = document.getElementsByTag("meta");
                Elements p = document.getElementsByTag("p");
                Element element = p.get(2);

                String product = metaTags.stream().filter(x -> x.attr(PROPERTY).equals("og:title")).map(x -> x.attr("content")).findAny().orElse("Not Found");
                String price = metaTags.stream().filter(x -> x.attr(PROPERTY).equals("product:price:amount")).map(x -> x.attr("content")).findAny().orElse("Not Found");
                String image = metaTags.stream().filter(x -> x.attr(PROPERTY).equals("og:image")).map(x -> x.attr("content")).findAny().orElse("Not Found");
                String description = element.text();

                logger.info("product        : {}", product);
                logger.info("price          : {}", price);
                logger.info("image          : {}", image);
                logger.info("description    : {}", description);

                result = "Success";
            }

        } catch (IOException ioe) {
            logger.error("URL can't be found");
            result = "Failed";
        }
        return result;
    }
}
