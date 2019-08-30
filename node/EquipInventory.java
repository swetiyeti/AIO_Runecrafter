package AIO_Runecrafter.node;

import AIO_Runecrafter.AIO_Runecrafter;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class EquipInventory extends Task {
    @Override
    public boolean validate() {
        return (AIO_Runecrafter.runeType!= null && AIO_Runecrafter.essenceType != null)
                && (!wearingTiara() && AIO_Runecrafter.runeType.getBankArea().contains(Players.getLocal()));
    }

    @Override
    public int execute() {
        Log.info("Attempting to fix inventory.");

        if (Bank.isOpen() && outOfItems()){
            Log.info("Out of items! Ending.");
            Game.logout();
            return -1; //end script if there are no items left

            //looking to see if tiara is in bank or in the inventory if it's not equipped.
        } else if (Bank.isOpen() && !wearingTiara() &&
                (Bank.contains(AIO_Runecrafter.runeType.getTiaraID()) || Inventory.contains(AIO_Runecrafter.runeType.getTiaraID())) ){
            //looking in the inventory
            if (!Inventory.contains(AIO_Runecrafter.runeType.getTiaraID())) {
                Log.info("Withdrawing Tiara");
                Bank.withdraw(AIO_Runecrafter.runeType.getTiaraID(), 1); // withdraw Tiara
                Time.sleepUntil(() -> Inventory.contains(AIO_Runecrafter.runeType.getTiaraID()), 5000); //wait until you have the Tiara
            }

            //set tiara to object and verify that it exists and is not null before trying to wear.
            final Item Tiara = Inventory.getFirst(AIO_Runecrafter.runeType.getTiaraID());
            if (Tiara != null){
                Log.info("Equipping Tiara");
                Tiara.interact("Wear"); //equip Tiara
                //wait until you're wearing the tiara
                Time.sleepUntil(this::wearingTiara, 2400);
            }

        } else {
            Log.info("Opening Bank");
            Time.sleepUntil(Bank::open,2000);
        }
        return 850;
    }

    private boolean wearingTiara(){
        Player local = Players.getLocal();
        return local.getAppearance().isEquipped(AIO_Runecrafter.runeType.getTiaraID());
    }

    private boolean outOfItems(){
        //no items in bank inventory or equipped.
        return (!Bank.contains(AIO_Runecrafter.runeType.getTiaraID()) && !wearingTiara()
                && !Inventory.contains(AIO_Runecrafter.runeType.getTiaraID()));
    }

}
