package AIO_Runecrafter.node;

import AIO_Runecrafter.AIO_Runecrafter;
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
        && (invRight() && AIO_Runecrafter.runeType.getAltarArea().contains(Players.getLocal()));
    }

    private int runesMade = 0;//for paint
    private long expGained = 0; //camelCase
    private final long startExp = Skills.getExperience(Skill.RUNECRAFTING);

    @Override
    public int execute() {
        Log.info("Crafting Runes");

        SceneObject Altar = SceneObjects.getNearest("Altar"); //ID for Altar
        //null check for altar
        if (Altar != null) {
            Time.sleepUntil(() -> Altar.interact("Craft-rune"), 5000);
            Time.sleepUntil(() -> Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID()), 5000); //optimized
            Time.sleep(1000,1200); //wait around one tick before updating paint.
            Time.sleepUntil(this::paintUpdated, 3000); //count runes made in paint
        }

        if (org.rspeer.runetek.api.component.Dialog.canContinue()) {
            Dialog.processContinue();
        }
        return 850;
    }

    private boolean invRight(){
        return Inventory.contains(AIO_Runecrafter.essenceType.getEssenceID());
    }


    private boolean paintUpdated(){
        final int RUNE_ID = AIO_Runecrafter.runeType.getRuneID();
        if (Inventory.contains(RUNE_ID)){
            Log.info("Updating Paint");
            runesMade = runesMade + Inventory.getCount(true, RUNE_ID);
            expGained = Skills.getExperience(Skill.RUNECRAFTING) - startExp;
            return true;
        }
        return false;
    }


    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g = renderEvent.getSource();
        String runesHour = String.format("%.2f", AIO_Runecrafter.timer.getHourlyRate(runesMade));
        String xpHour = String.format("%.2f", AIO_Runecrafter.timer.getHourlyRate(expGained));
        g.drawString("Runes made: " + runesMade, 30, 50);
        g.drawString("Runes per hour: " + runesHour, 30, 70);
        g.drawString("XP gained: " + expGained, 30, 90);
        g.drawString("XP gained per hour: " + xpHour, 30, 110);
    }
}
