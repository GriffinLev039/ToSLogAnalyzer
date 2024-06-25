import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        HashMap<String, Integer> userMap = new HashMap<>();
        HashMap<String, Integer> roleMap = Analyzer.getHashMap();
        HashMap<String, Integer> userWinMap = new HashMap<>();
        HashMap<String, Integer> roleWinMap = Analyzer.getHashMap();
        HashMap<String, Integer[]> playerInfo = new HashMap<>(); //FORMAT: <ROLENAME, [WININT, GAMEINT]
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the path to the directory: ");
        File[] directory =  DIRReader.importFiles(input.next());
        input.nextLine();
        System.out.println("Enter a username to get stats on:");
        String chosenUser = input.next();
        for (int i = 0; i < directory.length; i++) {
            HashMap<String, String[]> gameData = HTMLReader.parseHTMLData(directory[i]);
            Analyzer.countUsers(gameData, userMap);
            Analyzer.userWins(gameData, userWinMap);
            Analyzer.roleCount(gameData, roleMap);
            Analyzer.roleWins(gameData, roleWinMap);
            Analyzer.analyzeUser(gameData, playerInfo, chosenUser);
        }

        System.out.println("OUTPUT: \n");
        String[] roleKeys = roleMap.keySet().toArray(new String[0]);
        String[] userKeys = userMap.keySet().toArray(new String[0]);
        System.out.println("PLAYERS:");
        for (int i = 0; i < userKeys.length; i++) {
            String username = userKeys[i];
            String winCount = String.valueOf(userWinMap.get(username));
            String appearanceCount = String.valueOf(userMap.get(username));
            System.out.println(" " + username + " | wins: " + winCount + " games (with you): " + appearanceCount + " winrate: " + userWinMap.get(username) / (userMap.get(username) == 0 ? 0.00001 : userMap.get(username))*100 + "%");
        }
        System.out.println("\n ROLES: ");
        for (int i = 0; i < roleKeys.length; i++) {
            String rolename = roleKeys[i];
            String winCount = String.valueOf(roleWinMap.get(rolename));
            String appearanceCount = String.valueOf(roleMap.get(rolename));
            System.out.println(rolename);
            System.out.println(" " + rolename + " | wins: " + winCount + " games (with you): " + appearanceCount + " winrate: " + roleWinMap.get(rolename) / (roleMap.get(rolename) == 0 ? 0.00001 : roleMap.get(rolename))*100 + "%");

        }
        System.out.println("\n User Info: ");
        for (int i = 0; i < roleKeys.length; i++) {
            String rolename = roleKeys[i];
            try {
                int winCount = playerInfo.get(rolename)[0];
                int gameCount = playerInfo.get(rolename)[1];
                System.out.println(" "+rolename+" | wins: "+winCount+" games as role: "+ gameCount + " winrate: "+ (winCount/(gameCount == 0 ? 0.00001 : gameCount)));

            }
            catch (NullPointerException e) {
                System.out.println("You have never played as "+ rolename+".");
            }
        }
        WriteToFile.createCSV(userMap, userWinMap, roleMap, roleWinMap, playerInfo, chosenUser);
    }
}
