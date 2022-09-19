import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
    static int[] arrangement = new int[9];
    static int[] prioritize = new int[9];
    static boolean isOver;
    static boolean isWin;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String playerPick = "";
    static int[][] lines = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
            };

    static int winPossibility = 200;
    static int losePossibility = 100;
    static int center = 70;
    static int threat = 25;
    static int corner = 15;




    public static void main(String[] args) {
        System.out.println("Во время вашего хода выбирайте поле из следующих значений: {A1...C3}");
        Arrays.fill(arrangement, 0);
        firstTurn();
        while (!isOver) {
            playerTurn();
            if(!isOver) {
                initialPrioritize();
                checkPrioritize();
                computerTurn();
            }
        }

    }

    static void initialPrioritize() {
        prioritize[4] = center;
        prioritize[0] = corner;
        prioritize[2] = corner;
        prioritize[6] = corner;
        prioritize[8] = corner;
        prioritize[1] = 0;
        prioritize[3] = 0;
        prioritize[5] = 0;
        prioritize[7] = 0;
    }

    static void firstTurn() {
        System.out.println("Крестики нолики. Кто ходит первым? 1 игрок, 2 компьютер");

        try {
            playerPick = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!playerPick.equals("1")) {
            if (playerPick.equals("2")) {
                initialPrioritize();
                computerTurn();
            } else {
                System.out.println("Некорректное значение");
                firstTurn();
            }
        }
        System.out.println("Ваш ход:");
    }

    static void playerTurn() {
        int toInt = 10;
        try {
            playerPick = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (playerPick) {
            case "A1":
                toInt = 0;
                break;

            case "A2":
                toInt = 1;
                break;

            case "A3":
                toInt = 2;
                break;

            case "B1":
                toInt = 3;
                break;

            case "B2":
                toInt = 4;
                break;

            case "B3":
                toInt = 5;
                break;

            case "C1":
                toInt = 6;
                break;

            case "C2":
                toInt = 7;
                break;

            case "C3":
                toInt = 8;
                break;

            default:
                System.out.println("указано неверное поле");
                playerTurn();
                break;
        }
        if(toInt != 10) {
            if (arrangement[toInt] == 0) {
                arrangement[toInt] = 1;
                if (IntStream.of(arrangement).noneMatch(x -> x == 0)) {
                    isOver = true;
                    System.out.println("ничья");
                }
            }
            else {
                System.out.println("указано занятое поле");
                playerTurn();
            }


        }
    }

    static void checkPrioritize() {
        for (int[] line:lines) {
            int playerCount = 0;
            int computerCount = 0;
            int emptyCount = 0;
            int empty1 = 10;
            int empty2 = 10;
            for (int i = 0; i < 3; i++) {
                if(arrangement[line[i]] == 2)
                    computerCount++;
                else if(arrangement[line[i]] == 1)
                    playerCount++;
                else {
                    emptyCount++;
                    if (empty1 == 10)
                        empty1 = line[i];
                    else
                        empty2 = line[i];
                }
                if ((computerCount == 2)&&(emptyCount == 1)) {
                    prioritize[empty1] += winPossibility;
                    isWin = true;
                }
                if ((playerCount == 2)&&(emptyCount == 1))
                prioritize[empty1] += losePossibility;
                if ((computerCount == 1)&&(emptyCount == 2)) {
                    prioritize[empty1] += threat;
                    prioritize[empty2] += threat;
                }
            }
        }
    }

    static void computerTurn() {
        int myTurn = 10;
        int higherPrioritize = -1;
        for (int i = 0; i < prioritize.length; i++) {
            if ((prioritize[i] > higherPrioritize) && (arrangement[i] == 0)) {
                higherPrioritize = prioritize[i];
                myTurn = i;
            }
        }
        if (myTurn != 10) {
            arrangement[myTurn] = 2;
            String text = "";

            switch (myTurn) {
                case 0:
                    text = "A1";
                    break;

                case 1:
                    text = "A2";
                    break;

                case 2:
                    text = "A3";
                    break;

                case 3:
                    text = "B1";
                    break;

                case 4:
                    text = "B2";
                    break;

                case 5:
                    text = "B3";
                    break;

                case 6:
                    text = "C1";
                    break;

                case 7:
                    text = "C2";
                    break;

                case 8:
                    text = "C3";
                    break;
            }

            System.out.println("K " + text);

            if (isWin) {
                isOver = true;
                System.out.println("Компьютер выиграл");
            } else if (IntStream.of(arrangement).noneMatch(x -> x == 0)) {
                isOver = true;
                System.out.println("ничья");
            }
        }
    }
}
