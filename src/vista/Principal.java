package vista;


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Contacto;
import modelo.Tipo;

public class Principal extends javax.swing.JFrame {
    
    
    
    //contado para saber en que posicion del arraylist me encuentro.
    public static int posicionActual = 0;
    //combobox para el enum Tipo
    DefaultComboBoxModel<Tipo> tipos = new DefaultComboBoxModel<>();
    //variable global para indicar en que estado en el que esta la aplicacion en ese momento.
    public static Estados estadoActual = Estados.navegacion;
    Contacto c = new Contacto();
    //para ir comprobando si los campos que estoy metiendo son correctos, si lo son al final añade el contacto.
    public static boolean esContactoValdio = false;

    public Principal() {

        JOptionPane.showMessageDialog(null, "Bienvenido a Albertin 1.8.6", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
        
        initComponents();
        //salga centrado el menu
        this.setLocationRelativeTo(null);
        //modelo del combobox
        cmbTipo.setModel(tipos);

        //añado los tipos al combobox
        tipos.addElement(Tipo.amigo);
        tipos.addElement(Tipo.enemigo);
        tipos.addElement(Tipo.familiar);
        tipos.addElement(Tipo.trabajo);

    }
    //funcion para activar o desactivar controles segun el estado en el que se encuenta.
    public void editable(Estados estado) {

        switch (estado) {
            case navegacion:
                txtNombre.setEditable(false);
                txtApellido1.setEditable(false);
                txtApellido2.setEditable(false);
                txtDni.setEditable(false);
                txtFechaNacimiento.setEditable(false);
                txtTelefono.setEditable(false);
                txtTipo.setEditable(false);
                cmbTipo.setEnabled(false);

                break;
            case creacion:

                txtNombre.setEditable(true);
                txtApellido1.setEditable(true);
                txtApellido2.setEditable(true);
                txtDni.setEditable(true);
                txtFechaNacimiento.setEditable(true);
                txtTelefono.setEditable(true);
                txtTipo.setEditable(false);
                cmbTipo.setEnabled(true);

                break;
            case borrado:

                txtNombre.setEditable(false);
                txtApellido1.setEditable(false);
                txtApellido2.setEditable(false);
                txtDni.setEditable(false);
                txtFechaNacimiento.setEditable(false);
                txtTelefono.setEditable(false);
                txtTipo.setEditable(false);
                cmbTipo.setEnabled(false);

                break;
            case modificacion:

                txtNombre.setEditable(true);
                txtApellido1.setEditable(true);
                txtApellido2.setEditable(true);
                txtDni.setEditable(false);
                txtFechaNacimiento.setEditable(true);
                txtTelefono.setEditable(true);
                cmbTipo.setEnabled(true);
                txtTipo.setEditable(false);

                break;

            default:
                break;
        }

    }
    
    //funcion para cambiar el estado en el que se encuentra la apliacion y activa o desactiva los controles al llamar a editable.
    public void estado(Estados estado) {

        switch (estado) {
            case navegacion:
                btnAnadir.setEnabled(true);
                btnEditar.setEnabled(true);
                btnBorrar.setEnabled(true);
                btnContactoAnterior.setEnabled(true);
                btnContactoSiguiente.setEnabled(true);
                btnPrimerContacto.setEnabled(true);
                btnUltimoContacto.setEnabled(true);
                btnBuscarDNI.setEnabled(true);
                txtBuscaDNI.setEnabled(true);
                estadoActual = Estados.navegacion;
                editable(estado.navegacion);

                break;
            case creacion:
                btnAnadir.setEnabled(false);
                btnEditar.setEnabled(false);
                btnBorrar.setEnabled(false);
                btnContactoAnterior.setEnabled(false);
                btnContactoSiguiente.setEnabled(false);
                btnPrimerContacto.setEnabled(false);
                btnUltimoContacto.setEnabled(false);
                txtBuscaDNI.setEnabled(false);
                btnBuscarDNI.setEnabled(false);
                estadoActual = Estados.creacion;
                editable(estado.creacion);

                break;
            case borrado:
                btnAnadir.setEnabled(false);
                btnEditar.setEnabled(false);
                btnBorrar.setEnabled(false);
                btnContactoAnterior.setEnabled(false);
                btnContactoSiguiente.setEnabled(false);
                btnPrimerContacto.setEnabled(false);
                btnUltimoContacto.setEnabled(false);
                txtBuscaDNI.setEnabled(false);
                btnBuscarDNI.setEnabled(false);
                estadoActual = Estados.borrado;
                editable(estado.borrado);

                break;
            case modificacion:
                btnAnadir.setEnabled(false);
                btnEditar.setEnabled(false);
                btnBorrar.setEnabled(false);
                btnContactoAnterior.setEnabled(false);
                btnContactoSiguiente.setEnabled(false);
                btnPrimerContacto.setEnabled(false);
                txtBuscaDNI.setEnabled(false);
                btnUltimoContacto.setEnabled(false);
                estadoActual = Estados.modificacion;
                btnBuscarDNI.setEnabled(false);
                editable(estado.modificacion);

                break;

            default:
                break;
        }

    }
    //para ir actualizando los campos, despues de borrar, añadir, modificar, cargar agenda.
    
    private void actualizaCampos(int posicion) {

        if (!agenda.isEmpty()) {
    //agenda.get(posicion) != null &&
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            txtDni.setText(agenda.get(posicion).getDni());
            txtNombre.setText(agenda.get(posicion).getNombre());
            txtApellido1.setText(agenda.get(posicion).getApellido1());
            txtApellido2.setText(agenda.get(posicion).getApellido2());
            txtTelefono.setText(agenda.get(posicion).getTelefono());
            txtDni.setText(agenda.get(posicion).getDni());
            txtFechaNacimiento.setText(sdf.format(agenda.get(posicion).getFechaNacimiento()));
            txtTipo.setText(agenda.get(posicion).getTipo().toString()); 
            lblFoto.setIcon(agenda.get(posicion).getFoto());
            

        } else {
            //solcucion al problema comentado en la memoria
            limpiaCampos();

            estado(Estados.navegacion);

        }

    }

    //comprueba si el dni es correcto tanto en longitud como la letra.
    private boolean compruebaDni(String dni) {
        boolean correcto = false;
        int numero = 0;
        int letra = 0;

        if (dni.length() == 9) {

            try {

                numero = Integer.parseInt(dni.substring(0, 8));

               // System.out.println(numero);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Dni con formato no valido");
            }
            letra = numero % 23;

            switch (letra) {
                case 0:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("T")) {
                        correcto = true;
                    }
                    break;
                case 1:

                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("R")) {
                        correcto = true;
                    }
                    break;
                case 2:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("W")) {
                        correcto = true;
                    }
                    break;
                case 3:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("A")) {
                        correcto = true;
                    }
                    break;
                case 4:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("G")) {
                        correcto = true;
                    }
                    break;
                case 5:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("M")) {
                        correcto = true;
                    }
                    break;
                case 6:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("Y")) {
                        correcto = true;
                    }
                    break;
                case 7:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("F")) {
                        correcto = true;
                    }
                    break;
                case 8:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("P")) {
                        correcto = true;
                    }
                    break;
                case 9:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("D")) {
                        correcto = true;
                    }
                    break;
                case 10:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("X")) {
                        correcto = true;
                    }
                    break;
                case 11:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("B")) {
                        correcto = true;
                    }
                    break;
                case 12:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("N")) {
                        correcto = true;
                    }
                    break;
                case 13:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("J")) {
                        correcto = true;
                    }
                    break;
                case 14:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("Z")) {
                        correcto = true;
                    }
                    break;
                case 15:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("S")) {
                        correcto = true;
                    }
                    break;
                case 16:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("Q")) {
                        correcto = true;
                    }
                    break;
                case 17:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("V")) {
                        correcto = true;
                    }
                    break;
                case 18:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("H")) {
                        correcto = true;
                    }
                    break;
                case 19:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("L")) {
                        correcto = true;
                    }
                    break;
                case 20:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("C")) {
                        correcto = true;
                    }
                    break;
                case 21:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("K")) {
                        correcto = true;
                    }
                    break;
                case 22:
                    if (dni.substring(8).toUpperCase().equalsIgnoreCase("E")) {
                        correcto = true;
                    }
                    break;

                default:
                    correcto = false;

            }

        }

        return correcto;
    }

    //comprueba que la longitud del telefono sea correcta
    private boolean compruebaTelefono(String telefono) {// Revisar no funciona

        boolean correcto;

        correcto = false;
        for (int i = 0; i < telefono.length(); i++) {

            if (Character.isDigit(telefono.charAt(i)) && telefono.length() == 9) {
                correcto = true;

            }

        }

        return correcto;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fcBucar = new javax.swing.JFileChooser();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblDni = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblApellido1 = new javax.swing.JLabel();
        lblApellido2 = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblFechaNacimiento = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido1 = new javax.swing.JTextField();
        txtApellido2 = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtFechaNacimiento = new javax.swing.JTextField();
        txtTipo = new javax.swing.JTextField();
        cmbTipo = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        btnEditar = new javax.swing.JButton();
        btnAnadir = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtBuscaDNI = new javax.swing.JTextField();
        btnBuscarDNI = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnCargaAgenda = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnGuardaAgenda = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnPrimerContacto = new javax.swing.JButton();
        btnContactoAnterior = new javax.swing.JButton();
        btnContactoSiguiente = new javax.swing.JButton();
        btnUltimoContacto = new javax.swing.JButton();
        lblFoto = new javax.swing.JLabel();
        btnCargarFoto = new javax.swing.JButton();

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        lblDni.setText("DNI");

        lblNombre.setText("Nombre");

        lblApellido1.setText("Apellido 1");

        lblApellido2.setText("Apellido 2");

        lblTelefono.setText("Telefono");

        lblFechaNacimiento.setText("Fecha Nacimiento dd/mm/yyyy ");

        lblTipo.setText("Tipo");

        cmbTipo.setModel(cmbTipo.getModel());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Funciones"));

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnAnadir.setText("Añadir");
        btnAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirActionPerformed(evt);
            }
        });

        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAnadir, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnAnadir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Busca por DNI"));

        txtBuscaDNI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscaDNIMouseClicked(evt);
            }
        });

        btnBuscarDNI.setText("Buscar");
        btnBuscarDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarDNIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscaDNI)
                    .addComponent(btnBuscarDNI, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(txtBuscaDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Desde Fichero"));

        btnCargaAgenda.setText("Cargar");
        btnCargaAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargaAgendaActionPerformed(evt);
            }
        });

        jLabel1.setText("Cargar contactos");

        jLabel2.setText("Guardar Contactos");

        btnGuardaAgenda.setText("Guardar");
        btnGuardaAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaAgendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(54, 54, 54)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCargaAgenda)
                    .addComponent(btnGuardaAgenda))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCargaAgenda, btnGuardaAgenda, jLabel1, jLabel2});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCargaAgenda)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardaAgenda)
                    .addComponent(jLabel2))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCargaAgenda, btnGuardaAgenda, jLabel1, jLabel2});

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Aceptar/Cancelar"));

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(btnAceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Navegacion"));

        btnPrimerContacto.setText("<<");
        btnPrimerContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimerContactoActionPerformed(evt);
            }
        });

        btnContactoAnterior.setText("<");
        btnContactoAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContactoAnteriorActionPerformed(evt);
            }
        });

        btnContactoSiguiente.setText(">");
        btnContactoSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContactoSiguienteActionPerformed(evt);
            }
        });

        btnUltimoContacto.setText(">>");
        btnUltimoContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoContactoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnPrimerContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnContactoAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnContactoSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(btnUltimoContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrimerContacto)
                    .addComponent(btnContactoAnterior)
                    .addComponent(btnContactoSiguiente)
                    .addComponent(btnUltimoContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblFoto.setBackground(new java.awt.Color(65, 65, 65));
        lblFoto.setText("jLabel3");

        btnCargarFoto.setText("Cargar Foto");
        btnCargarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarFotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnCargarFoto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDni)
                            .addComponent(lblNombre))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblApellido1)
                        .addGap(40, 40, 40)
                        .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTipo)
                            .addComponent(lblFechaNacimiento)
                            .addComponent(lblTelefono)
                            .addComponent(lblApellido2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblApellido1, lblApellido2, lblDni, lblFechaNacimiento, lblNombre, lblTelefono, lblTipo, txtApellido1, txtApellido2, txtDni, txtFechaNacimiento, txtNombre, txtTelefono, txtTipo});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblDni)
                                            .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNombre)
                                            .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblApellido1)
                                            .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(lblApellido2)
                                                    .addComponent(txtApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblTelefono))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblFechaNacimiento)
                                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTipo)
                                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCargarFoto)
                                .addGap(41, 41, 41)))
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblApellido1, lblApellido2, lblDni, lblFechaNacimiento, lblTelefono, lblTipo, txtApellido1, txtApellido2, txtDni, txtFechaNacimiento, txtNombre, txtTelefono, txtTipo});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //actualizo los campos al iniciar y lo pongo en modo navegacion.
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        actualizaCampos(0);
        estado(Estados.navegacion);


    }//GEN-LAST:event_formWindowActivated
    
    //nos posicion en el prime contacto.
    private void btnPrimerContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimerContactoActionPerformed
        if (posicionActual > 0) {
            posicionActual = 0;
            actualizaCampos(posicionActual);
            //System.out.println("Posicion actual es " + posicionActual);
        }
    }//GEN-LAST:event_btnPrimerContactoActionPerformed
    
    //nos mueve a un contacto menos del arrayList
    private void btnContactoAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContactoAnteriorActionPerformed

        if (posicionActual > 0) {
            posicionActual--;
            actualizaCampos(posicionActual);
            //System.out.println("Posicion actual es " + posicionActual);
        }
    }//GEN-LAST:event_btnContactoAnteriorActionPerformed
    
    //nos posicion en el ultimo contacto.
    private void btnUltimoContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoContactoActionPerformed

        posicionActual = agenda.size() - 1;
        actualizaCampos(posicionActual);
        //System.out.println("Posicion actual es " + posicionActual);
    }//GEN-LAST:event_btnUltimoContactoActionPerformed
    
    //nos avanza un contacto
    private void btnContactoSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContactoSiguienteActionPerformed

        if (posicionActual < agenda.size() - 1) {
            posicionActual++;
            actualizaCampos(posicionActual);
            //System.out.println("Posicion actual es " + posicionActual);

        }

    }//GEN-LAST:event_btnContactoSiguienteActionPerformed

    
    //para poner los campos limpios, solucion al error comentado en la memoria
    private void limpiaCampos() {
        txtNombre.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        txtDni.setText("");
        txtFechaNacimiento.setText("");
        txtTelefono.setText("");
        txtTipo.setText("");
        lblFoto.setText("No Imagen");
    }

    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed

        estado(Estados.creacion);

        limpiaCampos();


    }//GEN-LAST:event_btnAnadirActionPerformed
    
    //dependiendo en el estado en el que nos encontremos hace una cosa o otra
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        switch (estadoActual) {
            case navegacion:
                estado(Estados.navegacion);
                break;
            case creacion:
                creaContacto();
                //si las comprobaciones han salido todas correctas guarda el contacto.
                if (esContactoValdio == true) {
                    agenda.add(c);
                }
                estado(Estados.navegacion);
                break;
            case borrado:

                borraContacto();
                //parte de la solcuion al problema comentado en la memoria
                if (!agenda.isEmpty()) {
                    estado(Estados.navegacion);

                    // actualizaCampos(0);
                } else {
                    estado(Estados.creacion);
                }

                break;
            case modificacion:
                modificaContacto();
                ///si las comprobaciones han salido todas correctas guarda el contacto.
                if (esContactoValdio == true) {
                    //me posiciono en el contacto que tengo en pantalla para poder modificarlos, sino me añadia unno nuevo
                    agenda.set(posicionActual, c);
                }
                estado(Estados.navegacion);
                break;
            default:
                break;
        }

        actualizaCampos(0);

    }//GEN-LAST:event_btnAceptarActionPerformed
    
    //cambia estado a borrar
    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        estado(Estados.borrado);

    }//GEN-LAST:event_btnBorrarActionPerformed
   
    
    //cancela la accion(vuelve a navegacion) y actualiza los campos de la posicion que me encuntro
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        estado(Estados.navegacion);
        actualizaCampos(posicionActual);
    }//GEN-LAST:event_btnCancelarActionPerformed

    //cambio el estado y llamo a la funcion modificar
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        estado(Estados.modificacion);
        modificaContacto();
    }//GEN-LAST:event_btnEditarActionPerformed
    
    //funcion para buscar por DNI
    private void btnBuscarDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarDNIActionPerformed

        


    }//GEN-LAST:event_btnBuscarDNIActionPerformed
    
    //para limpiar el campo al hacer click
    private void txtBuscaDNIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscaDNIMouseClicked
        txtBuscaDNI.setText("");
    }//GEN-LAST:event_txtBuscaDNIMouseClicked

    
    //borra la agenda antes de cargar el fichero desde  un filechooser, lo lee como un objeto y lo añade al arrayList
    private void btnCargaAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargaAgendaActionPerformed
        agenda.clear();
        //System.out.println("Borrado de la agenda");

        //filechooser para seleccionar 
        File fichero = new File("datos");
        fcBucar.showOpenDialog(null);
        fichero = fcBucar.getSelectedFile();

        try {
            // String fichero = "datos";

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));
            Object obj;
            while ((obj = ois.readObject()) instanceof Contacto) {
                agenda.add((Contacto) obj);
            }

        } catch (EOFException e) {
            //uso la excepcion para que cuando acabe de leer me saque un mensaje de que se ha cargado correctamente.
            JOptionPane.showMessageDialog(null, "Agenda Cargada con exito", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            posicionActual = 0;
                     
        } catch (Exception e) {
            //uso la excepcion para mostrar un mensaje si da error al cargar el fichero.
            JOptionPane.showMessageDialog(null, "Error al cargar la agenda desde el fichero", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnCargaAgendaActionPerformed

    //con un filechooser elegimos donde vamos a guardar el fichero. los guardamos como un objeto, vamos leyendo todo el arrayList y escribineendolos en el fichero.
    private void btnGuardaAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaAgendaActionPerformed

        File fichero = new File("datos");
        fcBucar.showSaveDialog(fcBucar);
        fichero = fcBucar.getSelectedFile();

        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(fichero));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        //leemos todo el arrayList
        for (Contacto b : agenda) {

            try {
                //vamos escribiendo los objetos.
                oos.writeObject(new Contacto(b.getNombre(), b.getApellido1(), b.getApellido2(), b.getDni(), b.getTelefono(), b.getFechaNacimiento(), b.getTipo(),b.getFoto()));
            } catch (IOException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Agenda Guardada con exito", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnGuardaAgendaActionPerformed

    private void btnCargarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarFotoActionPerformed
        
        File fichero = new File("datos");
        fcBucar.showOpenDialog(null);
        fichero = fcBucar.getSelectedFile();
        ImageIcon foto = new ImageIcon(fichero.toString());
        
      
        
        
        
        lblFoto.setIcon(foto);
        
    }//GEN-LAST:event_btnCargarFotoActionPerformed

    //funcion que borra el contacto que esta en la posicion actual
    private void borraContacto() {
       // System.out.println("La posicion actual es " + posicionActual);

        if (!agenda.isEmpty()) {
            agenda.remove(posicionActual);
        } else {
            JOptionPane.showMessageDialog(null, "No quedan mas contactos", "Aviso", JOptionPane.OK_OPTION);
        }
    }
    
    //funcion que crea el contacto
    private void creaContacto() {

        //Contacto c = new Contacto();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //comprueba que la fecha sea correcta
        sdf.setLenient(false);

        String fecha = txtFechaNacimiento.getText();

        String dni = txtDni.getText();
        //comprueba DNI
        if (compruebaDni(dni) == true) {
            c.setDni(dni);
            //para guardarlos al aceptar
            esContactoValdio = true;
        } else {
            JOptionPane.showMessageDialog(null, "DNI no valido ", "Error", JOptionPane.ERROR_MESSAGE);
            esContactoValdio = false;
            estado(Estados.navegacion);
        }

        c.setNombre(txtNombre.getText());
        c.setApellido1(txtApellido1.getText());
        c.setApellido2(txtApellido2.getText());

        String telefono = txtTelefono.getText();
        //compruebo telefono al igual que el DNI
        if (compruebaTelefono(telefono) == true) {
            c.setTelefono(telefono);
            esContactoValdio = true;

        } else {

            JOptionPane.showMessageDialog(null, "Telefono no valido ", "Error", JOptionPane.ERROR_MESSAGE);
            esContactoValdio = false;
            estado(Estados.navegacion);

        }

        c.setTipo((Tipo) cmbTipo.getSelectedItem());

        Date date = null;
        try {
            date = sdf.parse(fecha);
            //comprueba la fecha al parsearla
        } catch (ParseException ex) {
            //si no es correcta mensaje y a false para que no guarde el contacto
            JOptionPane.showMessageDialog(null, "Fecha no valido ", "Error", JOptionPane.ERROR_MESSAGE);
            esContactoValdio = false;
        }
        c.setFechaNacimiento(date);
        esContactoValdio = true;

        c.setFoto((ImageIcon) lblFoto.getIcon());
        
        //System.out.println("El tamaño de la agenda es " + agenda.size());

    }
    
    //modifica el contacto que esta en ese momento en pantalla , las comprobaciones igual que al crearcOntacto
    private void modificaContacto() {

        //cambia el estado
        estado(Estados.modificacion);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        String fecha = txtFechaNacimiento.getText();

        String dni = txtDni.getText();
        if (compruebaDni(dni) == true) {
            c.setDni(dni);
            esContactoValdio = true;
        } else {
            JOptionPane.showMessageDialog(null, "DNI no valido ", "Error", JOptionPane.ERROR_MESSAGE);
            esContactoValdio = false;
            estado(Estados.navegacion);
        }

        c.setNombre(txtNombre.getText());
        c.setApellido1(txtApellido1.getText());
        c.setApellido2(txtApellido2.getText());

        String telefono = txtTelefono.getText();

        if (compruebaTelefono(telefono) == true) {
            c.setTelefono(telefono);
            esContactoValdio = true;

        } else {

            JOptionPane.showMessageDialog(null, "Telefono no valido ", "Error", JOptionPane.ERROR_MESSAGE);
            esContactoValdio = false;
            estado(Estados.navegacion);

        }

        c.setTipo((Tipo) cmbTipo.getSelectedItem());

        Date date = null;
        try {
            date = sdf.parse(fecha);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Fecha no valido ", "Error", JOptionPane.ERROR_MESSAGE);
            esContactoValdio = false;
        }
        c.setFechaNacimiento(date);
        esContactoValdio = true;
        
        
        c.setFoto((ImageIcon) lblFoto.getIcon());

    }

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
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAnadir;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBuscarDNI;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargaAgenda;
    private javax.swing.JButton btnCargarFoto;
    private javax.swing.JButton btnContactoAnterior;
    private javax.swing.JButton btnContactoSiguiente;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardaAgenda;
    private javax.swing.JButton btnPrimerContacto;
    private javax.swing.JButton btnUltimoContacto;
    private javax.swing.JComboBox<Tipo> cmbTipo;
    private javax.swing.JFileChooser fcBucar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblApellido1;
    private javax.swing.JLabel lblApellido2;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JTextField txtApellido1;
    private javax.swing.JTextField txtApellido2;
    private javax.swing.JTextField txtBuscaDNI;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtFechaNacimiento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables

}
