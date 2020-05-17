package com.fabelio.test.repository;

import com.fabelio.test.model.SearchLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends CrudRepository<SearchLink, Long> {


}
