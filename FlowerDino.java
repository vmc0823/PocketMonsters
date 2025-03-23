import utilities.Dice;

public class FlowerDino extends Monster{
    public FlowerDino(String name) {
        super(name, ElementalType.GRASS);
        setAttackMax(6);
        setAttackMin(3);
        setDefenseMin(4);
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
