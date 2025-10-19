package shapes;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import utils.Port;

public class GeneralizationLine extends Line {
    public GeneralizationLine(Port from, Port to) {
        super(from, to);
    }

    @Override
    protected void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowSize = 10;

        AffineTransform originalTransform = g2d.getTransform();

        g2d.translate(x2, y2);
        g2d.rotate(angle);

        Path2D.Double arrowHead = new Path2D.Double();

        arrowHead.moveTo(0, 0);
        arrowHead.lineTo(-arrowSize, -arrowSize / 2.0);
        arrowHead.lineTo(-arrowSize, arrowSize / 2.0);
        arrowHead.closePath();

        g2d.draw(arrowHead);
        g2d.setTransform(originalTransform);
    }
}
