package com.trasselbackstudios.dexter.data;

public class PokemonTypes {
    public static final String BUG = "Bug";
    public static final String DARK = "Dark";
    public static final String DRAGON = "Dragon";
    public static final String ELECTRIC = "Electric";
    public static final String FAIRY = "Fairy";
    public static final String FIGHTING = "Fighting";
    public static final String FIRE = "Fire";
    public static final String FLYING = "Flying";
    public static final String GHOST = "Ghost";
    public static final String GRASS = "Grass";
    public static final String GROUND = "Ground";
    public static final String ICE = "Ice";
    public static final String NORMAL = "Normal";
    public static final String POISON = "Poison";
    public static final String PSYCHIC = "Psychic";
    public static final String ROCK = "Rock";
    public static final String STEEL = "Steel";
    public static final String WATER = "Water";

    public static final String BUG_COLORED = "<font color='#A8B820'>Bug</font>";
    public static final String DARK_COLORED = "<font color='#705848'>Dark</font>";
    public static final String DRAGON_COLORED = "<font color='#7038F8'>Dragon</font>";
    public static final String ELECTRIC_COLORED = "<font color='#DAA520'>Electric</font>";
    public static final String FAIRY_COLORED = "<font color='#EE99AC'>Fairy</font>";
    public static final String FIGHTING_COLORED = "<font color='#8B0000'>Fighting</font>";
    public static final String FIRE_COLORED = "<font color='#FF8C00'>Fire</font>";
    public static final String FLYING_COLORED = "<font color='#9370DB'>Flying</font>";
    public static final String GHOST_COLORED = "<font color='#705898'>Ghost</font>";
    public static final String GRASS_COLORED = "<font color='#006400'>Grass</font>";
    public static final String GROUND_COLORED = "<font color='#B8860B'>Ground</font>";
    public static final String ICE_COLORED = "<font color='#98D8D8'>Ice</font>";
    public static final String NORMAL_COLORED = "<font color='#A8A878'>Normal</font>";
    public static final String POISON_COLORED = "<font color='#BA55D3'>Poison</font>";
    public static final String PSYCHIC_COLORED = "<font color='#F85888'>Psychic</font>";
    public static final String ROCK_COLORED = "<font color='#8B4513'>Rock</font>";
    public static final String STEEL_COLORED = "<font color='#B8B8D0'>Steel</font>";
    public static final String WATER_COLORED = "<font color='#6495ED'>Water</font>";

    public static String getColoredType(String type) {
        String coloredType = "";
        switch (type) {
            case FIRE:
                coloredType += FIRE_COLORED;
                break;
            case GRASS:
                coloredType += GRASS_COLORED;
                break;
            case POISON:
                coloredType += POISON_COLORED;
                break;
            case WATER:
                coloredType += WATER_COLORED;
                break;
            case NORMAL:
                coloredType += NORMAL_COLORED;
                break;
            case FIGHTING:
                coloredType += FIGHTING_COLORED;
                break;
            case FLYING:
                coloredType += FLYING_COLORED;
                break;
            case ELECTRIC:
                coloredType += ELECTRIC_COLORED;
                break;
            case GROUND:
                coloredType += GROUND_COLORED;
                break;
            case PSYCHIC:
                coloredType += PSYCHIC_COLORED;
                break;
            case ROCK:
                coloredType += ROCK_COLORED;
                break;
            case ICE:
                coloredType += ICE_COLORED;
                break;
            case BUG:
                coloredType += BUG_COLORED;
                break;
            case DRAGON:
                coloredType += DRAGON_COLORED;
                break;
            case GHOST:
                coloredType += GHOST_COLORED;
                break;
            case DARK:
                coloredType += DARK_COLORED;
                break;
            case STEEL:
                coloredType += STEEL_COLORED;
                break;
            case FAIRY:
                coloredType += FAIRY_COLORED;
                break;
            default:
                coloredType += type;
                break;
        }
        return coloredType;
    }
}
