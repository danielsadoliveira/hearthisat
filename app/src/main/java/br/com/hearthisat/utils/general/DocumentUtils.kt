package br.com.hearthisat.utils.general

object DocumentUtil {

    // CPF
    private val weightCpf = intArrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2)

    // CNPJ
    private val weightCnpj = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

    private fun calculate(str: String, weight: IntArray): Int {
        var sum = 0
        var i = str.length - 1
        var digit: Int
        while (i >= 0) {
            digit = Integer.parseInt(str.substring(i, i + 1))
            sum += digit * weight[weight.size - str.length + i]
            i--
        }
        sum = 11 - sum % 11
        return if (sum > 9) 0 else sum
    }

    /**
     * Valida CPF
     *
     * @param ssn
     * @return
     */
    fun isValidCPF(text: String): Boolean {
        val cpf = text.replace(".", "").replace("-", "")
        if (cpf.length != 11 || cpf.matches((cpf[0] + "{11}").toRegex())) return false

        val digit1 = calculate(cpf.substring(0, 9), weightCpf)
        val digit2 = calculate(cpf.substring(0, 9) + digit1, weightCpf)
        return cpf == cpf.substring(0, 9) + digit1.toString() + digit2.toString()
    }

    fun isValidCNPJ(text: String): Boolean {
        val cnpj = text.replace(".", "").replace("-", "")
        if (cnpj.length != 14 || cnpj.matches((cnpj[0] + "{14}").toRegex())) return false

        val digit1 = calculate(cnpj.substring(0, 12), weightCnpj)
        val digit2 = calculate(cnpj.substring(0, 12) + digit1, weightCnpj)
        return cnpj == cnpj.substring(0, 12) + digit1.toString() + digit2.toString()
    }

}