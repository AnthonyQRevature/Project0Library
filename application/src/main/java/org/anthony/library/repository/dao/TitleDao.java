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
import org.anthony.library.repository.entity.TitleEntity;
import org.anthony.library.util.LibraryLogger;

public class TitleDao implements DaoInterface<TitleEntity, Integer> {
    private List<TitleEntity> PackAll(ResultSet rs) throws SQLException
    {
        ArrayList<TitleEntity> ret = new ArrayList<>();
        while(rs.next())
        {
            ret.add(Pack(rs));
        }
        return ret;
    }
    private TitleEntity Pack(ResultSet rs) throws SQLException
    {
        return new TitleEntity
        (
            rs.getInt("isbn"),
            rs.getString("title")
        );
    }

    @Override
    public Integer Create(TitleEntity entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<TitleEntity> findById(Integer id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "SELECT * FROM titles " + 
            "WHERE isbn = ?";
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
    public List<TitleEntity> findAll() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM titles";

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
    public TitleEntity updateById(TitleEntity entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
