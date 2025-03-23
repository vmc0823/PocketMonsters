import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.Dice;

class MonsterTest {

  private List<Monster> monsters = null;

  private Monster electricRat = null;
  private Monster weirdTurtle = null;
  private Monster flowerDino = null;
  private Monster fireLizard = null;

  private static final String ALL_PHRASE = "No phrase for me 'Lectric!'Urtle!Flowah!Deal with it.";

  private final String weirdTurtleName = "Weird Turtle Thing";

  @BeforeEach
  void setUp() {
    String electricRatName = "Electric Rat";
    electricRat = new ElectricRat(electricRatName);
    weirdTurtle = new WeirdTurtle(weirdTurtleName);
    String flowerDinoName = "Flower Dino";
    flowerDino = new FlowerDino(flowerDinoName);
    String fireLizardName = "Clearly the best";
    fireLizard = new FireLizard(fireLizardName);

    monsters = new ArrayList<>();

    monsters.add(electricRat);
    monsters.add(weirdTurtle);
    monsters.add(flowerDino);
    monsters.add(fireLizard);
  }

  @AfterEach
  void tearDown() {
    electricRat = weirdTurtle = flowerDino = fireLizard = null;
    System.out.printf("%n%n");
  }


  @Test
  void fainted() {
    for (Monster m : monsters){
      assertFalse(m.isFainted());
      m.setFainted(true);
      assertTrue(m.isFainted());
    }
  }

  @Test
  void defTest() {
    int roll;
    int defenseValue;
    int largeDefense = 5001;
    for (Monster monster : monsters) {
      for (int i = 0; i < 50; i++) {
        roll = Dice.roll(monster.getDefenseMin(), monster.getDefenseMax());
        defenseValue = roll;
        assertEquals(
            (roll < (monster.getDefenseMax() / 2.0) && roll % 2 == 0),
            (defenseValue % 2 == 0 && (roll * 2) < monster.getDefenseMax())
        );
        assertNotEquals(monster.getDefenseMax(),monster.getDefensePoints());
        assertNotEquals(monster.getDefenseMin(),monster.getDefensePoints());
      }
      assertNotEquals(largeDefense,monster.getDefensePoints());
      monster.setDefensePoints(largeDefense);
      assertEquals(largeDefense,monster.getDefensePoints());
      monster.setDefensePoints();
      assertNotEquals(largeDefense,monster.getDefensePoints());
    }
  }

  @Test
  void constructor_Test() {

    System.out.println("CONSTRUCTOR TEST");

    WeirdTurtle conTestMonster;
    conTestMonster = new WeirdTurtle(weirdTurtleName);
    assertNotNull(conTestMonster);

  }

  @Test
  void phraseTest() {
    for (Monster m : monsters) {
      System.out.printf("Testing %s%n",m.getPhrase());
      String phrase = m.getPhrase().split("[.!]")[0].trim();
      assertTrue(ALL_PHRASE.contains(phrase));
    }
  }

  @Test
  void rollTest() {

    System.out.println("ROLL TEST");

    int min = 1;
    int max = 10;
    int roll = Dice.roll(min, max);
    assertTrue(roll >= min);
    assertTrue(roll <= max);
  }

  @Test
  void getAttackPointsTest() {
    System.out.println("ATTACK POINT TEST");

    //attack points should NEVER be negative.
    for (Monster m : monsters) {
      System.out.println("Testing: " + m);
      assertTrue(m.getAttackPoints() > 0);
    }

  }

  @Test
  void setGetHealthPointTest(){
    double healthPointTestValue = 5000.1;
    for(Monster m : monsters){
      assertNotEquals(healthPointTestValue,m.getHealthPoints());
      m.setHealthPoints(healthPointTestValue);
      assertEquals(healthPointTestValue,m.getHealthPoints());
      m.takeDamage(Dice.roll(20,4242));
      assertNotEquals(healthPointTestValue,m.getHealthPoints());
    }
  }

  @Test
  void setAttackPointsTest() {
    //AP should never be this high.
    int attackPoints = 42;
    System.out.println("Testing setAttackPoints");
    for (Monster m : monsters) {
      System.out.println("Testing : " + m);
      assertNotEquals(attackPoints, m.getAttackPoints());
      m.setAttackPoints(attackPoints);
      assertEquals(attackPoints, m.getAttackPoints());

      m.setAttackPoints();
      assertTrue(m.getAttackPoints() <= m.getAttackMax());
    }
  }


  @Test
  void attack() {
    System.out.println("ATTACK TEST");

    for (Monster m : monsters) {
      for (Monster o : monsters) {
        System.out.println(m.attack(o));
      }
    }
    System.out.println(electricRat.attack(weirdTurtle));
  }

  @Test
  void toStringTest() {
    System.out.println("TESTING To Strings!");
    for (Monster m : monsters) {
      System.out.println(m);
    }

    electricRat.setType(Monster.ElementalType.GRASS);

    System.out.println(electricRat);

    while (!flowerDino.isFainted()) {
      fireLizard.attack(flowerDino);
    }
    System.out.println(flowerDino);

  }

  @Test
  void getElements() {
    System.out.println("TESTING GET ELEMENTS");
    List<Monster.ElementalType> testTypes = new ArrayList<>();
    testTypes.add(Monster.ElementalType.ELECTRIC);
    assertEquals(testTypes, electricRat.getElements());
    assertNotEquals(testTypes, weirdTurtle.getElements());
    testTypes.add(Monster.ElementalType.FIRE);
    assertNotEquals(testTypes, electricRat.getElements());

    System.out.println(electricRat);
  }

  @Test
  void setType() {

    System.out.println("TESTING SET TYPE");

    //Should already have electric set
    assertEquals(1, electricRat.setType(Monster.ElementalType.ELECTRIC));
    //should be able to add fire to electric
    assertEquals(0, electricRat.setType(Monster.ElementalType.FIRE));
    // can't have a water/electric type... in LDPocket anyway.
    assertEquals(-1, electricRat.setType(Monster.ElementalType.WATER));

    System.out.println(electricRat);
  }


}