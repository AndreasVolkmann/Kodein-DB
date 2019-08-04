package org.kodein.db.impl.model.cache

import org.kodein.db.model.ModelDB
import org.kodein.db.model.cache.ModelCache

class CachedModelSnapshot(override val mdb: ModelDB.Snapshot, override val cache: ModelCache, override val cacheCopyMaxSize: Long) : BaseCachedModelRead, ModelDB.Snapshot {
    override fun close()  = mdb.close()
}
