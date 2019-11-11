@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val x = other.center.distance(other = center) - (other.radius + radius)
        return maxOf(x, 0.0)
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = p.distance(other = center) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2)
        throw IllegalArgumentException()

    var x: Double
    var y = 0.0

    var result: Segment? = null
    for (i in 0 until points.size - 1) {
        for (j in i until points.size) {
            x = points[i].distance(points[j])
            if (x > y) {
                y = x
                result = Segment(points[i], points[j])

            }
        }
    }

    return result!!
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val x = (diameter.begin.x + diameter.end.x) / 2
    val y = (diameter.begin.y + diameter.end.y) / 2
    val radius = diameter.begin.distance(diameter.end) / 2

    return Circle(Point(x, y), radius)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val b1 = this.b
        val b2 = other.b
        val cos1 = cos(this.angle)
        val sin1 = sin(this.angle)
        val cos2 = cos(other.angle)
        val sin2 = sin(other.angle)

        val x = (b2 * cos1 - b1 * cos2) / (sin1 * cos2 - sin2 * cos1)
        val y = if (this.angle != PI / 2)
            (x * sin1 + b1) / cos1
        else (x * sin2 + b2) / cos2

        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    val angle = atan((s.end.y - s.begin.y) / (s.end.x - s.begin.x))
    return Line(Point(s.begin.x, s.begin.y), abs(angle))
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val midPoint = Point(((a.x + b.x) / 2), ((a.y + b.y) / 2))
    val slope = (a.x - b.x) / (b.y - a.y)
    val angle = when {
        b.y - a.y == 0.0 -> PI / 2
        b.x - a.x == 0.0 -> 0.0
        else -> {
            val x = atan(slope)
            if (x < 0.0)
                PI + x
            else x
        }
    }
    return Line(midPoint, angle)
}


/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2)
        throw IllegalArgumentException()

    var result: Pair<Circle, Circle>? = null
    var min = Double.MAX_VALUE
    for (i in 0 until circles.size - 1) {
        for (j in i + 1 until circles.size) {
            val distance = circles[i].distance(circles[j])
            if (distance < min) {
                min = distance
                result = circles[i] to circles[j]
            }
        }
    }
    return result!!
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val cx2 = c.x * c.x
    val cy2 = c.y * c.y
    val bx2 = b.x * b.x
    val by2 = b.y * b.y
    val ax2 = a.x * a.x
    val ay2 = a.y * a.y

    val t = (cx2 + cy2 - ax2 - ay2) * (b.x - a.x)
    val del = t - ((c.x - a.x) * (bx2 + by2 - ax2 - ay2))
    val dt = 2 * (a.y * (c.x - b.x) + b.y * (a.x - c.x) + c.y * (b.x - a.x))

    val y0 = del / dt

    val del1 = 2 * y0 * (a.y - b.y) + bx2 + by2 - ax2 - ay2
    val dt1 = 2 * (b.x - a.x)
    val x0 = del1 / dt1

    val radius = sqrt((a.x - x0) * (a.x - x0) + (a.y - y0) * (a.y - y0))
    return Circle(Point(x0, y0), radius)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty())
        throw IllegalArgumentException()

    if (points.size == 1)
        return Circle(points[0], 0.0)

    val max = diameter(*points)
    val first = max.begin
    val second = max.end
    if (points.size == 2)
        return circleByThreePoints(first, second, Point(((first.x + second.x) / 2), ((first.y + second.y) / 2)))

    var maxLength = 0.0
    var third: Point? = null
    val z = points.filter { it != first && it != second }
    for (i in 0 until z.size) {
        val length = z[i].distance(first) + z[i].distance(second)
        if (length > maxLength) {
            maxLength = length
            third = z[i]
        }
    }
    return circleByThreePoints(first, second, third!!)
}


