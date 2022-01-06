package com.speedhome.poc.service.search;

import com.speedhome.poc.service.entity.PropertyEntity;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class PropertySearch {
    private static final String PROPERTY_NAME = "name";
    private static final String PROPERTY_DESC = "desc";
    private static final String PROPERTY_TYPE = "type";
    private static final String PROPERTY_STATUS = "status";
    private static final String PROPERTY_STATUS_COMMENT = "statusComment";

    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public PropertySearch(EntityManager entityManager,
                          EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManager;
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<PropertyEntity> searchProperty(String searchTerm) {

        entityManager = entityManagerFactory.createEntityManager();

        FullTextEntityManager fullTextEntityManager = initializedFullTextEntityManager();

        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(PropertyEntity.class ).get();

        BooleanJunction<BooleanJunction> booleanJunction =
                HibernateSearchUtil.buildBooleanJunctionSearch(
                        qb,
                        searchTerm,
                        PROPERTY_NAME,
                        PROPERTY_DESC,
                        PROPERTY_TYPE,
                        PROPERTY_STATUS,
                        PROPERTY_STATUS_COMMENT);

        FullTextQuery query =
                fullTextEntityManager.createFullTextQuery(booleanJunction.createQuery(), PropertyEntity.class);

        List<PropertyEntity> propertyEntities = query.getResultList();

        commitTransaction();

        return propertyEntities;
    }

    private FullTextEntityManager initializedFullTextEntityManager() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        entityManager.getTransaction().begin();

        return fullTextEntityManager;
    }

    private void commitTransaction() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
