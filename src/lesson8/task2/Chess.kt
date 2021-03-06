@file:Suppress("UNUSED_PARAMETER")

package lesson8.task2

import lesson8.task1.Line
import lesson8.task1.Point
import java.rmi.UnexpectedException
import java.util.*
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {
        return if (inside())
            ('a' - 1 + column) + row.toString()
        else ""
    }
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    if (notation.length != 2)
        throw IllegalArgumentException()
    val first = notation[0] - 'a' + 1
    val second = notation[1] - '0'
    return if (Square(first, second).inside())
        Square(first, second)
    else throw IllegalArgumentException()
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside())
        throw IllegalArgumentException()
    return when {
        start == end -> 0
        start.column == end.column || start.row == end.row -> 1
        else -> 2
    }
}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    if (start == end)
        return listOf(start)
    val result = mutableListOf(start)
    if (rookMoveNumber(start, end) == 2)
        result.add(Square(end.column, start.row))
    result.add(end)
    return (result)
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside())
        throw IllegalArgumentException()

    val colorStart = if (start.column % 2 == start.row % 2)
        1
    else 0

    val colorEnd = if (end.column % 2 == end.row % 2)
        1
    else 0

    return when {
        start == end -> 0
        colorEnd != colorStart -> -1
        abs(start.column - end.column) == abs(start.row - end.row) -> 1
        else -> 2
    }
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    val pointFirst = Line(Point(start.column.toDouble(), start.row.toDouble()), PI / 4).crossPoint(
        Line(Point(end.column.toDouble(), end.row.toDouble()), 3 * PI / 4)
    )
    val pointSecond = Line(Point(start.column.toDouble(), start.row.toDouble()), 3 * PI / 4).crossPoint(
        Line(Point(end.column.toDouble(), end.row.toDouble()), PI / 4)
    )

    val squareFirst = Square(pointFirst.x.roundToInt(), pointFirst.y.roundToInt())
    val squareSecond = Square(pointSecond.x.roundToInt(), pointSecond.y.roundToInt())

    return when (bishopMoveNumber(start, end)) {
        -1 -> listOf()
        0 -> listOf(start)
        1 -> listOf(start, end)
        else -> if (squareFirst.inside())
            listOf(start, squareFirst, end)
        else listOf(start, squareSecond, end)
    }
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside())
        throw IllegalArgumentException()
    return if (start == end)
        0
    else max(abs(start.row - end.row), abs(start.column - end.column))
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    if (start == end)
        return listOf(start)

    fun move(k: Int, x: Int, y: Int, point: Square): List<Square> {
        val result = mutableListOf<Square>()
        var pt = point

        for (i in 1..k) {
            pt = Square(pt.column + x, pt.row + y)
            result.add(pt)
        }
        return result
    }

    fun diagonal(start: Square, end: Square): List<Square> {
        var x = 1
        var y = 1
        when {
            start.row > end.row && start.column < end.column -> y = -1
            start.row < end.row && start.column > end.column -> x = -1
            start.row > end.row && start.column > end.column -> {
                x = -1
                y = -1
            }
        }
        val k = minOf(abs(start.row - end.row), abs(start.column - end.column))
        return listOf(start) + move(k, x, y, start)
    }

    fun lineTravel(start: Square, end: Square): List<Square> {
        var x = 0
        var y = 0

        when {
            start.column == end.column && start.row < end.row -> y = 1
            start.column == end.column && start.row > end.row -> y = -1
            start.row == end.row && start.column < end.column -> x = 1
            else -> x = -1
        }
        val k = maxOf(abs(start.row - end.row), abs(start.column - end.column))
        return move(k, x, y, start)
    }

    val list = diagonal(start, end)
    val pointFirst = list[list.size - 1]

    return list + lineTravel(pointFirst, end)
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
private val knightMoves = listOf(1 to 2, -1 to 2, 2 to 1, 2 to -1, 1 to -2, -1 to -2, -2 to -1, -2 to 1)

fun knightMovesToEnd(start: Square, end: Square): List<Square> {
    if (start == end)
        return listOf(start)

    fun getNext(now: Square, visited: Set<Square>): List<Square> =
        knightMoves.map { Square(it.first + now.column, it.second + now.row) }
            .filter { it.inside() && it !in visited }

    val queue = ArrayDeque<Pair<Square, List<Square>>>()

    val visited = mutableSetOf(start)
    queue.add(start to listOf())

    while (!queue.isEmpty()) {
        val (square, prev) = queue.poll()
        val next = getNext(square, visited)

        next.forEach {
            if (it == end) {
                return prev + square + end
            }
            visited.add(it)
            queue.add(it to prev + square)
        }
    }

    throw UnexpectedException("Сюда мы никогда не попадем, так как конь может достичь любой точки на шахматной доске")
}

fun knightMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside())
        throw IllegalArgumentException()
    return knightMovesToEnd(start, end).size - 1
}

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> = knightMovesToEnd(start, end)
