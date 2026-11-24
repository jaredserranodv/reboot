package com.buap.reboot.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buap.reboot.data.bd.entidades.ArticuloEntity
import com.buap.reboot.data.bd.entidades.DireccionEnvioEntity
import com.buap.reboot.data.bd.entidades.MetodoPagoEntity
import com.buap.reboot.data.bd.entidades.NotificacionEntity
import com.buap.reboot.data.bd.entidades.ResenaEntity
import com.buap.reboot.data.bd.entidades.TransaccionEntity
import com.buap.reboot.data.bd.entidades.UsuarioEntity
import com.buap.reboot.data.dao.ArticuloDao
import com.buap.reboot.data.dao.DireccionEnvioDao
import com.buap.reboot.data.dao.MetodoPagoDao
import com.buap.reboot.data.dao.NotificacionDao
import com.buap.reboot.data.dao.ResenaDao
import com.buap.reboot.data.dao.TransaccionDao
import com.buap.reboot.data.dao.UsuarioDao

@Database(
    entities = [
        UsuarioEntity::class, ArticuloEntity::class, TransaccionEntity::class, NotificacionEntity::class,
        ResenaEntity::class, DireccionEnvioEntity::class, MetodoPagoEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class RebootDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun articuloDao(): ArticuloDao
    abstract fun transaccionDao(): TransaccionDao
    abstract fun notificacionDao(): NotificacionDao
    abstract fun resenaDao(): ResenaDao
    abstract fun direccionEnvioDao(): DireccionEnvioDao
    abstract fun metodoPagoDao(): MetodoPagoDao

    companion object {
        private const val NOMBRE_BD = "reboot.db"

        @Volatile
        private var instancia: RebootDatabase? = null

        fun obtenerInstancia(contexto: Context): RebootDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(contexto, RebootDatabase::class.java, NOMBRE_BD)
                    // ponytail: sin backend ni usuarios reales todavia, destructiva es suficiente;
                    // reemplazar por Migration real antes de un primer release.
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { instancia = it }
            }
    }
}
