package com.speedhome.poc.service.config;

import com.speedhome.poc.service.search.SearchServiceInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@EnableAutoConfiguration
@Configuration
public class HibernateSearchConfig {

    private final EntityManager entityManager;

    @Autowired
    public HibernateSearchConfig(final EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Bean
    SearchServiceInit initIndexer() {

        SearchServiceInit initializeIndex = new SearchServiceInit(entityManager);

        initializeIndex.initializeHibernateSearch();

        return initializeIndex;
    }

}
