package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    NumberWorker worker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 11, 41, 97, 137, 173, 349, 997})
    public void isPrimeForPrimes(int number) {
        assertTrue(worker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 18, 58, 90, 174, 224, 265, 441, 520, 998})
    public void isPrimeForNotPrimes(int number) {
        assertFalse(worker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, -10, -58})
    public void isPrimeForIncorrectNumbers(int number) {
        assertThrows(NumberWorker.IllegalNumberException.class, () -> worker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    public void checkDigitsSum(int input, int expected) {
        assertEquals(expected, worker.digitsSum(input));
    }
}
