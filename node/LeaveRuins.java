package AIO_Runecrafter.node;

import AIO_Runecrafter.AIO_Runecrafter;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class LeaveRuins extends Task{

    @Override
    public boolean validate() {
        return (AIO_Runecrafter.runeType!= null && AIO_Runecrafter.essenceType != null)
        && (!InvRight() && AIO_Runecrafter.runeType.getAltarArea().contains(Players.getLocal()));
    }

    @Override
    public int execute() {
        if (Dialog.canContinue()) {
            Dialog.processContinue();
        }
        Log.info("Leaving Ruins");
        SceneObject Portal = SceneObjects.getNearest("Portal");
        //null check for portal
        if (Portal != null) {
            Time.sleepUntil(()-> Portal.interact("Use"), 200);
        }
        return 650;
    }

    private boolean InvRight(){
        return Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID());
    }
}
