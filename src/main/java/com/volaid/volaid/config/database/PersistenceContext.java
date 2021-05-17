package com.volaid.volaid.config.database;

import com.volaid.volaid.util.Constants;
import org.apache.log4j.Logger;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

@Configuration
public class PersistenceContext {

    private static Logger LOGGER = Logger.getLogger(PersistenceContext.class);

    @Value("${neo4j.uri}")
    private String DB_URI;

    @Value("${neo4j.username}")
    private String DB_USERNAME;

    @Value("${neo4j.password}")
    private String DB_PASSWORD;

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {

        LOGGER.info("Configuring database...");

        /*
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri(DB_URI)
                .credentials(DB_USERNAME, DB_PASSWORD)
                .verifyConnection(true)
                .build();
        */

        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri(System.getenv("GRAPHENEDB_BOLT_URL"))
                .credentials(System.getenv("GRAPHENEDB_BOLT_USER"), System.getenv("GRAPHENEDB_BOLT_PASSWORD"))
                .verifyConnection(true)
                .build();

        LOGGER.info("Configuration for database is done...");

        return configuration;
    }

    @Bean
    public SessionFactory sessionFactory() {
        LOGGER.info("Creating SessionFactory with the given Configuration...");

        return new SessionFactory(getConfiguration(), Constants.PATH_DOMAIN);
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        LOGGER.info("Creating Transaction Manager with the given SessionFactory...");

        return new Neo4jTransactionManager(sessionFactory());
    }

}