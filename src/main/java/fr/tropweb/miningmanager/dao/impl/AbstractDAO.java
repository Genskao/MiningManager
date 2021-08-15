package fr.tropweb.miningmanager.dao.impl;

import fr.tropweb.miningmanager.dao.fields.BlockDataFields;
import fr.tropweb.miningmanager.exception.DatabaseException;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Material;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class AbstractDAO {

    protected static int getInt(final ResultSet resultSet, final BlockDataFields blockDataFields) throws SQLException {
        return resultSet.getInt(blockDataFields.getFieldName());
    }

    protected static String getString(final ResultSet resultSet, final BlockDataFields blockDataFields) throws SQLException {
        return resultSet.getString(blockDataFields.getFieldName());
    }

    protected static boolean getBoolean(final ResultSet resultSet, final BlockDataFields blockDataFields) throws SQLException {
        return resultSet.getBoolean(blockDataFields.getFieldName());
    }

    protected static Material getMaterial(final ResultSet resultSet, final BlockDataFields blockDataFields) throws SQLException {
        try {
            return Material.valueOf(resultSet.getString(blockDataFields.getFieldName()));
        } catch (final IllegalArgumentException e) {
            throw new DatabaseException(e);
        }
    }

    protected static String getArray(final List<String> strings) {
        final StringJoiner stringJoiner = new StringJoiner(",");
        for (final String s : strings) {
            stringJoiner.add("'" + StringEscapeUtils.escapeSql(s) + "'");
        }
        return stringJoiner.toString();
    }
}
