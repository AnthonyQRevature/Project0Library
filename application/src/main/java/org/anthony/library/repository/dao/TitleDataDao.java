package org.anthony.library.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.anthony.library.repository.ConnectionManager;
import org.anthony.library.repository.entity.TitleDataEntity;
import org.anthony.library.util.LibraryLogger;

public class TitleDataDao 
{
    private List<TitleDataEntity> PackAll(ResultSet rs) throws SQLException
    {
        ArrayList<TitleDataEntity> ret = new ArrayList<>();
        while(rs.next())
        {
            ret.add(Pack(rs));
        }
        return ret;
    }
    private TitleDataEntity Pack(ResultSet rs) throws SQLException
    {
        return new TitleDataEntity
        (
            rs.getInt("isbn"),
            rs.getString("title"),
            rs.getInt("num_copies"),
            rs.getString("genre")
        );
    }

    public Optional<TitleDataEntity> findById(Integer id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "SELECT * FROM title_data " + 
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

    public List<TitleDataEntity> RetrieveAllTitleData() throws SQLException
    {
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM title_data";

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            var rs = ps.executeQuery();
            return PackAll(rs);
        }
        catch(SQLException e)
        {
            LibraryLogger.LogException(e);
            return new ArrayList<>();
        }
    }
}
