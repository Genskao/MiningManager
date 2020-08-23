package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.data.BlockDAO;
import fr.tropweb.miningmanager.data.impl.BlockLiteDAO;
import fr.tropweb.miningmanager.data.sqlite.SQLiteDAO;
import fr.tropweb.miningmanager.pojo.BlockLite;

/**
 * This class manage the object <code>SQLiteDAO</code> and provide all DAO we need to use the database.
 */
public final class SQLiteEngine {
    private final BlockDAO<BlockLite> blockDAO;

    public SQLiteEngine(final SQLiteDAO sqliteDAO) {
        this.blockDAO = new BlockLiteDAO(sqliteDAO.getConnection());
    }

    public BlockDAO<BlockLite> getBlockDAO() {
        return this.blockDAO;
    }
}