import utilities.Dice;

public class FireLizard extends Monster {
    public FireLizard(String name) {
        super(name, ElementalType.FIRE);
        setAttackMin(8);
        setAttackMax(16);
        setDefenseMin(1);
        setDefenseMax(8);
    }

    @Override
    public void setAttackPoints() {
        setAttackPoints(Dice.roll(getAttackMin(),getAttackMax()));
    }

    @Override
    public void setAttackPoints(int points) {
        this.attackPoints = points;
    }

    @Override
    public void setDefensePoints() { setDefensePoints(Dice.roll(getDefenseMin(),getDefenseMax()));
    }

    @Override
    public void setDefensePoints(int points) {
        this.defensePoints = points;
    }
}
