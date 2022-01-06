package com.speedhome.poc.service.search;

import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.StringTokenizer;

public class HibernateSearchUtil {

    public static final String REGEX = "[^a-zA-Z0-9_/-]";
    public static final String SPACE = " ";
    public static final String MORE_SPACE = "\\s+";
    private static final String WILDCARD = "*";

    public static String removeSpecialCharacter(String term) {
        term = term.replaceAll(REGEX, SPACE);

        term = term.replaceAll(MORE_SPACE, SPACE);

        return term.trim();
    }

    public static BooleanJunction<BooleanJunction> buildBooleanJunctionSearch(QueryBuilder qb,
                                                                              String searchTerm,
                                                                              String ... onFields) {
        BooleanJunction<BooleanJunction> booleanJunction = qb.bool();

        String term = removeSpecialCharacter(searchTerm);

        StringTokenizer st = new StringTokenizer(term, SPACE);

        booleanJunction.should(qb.keyword()
                .onFields(onFields)
                .ignoreFieldBridge()
                .matching(searchTerm.toLowerCase(Locale.US))
                .createQuery());


        while(st.hasMoreElements()) {
            String keyword = st.nextElement().toString();

            if (keyword.length() >= 3) {
                booleanJunction.should(qb.keyword().wildcard()
                        .onFields(onFields)
                        .ignoreFieldBridge()
                        .matching(WILDCARD + keyword.toLowerCase(Locale.US) + WILDCARD)
                        .createQuery());
            }
        }


        return booleanJunction;
    }

    public static String decodeUrl(String term) {
        try {
            return URLDecoder.decode(term, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return term;
    }
}
