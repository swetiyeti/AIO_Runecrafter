package AIO_Runecrafter.node;

import AIO_Runecrafter.AIO_Runecrafter;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class equipInventory extends Task {
    @Override
    public boolean validate() {
        return (AIO_Runecrafter.runeType!= null && AIO_Runecrafter.essenceType != null)
                && (!wearingTiara() && AIO_Runecrafter.runeType.getBankArea().contains(Players.getLocal()));
    }

    @Override
    public int execute() {
        Log.info("Attempting to fix inventory.");
        if (Bank.isOpen() && OutOfItems()){
            Log.info("Out of items! Ending.");
            Game.logout();
            return -1; //end script if there are no items left
        } else if (Bank.isOpen() && !wearingTiara() && Bank.contains(AIO_Runecrafter.runeType.getTiaraID())){
            Log.info("Withdrawing Tiara");
            Bank.withdraw(AIO_Runecrafter.runeType.getTiaraID(), 1); // withdraw Tiara
            Time.sleepUntil(() -> Inventory.contains(AIO_Runecrafter.runeType.getTiaraID()), 5000); //wait until you have the Tiara
            Inventory.getFirst(AIO_Runecrafter.runeType.getTiaraID()).interact("Wear"); //equip Tiara
            Time.sleepUntil(this::wearingTiara, 2000);
            Log.info("Equipped Tiara");
        } else {
            Time.sleepUntil(Bank::open,2000);
        }
        return 650;
    }

    private boolean wearingTiara(){
        Player local = Players.getLocal();
        return local.getAppearance().isEquipped(AIO_Runecrafter.runeType.getTiaraID());
    }

    private boolean OutOfItems(){
        //no items in bank inventory or equipped.
        return (!Bank.contains(AIO_Runecrafter.runeType.getTiaraID()) && !wearingTiara()
                && !Inventory.contains(AIO_Runecrafter.runeType.getTiaraID()));
    }

}
