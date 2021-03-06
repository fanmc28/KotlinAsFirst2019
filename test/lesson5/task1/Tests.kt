package lesson5.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun shoppingListCostTest() {
        val itemCosts = mapOf(
            "Хлеб" to 50.0,
            "Молоко" to 100.0
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко"),
                itemCosts
            )
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                itemCosts
            )
        )
        assertEquals(
            0.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                mapOf()
            )
        )
    }

    @Test
    @Tag("Example")
    fun filterByCountryCode() {
        val phoneBook = mutableMapOf(
            "Quagmire" to "+1-800-555-0143",
            "Adam's Ribs" to "+82-000-555-2960",
            "Pharmakon Industries" to "+1-800-555-6321"
        )

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+999")
        assertEquals(0, phoneBook.size)
    }

    @Test
    @Tag("Example")
    fun removeFillerWords() {
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю Котлин".split(" "),
                "как-то"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю таки Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я люблю Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
    }

    @Test
    @Tag("Example")
    fun buildWordSet() {
        assertEquals(
            buildWordSet("Я люблю Котлин".split(" ")),
            mutableSetOf("Я", "люблю", "Котлин")
        )
        assertEquals(
            buildWordSet("Я люблю люблю Котлин".split(" ")),
            mutableSetOf("Котлин", "люблю", "Я")
        )
        assertEquals(
            buildWordSet(listOf()),
            mutableSetOf<String>()
        )
    }

    @Test
    @Tag("Easy")
    fun buildGrades() {
        assertEquals(
            mapOf(5 to listOf("Михаил", "Семён"), 3 to listOf("Марат")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
                .mapValues { (_, v) -> v.sorted() }
        )
        assertEquals(
            mapOf<Int, List<String>>(),
            buildGrades(mapOf())
        )
        assertEquals(
            mapOf(3 to listOf("Марат", "Михаил", "Семён")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 3, "Михаил" to 3))
                .mapValues { (_, v) -> v.sorted() }
        )
    }

    @Test
    @Tag("Easy")
    fun containsIn() {
        assertFalse(containsIn(mapOf("a" to "z", "b" to "sweet"), mapOf("a" to "z")))
        assertTrue(containsIn(mapOf("a" to "z", "b" to "sweet"), mapOf("a" to "z", "b" to "sweet")))
        assertTrue(containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")))
    }

    @Test
    @Tag("Easy")
    fun subtractOf() {
        val from = mutableMapOf("a" to "z", "b" to "c")

        subtractOf(from, mapOf())
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("b" to "z"))
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("a" to "z"))
        assertEquals(from, mapOf("b" to "c"))
    }

    @Test
    @Tag("Easy")
    fun whoAreInBoth() {
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(emptyList(), emptyList())
        )
        assertEquals(
            listOf("Marat"),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Marat", "Kirill"))
        )
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Sveta", "Kirill"))
        )
    }

    @Test
    @Tag("Normal")
    fun mergePhoneBooks() {
        assertEquals(
            mapOf("Emergency" to "112"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Fire department" to "01", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112", "Fire department" to "01"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
    }

    @Test
    @Tag("Normal")
    fun averageStockPrice() {
        assertEquals(
            mapOf<String, Double>(),
            averageStockPrice(listOf())
        )
        assertEquals(
            mapOf("MSFT" to 100.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 45.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
        )
    }

    @Test
    @Tag("Normal")
    fun findCheapestStuff() {
        assertEquals(
            "Мария",
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0)),
                "печенье"
            )
        )
        assertEquals(
            "Мария",
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0), "Орех" to ("печенье" to 80.0)),
                "печенье"
            )
        )
        assertNull(
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "торт"
            )
        )
        assertEquals(
            "Мария",
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "печенье"
            )
        )
    }

    @Test
    @Tag("Normal")
    fun canBuildFrom() {
        assertTrue(canBuildFrom(listOf('a', 'B', 'o'), "baobab"))
        assertTrue(canBuildFrom(listOf('a', 'b', 'o'), "baobab"))
        assertFalse(canBuildFrom(emptyList(), "foo"))
        assertFalse(canBuildFrom(listOf('a', 'm', 'r'), "Marat"))
    }

    @Test
    @Tag("Normal")
    fun extractRepeats() {
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(emptyList())
        )
        assertEquals(
            mapOf("a" to 2),
            extractRepeats(listOf("a", "b", "a"))
        )
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(listOf("a", "b", "c"))
        )
    }

    @Test
    @Tag("Normal")
    fun hasAnagrams() {
        assertFalse(hasAnagrams(emptyList()))
        assertTrue(hasAnagrams(listOf("рот", "свет", "тор")))
        assertFalse(hasAnagrams(listOf("рот", "свет", "код", "дверь")))
    }

    @Test
    @Tag("Hard")
    fun propagateHandshakes() {
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Mikhail"),
                "Mikhail" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Sveta"),
                    "Sveta" to setOf("Mikhail")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Marat", "Mikhail"),
                "Mikhail" to setOf("Sveta", "Marat")
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Mikhail", "Sveta"),
                    "Sveta" to setOf("Marat"),
                    "Mikhail" to setOf("Sveta")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sergey", "Sveta"),
                "Sveta" to setOf("Marat", "Mikhail", "Sergey"),
                "Mikhail" to setOf("Sveta", "Marat", "Sergey"),
                "Sergey" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Mikhail", "Sveta"),
                    "Sveta" to setOf("Marat"),
                    "Mikhail" to setOf("Sveta", "Sergey")
                )
            )
        )
    }

    @Test
    @Tag("Hard")
    fun findSumOfTwo() {

        assertEquals(
            Pair(1, 4),
            findSumOfTwo(listOf(1, 4, 2, 3, 4, 4), 8)
        )

        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(0), 1)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(1, 1), 0)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(0, 0), 1)
        )
        assertEquals(
            Pair(0, 1),
            findSumOfTwo(listOf(0, 1), 1)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(1, 2, 3, 4), 8)
        )
        assertEquals(
            Pair(13, 14),
            findSumOfTwo(listOf(1, 2, 3, 4, 6, 34, 54, 754, 734, 643, 2, 5, 125, 1000, 1001), 2001)
        )
        assertEquals(
            Pair(0, 4),
            findSumOfTwo(listOf(1, 3, 4, 5, 7), 8)
        )
        assertEquals(
            Pair(1, 4),
            findSumOfTwo(listOf(1, 4, 2, 3, 4), 8)
        )
        assertEquals(
            Pair(0, 2),
            findSumOfTwo(listOf(1, 2, 3), 4)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(emptyList(), 1)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(1, 2, 3), 6)
        )
    }

    @Test
    @Tag("Impossible")
    fun bagPacking() {
        assertEquals(
            setOf("Кубок"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                850
            )
        )
        assertEquals(
            setOf("Кубок"),
            bagPacking(
                mapOf(
                    "Кубок" to (1 to 1)
                ),
                1
            )
        )
        assertEquals(
            emptySet<String>(),
            bagPacking(
                mapOf(
                    "Кубок" to (1 to 1)
                ),
                0
            )
        )
        assertEquals(
            setOf("Кубок", "Корона"),
            bagPacking(
                mapOf(
                    "Кубок" to (500 to 2000),
                    "Слиток" to (850 to 3000),
                    "Корона" to (350 to 1001),
                    "Алмаз" to (3000 to 6500),
                    "Жезл" to (1500 to 500)
                ),
                850
            )
        )
        assertEquals(
            setOf("Кубок"),
            bagPacking(
                mapOf(
                    "Кубок" to (500 to 2000),
                    "Слиток" to (1000 to 4360),
                    "Корона" to (2000 to 5000),
                    "Алмаз" to (3000 to 6500),
                    "Жезл" to (1500 to 500)
                ),
                850
            )
        )
        assertEquals(
            setOf("Слиток", "Алмаз"),
            bagPacking(
                mapOf(
                    "Кубок" to (30 to 250),
                    "Слиток" to (45 to 500),
                    "Корона" to (50 to 350),
                    "Алмаз" to (30 to 550),
                    "Жезл" to (30 to 60)
                ),
                100
            )
        )
        assertEquals(
            setOf("Слиток", "Алмаз", "Слиток1", "Алмаз1", "Корона"),
            bagPacking(
                mapOf(
                    "Кубок" to (30 to 250),
                    "Слиток" to (45 to 500),
                    "Корона" to (50 to 350),
                    "Алмаз" to (30 to 550),
                    "Жезл" to (30 to 60),
                    "Кубок1" to (30 to 250),
                    "Слиток1" to (45 to 500),
                    "Корона1" to (50 to 350),
                    "Алмаз1" to (30 to 550),
                    "Жезл1" to (30 to 60)
                ),
                200
            )
        )
        assertEquals(
            setOf("Слиток", "Алмаз", "Алмаз2", "Алмаз12", "Слиток1", "Слиток2", "Слиток12", "Алмаз1", "Корона"),
            bagPacking(
                mapOf(
                    "Кубок" to (30 to 250),
                    "Слиток" to (45 to 500),
                    "Корона" to (50 to 350),
                    "Алмаз" to (30 to 550),
                    "Жезл" to (30 to 60),
                    "Кубок1" to (30 to 250),
                    "Слиток1" to (45 to 500),
                    "Корона1" to (50 to 350),
                    "Алмаз1" to (30 to 550),
                    "Жезл1" to (30 to 60),
                    "Кубок2" to (30 to 250),
                    "Слиток2" to (45 to 500),
                    "Корона2" to (50 to 350),
                    "Алмаз2" to (30 to 550),
                    "Жезл2" to (30 to 60),
                    "Кубок12" to (30 to 250),
                    "Слиток12" to (45 to 500),
                    "Корона12" to (50 to 350),
                    "Алмаз12" to (30 to 550),
                    "Жезл12" to (30 to 60)
                ),
                350
            )
        )
        assertEquals(
            setOf(
                "Алмаз",
                "Алмаз1",
                "Алмаз2",
                "Алмаз12",
                "Алмаз3",
                "Алмаз13",
                "Алмаз23",
                "Алмаз123",
                "Слиток",
                "Слиток1",
                "Слиток2",
                "Слиток12",
                "Слиток3",
                "Слиток13",
                "Слиток23",
                "Слиток123",
                "Корона"
            ),
            bagPacking(
                mapOf(
                    "Кубок" to (30 to 250),
                    "Слиток" to (45 to 500),
                    "Корона" to (50 to 350),
                    "Алмаз" to (30 to 550),
                    "Жезл" to (30 to 60),
                    "Кубок1" to (30 to 250),
                    "Слиток1" to (45 to 500),
                    "Корона1" to (50 to 350),
                    "Алмаз1" to (30 to 550),
                    "Жезл1" to (30 to 60),
                    "Кубок2" to (30 to 250),
                    "Слиток2" to (45 to 500),
                    "Корона2" to (50 to 350),
                    "Алмаз2" to (30 to 550),
                    "Жезл2" to (30 to 60),
                    "Кубок12" to (30 to 250),
                    "Слиток12" to (45 to 500),
                    "Корона12" to (50 to 350),
                    "Алмаз12" to (30 to 550),
                    "Жезл12" to (30 to 60),
                    "Кубок3" to (30 to 250),
                    "Слиток3" to (45 to 500),
                    "Корона3" to (50 to 350),
                    "Алмаз3" to (30 to 550),
                    "Жезл3" to (30 to 60),
                    "Кубок13" to (30 to 250),
                    "Слиток13" to (45 to 500),
                    "Корона13" to (50 to 350),
                    "Алмаз13" to (30 to 550),
                    "Жезл13" to (30 to 60),
                    "Кубок23" to (30 to 250),
                    "Слиток23" to (45 to 500),
                    "Корона23" to (50 to 350),
                    "Алмаз23" to (30 to 550),
                    "Жезл23" to (30 to 60),
                    "Кубок123" to (30 to 250),
                    "Слиток123" to (45 to 500),
                    "Корона123" to (50 to 350),
                    "Алмаз123" to (30 to 550),
                    "Жезл123" to (30 to 60)
                ),
                650
            )
        )
        assertEquals(
            emptySet<String>(),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                450
            )
        )
    }

    // TODO: map task tests
}
