package Paquetes;

import java.awt.Color;
import java.awt.Font;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SpinnerListModel;
import java.text.ParseException;

/**
 *
 * @author Kelevra
 */
public class Captura extends javax.swing.JPanel {

    private String claveClinicaSeleccionada;

    /**
     *
     */
    public Captura() {
        initComponents();
        cargarDatosAlComboBox();
        inicializarCamposConTextoPorDefecto();

        // Configurar el modelo para las horas y minutos
        String[] horasYMinutos = generarHorasYMinutos();
        SpinnerListModel modelInicio = new SpinnerListModel(horasYMinutos);
        jSpinHoraInicio.setModel(modelInicio);
        jSpinHoraInicio.setValue(horasYMinutos[0]);

        SpinnerListModel modelFin = new SpinnerListModel(horasYMinutos);
        jSpinHoraFin.setModel(modelFin);
        jSpinHoraFin.setValue(horasYMinutos[0]);

        jCheckSeguro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckSeguroItemStateChanged(evt);
            }
        });

        jbGuardar.setEnabled(false);
        btnImprimir.setEnabled(false);
        mostrarFechaActual();
    }

    private String[] generarHorasYMinutos() {
        String[] horasYMinutos = new String[24 * 60];
        int index = 0;
        for (int hora = 0; hora < 24; hora++) {
            for (int minuto = 0; minuto < 60; minuto++) {
                String horaYMinuto = String.format("%02d:%02d", hora, minuto);
                horasYMinutos[index++] = horaYMinuto;
            }
        }
        return horasYMinutos;
    }

    private String originalText1 = "Apellido Paterno";
    private String originalText2 = "Apellido Materno";
    private String originalText3 = "Nombre(s)";
    private String originalText4 = "Num. Expediente";
    private String originalText5 = "Nombre y Apellido de quien Elaboro";

    private void cargarDatosAlComboBox() {
        jComboClinica.addItem("Seleccione"); // Añade "Seleccione" como elemento predeterminado
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("bd/clinicas.txt");
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        String[] partes = linea.split(",");
                        if (partes.length > 1) {
                            jComboClinica.addItem(partes[1].trim());
                        }
                    }
                }
            } else {
                System.err.println("No se pudo encontrar el archivo bd/clinicas.txt");
            }
        } catch (IOException e) {
            // Manejo de errores si ocurre un problema al leer el archivo
            e.printStackTrace();
        }

        jComboClinica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboClinicaActionPerformed(evt);
            }
        });
    }

    private void inicializarCamposConTextoPorDefecto() {
        txtApePatFocusLost(null);
        txtApeMatFocusLost(null);
        txtNombreFocusLost(null);
        txtExpedienteFocusLost(null);
        txtElaboroFocusLost(null);
    }

    private void generarFolio() {
        // Obtén el año actual y el número consecutivo
        String anioActual = new SimpleDateFormat("yy").format(Calendar.getInstance().getTime());
        String numeroConsecutivo = obtenerNumeroConsecutivo();

        // Formatea el folio y muestra en la etiqueta lbFolio
        String folio = claveClinicaSeleccionada + "/" + anioActual + "/" + numeroConsecutivo;
        lbFolio.setText(folio);
    }

    private String obtenerNumeroConsecutivo() {
        
        String homeDir = System.getProperty("user.name");
        String rutaCompleta = "/Users/" + homeDir + "/source/bd/cvo.txt";
        
        try {
            // Lee el número consecutivo del archivo cvo.txt
            BufferedReader br = new BufferedReader(new FileReader(rutaCompleta));
            String linea;
            if ((linea = br.readLine()) != null) {
                // Comprueba si el número es "0000"
                if (linea.trim().equals("0000")) {
                    // Si es "0000", convierte a int y suma 1, luego formatea con ceros a la izquierda
                    int nuevoNumero = Integer.parseInt(linea.trim()) + 1;
                    return String.format("%04d", nuevoNumero);
                } else {
                    // Si es diferente de "0000", convierte a int y suma 1, luego formatea con ceros a la izquierda
                    int nuevoNumero = Integer.parseInt(linea.trim()) + 1;
                    return String.format("%04d", nuevoNumero);
                }
            }
        } catch (IOException e) {
            // Si ocurre un error al leer el archivo, se crea el archivo con un valor inicial
            System.err.println("Error al leer cvo.txt. Se creará el archivo con un valor inicial.");
            inicializarCvo();
        }

        // Agrega un return con un valor predeterminado en caso de excepción
        return "0000";
    }

    private void inicializarCvo() {
        String homeDir = System.getProperty("user.name");
        String rutaCompleta = "/Users/" + homeDir + "/source/bd/cvo.txt";
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaCompleta));
            // Escribe el valor inicial "0000" en el archivo
            writer.write("0000");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void actualizarNumeroConsecutivo(String nuevoNumero) {
        String homeDir = System.getProperty("user.name");
        String rutaCompleta = "/Users/" + homeDir + "/source/bd/cvo.txt";
        try {
            // Escribe el nuevo número consecutivo en el archivo cvo.txt
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaCompleta));
            writer.write(nuevoNumero);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarNuevoConsecutivo(String nuevoConsecutivo) {
        String homeDir = System.getProperty("user.name");
        String rutaCompleta = "/Users/" + homeDir + "/source/bd/cvo.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaCompleta))) {
            // Escribe el nuevo número consecutivo en el archivo
            writer.write(nuevoConsecutivo);
        } catch (IOException e) {
            // Si ocurre un error al escribir en el archivo, muestra un mensaje de error
            e.printStackTrace();
            System.err.println("Error al guardar el nuevo consecutivo en cvo.txt");
        }
    }

    private String obtenerClaveClinica(String nombreClinica) {        
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("bd/clinicas.txt");
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        String[] partes = linea.split(",");
                        if (partes.length > 1 && partes[1].trim().equals(nombreClinica.trim())) {
                            return partes[0].trim();  // Devuelve la clave encontrada
                        }
                    }
                }
            } else {
                System.err.println("No se pudo encontrar el archivo bd/clinicas.txt");
            }
        } catch (IOException e) {
            // Manejo de errores si ocurre un problema al leer el archivo
            e.printStackTrace();
        }
        return "";
    }

    private boolean guardarInformacion() {
        String homeDir = System.getProperty("user.name");
        String rutaCompleta = "/Users/" + homeDir + "/source/bd/constancias.txt";
        String cvo = obtenerNumeroConsecutivo();
        String folio = lbFolio.getText();
        String clinica = jComboClinica.getSelectedItem().toString();

        // Obtener la fecha actual del labelFecha
        String fechaStr = labelFecha.getText();
        Date fechaSeleccionada;
        try {
            fechaSeleccionada = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
        } catch (ParseException e) {
            // Manejar la excepción si la fecha en el label no es válida
            JOptionPane.showMessageDialog(this, "Error al obtener la fecha.", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Indicar que hubo un error al guardar
        }

        // Formatear la fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = dateFormat.format(fechaSeleccionada);

        String apellidoPaterno = txtApePat.getText();
        String apellidoMaterno = txtApeMat.getText();
        String nombre = txtNombre.getText();
        String numExpediente = txtExpediente.getText();
        String horaInicio = jSpinHoraInicio.getValue().toString();
        String horaFin = jSpinHoraFin.getValue().toString();
        String elaboro = txtElaboro.getText();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaCompleta, true))) {
            // Agregar una nueva línea al archivo
            writer.write(cvo + "," + folio + "," + clinica + "," + fecha + "," + apellidoPaterno + ","
                    + apellidoMaterno + "," + nombre + "," + numExpediente + "," + horaInicio + "," + horaFin + "," + elaboro);
            writer.newLine();

            // Actualizar el número consecutivo en el archivo cvo.txt
            guardarNuevoConsecutivo(cvo);

            // Indicar que el guardado fue exitoso
            return true;
        } catch (IOException e) {
            // Mostrar mensaje de error
            e.printStackTrace();
            return false; // Indicar que hubo un error al guardar
        }
    }

    private void mostrarFechaActual() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActual = formatoFecha.format(new Date());
        labelFecha.setText(fechaActual);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbFolio = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboClinica = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtApePat = new javax.swing.JTextField();
        txtApeMat = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtExpediente = new javax.swing.JTextField();
        jSpinHoraFin = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jSpinHoraInicio = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtElaboro = new javax.swing.JTextField();
        jbGuardar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnGenerarFolio = new javax.swing.JButton();
        jCheckSeguro = new javax.swing.JCheckBox();
        labelFecha = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1070, 700));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbFolio.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        add(lbFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 160, 23));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Clinica:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 60, 23));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Fecha:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 60, 23));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Tiempo en la clinica");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 190, 23));

        jComboClinica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboClinicaActionPerformed(evt);
            }
        });
        add(jComboClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 180, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Folio:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 60, 23));

        txtApePat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtApePatFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtApePatFocusLost(evt);
            }
        });
        txtApePat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApePatActionPerformed(evt);
            }
        });
        add(txtApePat, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 230, 30));

        txtApeMat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtApeMatFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtApeMatFocusLost(evt);
            }
        });
        txtApeMat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApeMatActionPerformed(evt);
            }
        });
        add(txtApeMat, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 220, 230, 30));

        txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNombreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreFocusLost(evt);
            }
        });
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 220, 230, 30));

        txtExpediente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtExpedienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtExpedienteFocusLost(evt);
            }
        });
        txtExpediente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExpedienteActionPerformed(evt);
            }
        });
        add(txtExpediente, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 220, 230, 30));

        jSpinHoraFin.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        add(jSpinHoraFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 310, 80, -1));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setText("Hora de llegada:");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, 120, 50));

        jSpinHoraInicio.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        add(jSpinHoraInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 310, 80, -1));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setText("Hora de salida:");
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 299, 110, 50));

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setText("Datos del interesado:");
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 160, 23));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setText("Datos de quien elaboro:");
        add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, 180, 30));

        txtElaboro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtElaboroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtElaboroFocusLost(evt);
            }
        });
        add(txtElaboro, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 340, 30));

        jbGuardar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        jbGuardar.setText("   Guardar");
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });
        add(jbGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 450, 160, 50));

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/printer.png"))); // NOI18N
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 80, 80, 70));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Imprimir");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 150, 80, -1));

        btnGenerarFolio.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGenerarFolio.setText("Generar Folio");
        btnGenerarFolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarFolioActionPerformed(evt);
            }
        });
        add(btnGenerarFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, 150, -1));

        jCheckSeguro.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jCheckSeguro.setText("He leído con atención los datos ingresados de la constancia y comprendo que es mi responsabilidad capturar correctamente.");
        jCheckSeguro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckSeguroItemStateChanged(evt);
            }
        });
        jCheckSeguro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckSeguroActionPerformed(evt);
            }
        });
        add(jCheckSeguro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 410, 800, -1));

        labelFecha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        add(labelFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 180, 40));

        jLabel7.setFont(new java.awt.Font("MetroDF", 1, 18)); // NOI18N
        jLabel7.setText("GERENCIA DE SALUD Y BIENESTAR SOCIAL");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 560, 50));
    }// </editor-fold>//GEN-END:initComponents

    private void txtApePatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApePatActionPerformed

    }//GEN-LAST:event_txtApePatActionPerformed

    private void txtApeMatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApeMatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApeMatActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtExpedienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExpedienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExpedienteActionPerformed

    private void txtApePatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApePatFocusGained
        if (txtApePat.getText().equals(originalText1)) {
            txtApePat.setText("");
            txtApePat.setForeground(Color.black);
            txtApePat.setHorizontalAlignment(txtApePat.LEFT);
            txtApePat.setFont(new Font(txtApePat.getFont().getName(), Font.PLAIN, 14));
        }
    }//GEN-LAST:event_txtApePatFocusGained

    private void txtApePatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApePatFocusLost
        if (txtApePat.getText().isEmpty()) {
            txtApePat.setText(originalText1);
            txtApePat.setHorizontalAlignment(txtApePat.CENTER);
            txtApePat.setFont(new Font(txtApePat.getFont().getName(), Font.BOLD, 14));
            txtApePat.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtApePatFocusLost

    private void txtApeMatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApeMatFocusGained
        if (txtApeMat.getText().equals(originalText2)) {
            txtApeMat.setText("");
            txtApeMat.setForeground(Color.black);
            txtApeMat.setHorizontalAlignment(txtApeMat.LEFT);
            txtApeMat.setFont(new Font(txtApeMat.getFont().getName(), Font.PLAIN, 14));
        }
    }//GEN-LAST:event_txtApeMatFocusGained

    private void txtApeMatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApeMatFocusLost
        if (txtApeMat.getText().isEmpty()) {
            txtApeMat.setText(originalText2);
            txtApeMat.setHorizontalAlignment(txtApeMat.CENTER);
            txtApeMat.setFont(new Font(txtApeMat.getFont().getName(), Font.BOLD, 14));
            txtApeMat.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtApeMatFocusLost

    private void txtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreFocusGained
        if (txtNombre.getText().equals(originalText3)) {
            txtNombre.setText("");
            txtNombre.setForeground(Color.black);
            txtNombre.setHorizontalAlignment(txtNombre.LEFT);
            txtNombre.setFont(new Font(txtNombre.getFont().getName(), Font.PLAIN, 14));
        }
    }//GEN-LAST:event_txtNombreFocusGained

    private void txtNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreFocusLost
        if (txtNombre.getText().isEmpty()) {
            txtNombre.setText(originalText3);
            txtNombre.setHorizontalAlignment(txtNombre.CENTER);
            txtNombre.setFont(new Font(txtNombre.getFont().getName(), Font.BOLD, 14));
            txtNombre.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtNombreFocusLost

    private void txtExpedienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtExpedienteFocusGained
        if (txtExpediente.getText().equals(originalText4)) {
            txtExpediente.setText("");
            txtExpediente.setForeground(Color.black);
            txtExpediente.setHorizontalAlignment(txtExpediente.LEFT);
            txtExpediente.setFont(new Font(txtExpediente.getFont().getName(), Font.PLAIN, 14));
        }
    }//GEN-LAST:event_txtExpedienteFocusGained

    private void txtExpedienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtExpedienteFocusLost
        if (txtExpediente.getText().isEmpty()) {
            txtExpediente.setText(originalText4);
            txtExpediente.setHorizontalAlignment(txtExpediente.CENTER);
            txtExpediente.setFont(new Font(txtExpediente.getFont().getName(), Font.BOLD, 14));
            txtExpediente.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtExpedienteFocusLost

    private void txtElaboroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtElaboroFocusGained
        if (txtElaboro.getText().equals(originalText5)) {
            txtElaboro.setText("");
            txtElaboro.setForeground(Color.black);
            txtElaboro.setHorizontalAlignment(txtExpediente.CENTER);
            txtElaboro.setFont(new Font(txtElaboro.getFont().getName(), Font.PLAIN, 14));
        }
    }//GEN-LAST:event_txtElaboroFocusGained

    private void txtElaboroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtElaboroFocusLost
        if (txtElaboro.getText().isEmpty()) {
            txtElaboro.setText(originalText5);
            txtElaboro.setHorizontalAlignment(txtElaboro.CENTER);
            txtElaboro.setFont(new Font(txtElaboro.getFont().getName(), Font.BOLD, 14));
            txtElaboro.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtElaboroFocusLost

    private void btnGenerarFolioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarFolioActionPerformed
        generarFolio();
    }//GEN-LAST:event_btnGenerarFolioActionPerformed

    private void jComboClinicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboClinicaActionPerformed
        String seleccion = jComboClinica.getSelectedItem().toString();
        if (seleccion.equals("Seleccione")) {
            claveClinicaSeleccionada = "";
        } else {
            // Busca la clave correspondiente al nombre seleccionado en el archivo clinicas.txt
            claveClinicaSeleccionada = obtenerClaveClinica(seleccion);
        }
    }//GEN-LAST:event_jComboClinicaActionPerformed

    private void jCheckSeguroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckSeguroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckSeguroActionPerformed

    private void jCheckSeguroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckSeguroItemStateChanged
        jbGuardar.setEnabled(jCheckSeguro.isSelected());
    }//GEN-LAST:event_jCheckSeguroItemStateChanged

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
        boolean guardadoCorrecto = guardarInformacion();

        if (guardadoCorrecto) {
            JOptionPane.showMessageDialog(this, "Información guardada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            btnImprimir.setEnabled(true);

        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar la información", "Error", JOptionPane.ERROR_MESSAGE);
            txtApePat.setText("");
            txtApeMat.setText("");
            txtNombre.setText("");
            txtExpediente.setText("");
            txtElaboro.setText("");
            lbFolio.setText("");
            jComboClinica.setSelectedIndex(0);
            inicializarCamposConTextoPorDefecto();
            jSpinHoraInicio.setValue("00:00");
            jSpinHoraFin.setValue("00:00");
            jCheckSeguro.setSelected(false);
            System.exit(0);
        }

    }//GEN-LAST:event_jbGuardarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // Obtener datos necesarios para el panel de impresión
        String numeroFolio = lbFolio.getText();
        String fecha = labelFecha.getText();
        String apellidoPaterno = txtApePat.getText();
        String apellidoMaterno = txtApeMat.getText();
        String nombre = txtNombre.getText();
        String expediente = txtExpediente.getText();
        String clinica = jComboClinica.getSelectedItem().toString();
        String horaInicio = jSpinHoraInicio.getValue().toString();
        String horaFin = jSpinHoraFin.getValue().toString();
        String elaboro = txtElaboro.getText();

        // Crear el panel de impresión con los datos
        ImpresionPanel impresionPanel = new ImpresionPanel(numeroFolio, fecha, apellidoPaterno, apellidoMaterno,
                nombre, expediente, clinica, horaInicio, horaFin, elaboro);

        // Crear la instancia de la clase Impresion y pasarle el panel
        Impresion impresion = new Impresion(impresionPanel);

        PrinterJob job = PrinterJob.getPrinterJob();

        if (job.printDialog()) {
            job.setPrintable(impresion);

            try {
                job.print();
                // Agrega un JOptionPane para indicar que la impresión fue exitosa
                JOptionPane.showMessageDialog(this, "Impresión Realizada con exito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtApePat.setText("");
                txtApeMat.setText("");
                txtNombre.setText("");
                txtExpediente.setText("");
                txtElaboro.setText("");
                lbFolio.setText("");
                jComboClinica.setSelectedIndex(0);
                inicializarCamposConTextoPorDefecto();
                jSpinHoraInicio.setValue("00:00");
                jSpinHoraFin.setValue("00:00");
                jCheckSeguro.setSelected(false);

            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se puede imprimir", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerarFolio;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JCheckBox jCheckSeguro;
    private javax.swing.JComboBox<String> jComboClinica;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSpinner jSpinHoraFin;
    private javax.swing.JSpinner jSpinHoraInicio;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel lbFolio;
    private javax.swing.JTextField txtApeMat;
    private javax.swing.JTextField txtApePat;
    private javax.swing.JTextField txtElaboro;
    private javax.swing.JTextField txtExpediente;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
