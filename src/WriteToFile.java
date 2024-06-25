import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class WriteToFile {
    public static boolean createCSV(HashMap<String, Integer> userMap, HashMap<String, Integer> userWinMap, HashMap<String, Integer> roleMap, HashMap<String, Integer> roleWinMap, HashMap<String, Integer[]> userInfo, String username) {
        String finalOutput = "Role, Wins, Games Played,"+username +" wins, "+username+ "games, " + username + " winrate, overall winrate, , Player, Wins, Games Played, Winrate\n";
        String[] roleKeyList = roleMap.keySet().toArray(new String[0]);
        String[] userKeyList = userMap.keySet().toArray(new String[0]);
        int ticker = 0;
        for (int i = 0; i < roleKeyList.length; i++) {
            ticker++;
            System.out.println(i);
            int roleWinCount = roleWinMap.get(roleKeyList[i]);
            int roleGameCount = roleMap.get(roleKeyList[i]);
            int userWinCount;
            int userGameCount;
            String usertitle;
            try {
                usertitle = userKeyList[i];
                userWinCount = userInfo.get(userKeyList[i])[0];
                userGameCount = userInfo.get(userKeyList[i])[1];
            } catch (Exception e) {
                usertitle = "N/A";
                userWinCount = 0;
                userGameCount = 0;
            }
            try {
                String tempStr = roleKeyList[i] + "," + roleWinMap.get(roleKeyList[i]) + "," + roleMap.get(roleKeyList[i]) + "," +userWinCount+","+userGameCount+","+ (userWinCount / (userGameCount == 0 ? 1 : userGameCount)) + "," + (roleWinCount / (roleGameCount == 0 ? 1 : roleGameCount)) + ",";
                finalOutput += tempStr;
            } catch (NullPointerException e) {
                String tempStr = roleKeyList[i] + "," + roleWinMap.get(roleKeyList[i]) + "," + roleMap.get(roleKeyList[i]) + "," + "N/A" + "," +userWinCount+","+userGameCount+","+ (roleWinCount / (userGameCount == 0 ? 1 : userGameCount)) + ",";
                finalOutput += tempStr;
            }
            try {
                String tempStr = "," + usertitle + "," + userWinCount + "," + (userWinCount / (userGameCount == 0 ? 1 : userGameCount))+",\n";
                finalOutput += tempStr;
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        }
        for (int i = ticker; i < userKeyList.length; i++) {
            int userWinCount = userInfo.get(roleKeyList[i])[0];
            int userGameCount = userInfo.get(roleKeyList[i])[1];
            String tempStr = ",,,,,," + userKeyList[i] + "," + userWinCount + "," + (userWinCount / (userGameCount == 0 ? 1 : userGameCount));
            finalOutput += tempStr;
    }
        writeToCSV(finalOutput);
        return true;
    }

    private static void writeToCSV(String inputStr) {
        try {
            File CSV = new File("ToSSheet.csv");
            FileWriter writer = new FileWriter(CSV);
            writer.write(inputStr);
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
