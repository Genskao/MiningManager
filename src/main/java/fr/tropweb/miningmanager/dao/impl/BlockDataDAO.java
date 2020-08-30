package fr.tropweb.miningmanager.dao.impl;

import fr.tropweb.miningmanager.dao.BlockDAO;
import fr.tropweb.miningmanager.exception.DatabaseException;
import fr.tropweb.miningmanager.pojo.BlockData;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static fr.tropweb.miningmanager.dao.fields.BlockDataFields.*;

public class BlockDataDAO extends AbstractDAO implements BlockDAO<BlockData> {
    private static final String LIST_QUERY = "SELECT * FROM precious_ore LIMIT 100";
    private static final String SELECT_QUERY = "SELECT * FROM precious_ore WHERE x = ? AND y = ? AND z = ? AND world = ? LIMIT 1";
    private static final String INSERT_QUERY = "INSERT INTO precious_ore(x, y, z, world, material, placed, blocked) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE precious_ore SET x = ?, y = ?, z = ?, world = ?, material = ?, placed = ?, blocked = ? WHERE x = ? AND y = ? AND z = ? AND world = ?";
    private static final String DELETE_QUERY = "DELETE FROM precious_ore WHERE x = ? AND y = ? AND z = ? AND world = ?";
    private static final String EXISTS_QUERY = "SELECT 1 AS OK FROM precious_ore WHERE x = ? AND y = ? AND z = ? AND world = ? LIMIT 1";
    private static final String RANDOM_BLOCK_QUERY = "SELECT * FROM precious_ore WHERE world = ? AND placed = 0 AND blocked = 0 ORDER BY RANDOM() LIMIT 1";
    private static final String RANDOM_WORLD_QUERY = "SELECT world from precious_ore WHERE world IN (?) GROUP BY world ORDER BY RANDOM() LIMIT 1";
    private static final String UNBLOCK_QUERY = "UPDATE precious_ore set blocked = 0 WHERE blocked = 1";

    private final Connection connection;

    public BlockDataDAO(final Connection connection) {
        this.connection = connection;
    }

    private static BlockData convertToObject(final ResultSet resultSet) {

        // create entity
        final BlockData blockData = new BlockData();

        // retrieve data information
        try {
            blockData.setX(getInt(resultSet, X));
            blockData.setY(getInt(resultSet, Y));
            blockData.setZ(getInt(resultSet, Z));
            blockData.setWorld(getString(resultSet, WORLD));
            blockData.setMaterial(getMaterial(resultSet, MATERIAL));
            blockData.setPlacedByPlayer(getBoolean(resultSet, PLACED));
            blockData.setBlocked(getBoolean(resultSet, BLOCKED));
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }

        // cannot be null
        return blockData;
    }

    @Override
    public List<BlockData> list() {
        // create list
        final List<BlockData> list = new ArrayList<>();

        // request to have a list
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(LIST_QUERY)) {

            // execute
            if (!preparedStatement.execute())
                return list;

            // if there is result
            final ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {

                // convert data to BlockLite
                final BlockData blockLite = convertToObject(resultSet);

                // add to the list
                list.add(blockLite);
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }

        return list;
    }

    @Override
    public BlockData select(final BlockData blockData) {

        // request to have the entity
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_QUERY)) {
            int index = 1;

            // where
            preparedStatement.setInt(index++, blockData.getX());
            preparedStatement.setInt(index++, blockData.getY());
            preparedStatement.setInt(index++, blockData.getZ());
            preparedStatement.setString(index++, blockData.getWorld());

            // execute
            if (preparedStatement.execute()) {

                // if there is result
                final ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {

                    // return converted data
                    return convertToObject(resultSet);
                }
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }

        // nothing to return;
        return null;
    }

    @Override
    public void save(@NotNull final BlockData blockData) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_QUERY)) {
            int index = 1;

            // save
            preparedStatement.setInt(index++, blockData.getX());
            preparedStatement.setInt(index++, blockData.getY());
            preparedStatement.setInt(index++, blockData.getZ());
            preparedStatement.setString(index++, blockData.getWorld());
            preparedStatement.setString(index++, blockData.getMaterial().name());
            preparedStatement.setBoolean(index++, blockData.isPlacedByPlayer());
            preparedStatement.setBoolean(index++, blockData.isBlocked());

            if (preparedStatement.executeUpdate() != 1) {
                throw new DatabaseException("Impossible, you should have result set for this request.");
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void update(final BlockData blockData) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_QUERY)) {
            int index = 1;

            // update
            preparedStatement.setInt(index++, blockData.getX());
            preparedStatement.setInt(index++, blockData.getY());
            preparedStatement.setInt(index++, blockData.getZ());
            preparedStatement.setString(index++, blockData.getWorld());
            preparedStatement.setString(index++, blockData.getMaterial().name());
            preparedStatement.setBoolean(index++, blockData.isPlacedByPlayer());
            preparedStatement.setBoolean(index++, blockData.isBlocked());

            // where
            preparedStatement.setInt(index++, blockData.getX());
            preparedStatement.setInt(index++, blockData.getY());
            preparedStatement.setInt(index++, blockData.getZ());
            preparedStatement.setString(index++, blockData.getWorld());

            if (preparedStatement.executeUpdate() != 1) {
                throw new DatabaseException("Impossible, you should have result set for this request.");
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void delete(final BlockData blockData) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_QUERY)) {
            int index = 1;

            // where
            preparedStatement.setInt(index++, blockData.getX());
            preparedStatement.setInt(index++, blockData.getY());
            preparedStatement.setInt(index++, blockData.getZ());
            preparedStatement.setString(index++, blockData.getWorld());

            if (preparedStatement.executeUpdate() != 1) {
                throw new DatabaseException("Impossible, you should have result set for this request.");
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public boolean exist(final BlockData blockData) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(EXISTS_QUERY)) {
            int index = 1;

            // exist
            preparedStatement.setInt(index++, blockData.getX());
            preparedStatement.setInt(index++, blockData.getY());
            preparedStatement.setInt(index++, blockData.getZ());
            preparedStatement.setString(index++, blockData.getWorld());

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
    public BlockData randomBlock(final String world) {

        // request to have the entity
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(RANDOM_BLOCK_QUERY)) {
            preparedStatement.setString(1, world);

            // execute
            if (preparedStatement.execute()) {

                // if there is result
                final ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {

                    // return converted data
                    return convertToObject(resultSet);
                }
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }

        // nothing to return;
        return null;
    }

    @Override
    public String randomWord(final List<String> worlds) {

        // request to have the entity
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(RANDOM_WORLD_QUERY.replace("?", getArray(worlds)))) {

            // execute
            if (preparedStatement.execute()) {

                // if there is result
                final ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {

                    // return converted data
                    return resultSet.getString(1);
                }
            }
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }

        // nothing to return;
        return null;
    }

    @Override
    public void unblock() {
        // request to unblock
        try (final Statement preparedStatement = this.connection.createStatement()) {
            preparedStatement.executeUpdate(UNBLOCK_QUERY);
        } catch (final SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
