package com.example.ysuselfstudy.logic

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import com.example.ysuselfstudy.YsuSelfStudyApplication
import java.io.IOException
import java.io.InputStream
import java.nio.file.AccessMode
import java.util.*
import kotlin.collections.HashMap

/**
 * @author  Ahyer
 * @date  2020/5/11 14:36
 * @version 1.0
 */
object CrackCode {
    private val trainMap: HashMap<Bitmap, String> =
        HashMap()

    fun getAllOcr(code: Bitmap): String? {
        val aftercode: Bitmap = removeBackGround(code)
        val listImg: List<Bitmap> = split(aftercode)
        val map: Map<Bitmap, String> = loadTrainData()
        var result: String? = ""
        for (bi in listImg) {
            result += getSingleCharOcr(bi, map)
        }
        return result
    }


    private fun getSingleCharOcr(
        img: Bitmap,
        map: Map<Bitmap, String>
    ): String? {
        var result: String? = "#"
        val width = img.width
        val height = img.height
        var min = width * height
        for (bi in map.keys) {
            var count = 0
            if (Math.abs(bi.width - width) > 2) continue
            val widthmin = if (width < bi.width) width else bi.width
            val heightmin = if (height < bi.height) height else bi.height
            Label1@ for (x in 0 until widthmin) {
                for (y in 0 until heightmin) {
                    if (isBlack(img.getPixel(x, y)) != isBlack(bi.getPixel(x, y))) {
                        count++
                        if (count >= min) break@Label1
                    }
                }
            }
            if (count < min) {
                min = count
                result = map[bi]
            }
        }
        return result
    }

    private fun split(aftercode: Bitmap): List<Bitmap> {
        val subImgs: MutableList<Bitmap> = ArrayList()
        val width = aftercode.width / 4
        val height = aftercode.height
        subImgs.add(Bitmap.createBitmap(aftercode, 0, 0, width, height))
        subImgs.add(Bitmap.createBitmap(aftercode, width, 0, width, height))
        subImgs.add(Bitmap.createBitmap(aftercode, width * 2, 0, width, height))
        subImgs.add(Bitmap.createBitmap(aftercode, width * 3, 0, width, height))
        return subImgs
    }

    private fun loadTrainData(): HashMap<Bitmap, String> {
        try {
            val picsptah: Array<String> =
                YsuSelfStudyApplication.context.getAssets().list("training")!!
            var inputStream: InputStream
            var bitmap: Bitmap?
            for (picpath in picsptah) {
                inputStream = YsuSelfStudyApplication.context.getResources().getAssets()
                    .open("training/$picpath")
                bitmap = BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    trainMap.put(bitmap, picpath[0].toString() + "")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return trainMap
    }

    private fun removeBackGround(code: Bitmap): Bitmap {

        var cutcode =
            Bitmap.createBitmap(code, 5, 1, code.width - 5, code.height - 2)

        cutcode = Bitmap.createBitmap(cutcode, 0, 0, 50, cutcode.height)

        val width = cutcode.width
        val height = cutcode.height
        for (x in 0 until width) for (y in 0 until height) {
            if (isBlue(cutcode.getPixel(x, y)) == 1) {
                cutcode.setPixel(x, y, Color.BLACK)
            } else {
                cutcode.setPixel(x, y, Color.WHITE)
            }
        }
        return cutcode
    }

    private fun isBlue(pixel: Int): Int {
        return if (Color.red(pixel) + Color.red(pixel) + Color.red(
                pixel
            ) == 0
        ) {
            1
        } else {
            0
        }
    }

    private fun isBlack(pixel: Int): Int {
        return if (Color.red(pixel) + Color.red(pixel) + Color.red(
                pixel
            ) < 100
        ) {
            1
        } else {
            0
        }
    }
}