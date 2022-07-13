package com.shokworks.firstnews.dbRoom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {

    /** Metodo para insertar una Noticia Favorita  */
    @Insert
    suspend fun insertFavNoticie(favNoticie: TFavNews)

    /** Return los datos de una Noticie favorita */
    @Query("SELECT * FROM TFavNews WHERE id LIKE :id")
    suspend fun getFavNoticie(id: Int): TFavNews?

    /** Return List Noticias favoritas */
    @Query("SELECT * FROM TFavNews ORDER BY id DESC ")
    fun getListFavNoticie(): LiveData<List<TFavNews>>?

    /** Eliminar una Noticia favorita */
    @Query("DELETE FROM TFavNews WHERE id = :id")
    suspend fun deleteItemFavNew(id: Int?)

    /** Eliminar todas las Noticias favoritas */
    @Query("DELETE FROM TFavNews")
    suspend fun deleteFavNoticiesAll()

}