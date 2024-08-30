import java.util.*;

public class AgeOfWar {

    static class Platoon {
        String soldireType;
        int soldiersCount;

        Platoon(String soldireType, int soldiersCount) {
            this.soldireType = soldireType;
            this.soldiersCount = soldiersCount;
        }

        boolean canWinAgainst(Platoon opponent) {
            int mySoldiers = this.soldiersCount;
            int opponentSoldiers = opponent.soldiersCount;

            // Checking if my platoon has an advantage over the opponent
            // if yes then we are doubling the mysoldiers count 
            if (checkAdvantage(this.soldireType, opponent.soldireType)) {
                mySoldiers *= 2; 
            }

            // Checking if the opponent platoon has an advantage over my platoon
            // if yes then we are doubling the oppoentsoldiers count 
            if (checkAdvantage(opponent.soldireType, this.soldireType)) {
                opponentSoldiers *= 2;
            }

            return mySoldiers > opponentSoldiers;
        }

        @Override
        public String toString() {
            return soldireType + "#" + soldiersCount;
        }
    }

    static boolean checkAdvantage(String mySoldierType, String opponentSoldierType) {

        // here we are maintating a map (key,value pair) for soldiers they have adavantage over
        Map<String, List<String>> advantages = new HashMap<>();
        advantages.put("Militia", Arrays.asList("Spearmen", "LightCavalry"));
        advantages.put("Spearmen", Arrays.asList("LightCavalry", "HeavyCavalry"));
        advantages.put("LightCavalry", Arrays.asList("FootArcher", "CavalryArcher"));
        advantages.put("HeavyCavalry", Arrays.asList("Militia", "FootArcher", "LightCavalry"));
        advantages.put("CavalryArcher", Arrays.asList("Spearmen", "HeavyCavalry"));
        advantages.put("FootArcher", Arrays.asList("Militia", "CavalryArcher"));

        return advantages.containsKey(mySoldierType) && advantages.get(mySoldierType).contains(opponentSoldierType);
    }

    static List<Platoon> getPlatoonsValue(String input) {
        //here we are parsing the input 
        //for example Spearmen#10;Militia#30 it will be return as Spearmen 10 and Miltia 30
        List<Platoon> platoons = new ArrayList<>();
        String[] parts = input.split(";");
        for (String part : parts) {
            String[] platoonDetails = part.split("#");
            platoons.add(new Platoon(platoonDetails[0], Integer.parseInt(platoonDetails[1])));
        }
        return platoons;
    }

    static boolean satisfiesWinCondition(List<Platoon> myPlatoons, List<Platoon> opponentPlatoons, int requiredWins) {
        // checking if winning condition is satisfying
        int winCount = 0;
        for (int i = 0; i < myPlatoons.size(); i++) {
            if (myPlatoons.get(i).canWinAgainst(opponentPlatoons.get(i))) {
                winCount++;
            }
        }
        return winCount >= requiredWins;
    }

    static void displayWinningCombinations(List<Platoon> myPlatoons, List<Platoon> opponentPlatoons) {
        // displaying winning combination if any
        System.out.println();
        System.out.println("Winning Combinations:");
        for (int i = 0; i < myPlatoons.size(); i++) {
            if (myPlatoons.get(i).canWinAgainst(opponentPlatoons.get(i))) {
                System.out.println("My Platoon: " + myPlatoons.get(i) + " vs Opponent's Platoon: " + opponentPlatoons.get(i));
            }
        }
        System.out.println();
    }

    static void generateCombinations(List<Platoon> myPlatoons, int start, int end, List<Platoon> opponentPlatoons, int requiredWins, 
                        List<List<Platoon>> validArrangements, int maxCombinations, int[] totalWinningCombinations) {
         // Here we are generating all possible combinations of the myPlatoons list. It swaps elements to explore different combination.                   
        if (start == end) {
            if (satisfiesWinCondition   (myPlatoons, opponentPlatoons, requiredWins)) {
                totalWinningCombinations[0]++;
                if (validArrangements.size() < maxCombinations) {
                    validArrangements.add(new ArrayList<>(myPlatoons));
                }
            }
        } else {
            for (int i = start; i <= end; i++) {
                Collections.swap(myPlatoons, start, i);
                generateCombinations(myPlatoons, start + 1, end, opponentPlatoons, requiredWins, validArrangements, maxCombinations, totalWinningCombinations);
                Collections.swap(myPlatoons, start, i); // Backtrack
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Taking input from user for myplatoons and opponent platoons
        System.out.println("Enter your platoons:");
        String myInput = scanner.nextLine();
        System.out.println(myInput);

        System.out.println();
        System.out.println("Enter opponent's platoons:");
        String opponentInput = scanner.nextLine();
        System.out.println(opponentInput);

        System.out.println(); 

        // Taking input from user for minium numbers of win
        System.out.println("Enter the minimum number of wins required (e.g., 3 for 3/5):");
        int requiredWins = scanner.nextInt();
        System.out.println(requiredWins);

        System.out.println();
        // Taking input from user for different winning combinations to display
        System.out.println("Enter the number of different winning combinations you want to see:");
        int maxCombinations = scanner.nextInt();
        System.out.println(maxCombinations);

        List<Platoon> myPlatoons = getPlatoonsValue(myInput);
        List<Platoon> opponentPlatoons = getPlatoonsValue(opponentInput);

        List<List<Platoon>> validArrangements = new ArrayList<>();
        int[] totalWinningCombinations = {0};

        generateCombinations(myPlatoons, 0, myPlatoons.size() - 1, opponentPlatoons, requiredWins, validArrangements, maxCombinations, totalWinningCombinations);

        if (totalWinningCombinations[0] == 0) {
            System.out.println();
            System.out.println("There is no chance of achieving the required number of wins.");
        } else {
           
            if (maxCombinations > totalWinningCombinations[0]) {
                System.out.println();
                System.out.println("Error: You have requested more combinations than possible. The maximum number of winning combinations is " + totalWinningCombinations[0]);
            } else {
                // Displaying the combinations to user only if requested combinations are less than or equal to the max combinations
                for (List<Platoon> arrangement : validArrangements) {
                    displayWinningCombinations(arrangement, opponentPlatoons);
                }
            }
            System.out.println();
            System.out.println("Total number of winning combinations possible: " + totalWinningCombinations[0]); 
        }
    }
}
