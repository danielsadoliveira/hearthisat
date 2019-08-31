package br.com.hearthisat.extensions

import java.util.*

/**
 * Função para calcular juros composto de forma invertida.
 * - Encontrar quantidade necessária mensal para alcançar um valor total atribindo juros sobre os meses.
 *
 * @param initial: Montante inicial
 * @param total: Total necessário
 * @param rate: Taxa de juros em porcentagem
 * @param months: Quantidade de meses rendendo
 *
 * @return Contribuição mensal necessária para alcançar o total desejado.
 *
 * @author Daniel Sant'Anna de Oliveira
 */
fun calculateContribution(initial: Double, total: Double, rate: Double, months: Int): Double {
    var smaller = 0.0
    var bigger = ((total - initial) / months) + (months*0.01)
    var difference = bigger / 2

    while (difference > 0.01) {
        val contribution = ((bigger + smaller) / 2)
        val temp = calculateTotal(initial, rate, contribution, months)

        if (temp < total){
            smaller = contribution
        } else {
            bigger = contribution
        }

        difference = bigger - smaller
    }

    bigger = Math.round(bigger * 100).toDouble()
    return bigger / 100
}

/**
 * Função para calcular o valor total acumulado de juros composto sobre um valor mensal e um valor inicial.
 *
 * @param initial: Montante inicial
 * @param rate: Taxa de juros em porcentagem
 * @param contribution: Aporte mensal
 * @param months: Quantidade de meses rendendo
 *
 * @return Valor total acumulado durante a quantidade de meses.
 *
 * @author Daniel Sant'Anna de Oliveira
 */
private fun calculateTotal(initial: Double, rate: Double, contribution: Double, months: Int): Double {
    var total = initial
    for (i in 0 until months) {
        total = (total * (1 + (rate * 0.01))) + contribution
    }
    return total
}

fun calculateEndDate(initial: Double, total: Double, rate: Double, contribution: Double, startDate: Date): Date {
    val endDate = startDate.getCalendar()

    if (total > initial){
        var months = 0
        var temp = initial
        while (temp < total) {
            ++months
            temp = calculateTotal(temp, rate, contribution, 1)
        }

        if (months > 0){
            endDate.add(Calendar.MONTH, months)
        }
    }
    return endDate.time
}