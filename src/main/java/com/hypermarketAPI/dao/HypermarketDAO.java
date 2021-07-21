package com.hypermarketAPI.dao;

import com.hypermarketAPI.exception.DAOException;
import com.hypermarketAPI.model.Hypermarket;
import com.hypermarketAPI.model.HypermarketInstance;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class HypermarketDAO {
    private final static Logger LOGGER = Logger.getLogger(HypermarketDAO.class.getName());

    private String userName;
    private String password;
    private String url;

    public HypermarketDAO() {
        try (InputStream inputStream = HypermarketDAO.class.getClassLoader().getResourceAsStream("db-config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            userName = properties.getProperty("db.user");
            url = properties.getProperty("db.url");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public long createHypermarket(Hypermarket hypermarket) throws DAOException {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            final DSLContext context = DSL.using(conn, SQLDialect.MYSQL);
            final int numberInserted = context.insertInto(table("HYPERMARKET"), field("HYPERMARKET.NAME"), field("HYPERMARKET.ADDRESS"))
                    .values(hypermarket.getName(), hypermarket.getAddress()).execute();
            if (numberInserted > 0) {
                return context.lastID().longValue();
            }
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
            throw new DAOException(exception);
        }
        LOGGER.severe("INSERT FAILED WITHOUT EXCEPTION");
        throw new DAOException("INSERT FAILED WITHOUT EXCEPTION");
    }

    public HypermarketInstance getHypermarketById(long id) throws DAOException {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            final DSLContext context = DSL.using(conn, SQLDialect.MYSQL);
            final ResultQuery<Record3<Object, Object, Object>> query = context.select(field("HYPERMARKET.HYPERMARKET_ID"), field("HYPERMARKET.NAME"), field("HYPERMARKET.ADDRESS"))
                    .from(table("HYPERMARKET")).where(field("HYPERMARKET.HYPERMARKET_ID").eq(id));
            Result<Record3<Object, Object, Object>> result = query.fetch();
            if (result.size() != 1) {
                throw new DAOException("HYPERMARKET ID NOT FOUND");
            }

            final String name = (String) result.getValue(0, "HYPERMARKET.NAME");
            final String address = (String) result.getValue(0, "HYPERMARKET.ADDRESS");

            final HypermarketInstance hypermarketInstance = new HypermarketInstance();
            hypermarketInstance.setId(id);
            hypermarketInstance.setAddress(address);
            hypermarketInstance.setName(name);
            return hypermarketInstance;

        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
            throw new DAOException(exception);
        }
    }

    public void deleteHypermarketById(final long hypermarketId) throws DAOException {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            final DSLContext context = DSL.using(conn, SQLDialect.MYSQL);
            final Query query = context.delete(table("HYPERMARKET")).where(field("HYPERMARKET.HYPERMARKET_ID").eq(hypermarketId));
            int deletedRecords = query.execute();
            if (deletedRecords == 0) {
                throw new DAOException("HYPERMARKET ID NOT FOUND");
            }
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
            throw new DAOException(exception);
        }
    }
}
