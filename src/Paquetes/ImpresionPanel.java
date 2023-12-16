package Paquetes;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Kelevra
 */
public class ImpresionPanel extends JPanel implements Printable {

    private String numeroFolio;
    private String fecha;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;
    private String expediente;
    private String clinica;
    private String horaInicio;
    private String horaFin;
    private String elaboro;

    /**
     *
     * @param numeroFolio
     * @param fecha
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param nombre
     * @param expediente
     * @param clinica
     * @param horaInicio
     * @param horaFin
     * @param elaboro
     */
    public ImpresionPanel(String numeroFolio, String fecha, String apellidoPaterno, String apellidoMaterno,
            String nombre, String expediente, String clinica, String horaInicio, String horaFin,
            String elaboro) {
        this.numeroFolio = numeroFolio;
        this.fecha = fecha;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombre = nombre;
        this.expediente = expediente;
        this.clinica = clinica;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.elaboro = elaboro;
    }

    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Intenta cargar la imagen de fondo
        try {
            // Ajusta la ruta de la imagen según la ubicación de tu proyecto
            Image fondo = ImageIO.read(getClass().getResource("/img/header.tif"));

            // Dibuja la imagen de fondo
            g.drawImage(fondo, 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear el contenido del documento
        String contenidoDocumento = String.format("\n\nFolio: %s%nFecha: %s%n%n\nA quien corresponda:\n\n\nPor medio de la presente, se hace constar que el(la) C. %s %s %s, \nNo. de Expediente: %s, asistió el día de hoy a atención médica en la Unidad %s, \nentre las %s y las %s horas.\n\n\n\n\n\n%s",
                numeroFolio, fecha, apellidoPaterno, apellidoMaterno, nombre, expediente, clinica, horaInicio, horaFin, elaboro);

        // Configurar el estilo de fuente para el documento
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        // Dividir el contenido del documento en líneas
        String[] lineas = contenidoDocumento.split("\n");

        // Calcular la altura de cada línea
        int alturaLinea = g.getFontMetrics().getHeight();

        // Ajusta la posición vertical según tus necesidades
        int y = 100;

        // Imprimir cada línea en la posición correcta
        for (int i = 0; i < 2; i++) {
            for (String linea : lineas) {
                g.drawString(linea, 50, y);
                y += alturaLinea;
            }

            // Añade un espacio entre las dos impresiones si es necesario
            y += alturaLinea * 11; // Puedes ajustar el factor multiplicador según tus preferencias
        }
    }

    /**
     *
     */
    public void imprimir() {
        PrinterJob job = PrinterJob.getPrinterJob();

        if (job.printDialog()) {
            job.setPrintable(this);

            try {
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Impresión cancelada por el usuario.");
        }
    }

    /**
     *
     * @param g
     * @param pf
     * @param pageIndex
     * @return
     * @throws PrinterException
     */
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        // Ajusta el tamaño del panel al tamaño de papel carta
        setSize((int) pf.getImageableWidth(), (int) pf.getImageableHeight());

        // Pinta el contenido del panel en el objeto Graphics
        printAll(g2d);

        return PAGE_EXISTS;
    }
}
