interface RPGCharacter {
    void showStats();
    void levelUp();
    void calSpeed();
    double getMaxHp();
    boolean isDie();
}

interface IWarrior extends RPGCharacter {
    void equipSword(Sword sword);
    void equipShield(Shield shield);
    void equipAccessories(Armor armor);
}

interface IArcher extends RPGCharacter {
    void equipBow(Bow bow);
    void equipAccessories(Grove grove);
}

interface Equipment {
    double calEffect();
    double calRunPenalty();
    void levelUp();
}

interface Accessory {
    double calAccEffect();
    double calRunPenalty();
    void levelUp();

    int getLevel();
}

class Sword implements Equipment {
    protected String name;
    protected double baseValue;
    protected int level = 1;
    protected double weight;

    public Sword(String name, double baseValue, double weight) {
        this.name = name;
        this.baseValue = baseValue;
        this.weight = weight;
    }

    @Override
    public double calEffect() {
        return baseValue * (1 + 0.1 * level);
    }

    @Override
    public double calRunPenalty() {
        return weight * (0.1 + 0.04);
    }

    @Override
    public void levelUp() {
        level++;
        System.out.println(name + " leveled up to " + level + "!");
    }
}

class Shield implements Equipment {
    protected String name;
    protected double baseValue;
    protected int level = 1;
    protected double weight;

    public Shield(String name, double baseValue, double weight) {
        this.name = name;
        this.baseValue = baseValue;
        this.weight = weight;
    }

    @Override
    public double calEffect() {
        return baseValue * (1 + 0.05 * level);
    }

    @Override
    public double calRunPenalty() {
        return weight * (0.1 + 0.08 * level);
    }

    @Override
    public void levelUp() {
        level++;
        System.out.println(name + " leveled up to " + level + "!");
    }
}

class Bow implements Equipment {
    protected String name;
    protected double baseValue;
    protected int level = 1;
    protected double weight;

    public Bow(String name, double baseValue, double weight) {
        this.name = name;
        this.baseValue = baseValue;
        this.weight = weight;
    }

    @Override
    public double calEffect() {
        return baseValue * (1 + 0.1 * level);
    }

    @Override
    public double calRunPenalty() {
        return weight * (0.1 + 0.03);
    }

    @Override
    public void levelUp() {
        level++;
        System.out.println(name + " leveled up to " + level + "!");
    }
}
class Armor implements Accessory {
    protected String name;
    protected double baseDefense;
    protected int level = 1;
    protected double weight;

    public Armor(String name, double baseDefense, double weight) {
        this.name = name;
        this.baseDefense = baseDefense;
        this.weight = weight;
    }

    @Override
    public double calAccEffect() {
        return baseDefense * (1 + 0.05 * level);
    }

    @Override
    public double calRunPenalty() {
        return weight * (0.1 + 0.03 * level);
    }

    @Override
    public void levelUp() {
        level++;
        System.out.println(name + " leveled up to " + level + "!");
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public double getBaseDefense() {
        return baseDefense;
    }

    public double getWeight() {
        return weight;
    }

}

class Grove implements Accessory {
    protected String name;
    protected double baseEffect;
    protected int level = 1;
    protected double weight;

    public Grove(String name, double baseEffect, double weight) {
        this.name = name;
        this.baseEffect = baseEffect;
        this.weight = weight;
    }

    @Override
    public double calAccEffect() {
        return baseEffect * (1 + 0.05 * level);
    }

    @Override
    public double calRunPenalty() {
        return weight * (0.1 + 0.02);
    }

    @Override
    public void levelUp() {
        level++;
        System.out.println(name + " level up to " + level + "!");
    }

    @Override
    public int getLevel() {
        return level;
    }
}

class Warrior implements IWarrior {
    protected String name;
    protected int level = 1;
    protected double baseRunSpeed = 25 + 2*level;
    protected double maxHP = 90 + 8*level;
    protected double maxStamina = 100 + 10*level;
    protected Sword sword;
    protected Shield shield;
    protected Armor armor;

    public Warrior(String name) {
        this.name = name;
    }

