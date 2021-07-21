package com.hypermarketAPI.dao;

import com.hypermarketAPI.exception.DAOException;
import com.hypermarketAPI.model.GroceryItem;
import com.hypermarketAPI.model.GroceryItemInstance;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class GroceryItemDAO {
    private final static Logger LOGGER = Logger.getLogger(HypermarketDAO.class.getName());

    private String userName;
    private String password;
    private String url;

    public GroceryItemDAO() {
        try (InputStream inputStream = GroceryItemDAO.class.getClassLoader().getResourceAsStream("db-config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            userName = properties.getProperty("db.user");
            url = properties.getProperty("db.url");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public List<Long> createGroceryItems(final long hypermarketId, final List<GroceryItem> groceryItems) throws DAOException {
        final List<Long> createdGroceryItemIds = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            for (final GroceryItem groceryItem: groceryItems) {
                final DSLContext context = DSL.using(conn, SQLDialect.MYSQL);
                final int numberInserted = context.insertInto(table("GROCERY_ITEM"),
                        field("GROCERY_ITEM.HYPERMARKET_ID"), field("GROCERY_ITEM.ITEM_NAME"),
                        field("GROCERY_ITEM.BRAND"), field("GROCERY_ITEM.EXPIRATION_DATE")).values(hypermarketId,
                        groceryItem.getItemName(), groceryItem.getBrand(), groceryItem.getExpirationDate()).execute();
                if (numberInserted > 0) {
                    createdGroceryItemIds.add(context.lastID().longValue());
                }
            }

        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
            throw new DAOException(exception);
        }
        return createdGroceryItemIds;
    }

    public GroceryItemInstance getGroceryItemById(final long groceryItemId) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            final DSLContext context = DSL.using(conn, SQLDialect.MYSQL);
            final ResultQuery<Record4<Object, Object, Object, Object>> resultQuery = context.select(field("GROCERY_ITEM.HYPERMARKET_ID"),
                    field("GROCERY_ITEM.ITEM_NAME"), field("GROCERY_ITEM.BRAND"),
                    field("GROCERY_ITEM.EXPIRATION_DATE")).from(table("GROCERY_ITEM")).where(field("GROCERY_ITEM.GROCERY_ITEM_ID").eq(groceryItemId));
            final Result<Record4<Object, Object, Object, Object>> result = resultQuery.fetch();
            final int hypermarketId = (int) result.getValue(0, field("GROCERY_ITEM.HYPERMARKET_ID"));
            final String itemName = (String) result.getValue(0, field("GROCERY_ITEM.ITEM_NAME"));
            final String brand = (String) result.getValue(0, field("GROCERY_ITEM.BRAND"));
            final Date expirationDate = (Date) result.getValue(0, field("GROCERY_ITEM.EXPIRATION_DATE"));

            final GroceryItemInstance groceryItemInstance = new GroceryItemInstance();

            groceryItemInstance.setId(groceryItemId);
            groceryItemInstance.setHypermarketId((long) hypermarketId);
            groceryItemInstance.setItemName(itemName);
            groceryItemInstance.setBrand(brand);
            groceryItemInstance.setExpirationDate(expirationDate.toLocalDate());

            return groceryItemInstance;
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
            throw new DAOException(exception);
        }
    }

    public List<GroceryItemInstance> getGroceryItemByHypermarketId(final long hypermarketId) {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            final DSLContext context = DSL.using(conn, SQLDialect.MYSQL);
            final ResultQuery<Record5<Object, Object, Object, Object, Object>> resultQuery = context.select(field("GROCERY_ITEM.GROCERY_ITEM_ID"), field("GROCERY_ITEM.HYPERMARKET_ID"),
                    field("GROCERY_ITEM.ITEM_NAME"), field("GROCERY_ITEM.BRAND"),
                    field("GROCERY_ITEM.EXPIRATION_DATE")).from(table("GROCERY_ITEM")).where(field("GROCERY_ITEM.HYPERMARKET_ID").eq(hypermarketId));
            final Result<Record5<Object, Object, Object, Object, Object>> result = resultQuery.fetch();

            final List<GroceryItemInstance> groceryItemInstances = new ArrayList<>();
            for (int i= 0; i < result.size(); i++) {
                final int groceryItemId = (int) result.getValue(i, field("GROCERY_ITEM.GROCERY_ITEM_ID"));
                final String itemName = (String) result.getValue(i, field("GROCERY_ITEM.ITEM_NAME"));
                final String brand = (String) result.getValue(i, field("GROCERY_ITEM.BRAND"));
                final Date expirationDate = (Date) result.getValue(i, field("GROCERY_ITEM.EXPIRATION_DATE"));

                final GroceryItemInstance groceryItemInstance = new GroceryItemInstance();
                groceryItemInstance.setId((long) groceryItemId);
                groceryItemInstance.setHypermarketId(hypermarketId);
                groceryItemInstance.setItemName(itemName);
                groceryItemInstance.setBrand(brand);
                groceryItemInstance.setExpirationDate(expirationDate.toLocalDate());

                groceryItemInstances.add(groceryItemInstance);
            }
            return groceryItemInstances;
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
            throw new DAOException(exception);
        }
    }
}
