package AIO_Runecrafter;

import AIO_Runecrafter.data.EssenceType;
import AIO_Runecrafter.data.RuneType;
import AIO_Runecrafter.node.*;
import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;

import java.awt.*;

@ScriptMeta(name = "Sweti's AIO Runecrafter",  desc = "Makes free to play runes", developer = "Sweti Yeti", category = ScriptCategory.RUNECRAFTING)
public class AIO_Runecrafter extends TaskScript implements RenderListener {

    private static final Task[] TASKS = {new Traverse(), new Banking(), new EnterRuins(), new CraftRunes(), new LeaveRuins(), new equipInventory()};
    public static StopWatch timer;

    public static RuneType runeType;
    public static EssenceType essenceType;

    @Override
    public void onStart() {
        new AIO_RunecrafterGUI().setVisible(true);
        timer = StopWatch.start();
        submit(TASKS);
    }

    @Override
    public void onStop() {
        //When the script is stopped the segment of code in this method will be ran once.
        setStopping(true);
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g = renderEvent.getSource();
        g.drawString("Time elapsed: " + timer.toElapsedString(), 30, 30);
    }
}