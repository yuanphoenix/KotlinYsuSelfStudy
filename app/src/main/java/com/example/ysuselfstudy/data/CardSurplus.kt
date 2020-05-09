package com.example.ysuselfstudy.data

/**
 * @author  Ahyer
 * @date  2020/5/9 9:34
 * @version 1.0
 */
data class Datas(
    val DM: String,
    val MC: String,
    val KYXQ: String,
    val KH: String,
    val KNYE: String
) {
}

data class Root(
    val id: String,

    val datas: Datas,

    val status: Int = 0,

    val cardstatuscode: String,

    val cardnum: String,

    val remining: String,

    val code: Int = 0,

    val yearMonths: List<String>,

    val cardstatusname: String,

    val availdate: String) {

}