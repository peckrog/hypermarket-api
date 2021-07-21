package com.hypermarketAPI.api;

import com.hypermarketAPI.model.ErrorResponse;
import com.hypermarketAPI.model.GroceryItem;
import com.hypermarketAPI.model.GroceryItemInstance;
import com.hypermarketAPI.model.Hypermarket;
import com.hypermarketAPI.model.HypermarketInstance;
import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

@Path("/hypermarkets")
@Api(description = "the hypermarkets API")
public interface HypermarketsApi {

    @POST
    @Path("/{hypermarketId}/grocery-items")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create a grocery item", notes = "", tags={ "grocery items",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "successfully created grocery item instance", response = GroceryItemInstance.class, responseContainer = "List"),
        @ApiResponse(code = 200, message = "unexpected error", response = ErrorResponse.class, responseContainer = "List") })
    Response createGroceryItem(@PathParam("hypermarketId") @ApiParam("The id of the hypermarket to create the grocery item for") Long hypermarketId,@Valid List<GroceryItem> groceryItem);

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create a hypermarket", notes = "", tags={ "hypermarkets",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Null response", response = Void.class),
        @ApiResponse(code = 200, message = "unexpected error", response = ErrorResponse.class) })
    Response createHypermarket(@Valid Hypermarket hypermarket);

    @DELETE
    @Path("/{hypermarketId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "delete hypermarket", notes = "", tags={ "hypermarkets",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "entry successfully deleted", response = Void.class),
        @ApiResponse(code = 200, message = "unexpected error", response = ErrorResponse.class) })
    Response deleteHypermarketById(@PathParam("hypermarketId") @ApiParam("The id of the hypermarket to retrieve") Long hypermarketId);

    @GET
    @Path("/{hypermarketId}/grocery-items")
    @Produces({ "application/json" })
    @ApiOperation(value = "retrieve all grocery items for a given hypermarket", notes = "", tags={ "grocery items",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "all grocery items for the requested hypermarket", response = GroceryItemInstance.class, responseContainer = "List"),
        @ApiResponse(code = 200, message = "unexpected error", response = ErrorResponse.class, responseContainer = "List") })
    Response getGroceryItemsForHypermarket(@PathParam("hypermarketId") @ApiParam("The id of the hypermarket to create the grocery item for") Long hypermarketId);

    @GET
    @Path("/{hypermarketId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Info for a specific hypermarket", notes = "", tags={ "hypermarkets" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Expected response to a valid request", response = HypermarketInstance.class),
        @ApiResponse(code = 200, message = "unexpected error", response = ErrorResponse.class) })
    Response getHypermarketById(@PathParam("hypermarketId") @ApiParam("The id of the hypermarket to retrieve") Long hypermarketId);
}
