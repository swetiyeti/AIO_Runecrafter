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
                && outfitRight() && (madeRunes() || !containsEssTaly()) && AIO_Runecrafter.runeType.getBankArea().contains(Players.getLocal());
    }

    @Override
    public int execute() {
        Log.info("Attempting to bank.");


        if (Bank.isOpen() && outOfItems()){
            Log.info("Out of items. Logging out.");
            Game.logout();
            return -1;
        } else if (Bank.isOpen() && (Inventory.contains(AIO_Runecrafter.runeType.getRuneID()) || !containsEssTaly())){

            if (Inventory.contains(AIO_Runecrafter.runeType.getRuneID())){
                Bank.depositAll(AIO_Runecrafter.runeType.getRuneID()); //deposits all runes
                Time.sleepUntil(() -> !Inventory.contains(AIO_Runecrafter.runeType.getRuneID()), 2400);
                Log.info("Deposited Runes.");
            } else if (!Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID())){

                if (Bank.getCount(AIO_Runecrafter.essenceType.getEssenceID()) < 28){
                    Log.info("Running out of ess. Last trip!");
                    Bank.withdraw(AIO_Runecrafter.essenceType.getEssenceID(), Bank.getCount(AIO_Runecrafter.essenceType.getEssenceID())); //withdraw essence
                    Time.sleepUntil(() -> Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID()), 2400);
                    Log.info("Withdrew ess");
                } else {
                    Bank.withdraw(AIO_Runecrafter.essenceType.getEssenceID(),28); //withdraw essence
                    Time.sleepUntil(() -> Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID()), 2400);
                    Log.info("Withdrew ess");
                }

            }
            //The following if statement verifies the previous two if statements performed correctly.
            //If the inventory is wrong, deposit everything and try again.
            if (!verifyInv()){
                Time.sleepUntil(Bank::depositInventory, 2400);
                //Time.sleep(200,300);
            }

        } else{
            Time.sleepUntil(Bank::open,2000);
        }

        return 850;
    }

    private boolean madeRunes() {
        return Inventory.contains(AIO_Runecrafter.runeType.getRuneID());
    }

    private boolean containsEssTaly(){
        //do not need to contain Taly at the moment
        return Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID());
    }

    private boolean outfitRight(){
        Player local = Players.getLocal();
        return local.getAppearance().isEquipped(AIO_Runecrafter.runeType.getTiaraID()); //&& local.getAppearance().isEquipped(5535) binding necklace
    }

    private boolean outOfItems(){
        //no items in bank inventory or equipped.
        return !Bank.contains(AIO_Runecrafter.essenceType.getEssenceID()) && !Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID());
    }


    private boolean verifyInv(){
        if (Inventory.getCount(AIO_Runecrafter.essenceType.getEssenceID()) != 28){
            return false;
        }
        return true;
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
