import java.util.Random;

public class Main {

    static void main(String[] args) {

        served(10);
    }

    static void served(int min) {

        QueueUStack<Integer> queue = new QueueUStack<>();


        Random rand = new Random();

        int minutes = min;

        int served = 0;

        int totalWait = 0;
        int maxWait = 0;

        // الوقت الحالي
        for (int currentTime = 1; currentTime <= minutes; currentTime++) {

            System.out.println("Minute: " + currentTime);

            // يطلع واحد من الكيو
            if (!queue.isEmpty()) {

                int enterTime = queue.dequeue();

                int wait = currentTime - enterTime;

                served++;

                totalWait += wait;

                if (wait > maxWait)
                    maxWait = wait;

                System.out.println("Served -> waited: " + wait);
            }

            // احتمالات الدخول
            int r = rand.nextInt(100);

            if (r < 50) {

                System.out.println("No one entered");

            } else if (r < 75) {

                queue.enqueue(currentTime);

                System.out.println("1 person entered");

            } else {

                queue.enqueue(currentTime);
                queue.enqueue(currentTime);

                System.out.println("2 people entered");
            }

            System.out.println("----------------");
        }

        System.out.println("Served = " + served);

        System.out.println("Max Wait = " + maxWait);

        if (served > 0) {

            double avgWait = (double) totalWait / served;

            System.out.println("Average Wait = " + avgWait);
        }
    }
}


