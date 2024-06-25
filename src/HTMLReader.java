import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class HTMLReader {
    private static String readHTML(File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        String content = "";
        while (input.hasNextLine()) {
            content+=input.nextLine();
            content+="\n";
        }
        input.close();
        return content;
    }

    private static String[] cleanHTML(String HTMLContent){
        String[] userRoleList = new String[15]; //This is a list of all strings corresponding to the users role
        HTMLContent = HTMLContent.substring(HTMLContent.indexOf("/style>"),HTMLContent.length()-1);
        HTMLContent = HTMLContent.replaceAll("<[^>]*>","");
        String[] HTMLContentSplit = HTMLContent.split("\n");
        int ticker = 0;
        for (int i = 0; i < HTMLContentSplit.length; i++) {
            if (HTMLContentSplit[i].contains("\uD83D\uDCDC")) { // Checks for 📜 emoji and makes that line contain the text #DEAD#, bc that corresponds to players will.
                HTMLContentSplit[i] = "#DEAD#";                 //And if a will shows it means that player is dead.
            };
            if (HTMLContentSplit[i].contains("span.")) { //Gets rid of all span lines
                HTMLContentSplit[i] = "";
            } else if (HTMLContentSplit[i].contains("[") && ticker < 15) {
                userRoleList[ticker]=HTMLContentSplit[i];
                while (!HTMLContentSplit[i].contains("(Username:")) {
                    i++;
                }
                userRoleList[ticker]+=HTMLContentSplit[i];
                ticker++;
            } else if (HTMLContentSplit[i].contains("AAAUUUUUGUHHHHHHHH")) { //The string here is just smth that should never pop up
                //USE THIS TO CHECK FOR PIRATE/OTHER THIRD PARTY WINS
            } else if (HTMLContentSplit[i].contains("died last night.")) {
                for (int j = 0; j < userRoleList.length; j++) {
                    if (userRoleList[j].contains(HTMLContentSplit[i].substring(0, HTMLContentSplit[i].indexOf("died last night.")))) {
                        userRoleList[j]+="|D!D|"; // |D!D| will be used to ID dead players
                    }
                }
            } else if (HTMLContentSplit[i].contains("died today.")) {
                for (int j = 0; j < userRoleList.length; j++) {
                    if (userRoleList[j].contains(HTMLContentSplit[i].substring(0, HTMLContentSplit[i].indexOf("died today.")))) {
                        userRoleList[j]+="|D!D|"; // |D!D| will be used to ID dead players
                    }
                }
            }
        }
        return userRoleList;
    } //This should return an object containing a dict of players/roles, a losing dict of players/roles, a dict of users and their corresponding roles

    public static HashMap<String, String[]> parseHTMLData(File file) {
        HashMap<String,String[]> infoMap = new HashMap<String, String[]>();
        String[] HTMLContent = {};
        try {
            HTMLContent = cleanHTML(readHTML(file));
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        for (int i = 0; i < HTMLContent.length; i++) {
            String userName = HTMLContent[i].substring(HTMLContent[i].indexOf(": ")+2,HTMLContent[i].indexOf(")"));
            String roleName = HTMLContent[i].substring(HTMLContent[i].indexOf(" -  ")+4,HTMLContent[i].indexOf(" ("));
            String isAlive = HTMLContent[i].contains("|D!D|") ? "DEAD" : "ALIVE";
            infoMap.put(HTMLContent[i].substring(HTMLContent[i].indexOf("] ")+2, HTMLContent[i].indexOf("-")), new String[]{userName, roleName, isAlive});
        }

//        for (int i = 0; i < HTMLContent.length; i++) {
//            String tempKey = (HTMLContent[i].substring(HTMLContent[i].indexOf("] ")+2,HTMLContent[i].indexOf("-")));
//            System.out.println(tempKey + " | " + infoMap.get(tempKey)[0] + " - " + infoMap.get(tempKey)[1] + " - " + infoMap.get(tempKey)[2]);
//        }
        return infoMap;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the file: ");
        File file = new File(input.next());
        parseHTMLData(file);
    }
}