    @Override
    public void showStats() {
        System.out.println("Warrior: " + name);
        System.out.println("Level: " + level);
        System.out.println("Max HP: " + maxHP);
        System.out.println("Run Speed: " + calRunSpeed());
        System.out.println("Sword Damage: " + (sword != null ? sword.calEffect() : 0));
        System.out.println("Shield Defense: " + (shield != null ? shield.calEffect() : 0));
    }

    @Override
    public void levelUp() {
        level++;
        maxHP = 90 + 8*level;
        maxStamina = 100 + 10*level;
        System.out.println(name + " leveled up to " + level);
    }

    @Override
    public void calSpeed() {
        System.out.println("Run Speed: " + calRunSpeed());
    }

    private double calRunSpeed() {
        double penalty = 0;
        if(sword != null) penalty += sword.calRunPenalty();
        if(shield != null) penalty += shield.calRunPenalty();
        if(armor != null) penalty += armor.calRunPenalty();
        return baseRunSpeed - penalty;
    }

    @Override
    public void equipSword(Sword sword) {
        this.sword = sword;
        System.out.println(name + " equipped " + sword.calEffect() + " damage sword.");
    }

    @Override
    public void equipShield(Shield shield) {
        this.shield = shield;
        System.out.println(name + " equipped " + shield.calEffect() + " defense shield.");
    }

    @Override
    public void equipAccessories(Armor armor) {
        this.armor = armor;
        System.out.println(name + " equipped armor.");
    }

    public boolean isDie(){
        if(maxHP <= 0)return true;
        return false;
    }

    @Override
    public double getMaxHp(){
        return maxHP;
    }

    public void attack(Archer target) {
        target.maxHP -= Math.max(this.sword.calEffect() - target.grove.calAccEffect(), 0);
        System.out.println(name + " attacks " + target.name + " " + Math.max(this.sword.calEffect() - target.grove.calAccEffect(), 0) + " damage!");
        if (target.isDie()) {
            System.out.println(target.name + " has been defeated!");
        }
        System.out.println("-------------------------------------------------------");
    }
}

class Archer implements IArcher {
    protected String name;
    protected int level = 1;
    protected double baseRunSpeed = 25 + 2*level;
    protected double maxHP = 90 + 8*level;
    protected double maxStamina = 100 + 10*level;
    protected Bow bow;
    protected Grove grove;

    public Archer(String name) {
        this.name = name;
    }

    @Override
    public void showStats() {
        System.out.println("Archer: " + name);
        System.out.println("Level: " + level);
        System.out.println("Max HP: " + maxHP);
        System.out.println("Run Speed: " + calRunSpeed());
        System.out.println("Bow Damage: " + (bow != null ? bow.calEffect() : 0));
    }

    @Override
    public void levelUp() {
        level++;
        maxHP = 90 + 8*level;
        maxStamina += 100 + 10*level;
        System.out.println(name + " leveled up to " + level);
    }

    @Override
    public void calSpeed() {
        System.out.println("Run Speed: " + calRunSpeed());
    }

    private double calRunSpeed() {
        double penalty = 0;
        if (bow != null) penalty += bow.calRunPenalty();
        if (grove != null) penalty += grove.calRunPenalty();
        return baseRunSpeed - penalty;
    }

    @Override
    public void equipBow(Bow bow) {
        this.bow = bow;
        System.out.println(name + " equipped bow with " + bow.calEffect() + " damage.");
    }

    @Override
    public void equipAccessories(Grove grove) {
        this.grove = grove;
        System.out.println(name + " equipped grove.");
    }

    @Override
    public double getMaxHp() {
        return this.maxHP;
    }

    public boolean isDie(){
        if(maxHP <= 0)return true;
        return false;
    }

    public void attack(Warrior target) {
        target.maxHP -= Math.max(this.bow.calEffect() - (target.shield.calEffect()+target.armor.calAccEffect()), 0);
        System.out.println(name + " attacks " + target.name + " " + Math.max(this.bow.calEffect() - (target.shield.calEffect()+target.armor.calAccEffect()), 0) + " damage!");
        if (target.isDie()) {
            System.out.println(target.name + " has been defeated!");
        }
        System.out.println("-------------------------------------------------------");
    }

}

