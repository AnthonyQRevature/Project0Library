package org.anthony.library.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.anthony.library.repository.ConnectionManager;
import org.anthony.library.repository.entity.MemberEntity;

@SuppressWarnings("CallToPrintStackTrace")
public class MemberDao implements DaoInterface<MemberEntity, Integer> {

    private List<MemberEntity> PackAll(ResultSet rs) throws SQLException
    {
        ArrayList<MemberEntity> ret = new ArrayList<>();
        while(rs.next())
        {
            ret.add(Pack(rs));
        }
        return ret;
    }
    private MemberEntity Pack(ResultSet rs) throws SQLException
    {
        return new MemberEntity
        (
            rs.getInt("library_card"),
            rs.getString("member_password"),
            rs.getString("member_name")
        );
    }

    //Create
    @Override
    public Integer Create(MemberEntity entity)
    {
        Integer val = null;
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "INSERT INTO members(library_card, member_password, customer_name) " +
            "VALUES (?, ?, ?)" +
            "RETURNING id";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, entity.getLibraryCard());
            ps.setString(2, entity.getMemberPassword());
            ps.setString(3, entity.getMemberName());

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                val = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return val;
    }
    //Read
    @Override
    public Optional<MemberEntity> findById(Integer id) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = 
            "SELECT * FROM members " + 
            "WHERE library_card = ?";
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
            e.printStackTrace();
        }

        return Optional.empty();
    }
    @Override
    public List<MemberEntity> findAll() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM members";

        try (PreparedStatement ps = connection.prepareStatement(sql)) 
        {
            ResultSet rs = ps.executeQuery();
            
            return PackAll(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
    //Update
    @Override
    public MemberEntity updateById(MemberEntity entity) throws SQLException {
        String sql = 
            "UPDATE members " +
            "SET member_password = ?, customer_name = ? " +
            "WHERE library_card = ?";
        Connection connection = ConnectionManager.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, entity.getMemberPassword());
            ps.setString(2, entity.getMemberName());
            ps.setInt(3, entity.getLibraryCard());

            ps.executeUpdate();
            return entity;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    //Delete
    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = 
            "DELETE FROM members " +
            "WHERE library_card = ?";
        Connection connection = ConnectionManager.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, id);
            int val = ps.executeUpdate();
            return val == 1;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return false;
    }
}
