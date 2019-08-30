package AIO_Runecrafter.node;

import AIO_Runecrafter.AIO_Runecrafter;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class EnterRuins extends Task {
    @Override
    public boolean validate() {
        return (AIO_Runecrafter.runeType!= null && AIO_Runecrafter.essenceType != null)
                && (InvRight() && AIO_Runecrafter.runeType.getRuinsArea().contains(Players.getLocal()));
    }

    @Override
    public int execute() {
        Log.info("Entering Ruins");
        SceneObject Ruins = SceneObjects.getNearest("Mysterious ruins"); //ID for Mysterious-ruins
        if (Ruins != null){
            Ruins.interact("Enter");
        }
        return 650;
    }

    private boolean InvRight(){
        return Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID());
    }
}
