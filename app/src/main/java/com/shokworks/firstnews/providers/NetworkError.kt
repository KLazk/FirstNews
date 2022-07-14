package com.shokworks.firstnews.providers

import android.content.Context
import com.shokworks.firstnews.R

object NetworkError {
    //TODO IMPLEMENTAR MENSAJES DE ERROR ESPECIFICOS
    fun get(message: String?): String {
        return message ?: "Ha ocurrido un error inesperado"
    }
}

object NetworkResponseHttp {
    fun e(
        context: Context,
        code: Int?,
        mensaje: String?,
        codeHttp: (String, String) -> Unit){

        when(code){

            /** Respuestas informativas */
            100 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE100))
            101 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE101))
            102 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE102))
            103 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE103))

            /** Respuestas satisfactorias */
            200 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE200))
            201 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE201))
            202 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE202))
            203 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE203))
            204 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE204))
            205 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE205))
            206 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE206))
            207 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE207))
            208 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE208))
            226 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE226))

            /** Redirecciones */
            300 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE300))
            301 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE301))
            302 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE302))
            303 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE303))
            304 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE304))
            305 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE305))
            306 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE306))
            307 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE307))
            308 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE308))

            /** Errores de cliente */
            400 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE400))
            401 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE401))
            402 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE402))
            403 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE403))
            404 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE404))
            405 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE405))
            406 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE406))
            407 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE407))
            408 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE408))
            409 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE409))
            410 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE410))
            411 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE411))
            412 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE412))
            413 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE413))
            414 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE414))
            415 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE415))
            416 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE416))
            417 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE417))
            418 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE418))
            421 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE421))
            422 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE422))
            423 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE423))
            424 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE424))
            426 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE426))
            428 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE428))
            429 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE429))
            431 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE431))
            451 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE451))
            499 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE499))

            /** Errores de servidor */
            500 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE500))
            501 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE501))
            502 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE502))
            503 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE503))
            504 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE504))
            505 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE505))
            506 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE506))
            507 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE507))
            508 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE508))
            510 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE510))
            511 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE511))
            521 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE521))
            525 -> codeHttp(mensaje.toString(), context.getString(R.string.CODE525))
            else -> codeHttp(mensaje.toString(), context.getString(R.string.Fallo))
        }
    }
}