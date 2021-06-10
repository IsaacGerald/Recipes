package com.example.recipeapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View


object ImagePreviewerUtils {
    fun getBlurredScreenDrawable(context: Context, screen: View): BitmapDrawable {
        val screenshot = takeScreenshot(screen)
        val blurred = blurBitmap(context, screenshot)
        return BitmapDrawable(context.getResources(), blurred)
    }

    private fun takeScreenshot(screen: View): Bitmap {
        screen.setDrawingCacheEnabled(true)
        val bitmap: Bitmap = Bitmap.createBitmap(screen.getDrawingCache())
        screen.setDrawingCacheEnabled(false)
        return bitmap
    }

    private fun blurBitmap(context: Context, bitmap: Bitmap): Bitmap {
        val bitmapScale = 0.3f
        val blurRadius = 10f
        val width = Math.round(bitmap.width * bitmapScale)
        val height = Math.round(bitmap.height * bitmapScale)
        val inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)
        val rs = RenderScript.create(context)
        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(blurRadius)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)
        return outputBitmap
    }
}
