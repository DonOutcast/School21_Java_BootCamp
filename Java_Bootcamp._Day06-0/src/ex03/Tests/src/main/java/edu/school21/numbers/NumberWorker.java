package edu.school21.numbers;

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number <= 1)
            throw new IllegalNumberException();
        else if (number== 2)
            return (true);
        else{
            for (int i = 2; i * i <= number; i++){
                if (number % i == 0)
                    return (false);
            }
        }
        return (true);
    }

    public int digitsSum(int number) {
        int sum = 0;
        while(number != 0)
        {
            sum += number % 10;
            number /= 10;
        }
        return (sum);
    }

    class IllegalNumberException extends RuntimeException {
        public IllegalNumberException() {
            super("Number can't be less than 1");
        }
    }
}
