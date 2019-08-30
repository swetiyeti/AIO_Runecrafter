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

public class Banking extends Task {
    @Override
    public boolean validate() {
        return (AIO_Runecrafter.runeType!= null && AIO_Runecrafter.essenceType != null)
                && outfitRight() && (MadeRunes() || !containsEssTaly()) && AIO_Runecrafter.runeType.getBankArea().contains(Players.getLocal());
    }

    @Override
    public int execute() {
        Log.info("Attempting to bank.");

        //The following nested if statement logic mess is garbage and needs revision.
        if (Bank.isOpen() && OutOfItems()){
            Log.info("Out of items. Logging out.");
            Game.logout();
            return -1;
        } else if (Bank.isOpen() && (Inventory.contains(AIO_Runecrafter.runeType.getRuneID()) || !containsEssTaly())){
            if (Inventory.contains(AIO_Runecrafter.runeType.getRuneID())){
                Bank.depositAll(AIO_Runecrafter.runeType.getRuneID()); //deposits all runes
                Time.sleepUntil(() -> !Inventory.contains(AIO_Runecrafter.runeType.getRuneID()), 1200);
                Log.info("Deposited Runes.");
            }
            if (!Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID())){
                Bank.withdraw(AIO_Runecrafter.essenceType.getEssenceID(),28); //withdraw essence
                Time.sleepUntil(() -> Inventory.contains(7936), 1200);
                Log.info("Withdrew ess");
            }
            //Time.sleepUntil(this::containsEssTaly,500); // Sleep until inventory is right
            //If the inventory is wrong, deposit everything and try again.
            if (!verifyInv()){
                Bank.depositInventory(); //deposit everything
                Time.sleep(200,300);
                //Bank.depositAll(AIO_Runecrafter.essenceType.getEssenceID());
                //Time.sleep(200,300);
            }

        } else{
            Time.sleepUntil(Bank::open,2000);
        }

        return 850;
    }

    private boolean MadeRunes() {
        return Inventory.contains(AIO_Runecrafter.runeType.getRuneID());
    }

    private boolean containsEssTaly(){
        return Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID()); //do not need to contain Taly at the moment
    }

    private boolean outfitRight(){
        Player local = Players.getLocal();
        return local.getAppearance().isEquipped(AIO_Runecrafter.runeType.getTiaraID()); //&& local.getAppearance().isEquipped(5535) binding necklace
    }

    private boolean OutOfItems(){
        //no items in bank inventory or equipped.
        return !Bank.contains(AIO_Runecrafter.essenceType.getEssenceID()) && !Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID());
    }


    private boolean verifyInv(){
        if (Inventory.getCount(AIO_Runecrafter.essenceType.getEssenceID()) != 28)
            return false;
        //nothing else at the moment.
        else return true;
    }

    /*
    private boolean verifyInv(){
        if (Inventory.getCount(7936) != 26)
            return false;
        else if (Inventory.getCount(<Taly ID>) !=1 )
            return false;
        else return true;
    }
     */
}
