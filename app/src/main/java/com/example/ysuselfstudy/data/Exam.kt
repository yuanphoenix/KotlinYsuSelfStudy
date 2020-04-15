package com.example.ysuselfstudy.data

import org.litepal.crud.LitePalSupport

/**
 * @author  Ahyer
 * @date  2020/4/12 15:53
 * @version 1.0
 */
data class Exam(val name: String, val time: String, val local: String, val number: String):LitePalSupport() {
}