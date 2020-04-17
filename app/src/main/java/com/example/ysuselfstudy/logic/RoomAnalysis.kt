package com.example.ysuselfstudy.logic

/**
 * @author  Ahyer
 * @date  2020/4/17 17:32
 * @version 1.0
 */
object RoomAnalysis {
    private val TimeClock = doubleArrayOf(
        0.0,
        8.00,
        9.35,
        10.05,
        11.40,
        13.30,
        15.05,
        15.35,
        17.10,
        18.10,
        19.45,
        20.05,
        21.40
    )

    fun obj(time: String): ArrayList<Int> {
        val BZ = time.substring(0, 2).toInt()
        val BX = time.substring(3, 5).toInt()
        val EZ = time.substring(6, 8).toInt()
        val EX = time.substring(9, 11).toInt()
        val begin = BZ + 0.01 * BX
        val end = EZ + 0.01 * EX
        var chu = 1
        var zhong = 11
        var a = true
        for (i in 1 until TimeClock.size) {
            if (begin < TimeClock[i] && a) {
                a = false
                chu = if (i % 2 == 1) i else i - 1
            }
            if (end < TimeClock[i]) {
                zhong = if (i % 2 == 1) i else i - 1
                break
            }
        }
        val num = ArrayList<Int>()
        num.add(chu)
        num.add(zhong)
        return num
    }

}
