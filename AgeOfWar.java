import java.util.*;

public class AgeOfWar {

    static class Platoon {
        String type;
        int soldiers;

        Platoon(String type, int soldiers) {
            this.type = type;
            this.soldiers = soldiers;
        }
}

public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Enter platoons
        System.out.println("Enter your platoons:");
        String ownInput = scanner.nextLine();
        System.out.println("Enter opponent's platoons:");
        String opponentInput = scanner.nextLine();
}
}
