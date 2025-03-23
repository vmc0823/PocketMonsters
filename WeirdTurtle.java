import utilities.Dice;

public class WeirdTurtle extends Monster {
    public WeirdTurtle(String name) {
        super(name, ElementalType.WATER);
        setAttackMin(3);
        setAttackMax(8);
        setDefenseMin(3);
        setDefenseMax(8);
    }

    @Override
    public void setAttackPoints() {
        setAttackPoints(Dice.roll(getAttackMin(), getAttackMax()));
    }

    @Override
    public void setAttackPoints(int points) {
        this.attackPoints = points;
    }

    @Override
    public void setDefensePoints() { setDefensePoints(Dice.roll(getDefenseMin(), getDefenseMax()));
    }

    @Override
    public void setDefensePoints(int points) {
        this.defensePoints = points;
    }
}
