package componente;

import Modelos.Hash;
import Modelos.Usuarios;
import Servicios.SqlUsuarios;
import swing.Button;
import swing.PasswordField;
import swing.TextField;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import jc.sgc.Login;
import jc.sgc.Menu;
import net.miginfocom.swing.MigLayout;
import swing.JCheckBoxCustom;

public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

    public Preferences pref = Preferences.userRoot().node("recuerdame");

    public PanelLoginAndRegister() {
        initComponents();
        initRegister();
        initLogin();
        login.setVisible(false);
        register.setVisible(true);
    }

    private void initRegister() {
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]10[]10[]25[]push"));

        ImageIcon icon = new ImageIcon(getClass().getResource("/jc/img/jcLogo.png"));

        JLabel lblJCIcono = new JLabel(icon);
        lblJCIcono.setSize(100,100);
        register.add(lblJCIcono);
        
        JLabel label = new JLabel("REGISTRO DE USUARIO");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(10, 110, 255));
        register.add(label);

        TextField txtUser = new TextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/user.png")));
        txtUser.setHint("Nombre de Usuario (Para iniciar Sesión)");
        register.add(txtUser, "w 60%");

        PasswordField txtPass = new PasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/pass.png")));
        txtPass.setHint("Introduce Contraseña Nueva");
        register.add(txtPass, "w 60%");

        PasswordField txtConfirmPass = new PasswordField();
        txtConfirmPass.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/pass.png")));
        txtConfirmPass.setHint("Confirma Contraseña");
        register.add(txtConfirmPass, "w 60%");

        TextField txtName = new TextField();
        txtName.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/user.png")));
        txtName.setHint("Introduce tu Nombre");
        register.add(txtName, "w 60%");

        Button cmd = new Button();
        cmd.setBackground(new Color(10, 110, 255));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.setText("REGISTAR");
        register.add(cmd, "w 40%, h 40");

        cmd.addActionListener(e -> {
            try {
                registrarUsuarios(txtUser.getText(), txtPass.getPassword(), txtConfirmPass.getPassword(), txtName.getText());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PanelLoginAndRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void initLogin() {
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]10[]25[]push"));
        
        ImageIcon iconLogo = new ImageIcon(getClass().getResource("/jc/img/jcLogo.png"));
        ImageIcon iconLogin = new ImageIcon(getClass().getResource("/jc/img/Login_37128.png")); 

        JLabel lblJCIcono = new JLabel(iconLogo);
        lblJCIcono.setSize(100,100);
        login.add(lblJCIcono);
        
        JLabel label = new JLabel("<html><div style='text-align: center;'>JC Sistema de <br>Gestión de Calidad</div></html>");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(10, 110, 255));
        login.add(label);

        TextField txtUsuario = new TextField();
        txtUsuario.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/user.png")));
        txtUsuario.setHint("Usuario");
        login.add(txtUsuario, "w 60%");

        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/pass.png")));
        txtContrasena.setHint("Password");
        login.add(txtContrasena, "w 60%");

        JCheckBox cmdRemember = new JCheckBoxCustom(new Color(25, 123, 255));
        cmdRemember.setText("Recuerdame");

        cmdRemember.setForeground(new Color(100, 100, 100));
        cmdRemember.setFont(new Font("sansserif", 1, 12));
        cmdRemember.setContentAreaFilled(false);
        cmdRemember.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdRemember);

        Button cmd = new Button();
        cmd.setBackground(new Color(10, 110, 255));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.setIcon(iconLogin);
        cmd.setFont(new Font("sansserif", 1, 13));
        cmd.setText("INICIAR SESION");
        login.add(cmd, "w 40%, h 40");

        String usr = null;
        usr = pref.get("userJC", usr);
        cmdRemember.setSelected(usr != null && !usr.isEmpty());
        txtUsuario.setText(usr);
        String pass = null;
        pass = pref.get("passwordJC", pass);
        txtContrasena.setText(pass);
        
        txtUsuario.addActionListener(e -> {
            iniciarSesion(txtUsuario.getText(), txtContrasena.getPassword());
        });

        txtContrasena.addActionListener(e -> {
            iniciarSesion(txtUsuario.getText(), txtContrasena.getPassword());
        });

        cmd.addActionListener(e -> {
            iniciarSesion(txtUsuario.getText(), txtContrasena.getPassword());
        });

        cmdRemember.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cmdRemember.isSelected()) {
                    recordar(true, txtUsuario.getText(), txtContrasena.getPassword());
                } else {
                    recordar(false, txtUsuario.getText(), txtContrasena.getPassword());
                }
            }
        });
    }

    public void recordar(boolean recordar, String txtUsuario, char[] txtContrasena) {
        if (recordar) {
            String pass = new String(txtContrasena); // Se obtiene la contraseña ingresada por el Usuario
            guardarPreferencias(txtUsuario, pass);
        } else {
            eliminarPreferencias();
        }
    }

    public void guardarPreferencias(String user, String password) {
        if (user == null || password == null) {
            System.out.println("Completa los campos de usuario y contraseña");
        } else {
            String userName = user;
            pref.put("userJC", userName);
            String pass = password;
            pref.put("passwordJC", pass);
        }
    }
    
    private void eliminarPreferencias() {
        pref.remove("userJC");
        pref.remove("passwordJC");
    }

    public void showRegister(boolean show) {
        if (show) {
            register.setVisible(true);
            login.setVisible(false);
        } else {
            register.setVisible(false);
            login.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables

    private void iniciarSesion(String strUser, char[] chrContrasena) {
        SqlUsuarios modSql = new SqlUsuarios(); 
        Usuarios mod = new Usuarios();
        Date date = new Date(); 

        DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String pass = new String(chrContrasena); 

        if (!strUser.equals("") && !pass.equals("")) { 
            try {
                String nuevoPass = Hash.sha1(pass); 
                mod.setUsuario(strUser); 
                mod.setPassword(nuevoPass);
                mod.setLast_session(fechaHora.format(date));  
                if (modSql.login(mod)) { 
                 
                    Container parent = this.login.getTopLevelAncestor();
                    if (parent instanceof JFrame) {
                        JFrame frame = (JFrame) parent;
                        frame.dispose();
                    }
                    Menu menuGUI = new Menu(mod); 
                    menuGUI.setVisible(true); 
                } else {
                    JOptionPane.showMessageDialog(this, "Datos incorrectos");
                }
            } catch (ClassNotFoundException ex) { // Cuando una clase no se puede cargar o acceder correctamente
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar sus datos.");
        }
    }

    public void registrarUsuarios(String strUser, char[] chrContrasena, char[] chrConfirmContrasena, String strNombre) throws ClassNotFoundException {

        SqlUsuarios modSql = new SqlUsuarios();
        Usuarios mod = new Usuarios();

        String pass = new String(chrContrasena);
        String passCon = new String(chrConfirmContrasena);

        if (strUser.equals("") || pass.equals("") || passCon.equals("") || strNombre.equals("")) {
            JOptionPane.showMessageDialog(null, "Campos Vacios, debe llenar todos los campos");
        } else {

            if (pass.equals(passCon)) {
             
                    if (modSql.existeUsuario(strUser) == 0) {
                        String nuevoPass = Hash.sha1(pass);
                        mod.setUsuario(strUser);
                        mod.setPassword(nuevoPass);
                        mod.setNombre(strNombre);
                        switch (strUser) {
                            case "JC":
                                mod.setId_tipo(1);   //1= Silvia, 2= Laura, 3= Saul, 4= invitado y 5= Produccion
                                break;
                            case "Planeacion":
                                mod.setId_tipo(2);
                                break;
                            case "Sao":
                                mod.setId_tipo(3);
                                break;
                            default:
                                mod.setId_tipo(5);
                                break;
                        }
                        if (modSql.registrar(mod)) {
                            JOptionPane.showMessageDialog(null, "Registro Guardado.");
                            showRegister(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al Guardar.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El Usuario ya existe.");
                    }
            } else {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
            }
        }
    }
}
