package org.kodein.db.impl

import org.kodein.db.*
import org.kodein.db.model.ModelDB
import org.kodein.memory.Closeable
import kotlin.reflect.KClass

internal class DBImpl(override val mdb: ModelDB) : DB, DBReadBase, DBWriteBase, KeyMaker by mdb, Closeable by mdb {

    override fun newBatch(): DBBatch = DBBatchImpl(mdb.newBatch())

    override fun newSnapshot(vararg options: Options.Read): DBSnapshot = DBSnapshotImpl(mdb.newSnapshot(*options))

    override fun onAll(): DB.RegisterDsl<Any> = RegisterDslImpl(mdb, emptyList())

    override fun <M : Any> on(type: KClass<M>): DB.RegisterDsl<M> = RegisterDslImpl(mdb, listOf<(M) -> Boolean>({ type.isInstance(it) }))

}
