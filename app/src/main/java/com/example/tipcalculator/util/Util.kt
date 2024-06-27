package com.example.tipcalculator.util

fun calculateTip(totalBill: Double, tipPercent: Int): Double {
    return if (totalBill >1 && totalBill.toString().isNotEmpty() && tipPercent >= 1)
        (totalBill * tipPercent)/100
    else
        0.0
}

fun calculateTotalPerPerson(
    totalBill: Double,
        tipPercent: Int,
        splitBy: Int,
): Double{
        val bill = calculateTip(totalBill = totalBill, tipPercent = tipPercent) + totalBill
    return (bill/splitBy)

}