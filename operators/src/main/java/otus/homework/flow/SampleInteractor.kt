package otus.homework.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class SampleInteractor(
    private val sampleRepository: SampleRepository
) {

    /**
     * Реализуйте функцию task1 которая последовательно:
     * 1) возводит числа в 5ую степень
     * 2) убирает чила <= 20
     * 3) убирает четные числа
     * 4) добавляет постфикс "won"
     * 5) берет 3 первых числа
     * 6) возвращает результат
     */
    fun task1(): Flow<String> {
        return flowOf(7, 12, 4, 8, 11, 5, 7, 16, 99, 1)
//            .map { value -> value.toDouble().pow(5).toInt() }
            .filter { it <= 20 }
            .filter { it % 2 != 0 }
            .map { value -> "$value won" }
            .take(3)

        // судя по ожидаемому результату, должно быть не возведение в 5ю степень, а умножение на 5
        // и еще непонятно, как убирать числа <=20, если они в 5й степени или умножены на 5
        // тогда правильный ответ должен быть "1 won"
    }

    /**
     * Классическая задача FizzBuzz с небольшим изменением.
     * Если входное число делится на 3 - эмитим само число и после него эмитим строку Fizz
     * Если входное число делится на 5 - эмитим само число и после него эмитим строку Buzz
     * Если входное число делится на 15 - эмитим само число и после него эмитим строку FizzBuzz
     * Если число не делится на 3,5,15 - эмитим само число
     */
    fun task2(): Flow<String> = flow {
        for (i in 1..21) {
            when {
                i % 15 == 0 -> {
                    emit(i.toString())
                    emit("FizzBuzz")
                }
                i % 3 == 0 -> {
                    emit(i.toString())
                    emit("Fizz")
                }
                i % 5 == 0 -> {
                    emit(i.toString())
                    emit("Buzz")
                }
                else -> {
                    emit(i.toString())
                }
            }
        }
    }

    /**
     * Реализуйте функцию task3, которая объединяет эмиты из двух flow и возвращает кортеж Pair<String,String>(f1,f2),
     * где f1 айтем из первого флоу, f2 айтем из второго флоу.
     * Если айтемы в одно из флоу кончились то результирующий флоу также должен закончится
     */
    fun task3(): Flow<Pair<String, String>> {
        val flow1 = flowOf(
            "Red",
            "Green",
            "Blue",
            "Black",
            "White"
        )
        val flow2 = flowOf("Circle", "Square", "Triangle")
        return flow1.zip(flow2) { a, b -> Pair(a,b) }
    }

    /**
     * Реализайте функцию task4, которая обрабатывает IllegalArgumentException и в качестве фоллбека
     * эмитит число -1.
     * Если тип эксепшена != IllegalArgumentException, пробросьте его дальше
     * При любом исходе, будь то выброс исключения или успешная отработка функции вызовите метод dotsRepository.completed()
     */
    fun task4(): Flow<Int> {
        return flow {
            (1..10).forEach {
                if (it == 5) {
                    throw IllegalArgumentException("Failed")
                } else {
                    emit(it)
                }
            }
        }.catch {
            emit(-1)
        }
    }

    // непонятно, что нужно сделать для dotsRepository.completed()
}