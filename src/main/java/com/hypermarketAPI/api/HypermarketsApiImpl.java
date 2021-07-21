package com.hypermarketAPI.api;

import com.hypermarketAPI.dao.GroceryItemDAO;
import com.hypermarketAPI.dao.HypermarketDAO;
import com.hypermarketAPI.model.*;
import jakarta.ws.rs.core.Response;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class HypermarketsApiImpl implements HypermarketsApi {
    @Override
    public Response createGroceryItem(Long hypermarketId, List<GroceryItem> groceryItem) {
        final GroceryItemDAO groceryItemDAO = new GroceryItemDAO();
        final List<Long> createdGroceryItemIds = groceryItemDAO.createGroceryItems(hypermarketId, groceryItem);

        final List<GroceryItemInstance> createdGroceryItemInstanceList = new ArrayList<>();
        for (long createdGroceryItemId: createdGroceryItemIds) {
            final GroceryItemInstance groceryItemInstance = new GroceryItemInstance();
            final GroceryItemInstance createdGroceryItem = groceryItemDAO.getGroceryItemById(createdGroceryItemId);
            groceryItemInstance.setId(createdGroceryItemId);
            groceryItemInstance.setItemName(createdGroceryItem.getItemName());
            groceryItemInstance.setBrand(createdGroceryItem.getBrand());
            groceryItemInstance.setExpirationDate(createdGroceryItem.getExpirationDate());
            groceryItemInstance.setHypermarketId(hypermarketId);
            createdGroceryItemInstanceList.add(groceryItemInstance);
        }
        return Response.status(Response.Status.CREATED).entity(createdGroceryItemInstanceList).build();

    }

    @Override
    public Response createHypermarket(Hypermarket hypermarket) {
        final HypermarketDAO hypermarketDAO = new HypermarketDAO();
        final long newHypermarketId = hypermarketDAO.createHypermarket(hypermarket);
        final HypermarketInstance hypermarketInstance = new HypermarketInstance();
        hypermarketInstance.setId(newHypermarketId);
        hypermarketInstance.setName(hypermarket.getName());
        hypermarketInstance.setAddress(hypermarket.getAddress());

        return Response.status(Response.Status.CREATED).entity(hypermarketInstance).build();
    }

    @Override
    public Response deleteHypermarketById(Long hypermarketId) {
        final HypermarketDAO hypermarketDAO = new HypermarketDAO();
        hypermarketDAO.deleteHypermarketById(hypermarketId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    public Response getGroceryItemsForHypermarket(Long hypermarketId) {
        final GroceryItemDAO groceryItemDAO = new GroceryItemDAO();
        final List<GroceryItemInstance> groceryItemInstances = groceryItemDAO.getGroceryItemByHypermarketId(hypermarketId);

        return Response.status(Response.Status.OK).entity(groceryItemInstances).build();
    }

    @Override
    public Response getHypermarketById(Long hypermarketId) {
        final HypermarketDAO hypermarketDAO = new HypermarketDAO();
        final HypermarketInstance hypermarketInstance = hypermarketDAO.getHypermarketById(hypermarketId);
        return Response.status(Response.Status.OK).entity(hypermarketInstance).build();
    }
}
