import utilities.Dice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pocket Monsters (Project 1)
 * Abstract class Monster for all monsters.
 * Subclasses must implement attack/defense point setters
 * @author Valentina Waltman
 * @since 2.0.1
 */
public abstract class Monster {

    //fields
    private int defenseMin = 1;
    private int defenseMax = 10;
    private int attackMin = 1;
    private int attackMax = 10;
    private final static double MAX_HP = 20.0; //private static double MAX_HP = 20.0; ?
    private String name = "";
    private String phrase = "";
    private Double healthPoints = MAX_HP;
    protected int attackPoints = 10;
    protected int defensePoints = 10;
    private boolean fainted = false;
     //List with types for Monster, like ELECTRIC, FIRE, GRASS, WATER.
    private final List<ElementalType> elements = new ArrayList<>();

    /**
     * calling setPhrase(Monster) method with the elemental type.
     * @param name Display monster's name, for example, Electric Rat
     * @param element primary element type, for example, ELECTRIC
     */
    public Monster(String name, ElementalType element) {
        this.name = name;
        this.elements.add(element);
        setPhrase(this);
    }

    //generated setters and getters
    public int getDefenseMin() {
        return defenseMin;
    }

    public void setDefenseMin(int defenseMin) {
        this.defenseMin = defenseMin;
    }

    public int getDefenseMax() {
        return defenseMax;
    }

    public void setDefenseMax(int defenseMax) {
        this.defenseMax = defenseMax;
    }

    public int getAttackMin() {
        return attackMin;
    }

    public void setAttackMin(int attackMin) {
        this.attackMin = attackMin;
    }

    public int getAttackMax() {
        return attackMax;
    }

