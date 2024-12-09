package com.grammer.code.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.Timer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.grammer.code.util.Datos;
import com.jgoodies.forms.factories.DefaultComponentFactory;

public class test extends JFrame  {
	
	    private static final long serialVersionUID = 1L;
	    JPanel contentPane; 
	    Color fuera = new Color(0, 81, 149); 
	    JTextField usuario = new JTextField();
	    JPasswordField pass = new JPasswordField(); 
	    JLabel grammer = new JLabel(); 
	    JLabel logo = new JLabel(); 
	    JLabel consejo; 
	    JFrame regisFrame;
	    boolean cerrarFrame; 
	    //TextPrompt phUsuario = new TextPrompt("Nombre", usuario); 
	    //TextPrompt passw = new TextPrompt("ContraseÃ±a", pass); 
	    String nUser = "";
	    //static ScriptDao daos = new ScriptDao(); 
	    //static ApprovalDao daosD = new ApprovalDao(); 
	    Timer timer;

	    // Este es el método principal que lanza la aplicación
	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    Login frame = new Login(); // Crea una instancia de la ventana de login
	                    frame.setVisible(true); // Hace visible la ventana
	                    Runtime.getRuntime().addShutdownHook(new Thread(Datos::ejecucionCierre)); 
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }

	    // Constructor de la clase Login
	    public test() {
	        setTitle("Confirmaciones SAP | Login"); 
	        setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/chrecking.png"))); 
	        try {
	            if(!Datos.ejecucion()){ // Verifica si el programa puede continuar ejecutándose
	                System.exit(0); // Cierra el programa si no puede continuar
	            } else{
	                loadingDos(); // Carga una pantalla inicial (animación)
	            }
	        } catch (MalformedURLException | InterruptedException e) {
	            e.printStackTrace(); 
	        }

	        // Hilo que monitorea constantemente si la ventana está visible
	        Thread monitorThread = new Thread(() -> {
	            while (isVisible()) {
	                try {
	                    Thread.sleep(1000); // Espera 1 segundo antes de volver a verificar
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	        monitorThread.start(); 

	        setResizable(false); // La ventana no puede redimensionarse
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	        setBounds(100, 100, 754, 613); 
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); 
	        setContentPane(contentPane); 
	        contentPane.setLayout(null); 

	        // Panel exterior
	        JPanel panelFuera = new JPanel(); 
	        panelFuera.setBounds(0, 0, 748, 578); 
	        panelFuera.setBackground(fuera); // Fondo del panel exterior
	        contentPane.add(panelFuera); 
	        panelFuera.setLayout(null); 
	        grammer.setForeground(Color.WHITE);

	        // Configuración de la imagen principal (grammer-label)
	        grammer.setVerticalAlignment(SwingConstants.TOP); 
	        grammer.setHorizontalAlignment(SwingConstants.CENTER); 
	        grammer.setLocation(0, 21); 
	        grammer.setSize(748, 140); 
	        Image imgG = new ImageIcon(Objects.requireNonNull(Login.class.getResource("/img/fono.png"))).getImage(); 
	        ImageIcon imgGM = new ImageIcon(imgG.getScaledInstance(200, 150, Image.SCALE_SMOOTH)); 
	        grammer.setIcon(imgGM); 
	        panelFuera.add(grammer); 

	        // Panel interior
	        JPanel panelDentro = new JPanel(); 
	        panelDentro.setBounds(49, 162, 655, 387); 
	        panelDentro.setBackground(Color.WHITE); // Fondo del panel interior
	        panelFuera.add(panelDentro); 
	        panelDentro.setLayout(null); 
	        usuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));

	        // Campo de texto para el usuario
	        usuario.setBounds(258, 161, 142, 30); 
	        panelDentro.add(usuario); 
	        //phUsuario.changeAlpha(0.75f); // Aplica un estilo semitransparente al "placeholder"
	        //phUsuario.changeStyle(Font.ITALIC); // Cambia el estilo del texto a itálica
	        usuario.setBackground(Color.WHITE); 
	        pass.setFont(new Font("Segoe UI", Font.PLAIN, 16));

	        // Campo de contraseña
	        pass.setBounds(258, 243, 142, 30); 
	        //passw.changeAlpha(0.75f); 
	        //passw.changeStyle(Font.ITALIC); 
	        panelDentro.add(pass); 

	        // Imagen del avatar
	        Image imgAvat = new ImageIcon(Objects.requireNonNull(Login.class.getResource("/img/AvatarL.png"))).getImage(); 
	        ImageIcon imgAvatar = new ImageIcon(imgAvat.getScaledInstance(30, 30, Image.SCALE_SMOOTH)); 
	        JLabel avLogin = new JLabel(); 
	        avLogin.setHorizontalAlignment(SwingConstants.LEFT); 
	        avLogin.setLocation(21, 80); 
	        avLogin.setSize(39, 39); 
	        avLogin.setIcon(imgAvatar); 
	        panelDentro.add(avLogin); 

	        // Imagen del candado (contraseña)
	        Image imgCan = new ImageIcon(Objects.requireNonNull(Login.class.getResource("/img/Candado.png"))).getImage(); 
	        ImageIcon imgCandado = new ImageIcon(imgCan.getScaledInstance(30, 30, Image.SCALE_SMOOTH)); 
	        JLabel Canda = new JLabel(); 
	        Canda.setHorizontalAlignment(SwingConstants.LEFT); 
	        Canda.setLocation(21, 132); 
	        Canda.setSize(39, 52); 
	        Canda.setIcon(imgCandado); 
	        panelDentro.add(Canda); 
	        
	        JLabel lblNewLabel = new JLabel("Usuario");
	        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
	        lblNewLabel.setBounds(258, 132, 142, 16);
	        panelDentro.add(lblNewLabel);
	        
	        JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a");
	        lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 18));
	        lblNewLabel_1.setBounds(258, 204, 142, 16);
	        panelDentro.add(lblNewLabel_1);
	        
	        	        // Etiqueta "Bienvenido"
	        	        JLabel lblBienvenido = DefaultComponentFactory.getInstance().createLabel("BIENVENIDO");
	        	        lblBienvenido.setBounds(0, 40, 655, 52);
	        	        panelDentro.add(lblBienvenido);
	        	        lblBienvenido.setForeground(Color.DARK_GRAY);
	        	        lblBienvenido.setFont(new Font("Segoe UI Semibold", Font.BOLD, 25)); 
	        	        lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER); 

	        // Etiqueta para mensajes de advertencia o consejo
	        consejo = new JLabel(""); 
	        consejo.setForeground(Color.WHITE); 
	        consejo.setFont(new Font("Tahoma", Font.BOLD, 14)); 
	        consejo.setHorizontalAlignment(SwingConstants.CENTER); 
	        consejo.setBounds(10, 430, 338, 33); 
	        panelFuera.add(consejo); 

	        // Temporizador para realizar acciones cada cierto tiempo
	     /*   timer = new Timer(18000, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Datos datos = new Datos(); 
	                String opened = datos.openApp(); 

	                if(opened.equals("OK")){
	                    pass.setEnabled(true); 
	                    usuario.setEnabled(true); 
	                    consejo.setText(""); 
	                    timer.stop(); 
	                    timer.setRepeats(false); 

	                } else if(opened.equals("NOK")){
	                    pass.setEnabled(false); 
	                    usuario.setEnabled(false); 
	                    //phUsuario.setEnabled(false); 
	                    consejo.setText("Antes de iniciar inicie sesión en SAP"); 
	                    timer.start(); 
	                }
	            }
	        });*/

	        // Verificación de la versión activa de la aplicación
	        String opened;
	        /*
	         * VersionadorRes versionadorRes = new VersionadorRes(); 
	         
	        Versionador versionador = versionadorRes.getByVersionActiva(); 
	        if (versionador == null) {
	            consejo.setText("VERSIÓN NO ACTUALIZADA"); 
	            consejo.setVisible(true); 
	            usuario.setEnabled(false); 
	            pass.setEnabled(false); 
	            //phUsuario.setEnabled(false); 
	        } else if (versionador.getStatus().equals("B")) {
	            String version = versionador.getVersion(); 
	            JLabel lblVersionj = DefaultComponentFactory.getInstance().createLabel("V " + version); 
	            lblVersionj.setBounds(10, 463, 56, 14); 
	            panelFuera.add(lblVersionj); 
	            Datos datos = new Datos(); 
	            opened = datos.openApp(); 
	            if(opened.equals("OK")){
	                pass.setEnabled(true); 
	                usuario.setEnabled(true); 
	                timer.stop(); 
	                timer.setRepeats(false); 

	            } else if(opened.equals("NOK")){
	                pass.setEnabled(false); 
	                usuario.setEnabled(false); 
	                //phUsuario.setEnabled(false); 
	                consejo.setText("Antes de iniciar inicie sesión en SAP"); 
	                timer.start(); 
	            }
	        }
	        */

	        // Inicialmente oculta los campos de contraseña y su ícono
	        pass.setVisible(false); 
	        Canda.setVisible(false); 

	        // Acción que se ejecuta cuando el usuario presiona Enter después de ingresar su nombre
	        /*Action actionUser = new AbstractAction() {
	            private static final long serialVersionUID = 1L;

	            @SuppressWarnings("deprecation")
	            @Override
	            public void actionPerformed(ActionEvent evt) {
	                nUser = usuario.getText().toLowerCase(); 
	                try {
	                    if (null != regisFrame && cerrarFrame) {
	                        regisFrame.setVisible(false); 
	                    }
	                    //PUserHorasDao dao = new PUserHorasDao(); 
	                    //UsuarioRes usu = dao.getById(nUser); 
	                    // Si el usuario existe, muestra el campo de contraseña
	                    if (usu.getUsuario() != null) {
	                        pass.setVisible(true); 
	                        Canda.setVisible(true); 
	                        pass.requestFocus(); 
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Usuario incorrecto", "ERROR", JOptionPane
	                                .ERROR_MESSAGE); // Muestra un mensaje de error si el usuario no existe
	                        usuario.setText(""); // Limpia el campo de texto del usuario
	                        usuario.requestFocus(); // Regresa el foco al campo de usuario
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace(); // Muestra cualquier excepción que ocurra
	                }
	            }
	        };

	        usuario.addActionListener(actionUser); // Asocia la acción `actionUser` al campo de texto del usuario
*/
	        // Acción que se ejecuta cuando el usuario presiona Enter después de ingresar la contraseña
	        Action actionPass = new AbstractAction() {
	            private static final long serialVersionUID = 1L;

	            @SuppressWarnings("deprecation")
	            @Override
	            public void actionPerformed(ActionEvent evt) {
	                String password = pass.getText(); 
	                try {
	                    // Aquí podrías agregar la lógica para validar la contraseña
	                    if (validarUsuario(nUser, password)) { // Verifica las credenciales
	                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso"); 
	                        // Lógica para abrir la siguiente ventana o panel
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE); 
	                        pass.setText(""); // Limpia el campo de texto de la contraseña
	                        pass.requestFocus(); // Regresa el foco al campo de contraseña
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace(); 
	                }
	            }
	        };

	        pass.addActionListener(actionPass); // Asocia la acción `actionPass` al campo de contraseña

	        // Finalmente, establece que el campo de usuario reciba el foco cuando la ventana se abre
	        usuario.requestFocusInWindow(); 
	    }

	    // Método para validar las credenciales del usuario (esto es solo un ejemplo)
	    private boolean validarUsuario(String nombreUsuario, String contraseña) {
	        // Aquí iría la lógica para validar las credenciales contra la base de datos u otro sistema
	        return "usuarioEjemplo".equals(nombreUsuario) && "contraseñaEjemplo".equals(contraseña); // Comparación simple
	    }

	    // Método para cargar una animación o pantalla de carga (opcional)
	    private void loadingDos() throws MalformedURLException, InterruptedException {
	        // Aquí podrías mostrar una animación de carga mientras se prepara la aplicación
	        Thread.sleep(2000); // Simula un tiempo de carga
	    }
}

