public class Stopwatch {
    private long startTime;
    private long elapsedTime;
    private boolean running;

    // Запускает секундомер
    public void start() {
        if (!running) {
            startTime = System.nanoTime();
            running = true;
        }
    }

    // Останавливает секундомер и фиксирует прошедшее время
    public void stop() {
        if (running) {
            elapsedTime += System.nanoTime() - startTime;
            running = false;
        }
    }

    // Сбрасывает секундомер
    public void reset() {
        elapsedTime = 0;
        running = false;
    }
    public long getNanoSeconds()
    {
        long time = elapsedTime;
        if (running) {
            time += System.nanoTime() - startTime;
        }
        return time;
    }

    // Возвращает прошедшее время в миллисекундах
    public long getElapsedTimeMillis() {
        long time = elapsedTime;
        if (running) {
            time += System.nanoTime() - startTime;
        }
        return time / 1_000_000; // Переводим наносекунды в миллисекунды
    }

    // Возвращает прошедшее время в секундах
    public double getElapsedTimeSeconds() {
        return getElapsedTimeMillis() / 1000.0;
    }
}
