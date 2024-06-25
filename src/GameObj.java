import java.util.HashMap;

public class GameObj {
    private  final String[] ROLES = new String[]{"Coroner", "Investigator", "Lookout", "Psychic", "Sheriff", "Spy", "Tracker", "Bodyguard", "Cleric", "Crusader", "Trapper", "Veteran", "Trickster", "Deputy", "Vigilante", "Admirer", "Amnesiac", "Retributionist", "Socialite", "Tavern Keeper", "Jailor", "Marshal", "Mayor", "Monarch", "Prosecutor", "Coven Leader", "Hex Master", "Witch", "Conjurer", "Jinx", "Ritualist", "Dreamweaver", "Enchanter", "Illusionist", "Medusa", "Necromancer", "Poisoner", "Potion Master", "Voodoo Master", "Wildling", "Arsonist", "Serial Killer", "Shroud", "Werewolf", "Baker", "Berserker", "Plaguebearer", "Soul Collector", "Famine", "War", "Pestilence", "Death", "Vampire", "Cursed Soul"};
    private HashMap<String, String> winnerMap;
    private HashMap<String, String> loserMap;
    private HashMap<String, String> playerMap;
    private HashMap<String, Integer> roleMap;
    public GameObj() {
        for (int i = 0; i < ROLES.length; i++) {
            roleMap.put(ROLES[i],0);
        }
    }
    public GameObj(String arg) {
        for (int i = 0; i < ROLES.length; i++) {
            roleMap.put(ROLES[i],0);
        }
    }

}
