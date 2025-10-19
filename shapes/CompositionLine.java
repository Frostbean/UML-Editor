package shapes;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.Color;
import java.awt.Polygon;

import utils.Port;

public class CompositionLine extends Line {
    public CompositionLine(Port from, Port to) {
        super(from, to);
    }

    @Override
    protected void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
		int arrowSize = 10;
		int x[] =
		{x2, x2 - arrowSize, x2, x2 + arrowSize};
		int y[] =
		{y2 + arrowSize, y2, y2 - arrowSize, y2};
		Polygon polygon = new Polygon(x, y, x.length);
		g2d.setColor(Color.WHITE);
		g2d.fillPolygon(polygon);
    	g2d.setColor(Color.BLACK);
		g2d.drawPolygon(polygon);
    }
}
