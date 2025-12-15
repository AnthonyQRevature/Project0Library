package org.anthony.library.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.anthony.library.repository.ConnectionManager;
import org.anthony.library.repository.entity.Borrow;
import org.anthony.library.util.LibraryLogger;

public class BorrowDao implements DaoInterface<Borrow, Integer>
{
    private List<Borrow> PackAll(ResultSet rs) throws SQLException
    {
        ArrayList<Borrow> ret = new ArrayList<>();
        while(rs.next())
        {
            ret.add(Pack(rs));
        }
        return ret;
    }
    private Borrow Pack(ResultSet rs) throws SQLException
    {
        return new Borrow
        (
            rs.getInt("book_id"),
            rs.getInt("library_card_num"),
            rs.getDate("checkout_date"),
            rs.getDate("due_date")
        );
    }

    @Override
    public Integer Create(Borrow entity) throws SQLException {
        Integer val = null;
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "INSERT INTO borrows(book_id, library_card_num, checkout_date, due_date, fine) " +
            "VALUES (?, ?, ?, ?)" +
            "RETURNING id";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, entity.getBook_id());
            ps.setInt(2, entity.getLibrary_card_number());
            ps.setDate(3, entity.getCheckout_date());
            ps.setDate(4, entity.getDue_date());

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                val = rs.getInt("id");
            }
        } catch (SQLException e) {
            LibraryLogger.LogException(e);
        }

        return val;
    }

    @Override
    public Optional<Borrow> findById(Integer id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "SELECT * FROM borrows " + 
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

    @Override
    public List<Borrow> findAll() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM borrows";

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
    public Borrow updateById(Borrow entity) throws SQLException {
        String sql = 
            "UPDATE borrows " +
            "SET library_card_num = ?, checkout_date = ?, due_date = ? " +
            "WHERE book_id = ?";
        Connection connection = ConnectionManager.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, entity.getLibrary_card_number());
            ps.setDate(2, entity.getCheckout_date());
            ps.setDate(3, entity.getDue_date());
            ps.setInt(4, entity.getBook_id());

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
            "DELETE FROM borrow " +
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
