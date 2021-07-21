package com.hypermarketAPI.exception;

import com.hypermarketAPI.model.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DAOException extends RuntimeException implements ExceptionMapper<DAOException> {

  public DAOException() {
    super();
  }

  public DAOException(Throwable throwable) {
    super(throwable);
  }

  public DAOException(String message) {
    super(message);
  }

  @Override
  public Response toResponse(DAOException daoException) {
    final ErrorResponse errorResponse = new ErrorResponse().message(daoException.getMessage());
    if (daoException.getMessage().contains("FOREIGN KEY")) {
      return Response.status(Response.Status.CONFLICT).entity(errorResponse).build();
    } else if (daoException.getMessage().contains("ID NOT FOUND")) {
      return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
  }
}
