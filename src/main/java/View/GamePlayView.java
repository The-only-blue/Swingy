package View;

import Controller.Controls;
import DB.CloseDB;
import Modal.Play;
import Modal.Points;

import java.util.Scanner;

public class GamePlayView implements GamePlayViewInterface{

    private Controls controls;

    @Override
    public void start() {
        controls = new Controls(this);

        controls.onStart();
    }

    @Override
    public void update(Play play) {
        System.out.println("----------INFO----------");
        System.out.println(play.getHero().toString() +
                "Position: " + "(" + play.getHeroCoord().getX() +
                "," + play.getHeroCoord().getY() + ")");
        System.out.println("------------------------");

        printMap(play.getMap(), play.getHeroCoord());
        getUserInput();
    }

    private void getUserInput() {
        Scanner scanner = SelectPlayerView.getScanner();

        System.out.println("W: UP, D: RIGHT, S: Down A: LEFT - to move to this direction");
        System.out.println("Commands (W: UP, D: RIGHT, S: Down A: LEFT):");
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            if ("map".equalsIgnoreCase(input)) {
                controls.onPrintMap();
                break;
            } else if ("W".equalsIgnoreCase(input) ||
                    "D".equalsIgnoreCase(input) ||
                    "S".equalsIgnoreCase(input) ||
                    "A".equalsIgnoreCase(input)) {
                controls.onMove(input);
                break;
            } else if ("switch".equalsIgnoreCase(input)) {
                controls.onSwitchButtonPressed();
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    @Override
    public void printMap(boolean[][] map, Points heroCoord) {
        System.out.printf("MAP %dx%d", map.length, map.length);
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (heroCoord.getX() == j && heroCoord.getY() == i)
                    System.out.print("H ");
                else if (map[i][j])
                    System.out.print("* ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }

    @Override
    public void gameFinished() {
        CloseDB.closeConnections();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void getVillainCollisionInput() {
        Scanner scanner = SelectPlayerView.getScanner();

        System.out.println();
        System.out.println("You moved to position occupied by villain");
        System.out.println("F - to fight with villain");
        System.out.println("R - If he catch you. You have no choice but fight");
        System.out.println("Commands (FIGHT, RUN):");
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            if ("F".equalsIgnoreCase(input)) {
                controls.onFight();
                break;
            } else if ("R".equalsIgnoreCase(input)) {
                controls.onRun();
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    @Override
    public boolean replaceArtifact(String replaceMessage) {
        Scanner scanner = SelectPlayerView.getScanner();

        System.out.println();
        System.out.println("Would you like to replace " + replaceMessage + "?");
        System.out.println("LEAVE - to leave your artifact");
        System.out.println("REPLACE - to replace by new artifact");
        System.out.println("Commands (LEAVE, REPLACE):");
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            if ("leave".equalsIgnoreCase(input)) {
                return false;
            } else if ("replace".equalsIgnoreCase(input)) {
                return true;
            } else {
                System.out.println("Unknown command");
            }
        }
        return false;
    }

    @Override
    public void switchView() {
        new StartGameView().start();
    }
}
