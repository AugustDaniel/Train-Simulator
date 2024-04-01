package guiapplication.schedulePlanner.Simulator.measuring;

import guiapplication.schedulePlanner.Simulator.InfoScreen;
import guiapplication.schedulePlanner.Simulator.npc.NPC;
import guiapplication.schedulePlanner.Simulator.npc.traveler.Traveler;
import util.graph.Node;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MeasurePoint {

    private Set<NPC> npcs;
    private Node node;
    private final InfoScreen infoScreen;

    public MeasurePoint(Node node, LocalTime startTime) {
        this.node = node;
        this.npcs = new HashSet<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        this.infoScreen = new InfoScreen(
                new String[]{
                        "Meet sinds: " + startTime.format(dtf),
                        "Aantal overgelopen: " + 0
                },
                new Point2D.Double(this.node.getPosition().getX() + 20, this.node.getPosition().getY() - 20)
        );
    }

    public void update(List<Traveler> npcs) {
        for (Traveler npc : npcs) {
            if (npc.getCurrentNode() == node) {
                this.npcs.add(npc);
                this.infoScreen.updateInfo("Aantal overgelopen: " + this.npcs.size(), 1);
            }
        }
    }

    public void draw(Graphics2D g) {
        this.infoScreen.draw(g);
        g.setColor(Color.RED);
        g.draw(new Rectangle2D.Double(node.getPosition().getX() - 16, node.getPosition().getY() - 16, 32, 32));
    }

    public boolean contains(Point2D p) {
        Point2D np = node.getPosition();
        return new Rectangle2D.Double(np.getX() - 16, np.getY() - 16, 32, 32).contains(p.getX(), p.getY());
    }
}