    public void setAttackMax(int attackMax) {
        this.attackMax = attackMax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    private static final Map<Class<? extends Monster>, String> PHRASE_MAP = new HashMap<>();

    static {
        PHRASE_MAP.put(ElectricRat.class, "'Lectric!");
        PHRASE_MAP.put(FireLizard.class, "Deal with it.");
        PHRASE_MAP.put(FlowerDino.class, "Flowah!");
        PHRASE_MAP.put(WeirdTurtle.class, "'Urtle!");
    }

    /**
     * setPhrase() will assign a phrase to each individual concrete implementation of Monster
     * @param monster phrase for each of the monsters
     */
    private void setPhrase(Monster monster) {
        String assignedPhrase = PHRASE_MAP.getOrDefault(monster.getClass(), "No phrase for me");
        monster.setPhrase(assignedPhrase);
    }

    public Double getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(Double healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public boolean isFainted() {
        return fainted;
    }

    public void setFainted(boolean fainted) {
        this.fainted = fainted;
    }

    //abstract methods
    public abstract void setAttackPoints();
    public abstract void setAttackPoints(int points);
    public abstract void setDefensePoints();
    public abstract void setDefensePoints(int points);

    /**
     * @return a copy of internal list
     */
    public List<ElementalType> getElements() {
        return new ArrayList<>(elements); //return a copy of the arraylist to prevent encapsulation issues
    }

    /**
     *Monster object as a parameter and calculates the attack points the current object will use against it.
     * The Monster object being passed in is the Monster being attacked
     * @param monster name
     * @return damageIntake
     */
    public double attack(Monster monster) {
        /*
          If the current Monster has fainted, print the following:
          "{name} isn't conscious… it can't attack."
          {name} is whatever value has been assigned as the name to the current object.
          And return 0.0
         */
        if (isFainted()) {
            System.out.println(getName() + " isn't conscious... it can't attack. ");
            return 0.0;
        }
        /*
          "{name} is attacking {monster name}"
          where {name} is the name of the current object
          and {monster name} is the name from the monster object that was passed in to the method.
         */
        System.out.println(getName() + " is attacking " + monster.getName());
        /*
          Print the result of the getPhrase() method.
         */
        System.out.println(getPhrase());
        /*
          Calculate the value for the attack by calling calculateAttackPoints() passing in the current object
          and the elemental types of the monster being attacked
         */
        double attackValue = calculateAttackPoints(monster, monster.getElements());
        /*
          Print the following to the screen
          "{name} is attacking with {attack value}"
          where {name} is the name of the current object and {attack value} the result from calling calculateAttackPoints().
         */
        System.out.println(getName() + " is attacking with " + attackValue + " attack points!");
        /*
          Calculate how much damage was incurred by calling the takeDamage() method of the monster
          that is being attacked and store the result
         */
        double damageIntake = monster.takeDamage(attackValue);
        /*
          If the current object is equal to the monster object being attack and the value returned from takeDamage()
          is greater than 0 print the following:
          "{name} hurt itself in the confusion."
          where {name} is the name of the current object
         */
        if (this == monster && damageIntake > 0) {
            System.out.println(getName() + " hurt itself in the confusion.");
        }
        return damageIntake;
    }

    /**
     * takeDamage() uses the passed in 'attackValue' to calculate how much damage, if any, the monster takes
     * @param attackValue calculate how much damage
     * @return attackPts damage after defense
     */
    public double takeDamage(double attackValue) {
        /*
          First calculate the defense points by calling calculateDefensePoints() and storing the results.
         */
        double defensePts = calculateDefensePoints(this);
        /*
          Then calculate attack points by subtracting the defense points from the passed in attack value
         */
        double attackPts = attackValue - defensePts;
        /*
          If the attack points are greater than 0 print
          "{name} is hit for {attack points} damage!"
          Where {name} is the name of the current object and {attack points} is the value calculated for attack points
          and reduce the current object's health points by the value calculated for attack points
         */
        if (attackPts > 0) {
            healthPoints -= attackPts;
            System.out.println(getName() + " is hit for " + attackPts + " damage!");
            /*
              If the value for attack points is equal to 0 print
              "{name} is nearly hit!."
              where {name} is the name of the current object
             */
        } else if (attackPts == 0) {
            System.out.println(getName() + " is nearly hit! ");
            /*
              If the attack points are less than half the value calculated for defense points, print
              "{name} shrugs off the puny attack."
              where {name} is the name of the current object
             */
        } else if (attackPts < (defensePts / 2.0)) {
            System.out.println(getName() + " shrugs off the puny attack.");
        }
        if (healthPoints <= 0) {
            fainted = true;
            System.out.println(getName() + " has faint-- passed out. It's passed out.");
            /*
              If the value for healthPoints for the current object has fallen to 0 or less print
              "{name} has faint-- passed out. It's passed out."
              where {name} is the name of the current object
              Set fainted to true
             */
        } else {
            System.out.println(getName() + " has " + healthPoints + "/" + MAX_HP + " HP remaining");
            /*
              Otherwise print
              "{name} has {healthPoints} / {MAX_HP} HP remaining"
              where {name} is the name of the current object. {healthPoints} are the healthPoints of the current object
              and {MAX_HP} is the value stored for MAX_HP of the current object
             */
      }
        /*
          Return the value that was calculated for attack points
         */
      return attackPts;
    }

    /**
     * Call Dice.roll() with the minimum defense value and the maximum defense value of the Monster object that was passed in as a parameter.
     * This becomes the defenseValue
     * @param monster name
     * @return defenseValue
     */
    private double calculateDefensePoints(Monster monster) {
        double defenseValue = Dice.roll(monster.getDefenseMin(), monster.getDefenseMax()); //if double, casting defenseValue fails test
        /*
          If the defenseValue is even and less than half the maximum defense of the passed in monster,
          add 1 to the defenseValue then multiply the result by 2. Print:
          where {name} is the name of the passed in Monster
         */

        if (defenseValue % 2 == 0 && defenseValue < ((double) monster.getDefenseMax() / 2.0)) {
            defenseValue = (defenseValue + 1) * 2;
            System.out.println(monster.getName() + " finds courage in the desperate situation");
        } else if (defenseValue == monster.getDefenseMin()) {
            /*
              Otherwise if the defenseValue is equal to the minimum defense of the passed in Monster object:
              "{name} is clearly not paying attention."
              where {name} is the name of the passed in monster object
             */
            System.out.println(monster.getName() + " is clearly not paying attention.");
        }
        /*
          Return the defenseValue
         */
        return defenseValue;
    }

    /**
     *Use the method roll from the Dice class to calculate the attack value for the passed in Monster
     * @param monster the one rolling
     * @param enemyTypes elemental types for the monster
     * @return result of multiplier
     */
    private double calculateAttackPoints(Monster monster, List<ElementalType> enemyTypes) {
        /*
          Call Dice.roll() with the value for attackMin and attackMax of the passed in Monster object.
          Store the result. This will be our attack value.
         */
       double rollNumber = Dice.roll(monster.getAttackMin(), monster.getAttackMax());
        /*
          Print the following
          "{name} rolls a {attack points}"
          where {name} is the name of the passed in Monster object
          and {attack points} is the value returned from the call to Dice.roll().
         */
        System.out.println(monster.getName() + " rolls a " + rollNumber);
        /*
          Create a double named modifier and set it to 1.0
         */
        double modifier = 1.0;
        /*
          The value for modifier is multiplied by the value returned from calling attackModifier()
          with each of the enemyTypes that were passed to calculateAttackPoints()
         */
        for(ElementalType enemyType : enemyTypes) {
            modifier *= attackModifier(enemyType);
        }
        /*
          Create a double named modifier and set it to 1.0
         */
        if (modifier >= 2.0) {
            System.out.println("It's su-- *cough* very effective");
        }
        /*
          Return the attack value multiplied by the modifier.
         */
        return rollNumber * modifier;
    }

    private static final Map<String, Double> ATTACK_MODIFIER = new HashMap<>();
    static {
        ATTACK_MODIFIER.put("ELECTRIC->WATER", 2.0);
        ATTACK_MODIFIER.put("ELECTRIC->ELECTRIC", 0.5);
        ATTACK_MODIFIER.put("ELECTRIC->GRASS", 0.5);
        ATTACK_MODIFIER.put("FIRE->WATER", 0.5);
        ATTACK_MODIFIER.put("FIRE->FIRE", 0.5);
        ATTACK_MODIFIER.put("FIRE->GRASS", 2.0);
        ATTACK_MODIFIER.put("WATER->ELECTRIC", 0.5);
        ATTACK_MODIFIER.put("WATER->WATER", 0.5);
        ATTACK_MODIFIER.put("WATER->FIRE", 2.0);
        ATTACK_MODIFIER.put("GRASS->FIRE", 0.5);
        ATTACK_MODIFIER.put("GRASS->WATER", 2.0);
        ATTACK_MODIFIER.put("GRASS->GRASS", 0.5);
    }

    /**
     * AttackModifier takes an ElementalType and compares it to the values stored in the ArrayList of elemental types (elements) stored in the current object.
     * Based on these values an attack modifier is chosen
     * @param defending elemental type to compare
     * @return the result of multipliers
     */
    private double attackModifier(ElementalType defending) {
        double modifier = 1.0;
        for (ElementalType attackType : getElements()) {
            String key = attackType + "->" + defending;
            modifier *= ATTACK_MODIFIER.getOrDefault(key, 1.0);
        }
        /*
        If there is no match then return 1.0
         */
        return modifier;
    }

    /**
     * Returns "{phrase} {phrase}" where {phrase} is the value stored in the field phrase.
     * @return "{phrase} {phrase}"
     */
    public String getPhrase() {
        return phrase + " " + phrase;
    }

    /**
     *If the Monster object being printed has fainted set to false print the following
     * "{name} has {currentHp} / {MAX_HP} hp."
     * "Elemental type: [Type1{, Type2 …}]"
     * @return Status string of the monster
     */
    @Override
    public String toString() {
        String typeString = getElements().toString();
        if (!fainted) {
            return String.format("%s has %.1f/%.1f hp.%nElemental type: %s", getName(), getHealthPoints(), MAX_HP, typeString);
        } else {
            return String.format("%s has fainted.%nElemental type: %s", getName(), typeString);
        }
    }

    /**
     * Used to set the ElementalType of the current object.
     * @param type to add to the monster
     */
    public int setType(ElementalType type) {
        /*
          If the type that is passed in already exists in the current objects list of ElementalTypes (elements) print
          "{type} already set!"
          Where {type} is the value passed in to setType.
          Return 1
         */
        if (elements.contains(type)) {
            System.out.println(type + " already set!");
            return 1;
        }
        /*
            If the value returned from attackModifier() called with the passed in type, is greater than 1.0 print:
            "Can't have conflicting types!"
            Return -1
             */
        for (ElementalType existing : elements) {
            double singleModifier = singleAttackModifier(existing, type);
            if (singleModifier > 1.0) {
                System.out.println("Can't have conflicting types!");
                return -1;
            }
        }
        /*
          Otherwise print
          "{name} now has {type}"
          Where {name} is the name of the current object and {type} is the  value passed in to setType.
          And add the type to the monster!
          Return 0
         */
        elements.add(type);
        System.out.println(getName() + " now has " + type);
        return 0;
    }

    /**
     * checks only one attacker and defender
     * @param attacker one
     * @param defender one
     * @return 1.0
     */
    private double singleAttackModifier(ElementalType attacker, ElementalType defender) {
        if (attacker == ElementalType.ELECTRIC && defender == ElementalType.WATER) return 2.0;
        if (attacker == ElementalType.ELECTRIC && (defender == ElementalType.ELECTRIC || defender == ElementalType.GRASS)) return 0.5;
        return 1.0;
    }

    /**
     * THis is an inner class that represents the types that may be used to
     * create pocket monsters.
     */
    protected enum ElementalType {
        ELECTRIC,
        FIRE,
        GRASS,
        WATER,
    }

}
