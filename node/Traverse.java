package AIO_Runecrafter.node;

import AIO_Runecrafter.AIO_Runecrafter;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Traverse extends Task {
    @Override
    public boolean validate() {
        return (AIO_Runecrafter.runeType!= null && AIO_Runecrafter.essenceType != null) && (traverseToBank() || traverseToAltar());
    }

    @Override
    public int execute() {
        Log.info("Traversing");
        if (Movement.getRunEnergy() > 45 && !Movement.isRunEnabled()) {
            Movement.toggleRun(!Movement.isRunEnabled());
        }

        Movement.walkTo(traverseToBank() ? AIO_Runecrafter.runeType.getBankArea().getCenter() : AIO_Runecrafter.runeType.getRuinsRunArea().getCenter() );

        return 1450;
    }


    private boolean madeRunes() {
        return Inventory.contains(AIO_Runecrafter.runeType.getRuneID()); //selected rune ID
    }

    private boolean invRight(){
        return Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID()); //include tally in future?
    }

    private boolean traverseToAltar() {
        return invRight() && !madeRunes() && !AIO_Runecrafter.runeType.getRuinsArea().contains(Players.getLocal()) && !AIO_Runecrafter.runeType.getAltarArea().contains(Players.getLocal());
    }

    private boolean traverseToBank() {
        return (!invRight() || madeRunes()) && !AIO_Runecrafter.runeType.getBankArea().contains(Players.getLocal()) && !AIO_Runecrafter.runeType.getAltarArea().contains(Players.getLocal());
    }

}

