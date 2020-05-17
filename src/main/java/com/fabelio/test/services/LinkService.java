package com.fabelio.test.services;

import com.fabelio.test.model.SearchLink;
import com.fabelio.test.repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yoga.pramugia01
 */
@Service
public class LinkService {

    private LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<SearchLink> getAllLink() {
        List<SearchLink> searchLinks = new ArrayList<>();
        linkRepository.findAll().forEach(searchLink -> searchLinks.add(searchLink));
        return searchLinks;
    }
}
