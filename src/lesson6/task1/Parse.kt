@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months: List<String> = listOf(
    "",
    "января",
    "февраля",
    "марта",
    "апреля",
    "мая",
    "июня",
    "июля",
    "августа",
    "сентября",
    "октября",
    "ноября",
    "декабря"
)

fun dateStrToDigit(str: String): String {
    val result = str.split(" ")
    if (result.size != 3)
        return ""

    val y = str.matches(Regex("""\d+\s[а-я]+\s\d+"""))
    if (!y || result[1] !in months || daysInMonth(
            months.indexOf(result[1]),
            result[2].toInt()
        ) < result[0].toInt()
    ) return ""

    val day = result[0].toInt()
    val year = result[2]
    val month = months.indexOf(result[1])

    return String.format("%02d.%02d.%s", day, month, year)
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val result = digital.split(".")
    val y = digital.matches(Regex("""\d+\.\d+\.\d+"""))
    if (result.size != 3 || !y)
        return ""

    val day = result[0].toInt()
    val month = result[1].toInt()
    if (month !in 1..12)
        return ""

    val monthName = months[month]
    val year = result[2].toInt()
    if (daysInMonth(month, year) < day)
        return ""

    return "$day $monthName $year"
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    var y = phone.replace(Regex("""[\-\s]"""), "")
    if (y.isEmpty())
        return ""
    val prefix = if (y[0] == '+') "+" else ""
    y = y.replace(Regex("""[+]"""), "")

    if (!y.matches(Regex("""\d+|\d*\(\d+\)\d*""")))
        return ""
    return String.format("%s%s", prefix, y.replace(Regex("""[()]"""), ""))
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (!jumps.matches(Regex("""(\d+|%|-)|((\d+|%|-)\s)+(\d+|%|-)""")))
        return -1
    val z = """(\d+)""".toRegex().findAll(jumps)
    val result = z.map { it.value.toInt() }.max()
    return result ?: -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (!jumps.matches(Regex("""(\d+\s)([%+\-])+|((\d+\s)([%+\-])+\s)+(\d+\s)([%+\-])+""")))
        return -1
    val z = """(\d+\s\+)""".toRegex().findAll(jumps)
    val result = z.map { it.value.filter { i -> (i in '0'..'9') } }.map { it.toInt() }.max()
    return result ?: -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (!expression.matches(Regex("""\d+|((\d+\s)([+\-])\s)+\d+""")))
        throw IllegalArgumentException(expression)
    val x = expression.split(" ").toList()
    var result = x[0].toInt()
    for (i in 1 until x.size - 1 step 2) {
        if (x[i] == "+")
            result += x[i + 1].toInt()
        else result -= x[i + 1].toInt()
    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val x = str.split(" ").map { it.map { i -> i.toLowerCase() }.joinToString(separator = "") }.toList()
    if (x.groupBy { it }.filter { (_, v) -> v.size >= 2 }.isEmpty())
        return -1
    var result = -1
    for (i in 0 until x.size - 1) {
        if (x[i].length == x[i + 1].length && x[i] == x[i + 1])
            return result + 1
        result += x[i].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (!description.matches(Regex("""([А-яA-z]+\s(\d+.\d+|0))|(([А-яA-z]+\s(\d+.\d+|0));\s)+([А-яA-z]+\s(\d+.\d+|0))""")))
        return ""
    val x = description.split("; ").map { it.split(" ") }
    val maxCost = x.map { it.component2().toDouble() }.max()
    val result = x.filter { it.component2().toDouble() == maxCost }
    return result[0].component1()
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
val romanNumber: Map<Char, Int> = mapOf(
    'I' to 1,
    'X' to 10,
    'C' to 100,
    'M' to 1000,
    'V' to 5,
    'L' to 50,
    'D' to 500
)

fun fromRoman(roman: String): Int {
    if (!roman.matches(Regex("""[IXCMVLD]+""")))
        return -1
    if (roman.isEmpty())
        return 0
    val l = roman.length
    var result = 0
    for (i in 0 until l - 1) {
        val value = romanNumber[roman[i]]
        val next = romanNumber[roman[i + 1]]
        if (value != null) {
            result += if (value < next!!)
                -value
            else value
        }
    }
    return result + (romanNumber[roman[l - 1]] ?: error(""))
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val t: Stack<Int> = Stack()
    val gotoNext: MutableMap<Int, Int> = mutableMapOf()
    val gotoPrev: MutableMap<Int, Int> = mutableMapOf()

    val otherCommands = setOf(' ', '<', '>', '+', '-')

    fun checkCommands() {
        for (i in 0 until commands.length) {
            when (commands[i]) {
                '[' -> t.push(i)
                ']' -> {
                    if (t.isEmpty())
                        throw IllegalArgumentException()

                    val lastOpenPos = t.pop()
                    gotoNext[lastOpenPos] = i
                    gotoPrev[i] = lastOpenPos
                }
                else -> {
                    if (!otherCommands.contains(commands[i]))
                        throw  IllegalArgumentException()
                }
            }
        }
        if (!t.isEmpty())
            throw IllegalArgumentException()
    }

    fun runCommands(): List<Int> {
        var i = 0
        val result = IntArray(cells)
        var limit1 = 0
        var startPos: Int = cells / 2
        while (i < commands.length) {
            if (limit == limit1)
                return result.toList()

            if (startPos > cells - 1 || startPos < 0)
                throw IllegalStateException()
            when (commands[i]) {
                '+' -> result[startPos] += 1
                '-' -> result[startPos] -= 1
                '>' -> startPos += 1
                '<' -> startPos -= 1
                '[' -> {
                    if (result[startPos] == 0)
                        i = gotoNext[i]!!
                }
                ']' -> {
                    if (result[startPos] != 0)
                        i = gotoPrev[i]!!
                }
            }
            limit1++
            i++
        }
        return result.toList()
    }

    checkCommands()

    return runCommands()

}
