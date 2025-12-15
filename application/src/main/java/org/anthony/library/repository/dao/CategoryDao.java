package org.anthony.library.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.anthony.library.repository.ConnectionManager;
import org.anthony.library.repository.entity.Categorization;
import org.anthony.library.util.LibraryLogger;

import javafx.util.Pair;

public class CategoryDao implements DaoInterface<Categorization, Pair<Integer, Integer>>
{
    private List<Categorization> PackAll(ResultSet rs) throws SQLException
    {
        ArrayList<Categorization> ret = new ArrayList<>();
        while(rs.next())
        {
            ret.add(Pack(rs));
        }
        return ret;
    }
    private Categorization Pack(ResultSet rs) throws SQLException
    {
        return new Categorization
        (
            rs.getInt("isbn"),
            rs.getInt("genre_id")
        );
    }

    @Override
    public Pair<Integer, Integer> Create(Categorization entity) throws SQLException {
        Pair<Integer, Integer> val = null;
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "INSERT INTO categories " +
            "VALUES (?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(2, entity.getIsbn());
            ps.executeUpdate();
            val = entity.getKey();
        } catch (SQLException e) {
            LibraryLogger.LogException(e);
        }

        return val;
    }

    @Override
    public Optional<Categorization> findById(Pair<Integer, Integer> id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "SELECT * FROM categories " + 
            "WHERE isbn = ? AND genre_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) 
        {
            ps.setInt(1, id.getKey());
            ps.setInt(2, id.getValue());

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
    public List<Categorization> findAll() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM categories";

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
    public Categorization updateById(Categorization entity) throws SQLException {
        throw new UnsupportedOperationException("Key only table");
    }

    @Override
    public boolean deleteById(Pair<Integer, Integer> id) throws SQLException {
        String sql = 
            "DELETE FROM categories " +
            "WHERE isbn = ? AND genre_id = ?";
        Connection connection = ConnectionManager.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, id.getKey());
            ps.setInt(2, id.getValue());
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
