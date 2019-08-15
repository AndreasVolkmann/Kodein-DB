package org.kodein.db.impl.model

import org.kodein.db.*
import org.kodein.db.data.DataDBFactory
import org.kodein.db.model.*

abstract class AbstractModelDBFactory : ModelDBFactory {

    protected abstract val ddbFactory: DataDBFactory

    protected abstract fun defaultSerializer(): Serializer<Any>

    protected abstract fun defaultMetadataExtractor(): MetadataExtractor

    final override fun open(path: String, vararg options: Options.Open): ModelDB {
        val serializer = options<DBSerializer>()?.serializer ?: defaultSerializer()
        val metadataExtractor = options<DBMetadataExtractor>()?.extractor ?: defaultMetadataExtractor()
        val typeTable = options<DBTypeTable>()?.typeTable ?: TypeTable()

        val modelMiddlewares = options.all<Middleware.Model>().map { it.middleware }

        return modelMiddlewares.fold(ModelDBImpl(serializer, metadataExtractor, typeTable, ddbFactory.open(path, *options)) as ModelDB) { mdb, middleware -> middleware(mdb) }
    }

    override fun destroy(path: String, vararg options: Options.Open) {
        ddbFactory.destroy(path, *options)
    }

}
