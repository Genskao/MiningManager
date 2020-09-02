package fr.tropweb.miningmanager.engine;

import fr.tropweb.miningmanager.dao.BlockDAO;
import fr.tropweb.miningmanager.dao.impl.BlockDataDAO;
import fr.tropweb.miningmanager.dao.sqlite.SQLiteDAO;
import fr.tropweb.miningmanager.pojo.BlockData;

/**
 * This class manage the object <code>SQLiteDAO</code> and provide all DAO we need to use the database.
 */
public final class SQLiteEngine {
    private final BlockDAO<BlockData> blockDAO;

    public SQLiteEngine(final SQLiteDAO sqliteDAO) {
        this.blockDAO = new BlockDataDAO(sqliteDAO.getConnection());
    }

    public BlockDAO<BlockData> getBlockDAO() {
        return this.blockDAO;
    }
}
