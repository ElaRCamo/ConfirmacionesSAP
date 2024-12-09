package com.grammer.code.util;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

/**********************
 * Esta clase define un borde personalizado en forma de burbuja de texto
 * con un puntero que puede apuntar a la izquierda o a la derecha. 
 * Es útil para dar un estilo de "burbuja de chat" a los componentes Swing.
 **********************/
@SuppressWarnings("serial")
public class TextBubbleBorder extends AbstractBorder {

    private Color color;
    private int thickness = 4;
    private int radii = 8;
    private int pointerSize = 7;
    private Insets insets = null;
    private BasicStroke stroke = null;
    private int strokePad;
    private int pointerPad = 4;
    private boolean left = true;
    private RenderingHints hints;

    public TextBubbleBorder(Color color) {
        this(color, 4, 8, 7);
    }

    public TextBubbleBorder(Color color, int thickness, int radii, int pointerSize) {
        this.thickness = thickness;
        this.radii = radii;
        this.pointerSize = pointerSize;
        this.color = color;

        stroke = new BasicStroke(thickness);
        strokePad = thickness / 2;

        hints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        //Borde inferior
        int pad = radii + strokePad;
        int bottomPad = pad + pointerSize + strokePad;
        insets = new Insets(pad, pad, bottomPad, pad);
    }

    public TextBubbleBorder(Color color, int thickness, int radii, int pointerSize, boolean left) {
        this(color, thickness, radii, pointerSize);
        this.left = left;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        return getBorderInsets(c);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;

        int bottomLineY = height - thickness - pointerSize;

        RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(
            0 + strokePad,
            0 + strokePad,
            width - thickness,
            bottomLineY,
            radii,
            radii
        );

        Polygon pointer = new Polygon();

        if (left) {
            // Puntero apuntando a la izquierda
            pointer.addPoint(strokePad + radii + pointerPad, bottomLineY);
            pointer.addPoint(strokePad + radii + pointerPad + pointerSize, bottomLineY);
            pointer.addPoint(strokePad + radii + pointerPad + (pointerSize / 2), height - strokePad);
        } else {
            // Puntero apuntando a la derecha
            pointer.addPoint(width - (strokePad + radii + pointerPad), bottomLineY);
            pointer.addPoint(width - (strokePad + radii + pointerPad + pointerSize), bottomLineY);
            // El tercer punto del puntero estaba comentado, por lo que se ha mantenido sin cambios
            /* pointer.addPoint(
                width - (strokePad + radii + pointerPad + (pointerSize / 2)),
                height - strokePad
            ); */
        }

        Area area = new Area(bubble);
        area.add(new Area(pointer));

        g2.setRenderingHints(hints);

        // Pinta el color de fondo del componente padre, fuera de la burbuja de texto
        Component parent = c.getParent();
        if (parent != null) {
            Color bg = parent.getBackground();
            Rectangle rect = new Rectangle(0, 0, width, height);
            Area borderRegion = new Area(rect);
            borderRegion.subtract(area);
            g2.setClip(borderRegion);
            g2.setColor(bg);
            g2.fillRect(0, 0, width, height);
            g2.setClip(null);
        }

        g2.setStroke(stroke);
        g2.draw(area);
    }
}
