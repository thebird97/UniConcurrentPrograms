package sevenLocks.solution.date;

import java.util.*;

public class Participant {

    private static final Random rand = new Random();
    private final List<Attribute> ownAttributes;
    private final Map<Attribute, Integer> preferences;
    private final String name;

    public Participant(String name) {
        this.ownAttributes = new ArrayList<>();
        this.preferences = new EnumMap<>(Attribute.class);
        this.name = name;
    }

    public void tryToAddToOwnAttributes(Attribute attr){
        if(!ownAttributes.contains(attr))
            ownAttributes.add(attr);
    }

    public void addToPreferences(Attribute attr){
        int modifier = rand.nextInt() % 2 == 0 ? 1 : -1;
        if(preferences.containsKey(attr)){
            preferences.put(attr, preferences.get(attr) + modifier);
        } else {
            preferences.put(attr, modifier);
        }
    }

    public int evaluateOther(List<Attribute> otherAttributes){
        int result = 0;
        for( Attribute attr : otherAttributes){
            if(preferences.containsKey(attr)){
                result += preferences.get(attr);
            }
        }
        return result;
    }

    public Attribute getRandomAttribute(){
        int randIndex = rand.nextInt(ownAttributes.size());
        return ownAttributes.get(randIndex);
    }

    public List<Attribute> getOwnAttributes(){
        return Collections.unmodifiableList(ownAttributes);
    }

    public String getName(){
        return this.name;
    }

    public Map<Attribute, Integer> getPreferences(){
        return Collections.unmodifiableMap(preferences);
    }
}
