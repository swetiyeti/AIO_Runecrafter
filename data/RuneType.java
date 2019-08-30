package AIO_Runecrafter.data;

import org.rspeer.runetek.api.movement.position.Area;

public enum RuneType {

    //enum goes as follows: bank area cords, ruins area cords, ruins run area cords, altar area cords, tiara ID, rune ID.
    AIR(Area.rectangular(3009, 3357, 3018, 3355),
            Area.rectangular(2978, 3285, 2993, 3301),
            Area.rectangular(2985,3294,2987,3296),
            Area.rectangular(2836,4823,2850,4842),
            5527,556),

    WATER(Area.rectangular(3207, 3221, 3210, 3215, 2),
            Area.rectangular(3174, 3169, 3197, 3155),
            Area.rectangular(3187,3166,3188,3167),
            Area.rectangular(2708, 4825, 2730, 4844),
            5531, 555),

    EARTH(Area.rectangular(3251, 3422, 3256, 3419),
            Area.rectangular(3299, 3484, 3310, 3471),
            Area.rectangular(3305, 3473, 3303, 3471),
            Area.rectangular(2651, 4828, 2665, 4847),
            5535, 557),

    FIRE(Area.rectangular(3380, 3272, 3384, 3267),
            Area.rectangular(3306, 3248, 3318, 3260),
            Area.rectangular(3309,3250,3311,3252),
            Area.rectangular(2572,4830,2598,4849),
            5537,554)
    //add Mind and Body
    ;

    //initialize variables
    private final Area bankArea, ruinsArea, ruinsRunArea, altarArea;
    private final int tiaraID, runeID;

    //constructor method
    RuneType(final Area bankArea, final Area ruinsArea, final Area ruinsRunArea, final Area altarArea, final int tiaraID, final int runeID) {
        this.bankArea = bankArea;
        this.ruinsArea = ruinsArea;
        this.ruinsRunArea = ruinsRunArea;
        this.altarArea = altarArea;
        this.tiaraID = tiaraID;
        this.runeID = runeID;
    }

    //getters so you can use the enum.
    public Area getBankArea(){
        return bankArea;
    }

    public Area getRuinsArea(){
        return ruinsArea;
    }

    public Area getRuinsRunArea(){
        return ruinsRunArea;
    }

    public Area getAltarArea(){
        return altarArea;
    }

    public int getTiaraID(){
        return tiaraID;
    }

    public int getRuneID(){
        return runeID;
    }

}
