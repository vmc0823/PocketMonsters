import utilities.Dice;

public class ElectricRat extends Monster {
    public ElectricRat(String name) {
        super(name, ElementalType.ELECTRIC);
        setAttackMin(5);
        setAttackMax(8);
        setDefenseMin(5);
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
