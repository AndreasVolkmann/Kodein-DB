@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package org.kodein.db

import kotlin.reflect.KClass

interface AsyncDBRead : KeyMaker {

    suspend fun <M : Any> get(type: KClass<M>, key: Key<M>, vararg options: Options.Read): M?

    fun findAll(vararg options: Options.Read): AsyncCursor<*>

    interface FindDsl<M : Any> {

        interface ByDsl<M : Any> {

            fun all(): AsyncCursor<M>

            fun withValue(value: Value, isOpen: Boolean = true): Cursor<M>
        }

        fun all(): AsyncCursor<M> = byPrimaryKey().all()

        fun byPrimaryKey(): ByDsl<M>

        fun byIndex(name: String): ByDsl<M>
    }

    fun <M : Any> find(type: KClass<M>, vararg options: Options.Read): FindDsl<M>

    suspend fun getIndexesOf(key: Key<*>, vararg options: Options.Read): List<String>

    fun sync(): DBRead
}

suspend inline fun <reified M : Any> AsyncDBRead.get(key: Key<M>, vararg options: Options.Read) = get(M::class, key, *options)
inline fun <reified M : Any> AsyncDBRead.find(vararg options: Options.Read) = find(M::class, *options)