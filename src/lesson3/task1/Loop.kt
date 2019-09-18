@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.sqr
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var kol = 0
    var x = n
    return if ((0 <= x) and (x < 10))
        1
    else {
        while (x != 0) {
            kol += 1
            x /= 10
        }
        kol
    }

}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    val y: Double = ((((1 + sqrt(5.0)) / 2).pow(n)) - (((1 - sqrt(5.0)) / 2).pow(n))) / (sqrt(5.0))
    return y.toInt()
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var x = m
    var y = n

    while ((x != 0) and (y != 0)) {
        if (x > y) {
            x %= y
        } else {
            y %= x
        }
    }

    return m * n / (x + y)
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var del = 2
    for (i in 2..n) {
        if (n % i != 0) {
            del += 1
            continue
        }
        if (n % i == 0)
            break
    }
    return del
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var del: Int = n - 1
    for (i in n - 1 downTo 1) {
        if (n % i != 0) {
            del -= 1
            continue
        }
        if (n % i == 0)
            break
    }
    return del
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var x = m
    var y = n

    while ((x != 0) and (y != 0)) {
        if (x > y) {
            x %= y
        } else {
            y %= x
        }
    }
    return (x + y) == 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var result: Int = -1
    val x: Double = ((sqrt(m.toDouble())) * 10) % 10
    val y: Double = ((sqrt(n.toDouble())) * 10) % 10
    loop@ for (i in m..n) {
        val z: Double = ((sqrt(i.toDouble())) * 10) % 10
        when {
            (m == n) and (x == 0.0) -> {
                result = i
                break@loop
            }
            x == 0.0 -> {
                result = m
                break@loop
            }
            y == 0.0 -> {
                result = n
                break@loop
            }
            z == 0.0 -> {
                result = i
                break@loop
            }
        }
    }
    return result != -1
}


/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var y = x
    var result = 0
    while (y != 1) {
        if (y % 2 == 0) {
            y /= 2
            result += 1
        } else {
            y = y * 3 + 1
            result += 1
        }
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double = TODO()

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double = TODO()

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var y = n
    var x: Int = digitNumber(n)
    var result: Int = y % 10
    return if (y / 10 == 0)
        result
    else {
        y /= 10
        x -= 1
        for (i in 1..x) {
            result = result * 10 + y % 10
            y /= 10
        }
        result
    }
}

fun revert(n: Long): Long {
    var y = n
    var x: Int = digitNumber(n.toInt())
    var result: Long = y % 10
    return if (y / 10 == 0L)
        result
    else {
        y /= 10
        x -= 1
        for (i in 1..x) {
            result = result * 10 + y % 10
            y /= 10
        }
        result
    }
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    val x: Int = revert(n)
    return x == n
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var k = 1
    if (n < 10)
        return false
    else {
        var x: Int = n % 10
        var y: Int = n % 100 / 10
        var z: Int = n / 100
        while (x == y) {
            k += 1
            x = y
            y = z % 10
            z /= 10
        }
    }
    return k != digitNumber(n)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162549366481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var k = 1
    var y = 1
    while (n > k) {
        y += 1
        k += digitNumber(sqr(y))
    }
    if (n == k)
        y = sqr(y) % 10
    else {
        val y2 = sqr(y)
        k = n - (k - digitNumber(y2)) - 1
        var y3 = revert(y2.toLong())
        for (i in 1..k) {
            y3 /= 10
        }
        y = (y3 % 10).toInt()
    }
    return y
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int = TODO()
