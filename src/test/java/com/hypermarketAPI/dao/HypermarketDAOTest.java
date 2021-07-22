package com.hypermarketAPI.dao;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mockStatic;

import com.hypermarketAPI.exception.DAOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HypermarketDAOTest {

  private final HypermarketDAO hypermarketDAO = new HypermarketDAO();

  //todo why does this test not work
//  @Test
//  public void getHypermarketById_noResults_exceptionThrown() {
//    try (MockedStatic<DriverManager> driverManagerMock = mockStatic(DriverManager.class)) {
//      final MockDataProvider provider = new MockDataProvider() {
//        @Override
//        public MockResult[] execute(MockExecuteContext ctx) throws SQLException {
//          return new MockResult[]{new MockResult(0)};
//        } };
//      final MockConnection connection = new MockConnection(provider);
//      driverManagerMock
//          .when(() -> DriverManager.getConnection(any(), any(), any())).thenReturn(connection);
//
//      final DAOException daoException = Assert.assertThrows(DAOException.class, () -> {
//        hypermarketDAO.getHypermarketById(2);
//      });
//      assertTrue(daoException.getMessage().contains("HYPERMARKET ID NOT FOUND"));
//    }
//  }

  @Test
  public void deleteHypermarketById_noResults_exceptionThrown() {
    try (MockedStatic<DriverManager> driverManagerMock = mockStatic(DriverManager.class)) {
      final MockDataProvider provider = ctx -> new MockResult[]{ new MockResult(0) };
      final MockConnection connection = new MockConnection(provider);
      driverManagerMock
          .when(() -> DriverManager.getConnection(any(), any(), any())).thenReturn(connection);

      final DAOException daoException = Assert.assertThrows(DAOException.class, () -> {
        hypermarketDAO.deleteHypermarketById(2);
      });
      assertTrue(daoException.getMessage().contains("HYPERMARKET ID NOT FOUND"));
    }
  }

  @Test
  public void deleteHypermarketById_sqlExceptionOccurs_exceptionWrappedAndThrown() {
    final SQLException sqlException = new SQLException("testSqlException");
    try (MockedStatic<DriverManager> driverManagerMock = mockStatic(DriverManager.class)) {
      final MockDataProvider provider = ctx -> {
        throw sqlException;
      };
      final MockConnection connection = new MockConnection(provider);
      driverManagerMock
          .when(() -> DriverManager.getConnection(any(), any(), any())).thenReturn(connection);

      final DAOException daoException = Assert.assertThrows(DAOException.class, () -> {
        hypermarketDAO.deleteHypermarketById(2);
      });
      // jOOQ testing libraries wrap the exception
      assertTrue(daoException.getCause().getMessage().contains(sqlException.getMessage()));
    }
  }
}