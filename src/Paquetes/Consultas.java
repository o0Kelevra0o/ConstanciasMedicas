package Paquetes;

import java.awt.Color;
import java.awt.Font;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;

/**
 *
 * @author Kelevra
 */
public class Consultas extends javax.swing.JPanel {

    /**
     *
     */
    public Consultas() {
        initComponents();
        inicializarTablaDatos();
        txtBuscarFocusLost(null);
    }

    private String originalText1 = "Folio, Apellido, Nombre";

    private void inicializarTablaDatos() {
        DefaultTableModel modelo = (DefaultTableModel) tableDatos.getModel();
        // Limpiar la tabla antes de cargar nuevos datos
        modelo.setRowCount(0);

        // Agregar los encabezados de las columnas
        modelo.setColumnIdentifiers(new Object[]{
            "Folio", "Clinica", "Fecha", "Apellido Paterno", "Apellido Materno",
            "Nombre", "No. Expediente", "Hora de llegada", "Hora de salida", "Elaborado por"
        });

        // Obtener la ruta del directorio de inicio del usuario
        String homeDir = System.getProperty("user.home");

        // Construir la ruta completa al archivo constancias.txt
        String rutaCompleta = homeDir + "/source/bd/constancias.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaCompleta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 11) {
                    modelo.addRow(new Object[]{
                        partes[1], partes[2], partes[3], partes[4], partes[5],
                        partes[6], partes[7], partes[8], partes[9], partes[10]
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Hacer que las celdas no sean editables
        for (int i = 0; i < tableDatos.getColumnCount(); i++) {
            Class<?> columnClass = tableDatos.getColumnClass(i);
            tableDatos.setDefaultEditor(columnClass, null);
        }

        // Ajustar el alto de las celdas
        tableDatos.setRowHeight(25); // Ajusta el valor según tu preferencia
    }

    private List<String[]> getDatosSeleccionados(int[] filasSeleccionadas) {
        DefaultTableModel modelo = (DefaultTableModel) tableDatos.getModel();
        List<String[]> datosSeleccionados = new ArrayList<>();

        for (int fila : filasSeleccionadas) {
            String[] datos = new String[modelo.getColumnCount()];
            for (int col = 0; col < modelo.getColumnCount(); col++) {
                datos[col] = modelo.getValueAt(fila, col).toString();
            }
            datosSeleccionados.add(datos);
        }

        return datosSeleccionados;
    }

    private void exportarCSV() {
        // Obtener el nombre de usuario
        String homeDir = System.getProperty("user.name");

        // Obtener la ruta del archivo CSV seleccionando la ubicación con un cuadro de diálogo
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV (*.csv)", ".csv"));

        int seleccion = fileChooser.showSaveDialog(this);

        if (seleccion == javax.swing.JFileChooser.APPROVE_OPTION) {
            File archivoCSV = fileChooser.getSelectedFile();
            String rutaArchivoCSV = archivoCSV.getAbsolutePath();

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaArchivoCSV), StandardCharsets.UTF_8))) {
                writer.write("Cvo,Folio,Clinica,Fecha,ApePat,ApeMat,Nombres,Expediente,HoraLlegada,HoraSalida,ElaboradoPor");
                writer.newLine();

                // Construir la ruta completa al archivo constancias.txt
                String rutaCompleta = "/Users/" + homeDir + "/source/bd/constancias.txt";

                // Leer datos del archivo constancias.txt y escribir en el archivo CSV
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(rutaCompleta), StandardCharsets.UTF_8))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        // Procesar y escribir datos en el archivo CSV
                        // ...

                        // Ejemplo: dividir la línea por comas y escribir en el archivo CSV
                        String[] partes = linea.split(",");
                        for (int i = 0; i < partes.length; i++) {
                            writer.write(partes[i]);
                            if (i < partes.length - 1) {
                                writer.write(",");
                            }
                        }
                        writer.newLine();
                    }
                }

                JOptionPane.showMessageDialog(this, "Datos exportados exitosamente a " + rutaArchivoCSV, "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al exportar datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDatos = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnCargar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnExportar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("MetroDF", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("GERENCIA DE SALUD Y BIENESTAR SOCIAL");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 550, 40));

        tableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableDatos);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 1020, 230));

        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 180, 30));

        btnBuscar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 80, 60));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Buscar");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 80, 20));

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/printer.png"))); // NOI18N
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 440, 90, 80));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Imprimir");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 520, 90, 20));

        jLabel4.setText("Ingrese los datos:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 180, -1));

        btnCargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/system_upgrade.png"))); // NOI18N
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });
        add(btnCargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 90, 90, 80));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Reestablecer");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 170, 90, -1));

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/csv.png"))); // NOI18N
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });
        add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 440, 90, 80));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Exportar Datos");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 520, 90, 20));
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed

    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String textoBuscado = txtBuscar.getText().trim().toLowerCase();

        // Obtener el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tableDatos.getModel();

        // Obtener el sorter actual o crear uno nuevo si es nulo
        TableRowSorter<DefaultTableModel> rowSorter = (TableRowSorter<DefaultTableModel>) tableDatos.getRowSorter();
        if (rowSorter == null) {
            rowSorter = new TableRowSorter<>(modelo);
            tableDatos.setRowSorter(rowSorter);
        }

        // Limpiar el filtro existente para asegurarse de que se muestren todos los datos
        rowSorter.setRowFilter(null);

        if (!textoBuscado.isEmpty()) {
            // Filtrar las filas según el texto buscado en cualquier columna
            RowFilter<DefaultTableModel, Object> filtro = RowFilter.regexFilter("(?i)" + textoBuscado);
            rowSorter.setRowFilter(filtro);
            txtBuscar.setText("");
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        if (txtBuscar.getText().equals(originalText1)) {
            txtBuscar.setText("");
            txtBuscar.setForeground(Color.black);
            txtBuscar.setHorizontalAlignment(txtBuscar.LEFT);
            txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 12));
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if (txtBuscar.getText().isEmpty()) {
            txtBuscar.setText(originalText1);
            txtBuscar.setHorizontalAlignment(txtBuscar.CENTER);
            txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.BOLD, 12));
            txtBuscar.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtBuscarFocusLost

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        // Volver a cargar todos los datos en la tabla
        tableDatos.setRowSorter(null);
        // Limpiar el campo de búsqueda
        txtBuscar.setText("");
        txtBuscarFocusLost(null);
    }//GEN-LAST:event_btnCargarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        int[] filasSeleccionadas = tableDatos.getSelectedRows();

        if (filasSeleccionadas.length > 0) {
            // Pregunta al usuario si desea proceder con la impresión
            int opcion = JOptionPane.showConfirmDialog(this, "¿Proceder con la impresión?", "Confirmar impresión", JOptionPane.YES_NO_CANCEL_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                // Obtener la primera fila seleccionada (asumimos que todas tienen la misma cantidad de columnas)
                int fila = filasSeleccionadas[0];

                // Obtener el modelo de la tabla
                DefaultTableModel modelo = (DefaultTableModel) tableDatos.getModel();

                // Crear una instancia de ImpresionPanel y pasarle los valores de la fila seleccionada
                ImpresionPanel panelImpresion = new ImpresionPanel(
                        modelo.getValueAt(fila, 0).toString(),
                        modelo.getValueAt(fila, 2).toString(),
                        modelo.getValueAt(fila, 3).toString(),
                        modelo.getValueAt(fila, 4).toString(),
                        modelo.getValueAt(fila, 5).toString(),
                        modelo.getValueAt(fila, 6).toString(),
                        modelo.getValueAt(fila, 1).toString(),
                        modelo.getValueAt(fila, 7).toString(),
                        modelo.getValueAt(fila, 8).toString(),
                        modelo.getValueAt(fila, 9).toString()
                );

                // Crear una instancia de Impresion y pasarle el panel
                Impresion impresion = new Impresion(panelImpresion);

                // Crear una instancia de PrinterJob para gestionar la impresión
                PrinterJob job = PrinterJob.getPrinterJob();

                // Configurar el trabajo de impresión
                job.setPrintable(impresion);

                // Mostrar el cuadro de diálogo de impresión
                if (job.printDialog()) {
                    try {
                        // Iniciar el trabajo de impresión
                        job.print();
                    } catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (opcion == JOptionPane.NO_OPTION) {
                // El usuario seleccionó "No", realizar alguna acción apropiada si es necesario
            } else {
                // El usuario seleccionó "Cancelar" o cerró el cuadro de diálogo, realizar alguna acción apropiada si es necesario
            }
        } else {
            // No hay filas seleccionadas, mostrar un mensaje o realizar alguna acción adecuada
            JOptionPane.showMessageDialog(this, "Seleccione al menos una fila para imprimir", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this, "¿Exportar los datos?", "Confirmar exportación", JOptionPane.YES_NO_CANCEL_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            exportarCSV();
        }

    }//GEN-LAST:event_btnExportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableDatos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
