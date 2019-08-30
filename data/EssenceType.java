package AIO_Runecrafter.data;

import org.rspeer.runetek.api.movement.position.Area;

public enum EssenceType {

    //enum goes as follows: bank area cords, ruins area cords, ruins run area cords, altar area cords, tiara ID, rune ID.
    PURE_ESSENCE( 7936),
    NORMAL_ESSENCE(1436) 
    ;

    private final int essenceID;

    //constructor method
    EssenceType(final int essenceID) {
        this.essenceID = essenceID;
    }

    //getters so you can use the enum.
    public int getEssenceID(){
        return essenceID;
    }


}
