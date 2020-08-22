package fr.tropweb.miningmanager.data.sqlite;

import fr.tropweb.miningmanager.Utils;
import fr.tropweb.miningmanager.exception.DatabaseException;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.sql.*;

public final class SQLiteDAO {
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
        this.plugin.getLogger().info("Create tables...");

        // check the update file
        final InputStream update = this.plugin.getResource("001.sql");

        // create table based on file
        try (final Statement statement = this.getConnection().createStatement()) {

            // statement to create table
            statement.execute(Utils.getFileContent(update));
        } catch (final SQLException e) {

            // generic error
            throw new DatabaseException(e);
        }

        this.plugin.getLogger().info("Tables created.");
    }
}
