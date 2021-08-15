package fr.tropweb.miningmanager.dao.sqlite;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.exception.DatabaseException;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.sql.*;

public final class SQLiteDAO {
    private static final int LAST_VERSION_DB = 2;
    private final Plugin plugin;
    private final String filePath;

    private final Connection connection;

    public SQLiteDAO(Plugin plugin) {
        this.plugin = plugin;
        this.filePath = this.plugin.getDataFolder().getAbsolutePath() + "/MiningManager.db";

        // check if database already created
        final File database = new File(this.filePath);

        try {
            // create the connector
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.filePath);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        // create the database if not exist
        if (!database.exists()) {
            createNewDatabase();
        }

        // update database
        updateDatabase();
    }

    public void stop() {
        // if the connection exists
        if (this.connection != null) {
            try {
                // close connection
                this.connection.close();
            } catch (SQLException e) {

                // generic error
                throw new DatabaseException(e);
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void createNewDatabase() {
        try {
            // create the new database
            final DatabaseMetaData meta = this.getConnection().getMetaData();

            // return information
            this.plugin.getLogger().info("The driver name is " + meta.getDriverName());
            this.plugin.getLogger().info("A new database has been created.");
        } catch (SQLException e) {

            // generic error
            throw new DatabaseException(e);
        }
    }

    private void updateDatabase() {
        this.plugin.getLogger().info("Upgrade database started...");

        // get the version
        int version = this.getDBVersion();

        // loop on version
        while (version < LAST_VERSION_DB) {

            // execute the script update
            this.execute(String.format("00%s.sql", (version + 1)));

            // update the version
            version = this.getDBVersion();
        }

        this.plugin.getLogger().info("Upgrade database finished...");
    }

    private void execute(final String file) {
        this.plugin.getLogger().info("Read the script " + file + "...");

        // check the update file
        final InputStream update = this.plugin.getResource(file);

        // create table based on file
        try (final Statement statement = this.getConnection().createStatement()) {

            // statement to create table
            statement.executeUpdate(Utils.getFileContent(update));
        } catch (final SQLException e) {

            // generic error
            throw new DatabaseException(e);
        }

        this.plugin.getLogger().info("Script has been read.");
    }

    private int getDBVersion() {

        // create table based on file
        try (final Statement statement = this.getConnection().createStatement()) {

            // select the last version
            final ResultSet resultSet = statement.executeQuery("SELECT MAX(version_number) AS NB FROM version_db LIMIT 1");

            // return if something
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (final SQLException e) {
            this.plugin.getLogger().info("Database is empty.");
        }

        // return 0 by default to start creation
        return 0;
    }
}
