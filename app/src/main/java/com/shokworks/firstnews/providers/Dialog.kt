package com.shokworks.firstnews.providers

import android.content.Context
import android.view.LayoutInflater
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.AlertdialogBinding
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
}