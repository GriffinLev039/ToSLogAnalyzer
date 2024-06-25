import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Analyzer {
    private static final String[] ROLES = new String[]{"Jester","Executioner","Coroner","Seer","Pirate", "Doomsayer","Investigator", "Lookout", "Psychic", "Sheriff", "Spy", "Tracker", "Bodyguard", "Cleric", "Crusader", "Trapper", "Veteran", "Trickster", "Deputy", "Vigilante", "Admirer", "Amnesiac", "Retributionist", "Socialite", "Tavern Keeper", "Jailor", "Marshal", "Mayor", "Monarch", "Prosecutor", "Coven Leader", "Hex Master", "Witch", "Conjurer", "Jinx", "Ritualist", "Dreamweaver", "Enchanter", "Illusionist", "Medusa", "Necromancer", "Poisoner", "Potion Master", "Voodoo Master", "Wildling", "Arsonist", "Serial Killer", "Shroud", "Werewolf", "Baker", "Berserker", "Plaguebearer", "Soul Collector", "Famine", "War", "Pestilence", "Death", "Vampire", "Cursed Soul"};
    private static final String[] TOWNROLES = new String[]{"Coroner", "Investigator", "Lookout", "Psychic", "Sheriff", "Spy", "Tracker", "Bodyguard", "Cleric", "Crusader", "Trapper", "Veteran", "Trickster", "Deputy", "Vigilante", "Admirer", "Amnesiac", "Retributionist", "Socialite", "Tavern Keeper", "Jailor", "Marshal", "Mayor", "Monarch", "Prosecutor"};
    private static String[] COVENROLES = new String[]{"Coven Leader", "Hex Master", "Witch", "Conjurer", "Jinx", "Ritualist", "Dreamweaver", "Enchanter", "Illusionist", "Medusa", "Necromancer", "Poisoner", "Potion Master", "Voodoo Master", "Wildling"};
    private static String[] APOCROLES = new String[]{"Baker", "Berserker", "Plaguebearer", "Soul Collector", "Famine", "War", "Pestilence", "Death"};

    public static HashMap<String, Integer> getHashMap() {
        HashMap<String, Integer> roleCounter = new HashMap<>();
        for (int i = 0; i < ROLES.length; i++) {
            roleCounter.put(ROLES[i],0);
        }
        return roleCounter;
    }

    public static void roleCount(HashMap<String, String[]> infoMap, HashMap<String, Integer> roleCount) {
        String[] keyList = infoMap.keySet().toArray(new String[0]);
        String[] roleList = new String[15];
        for (int i = 0; i < roleList.length; i++) {

            roleList[i] = infoMap.get(keyList[i])[1];
        }
        for (int i = 0; i < roleList.length; i++) {
            try {
                roleCount.put(roleList[i], roleCount.get(roleList[i]) + 1);
            }
            catch (Exception e) {
                roleCount.put(roleList[i],1);
            }
        }
    }

    public static HashMap<String, Integer> countUsers(HashMap<String, String[]> infoMap, HashMap<String, Integer> userCount) {
        String[] keyList = infoMap.keySet().toArray(new String[0]);
        for (int i = 0; i < keyList.length; i++) {
            String username = infoMap.get(keyList[i])[0];
            if (!userCount.containsKey(username)) {
                userCount.put(username, 1);
            } else {
                userCount.put(username, userCount.get(username)+1);
            }
        }
        return userCount;
    }


    public static void roleWins(HashMap<String, String[]> infoMap, HashMap<String, Integer> roleWinCount) {
        String winner = whoWon(infoMap);
        String[] keyList = infoMap.keySet().toArray(new String[0]);
        String[] roleList = new String[15];
        for (int i = 0; i < roleList.length; i++) {

            roleList[i] = infoMap.get(keyList[i])[1];
        }
        Set<String> set = new HashSet<String>();
        for (String str : roleList) {
            set.add(str);
        }
        roleList = set.toArray(new String[0]);
        switch (winner) {
            case "TOWN":
                for (int i = 0; i < TOWNROLES.length; i++) {
                    for (int j = 0; j < roleList.length; j++) {
                        if (TOWNROLES[i].equals(roleList[j])) {
                            roleWinCount.put(roleList[j],roleWinCount.get(roleList[j])+1);
                        }
                    }
                }
                break;
            case "COVEN":
                for (int i = 0; i < COVENROLES.length; i++) {
                    for (int j = 0; j < roleList.length; j++) {
                        if (COVENROLES[i].equals(roleList[j])) {
                            roleWinCount.put(roleList[j],roleWinCount.get(roleList[j])+1);
                        }
                    }
                }
                break;
            case "APOC":
                for (int i = 0; i < APOCROLES.length; i++) {
                    for (int j = 0; j < roleList.length; j++) {
                        if (APOCROLES[i].equals(roleList[j])) {
                            roleWinCount.put(roleList[j],roleWinCount.get(roleList[j])+1);
                        }
                    }
                }
                break;
            case "SK":
                roleWinCount.put("Serial Killer",roleWinCount.get("Serial Killer")+1);
                break;
            case "WW":
                roleWinCount.put("Werewolf",roleWinCount.get("Werewolf")+1);
                break;
            case "AR":
                roleWinCount.put("Arsonist",roleWinCount.get("Arsonist")+1);
                break;
        }
    }



    public static void userWins(HashMap<String, String[]> infoMap, HashMap<String, Integer> userWinCount) {
        String winner = whoWon(infoMap);
        String[] keyList = infoMap.keySet().toArray(new String[0]);
        for (int i = 0; i < keyList.length; i++) {
            String username = infoMap.get(keyList[i])[0];
            if (!userWinCount.containsKey(username)) {
                userWinCount.put(username, 0);
            }
            if (winner.equals("TOWN")) {
                for (int j = 0; j < TOWNROLES.length; j++) {
                    if (infoMap.get(keyList[i])[1].matches(TOWNROLES[j])) {
                        if (userWinCount.containsKey(username)) {
                            userWinCount.put(username, userWinCount.get(username) + 1);
                        } else {
                            userWinCount.put(username, 1);
                        }
                    }
                }
            }

            if (winner.equals("COVEN")) {
                for (int j = 0; j < COVENROLES.length; j++) {
                    if (infoMap.get(keyList[i])[1].matches(COVENROLES[j])) {
                        if (userWinCount.containsKey(username)) {
                            userWinCount.put(username, userWinCount.get(username) + 1);
                        } else {
                            userWinCount.put(username, 1);
                        }
                    }
                }
            }


            if (winner.equals("APOC")) {
                for (int j = 0; j < APOCROLES.length; j++) {
                    if (infoMap.get(keyList[i])[1].matches(APOCROLES[j])) {
                        if (userWinCount.containsKey(username)) {
                            userWinCount.put(username, userWinCount.get(username) + 1);
                        } else {
                            userWinCount.put(username, 1);
                        }
                    }
                }
            }
            if (winner.equals("SK")) {
                if (infoMap.get(keyList[i])[1].matches("Serial Killer")) {
                    if (userWinCount.containsKey(username)) {
                        userWinCount.put(username, userWinCount.get(username) + 1);
                    } else {
                        userWinCount.put(username, 1);
                    }
                }
            }
            if (winner.equals("WW")) {
                if (infoMap.get(keyList[i])[1].matches("Werewolf")) {
                    if (userWinCount.containsKey(username)) {
                        userWinCount.put(username, userWinCount.get(username) + 1);
                    } else {
                        userWinCount.put(username, 1);
                    }
                }
            }
            if (winner.equals("SH")) {
                if (infoMap.get(keyList[i])[1].matches("Shroud")) {
                    if (userWinCount.containsKey(username)) {
                        userWinCount.put(username, userWinCount.get(username) + 1);
                    } else {
                        userWinCount.put(username, 1);
                    }
                }
            }
            if (winner.equals("AR")) {
                if (infoMap.get(keyList[i])[1].matches("Arsonist")) {
                    if (userWinCount.containsKey(username)) {
                        userWinCount.put(username, userWinCount.get(username) + 1);
                    } else {
                        userWinCount.put(username, 1);
                    }
                }
            }

        }
    }


    public static String whoWon(HashMap<String, String[]> infoMap) {
        String[] keyList = infoMap.keySet().toArray(new String[0]);
        for (int i = 0; i < keyList.length; i++) {
            if (infoMap.get(keyList[i])[2].matches("ALIVE")) {
                for (int j = 0; j < TOWNROLES.length; j++) {
                    if (infoMap.get(keyList[i])[1].matches(TOWNROLES[j])) {
                        return "TOWN";
                    }
                }
                for (int j = 0; j < COVENROLES.length; j++) {
                    if (infoMap.get(keyList[i])[1].matches(COVENROLES[j])) {
                        return "COVEN";
                    }
                }
                for (int j = 0; j < APOCROLES.length; j++) {
                    if (infoMap.get(keyList[i])[1].matches(APOCROLES[j])) {
                        return "APOC";
                    }
                }
                if (infoMap.get(keyList[i])[1].matches("Serial Killer")) {
                    return "SK";
                }
                if (infoMap.get(keyList[i])[1].matches("Werewolf")) {
                    return "WW";
                }
                if (infoMap.get(keyList[i])[1].matches("Shroud")) {
                    return "SH";
                }
                if (infoMap.get(keyList[i])[1].matches("Arsonist")) {
                    return "AR";
                }
            }
        }
        return "DRAW";
    }

    public static void analyzeUser(HashMap<String, String[]> infoMap,HashMap<String, Integer[]> playerInfo, String username) {
        String winner = whoWon(infoMap);
        String[] keyArr = infoMap.keySet().toArray(new String[0]);
        for (int i = 0; i < infoMap.size(); i++) {
            if (username.equals(infoMap.get(keyArr[i])[0])) {
                username = keyArr[i];
                System.out.println("Match!");
                break;
            }
        }
        try {
            String rolename = infoMap.get(username)[1];
                switch (winner) {
                    case "TOWN":
                        for (int i = 0; i < TOWNROLES.length; i++) {
                            if (rolename.equals(TOWNROLES[i])) {
                                if (playerInfo.containsKey(rolename)) {
                                    int winCount = playerInfo.get(username)[0]+1;
                                    int gameCount = playerInfo.get(username)[1]+1;
                                    playerInfo.put(rolename, new Integer[]{winCount, gameCount});
                                } else {
                                    playerInfo.put(rolename, new Integer[]{1, 1});
                                }
                            }
                        }
                        if (playerInfo.containsKey(rolename)) {
                            int winCount = playerInfo.get(username)[0];
                            int gameCount = playerInfo.get(username)[1]+1;
                            playerInfo.put(rolename, new Integer[]{winCount, gameCount});

                        } else {
                            playerInfo.put(rolename, new Integer[]{0, 1});
                        }
                        break;
                    case "COVEN":
                        for (int i = 0; i < COVENROLES.length; i++) {
                            if (rolename.equals(COVENROLES[i])) {
                                if (playerInfo.containsKey(rolename)) {
                                    int winCount = playerInfo.get(username)[0]+1;
                                    int gameCount = playerInfo.get(username)[1]+1;
                                    playerInfo.put(rolename, new Integer[]{winCount, gameCount});
                                } else {
                                    playerInfo.put(rolename, new Integer[]{1, 1});
                                }
                            }
                        }
                        if (playerInfo.containsKey(rolename)) {
                            int winCount = playerInfo.get(username)[0];
                            int gameCount = playerInfo.get(username)[1]+1;
                            playerInfo.put(rolename, new Integer[]{winCount, gameCount});

                        } else {
                            playerInfo.put(rolename, new Integer[]{0, 1});
                        }
                        break;
                    case "APOC":
                        for (int i = 0; i < APOCROLES.length; i++) {
                            if (rolename.equals(APOCROLES[i])) {
                                if (playerInfo.containsKey(rolename)) {
                                    int winCount = playerInfo.get(username)[0]+1;
                                    int gameCount = playerInfo.get(username)[1]+1;
                                    playerInfo.put(rolename, new Integer[]{winCount, gameCount});
                                } else {
                                    playerInfo.put(rolename, new Integer[]{1, 1});
                                }
                            }
                        }
                        if (playerInfo.containsKey(rolename)) {
                            int winCount = playerInfo.get(username)[0];
                            int gameCount = playerInfo.get(username)[1]+1;
                            playerInfo.put(rolename, new Integer[]{winCount, gameCount});

                        } else {
                            playerInfo.put(rolename, new Integer[]{0, 1});
                        }
                        break;
                    case "SK":
                        break;
                    case "WW":
                        break;
                    case "AR":
                        break;
                    case "SH":
                        break;

                }

        }
        catch (Exception e) {
            System.out.println("You were not in this game.");
        }
    }
}
