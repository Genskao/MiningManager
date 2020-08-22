package fr.tropweb.miningmanager.data.impl;

import fr.tropweb.miningmanager.data.BlockDAO;
import fr.tropweb.miningmanager.exception.DatabaseException;
import fr.tropweb.miningmanager.pojo.BlockLite;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlockLiteDAO implements BlockDAO<BlockLite> {
    private static final String EXISTS_QUERY = "SELECT 1 AS OK FROM precious_ore WHERE x = ? AND y = ? AND z = ? AND world = ?";
    private static final String INSERT_QUERY = "INSERT INTO precious_ore(x, y, z, world, material, placed) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM precious_ore WHERE x = ? AND y = ? AND z = ? AND world = ?";

    private final Connection connection;

    public BlockLiteDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean exist(final BlockLite blockLite) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(EXISTS_QUERY)) {
            preparedStatement.setInt(1, blockLite.getX());
            preparedStatement.setInt(2, blockLite.getY());
            preparedStatement.setInt(3, blockLite.getZ());
            preparedStatement.setString(4, blockLite.getWorld());

            if (preparedStatement.execute()) {
                final ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 1;
                }
            } else {
                throw new DatabaseException("Impossible, you should have result set for this request.");
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
        return false;
    }

    @Override
    public void save(@NotNull final BlockLite blockLite) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, blockLite.getX());
            preparedStatement.setInt(2, blockLite.getY());
            preparedStatement.setInt(3, blockLite.getZ());
            preparedStatement.setString(4, blockLite.getWorld());
            preparedStatement.setString(5, blockLite.getMaterial().name());
            preparedStatement.setBoolean(6, blockLite.getPlacedByPlayer());

            if (preparedStatement.executeUpdate() != 1) {
                throw new DatabaseException("Impossible, you should have result set for this request.");
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void delete(final BlockLite blockLite) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setInt(1, blockLite.getX());
            preparedStatement.setInt(2, blockLite.getY());
            preparedStatement.setInt(3, blockLite.getZ());
            preparedStatement.setString(4, blockLite.getWorld());

            if (preparedStatement.executeUpdate() != 1) {
                throw new DatabaseException("Impossible, you should have result set for this request.");
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
