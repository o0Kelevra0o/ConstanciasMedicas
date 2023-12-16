package Paquetes;

import java.awt.*;
import java.awt.print.*;

/**
 *
 * @author Kelevra
 */
public class Impresion implements Printable {
    private ImpresionPanel panel;

    /**
     *
     * @param panel
     */
    public Impresion(ImpresionPanel panel) {
        this.panel = panel;
    }

    /**
     *
     * @param graphics
     * @param pageFormat
     * @param pageIndex
     * @return
     * @throws PrinterException
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // Ajusta el tamaño del panel al tamaño de papel carta
        panel.setSize((int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());

        // Pinta el contenido del panel en el objeto Graphics
        panel.printAll(g2d);

        return PAGE_EXISTS;
    }
}
