package com.shokworks.firstnews.providers

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.AlertdialogBinding
import com.shokworks.firstnews.databinding.AlertmessageBinding
import javax.inject.Inject

class Dialog @Inject constructor(){

    /** Inflar Ventana BottomSheet */
    fun bottomSheet(
        context: Context,
        mensaje: String,
        descripcion: String,
        boolean: Boolean,
        refresh: (Boolean) -> Unit,
    ) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottonSheetDialogTheme)
        val bind: AlertdialogBinding = AlertdialogBinding.inflate(LayoutInflater.from(context))

        bind.idMensaje.text = mensaje
        bind.idDescripcion.text = descripcion
        bind.idBtn2.text = if (boolean) context.getString(R.string.Agregar) else context.getString(R.string.Eliminar)
        bind.idBtn2.setOnClickListener {
            refresh(true)
            bottomSheetDialog.dismiss()
        }

        bind.idCancelar.setOnClickListener {
            refresh(false)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bind.root)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.show()
    }

    /** Libreria para el animationLoading en los items de las listas */
    fun shimmerEffect(): ShimmerDrawable {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(2000)
            .setBaseAlpha(0.9f)
            .setHighlightAlpha(0.93f)
            .setWidthRatio(1.5F)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        return ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
    }

    fun menssage(
        context: Context,
        mensaje: String,
        descripcion: String,
        boolean: Boolean,
        refresh: (Boolean) -> Unit,
    ) {
        val dialog = Dialog(context)
        val bind: AlertmessageBinding = AlertmessageBinding.inflate(LayoutInflater.from(context))

        bind.idMensaje.text = mensaje
        bind.idDescripcion.text = descripcion
        bind.idBtn2.text = context.getString(R.string.Recargar)
        bind.idBtn2.visibility = if (!boolean) View.GONE else View.VISIBLE
        bind.idBtn2.setOnClickListener {
            refresh(true)
            dialog.dismiss()
        }

        bind.idCancelar.text = context.getString(R.string.Cancelar)
        bind.idCancelar.setOnClickListener {
            refresh(false)
            dialog.dismiss()
        }

        dialog.setContentView(bind.root)
        dialog.setCancelable(false)
        dialog.show()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }
}