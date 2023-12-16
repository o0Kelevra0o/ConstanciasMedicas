package Paquetes;

import java.awt.Dimension;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author Kelevra
 */
public class Principal extends javax.swing.JFrame {

    /**
     *
     */
    public static JDesktopPane escritorio;

    /**
     *
     */
    public Principal() {
        escritorio = new JDesktopPane();
        this.setContentPane(escritorio);
        this.setSize(1200, 720);
        this.pack();
        this.setLocation(500, 181);
        this.setTitle("Constancias");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuCaptura = new javax.swing.JMenuItem();
        jMenuConsulta = new javax.swing.JMenuItem();
        jMenuSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1400, 900));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/METRO_CIUDAD_DE_MEXICO.png"))); // NOI18N
        getContentPane().add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 740, 280, 80));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jMenu1.setText("Archivo");

        jMenuCaptura.setText("Capturar Constancia");
        jMenuCaptura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCapturaActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuCaptura);

        jMenuConsulta.setText("Consultar Constancia");
        jMenuConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuConsultaActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuConsulta);

        jMenuSalir.setText("Salir");
        jMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSalirActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuSalir);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuCapturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCapturaActionPerformed
        JInternalFrame vhija = new JInternalFrame("Constancia de Tiempo de Atención Médica", false, true, false, false);
        Captura hijo = new Captura();
        vhija.add(hijo);
        vhija.pack();
        Dimension d = this.getSize();
        int x = (d.width - vhija.getWidth()) / 2;
        int y = (d.height - vhija.getHeight()) / 2;
        vhija.setLocation(x, y);
        vhija.setSize(1129, 568);
        vhija.setVisible(true);
        escritorio.add(vhija);
        vhija.toFront();
        vhija.requestFocus();
    }//GEN-LAST:event_jMenuCapturaActionPerformed

    private void jMenuConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuConsultaActionPerformed
        JInternalFrame vhija = new JInternalFrame("Consultar Constancias de Tiempo de Atención Médica", false, true, false, false);
        Consultas hijo = new Consultas();
        vhija.add(hijo);
        vhija.pack();
        vhija.setLocation(140, 70);
        vhija.setSize(1129, 580);
        vhija.setVisible(true);
        escritorio.add(vhija);
        vhija.toFront();
        vhija.requestFocus();
    }//GEN-LAST:event_jMenuConsultaActionPerformed

    private void jMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuCaptura;
    private javax.swing.JMenuItem jMenuConsulta;
    private javax.swing.JMenuItem jMenuSalir;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}
