package com.shokworks.firstnews.providers

object NetworkError {
    //TODO IMPLEMENTAR MENSAJES DE ERROR ESPECIFICOS
    fun get(message: String?): String {
        return message ?: "Ha ocurrido un error inesperado"
    }
}