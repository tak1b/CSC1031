import java.util.Scanner;

public class PrimeNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the upper limit
        int limit = scanner.nextInt();
        scanner.close();

        // Call the sieve method to count primes
        int primeCount = countPrimes(limit);

        // Print the result
        System.out.println(primeCount);
    }

    // Function to count prime numbers using the Sieve of Eratosthenes
    private static int countPrimes(int limit) {
        if (limit < 2) return 0; // No primes less than 2

        boolean[] isPrime = new boolean[limit + 1];
        // Assume all numbers are prime initially
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }

        // Mark non-prime numbers
        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        // Count primes
        int count = 0;
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                count++;
            }
        }

        return count;
    }
}
