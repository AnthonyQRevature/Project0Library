package org.anthony.library.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.anthony.library.repository.ConnectionManager;
import org.anthony.library.repository.entity.BookEntity;
import org.anthony.library.util.LibraryLogger;

public class BookDao implements DaoInterface<BookEntity, Integer>{
    private List<BookEntity> PackAll(ResultSet rs) throws SQLException
    {
        ArrayList<BookEntity> ret = new ArrayList<>();
        while(rs.next())
        {
            ret.add(Pack(rs));
        }
        return ret;
    }
    private BookEntity Pack(ResultSet rs) throws SQLException
    {
        return new BookEntity
        (
            rs.getInt("book_id"),
            rs.getInt("isbn")
        );
    }

    @Override
    public Integer Create(BookEntity entity) throws SQLException {
        Integer val = null;
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "INSERT INTO books(isbn) " +
            "VALUES (?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(2, entity.getIsbn());
            ps.executeUpdate();
            val = entity.getBook_id();
        } catch (SQLException e) {
            LibraryLogger.LogException(e);
        }

        return val;
    }

    @Override
    public Optional<BookEntity> findById(Integer id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "SELECT * FROM books " + 
            "WHERE book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) 
        {
            ps.setInt(1, id);

            var rs = ps.executeQuery();
            if (rs.next())
            {
                return Optional.of(Pack(rs));
            }
            else
            {
                return Optional.empty();
            }
        }
        catch (SQLException e) {
            LibraryLogger.LogException(e);
        }

        return Optional.empty();
    }

    public Optional<BookEntity> ObtainBookByIsbn(Integer isbn) throws SQLException
    {
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "SELECT * FROM books " + 
            "WHERE isbn = ?" + 
            "LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) 
        {
            ps.setInt(1, isbn);

            var rs = ps.executeQuery();
            if (rs.next())
            {
                return Optional.of(Pack(rs));
            }
            else
            {
                return Optional.empty();
            }
        }
        catch (SQLException e) {
            LibraryLogger.LogException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<BookEntity> findAll() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM books";

        try (PreparedStatement ps = connection.prepareStatement(sql)) 
        {
            ResultSet rs = ps.executeQuery();
            
            return PackAll(rs);
        } catch (SQLException e) {
            LibraryLogger.LogException(e);
        }

        return new ArrayList<>();
    }

    @Override
    public BookEntity updateById(BookEntity entity) throws SQLException {
        String sql = 
            "UPDATE books " +
            "SET isbn = ? " +
            "WHERE book_id = ?";
        Connection connection = ConnectionManager.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, entity.getIsbn());
            ps.setInt(2, entity.getBook_id());

            ps.executeUpdate();
            return entity;
        }
        catch (SQLException e)
        {
            LibraryLogger.LogException(e);
        }
        
        return null;
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = 
            "DELETE FROM books " +
            "WHERE book_id = ?";
        Connection connection = ConnectionManager.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, id);
            int val = ps.executeUpdate();
            return val == 1;
        }
        catch (SQLException e)
        {
            LibraryLogger.LogException(e);
        }
        
        return false;
    }

}
