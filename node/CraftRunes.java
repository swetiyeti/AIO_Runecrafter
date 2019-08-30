package AIO_Runecrafter.node;

import AIO_Runecrafter.AIO_Runecrafter;
import MudMan.MudMan;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.awt.*;

public class CraftRunes extends Task implements RenderListener {
    @Override
    public boolean validate() {
        return (AIO_Runecrafter.runeType!= null && AIO_Runecrafter.essenceType != null)
        && (InvRight() && AIO_Runecrafter.runeType.getAltarArea().contains(Players.getLocal()));
    }

    private int runesMade = 0;//for paint
    private long EXP_GAINED = 0;
    private final long START_EXP = Skills.getExperience(Skill.RUNECRAFTING);

    @Override
    public int execute() {
        Log.info("Crafting Runes");

        SceneObject Altar = SceneObjects.getNearest("Altar"); //ID for Altar
        if (Altar != null) {
            Time.sleepUntil(() -> Altar.interact("Craft-rune"), 5000);
            Time.sleepUntil(() -> Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID()), 5000); //optimized
            Time.sleep(30,90);
            Time.sleepUntil(this::paintUpdated, 3000); //count runes made in paint
            Time.sleep(30,90);
        }

        if (org.rspeer.runetek.api.component.Dialog.canContinue()) {
            Dialog.processContinue();
        }
        return 850; //can be improved
    }

    private boolean InvRight(){
        return Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID());
    }


    private boolean paintUpdated(){
        final int RUNE_ID = AIO_Runecrafter.runeType.getRuneID();
        if (Inventory.contains(RUNE_ID)){
            Log.info("Updating Paint");
            runesMade = runesMade + Inventory.getCount(true, RUNE_ID);
            Time.sleep(15,35);
            EXP_GAINED = Skills.getExperience(Skill.RUNECRAFTING) - START_EXP;
            Time.sleep(15,35);
            return true;
        }
        return false;
    }


    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g = renderEvent.getSource();
        String runesHour = String.format("%.2f", AIO_Runecrafter.timer.getHourlyRate(runesMade));
        String xpHour = String.format("%.2f", AIO_Runecrafter.timer.getHourlyRate(EXP_GAINED));
        g.drawString("Runes made: " + runesMade, 30, 50);
        g.drawString("Runes per hour: " + runesHour, 30, 70);
        g.drawString("XP gained: " + EXP_GAINED, 30, 90);
        g.drawString("XP gained per hour: " + xpHour, 30, 110);
    }
}
