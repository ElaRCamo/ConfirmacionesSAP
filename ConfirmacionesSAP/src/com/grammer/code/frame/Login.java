package com.grammer.code.frame;
/**
 * Project: Confirmaciones SAP
 * File: ConfirmacionesSAP.java
 * 
 * Version 1.0.0 - 5.0.0 
 * Programmed by: Neftali Ramirez
 * - Implementación inicial 
 * 
 * Version: 6.0.0
 * Programmed by: Mariela Reyes Camo
 * Date: 05/09/2024
 * 
 * Changelog:
 * ----------------------------------------------------------------------------
 * - * Validaciones:
 * - Campos vacios
 * - Formato correcto (cantidad con solo números)
 * - Script SAP
 * 		- Si el mensaje arroja un error en SAP, da aviso, algunos errores ya estan mapeados
			 * Errores mapeados:
			 * - P123123    | sapMessage: No extiste material 123123 en centro 3330
			 * - P6514565646 |sapMessage: Entry 000000006514565646   does not exist in MARA (check entry)
			 * - Q larga    | sapMessage: Entrada demasiado larga (entrada sólo con el formato _,___,___,__~.___)
			 * - Sesión no iniciada:
			 * 		-80010108 / The object invoked has disconnected from its clients
			 * 		-The enumerator of the collection cannot find an element with the specified index.
			 * 		-Dispatch not hooked to windows memory
			 * 		-The control could not be found by id.
			 * 
 *		- Si el mensaje no es exitoso, se manda a pantalla como un error
			 *  Mensajes exitosos:Se identifican idependientemente del idioma de SAP 
			 * - P1500351-B | sapMessage: Contabil. EM y SM-parcial con doc. 4900434439 y actividades
			 * - P1466472   | sapMessage: EM y SM contabil. con documento 4900434438
			 * 
			 * - P1500351-B | sapMessage: GR and partial GI with document 4900434440 and activities posted
			 * - P1466472   | sapMessage: GR and GI with document 4900434442 and activities posted
 
 * Se cambia el orden de la secuencia del proceso, ahora, sólo si se realiza exitosamente la confirmación en SAP, 
 * se procede a guardar el registro en la base de datos.
 * 
 * Elementos visuales
 * - Se rediseñó la interfaz
 * - Se agregan avisos segun el mensaje de error
 * - Tabla con los datos de las confirmaciones realizadas durante el turno actual
 * - Se agrega barra de progreso
 * 
 * Nuevas funcionalidades
 * - Ajuste a la hora real
 * - La libreria jacob se agrega desde ubicacion especifica
 * - Se agrega el calculo de HoraXHora
 * - Se manda aviso cuando ya hay sesión iniciada
 * 
 * Version: 6.0.2
 * Programmed by: Mariela Reyes Camo
 * Date: 07/10/2024
 * 
 * - Se valida que el documento SAP no exista en la BD antes de guardarlo, si existe es porque hubo un error en SAP y es necesario reiniciarlo.
 * - Se agrega tabla de los palets totales por número de parte.
 * 
 * Version 6.0.3
 * Se actualiza validacion de hora 
 * ----------------------------------------------------------------------------
 * Copyright © 2024 Grammer Automotive Puebla S. A. de C. V. Todos los derechos reservados.
 */

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.TimeZone;
import java.util.Timer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import com.grammer.code.dao.PUserHorasDao;
import com.grammer.code.dao.ScriptDao;
import com.grammer.code.util.Datos;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.grammer.code.dao.ApprovalDao;

public class Login extends JFrame  {
	
	    private static final long serialVersionUID = 1L;
	    JPanel contentPane; 
	    Color fuera = new Color(0, 81, 149); 
	    JTextField usuario = new JTextField();
	    JPasswordField pass = new JPasswordField(); 
	    JLabel grammer = new JLabel(); 
	    JLabel logo = new JLabel(); 
	    JFrame regisFrame;
	    boolean cerrarFrame; 
	    static ApprovalDao daosD = new ApprovalDao(); // Instancia de DAO para manejar aprobaciones
	    static ScriptDao daos = new ScriptDao();
	    Color colorPrincipal = new Color(0, 81, 149);
	    Color colorBlanco = new Color(250,251,253);
	    String nUser = "";
	    Timer timer;
	    int x, y;
	    static Color colorError = new Color(255, 0, 20);
	    private static JPanel panelFuera = new JPanel(); 
	    static boolean estatusJacob = false;
	    static JLabel error = new JLabel("");
	    Font fuentePrincipalBold = new Font("Segoe UI", Font.BOLD, 15);

	    // Este es el método principal que lanza la aplicación
	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    Login frame = new Login(); // Crea una instancia de la ventana de login
	                    frame.setVisible(true); // Hace visible la ventana
	                    Runtime.getRuntime().addShutdownHook(new Thread(Datos::ejecucionCierre)); 
	                    estatusJacob = cargarJacobDLL();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
	    // Constructor de la clase Login
	    public Login() {
	    	TimeZone.setDefault(TimeZone.getTimeZone("GMT-6"));
	    	UIManager.put("OptionPane.background", colorPrincipal);
	        UIManager.put("Panel.background", colorPrincipal);
	        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.BOLD,15));
	        UIManager.put("OptionPane.messageForeground", colorBlanco);
	        
	    	 try {
	             if(!Datos.ejecucion()){ // Verifica alguna condición al inicio (ejecución válida)
	                 System.exit(0); // Si no es válida, cierra la aplicación
	             } else{
	                 loadingDos(); // Si es válida, carga otra parte del proceso
	             }
	         } catch (MalformedURLException | InterruptedException e) {
	             e.printStackTrace(); // Captura y maneja excepciones
	         }
	    	 
	    	// Hilo que monitorea el estado de la ventana mientras esté visible
	         Thread monitorThread = new Thread(() -> {
	             while (isVisible()) {
	                 // Aquí se puede realizar alguna verificación periódica
	                 try {
	                     Thread.sleep(1000); // Espera 1 segundo antes de la próxima verificación
	                 } catch (InterruptedException e) {
	                     e.printStackTrace(); // Maneja la excepción
	                 }
	             }
	         });
	         monitorThread.start(); // Inicia el hilo de monitoreo
	         
	         
	    	 
	        setTitle("Confirmaciones SAP | Login"); 
	        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/grammer/code/img/chrecking.png")));
	        setResizable(false); // La ventana no puede redimensionarse
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	        setBounds(100, 100, 754, 613); 
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); 
	        setContentPane(contentPane); 
	        contentPane.setLayout(null); 

	        // Panel exterior
	        panelFuera.setBounds(0, 0, 754, 613); 
	        panelFuera.setBackground(fuera); // Fondo del panel exterior
	        contentPane.add(panelFuera); 
	        panelFuera.setLayout(null); 
	        
	        getContentPane().addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					x = e.getX();
					y = e.getY();
				}
			});
			getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					setLocation(getLocation().x + e.getX() - x, getLocation().y + e.getY() - y);
				}
			});
	        
	        
	        // Calcula y establece la ubicación de la ventana en la pantalla
	        setUndecorated(true); // Elimina la decoración de la ventana (barras de título, bordes, etc.)
			getRootPane().setWindowDecorationStyle(JRootPane.NONE); 
			this.setLocationRelativeTo(null); 
	     		GraphicsConfiguration config = getGraphicsConfiguration();
	     		Rectangle bounds = config.getBounds();
	     		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
	     		int x = bounds.x + bounds.width - insets.right - getWidth() - 10; // Calcula la posición X
	     		int y = bounds.y + insets.top + 10; // Calcula la posición Y
	     		setLocation(x, y); // Establece la ubicación de la ventana

	     		setVisible(true); // Hace visible la ventana
	     		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Establece que al cerrar la ventana, se liberen los recursos asociados
	    		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
	    		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

	    		getContentPane().setLayout(null);
	    		getContentPane().add(panelFuera);

	        // Panel interior
	        JPanel panelDentro = new JPanel(); 
	        panelDentro.setBounds(50, 70, 655, 477); 
	        panelDentro.setBackground(Color.WHITE); // Fondo del panel interior
	        panelFuera.add(panelDentro); 
	        panelDentro.setLayout(null); 
	        usuario.setForeground(new Color(0, 51, 102));
	        usuario.setFont(new Font("Segoe UI", Font.PLAIN, 20));

	        // Campo de texto para el usuario
	        usuario.setBounds(250, 261, 200, 30); 
	        panelDentro.add(usuario); 
	        usuario.requestFocus();
	        usuario.setBackground(new Color(229, 238, 247)); 
	        pass.setForeground(new Color(0, 51, 102));
	        pass.setFont(new Font("Segoe UI", Font.PLAIN, 20));
	        usuario.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(196, 218, 236)));
	        pass.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,new Color(196, 218, 236)));

	        // Campo de contraseña
	        pass.setBounds(250, 343, 200, 30); 
	        pass.setBackground(new Color(229, 238, 247)); 
	        panelDentro.add(pass); 

	        // Imagen del avatar
	        JLabel avLogin = new JLabel(); 
	        avLogin.setHorizontalAlignment(SwingConstants.LEFT); 
	        avLogin.setLocation(21, 80); 
	        avLogin.setSize(39, 39); 
	        panelDentro.add(avLogin); 
	        
	        JLabel lblNewLabel = new JLabel("Usuario");
	        lblNewLabel.setForeground(new Color(0, 51, 102));
	        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
	        lblNewLabel.setBounds(250, 232, 200, 25);
	        panelDentro.add(lblNewLabel);
	        
	        JLabel lblPassword = new JLabel("Contrase\u00F1a");
	        lblPassword.setForeground(new Color(0, 51, 102));
	        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 20));
	        lblPassword.setBounds(250, 314, 200, 25);
	        panelDentro.add(lblPassword);
	        
	        	        // Etiqueta "Bienvenido"
	        	        JLabel lblBienvenido = DefaultComponentFactory.getInstance().createLabel("BIENVENIDO");
	        	        lblBienvenido.setBounds(112, 145, 505, 52);
	        	        panelDentro.add(lblBienvenido);
	        	        lblBienvenido.setForeground(new Color(0, 51, 102));
	        	        lblBienvenido.setFont(new Font("Segoe UI", Font.BOLD, 40)); 
	        	        lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
	        	        
	        	        JLabel imgUser = new JLabel("");
	        	        imgUser.setIcon(new ImageIcon(Login.class.getResource("/com/grammer/code/img/AvatarL.png")));
	        	        imgUser.setBounds(460, 260, 30, 30);
	        	        panelDentro.add(imgUser);
	        	        
	        	        JLabel imgCandado = new JLabel("");
	        	        imgCandado.setIcon(new ImageIcon(Login.class.getResource("/com/grammer/code/img/Candado.png")));
	        	        imgCandado.setBounds(460, 342, 30, 30);
	        	        panelDentro.add(imgCandado);
	        	        
	        	        JLabel imagenFondo = new JLabel("");
	        	        imagenFondo.setIcon(new ImageIcon(Login.class.getResource("/com/grammer/code/img/ITLogo.png")));
	        	        imagenFondo.setBounds(548, 13, 87, 90);
	        	        panelDentro.add(imagenFondo);
	        	        
	        	        JLabel imgLogin = new JLabel("");
	        	        imgLogin.setBounds(31, 31, 209, 446);
	        	        panelDentro.add(imgLogin);
	        	        imgLogin.setIcon(new ImageIcon(Login.class.getResource("/com/grammer/code/img/grammito.png")));
	        	        error.setHorizontalAlignment(SwingConstants.CENTER);
	        	        error.setBounds(212, 417, 386, 47);
	        	        error.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, colorPrincipal));
	        	        error.setForeground(Color.RED);
	        	        error.setFont(new Font("Segoe UI", Font.BOLD, 16));
	        	        panelDentro.add(error);
	        	        error.setText("<html></html>");
	        
	     // Cargar la imagen desde un archivo
	        ImageIcon imgGM = new ImageIcon("/com/grammer/code/img/Grammer.png");
	        	        
	        	        	        // Imagen del candado (contraseña)
	        	        	        JLabel Canda = new JLabel(); 
	        	        	        Canda.setBounds(349, 81, 39, 52);
	        	        	        panelFuera.add(Canda);
	        	        	        Canda.setHorizontalAlignment(SwingConstants.LEFT);
	        	        	        grammer.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        	        	        grammer.setText("Version 6.0.3");
	        	        	        grammer.setBounds(0, 571, 167, 16);
	        	        	        panelFuera.add(grammer);
	        	        	        grammer.setForeground(Color.WHITE);
	        	        	        // Configuración de la imagen principal (grammer-label)
	        	        	        grammer.setVerticalAlignment(SwingConstants.TOP); 
	        	        	        grammer.setHorizontalAlignment(SwingConstants.CENTER);
	        	        	        grammer.setIcon(imgGM);
	        	        	        
	        	        	        
	        	        	        JButton btnSalir = new JButton("X");
	        	        	        btnSalir.setBounds(685, 13, 51, 29);
	        	        	        
	        	        	        btnSalir.setForeground(colorBlanco);
	        	        	        btnSalir.setFont(fuentePrincipalBold);
	        	        	        btnSalir.setBackground(new Color (0, 0, 0, 0));
	        	        	        btnSalir.setBorder(BorderFactory.createEmptyBorder());
	        	        	        btnSalir.setContentAreaFilled(false); // Elimina el relleno
	        	        	        btnSalir.setFocusPainted(false); // Elimina el borde que aparece cuando el botón es enfocado
	        	        	        btnSalir.setOpaque(false); 
	        	        	        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        	        	        btnSalir.addActionListener(new ActionListener() {
	        	        	        	public void actionPerformed(ActionEvent e) {
	        	        	        		System.exit(0);
	        	        	        	}
	        	        	        });
	        	        	        
	        	        	        panelFuera.add(btnSalir);
	        	        	        
	        	        
	        // Inicialmente oculta los campos de contraseña y su ícono
	        pass.setVisible(false); 
	        lblPassword.setVisible(false);
	        imgCandado.setVisible(false);

	     // Acción que se ejecuta cuando el usuario presiona Enter después de ingresar su nombre
	     // Declaración y creación de una acción personalizada
	        Action actionUser = new AbstractAction() {
	            private static final long serialVersionUID = 1L;  // ID de versión para la serialización

	            @SuppressWarnings("deprecation")
	            @Override
	            public void actionPerformed(ActionEvent evt) {
	                // Convierte el texto del campo 'usuario' a minúsculas y lo asigna a 'nUser'
	                nUser = usuario.getText().toLowerCase();
	                try {

	                    // Si el frame 'regisFrame' no es nulo y 'cerrarFrame' es verdadero, lo oculta
	                    if (null != regisFrame && cerrarFrame) {
	                        regisFrame.setVisible(false);
	                    }
	                    
	                    // Crea una instancia de 'PUserHorasDao' para interactuar con la base de datos
	                    PUserHorasDao dao = new PUserHorasDao();

	                    // Verifica si el usuario existe en la base de datos
	                    if (dao.getAccesoUser(usuario.getText().toUpperCase().trim()) != null) {
	                        
	                        pass.setVisible(true); 
	                        Canda.setVisible(true); 
	                        lblPassword.setVisible(true);
	                        imgCandado.setVisible(true);
	                        pass.requestFocus();

	                        // Si ya hay texto en el campo de contraseña
	                        if (pass.getText().length() > 0) {
	                            // Verifica las credenciales del usuario
	                            if (dao.getAcceso(usuario.getText().toUpperCase().trim(),
	                                    pass.getText().trim()) != null ) {
	                            	
	                            	if(!estatusJacob) {
	                            		JOptionPane.showMessageDialog(null, "Error al cargar Jacob. Verifique e intente nuevamente.", "ERROR JACOB", JOptionPane.ERROR_MESSAGE);
	                            	}else {
	                            		entradaDos(usuario.getText().toUpperCase().trim());  // Llama a 'entradaDos' si la contraseña es correcta
	                                	dispose();  // Cierra el frame actual
	                            	}
	                                	
	                            } else {
	                                // Muestra un mensaje de error si la contraseña es incorrecta
	                                JOptionPane.showMessageDialog(panelFuera, "Contraseña incorrecta");
	                            }
	                        }

	                    } else if (nUser.equals("leon-c")) {// Caso especial para el usuario 'leon-c'
	                        pass.setVisible(true); 
	                        Canda.setVisible(true); 
	                        lblPassword.setVisible(true);
	                        imgCandado.setVisible(true);
	                        pass.requestFocus();  
	                        
	                        // Si ya hay texto en el campo de contraseña
	                        if (pass.getText().length() > 0) {
	                            // Verifica la contraseña especial
	                            if (pass.getText().equals("Planning4")) {
	                            	dispose();  // Cierra el frame actual
	                            	entradaAdmin();  // Llama a 'entradaAdmin' si la contraseña es correcta
	                                
	                            } else {
	                                JOptionPane.showMessageDialog(panelFuera, "Contraseña incorrecta");
	                            }
	                        }
	                    } else {
	                        JOptionPane.showMessageDialog(panelFuera, "Usuario incorrecto");
	                    }
	                } catch (Exception e1) {
	                    e1.printStackTrace();
	                }
	            }
	        };

	        // Asigna la acción 'actionUser' a los campos 'usuario' y 'pass'
	        usuario.addActionListener(actionUser);
	        pass.addActionListener(actionUser);
	    }
	    
	    public static boolean cargarJacobDLL() {
	    	
	    	// Añadir la carpeta de Documentos al java.library.path
            //System.setProperty("java.library.path", System.getProperty("user.home") + "\\Downloads"); //Ejemplo: C:\Users\Ex-Reyes-M\\Downloads
	    	System.setProperty("java.library.path", "C:\\Windows\\System32");
	    	
            try {
                // Cargar la DLL utilizando System.loadLibrary()
                System.loadLibrary("jacob-1.18-x64");
                System.out.println("DLL cargada exitosamente.");
                estatusJacob = true;
            } catch (UnsatisfiedLinkError e) {
            	String errorJacob ="Error al cargar JACOB: " + e.getMessage();
                System.err.println(errorJacob);
                mostrarError(errorJacob, "ERROR JACOB");
                estatusJacob = false;
            }
            return estatusJacob;
	    }
	    
	    public static void mostrarError(String errorMsj, String encabezado) {
	    	panelFuera.setBackground(colorError);
	    	error.setText("<html>"+errorMsj+"</html>");
	        error.setVisible(true);
	    	JOptionPane.showMessageDialog(null, errorMsj, encabezado, JOptionPane.ERROR_MESSAGE);
	    }
	    
	    public void entradaDos(String usuario) {
	    	System.out.println("Entrada al panel principal");
		    regisFrame = new Confirmaciones(usuario); // Se crea una instancia de la ventana o frame 'Confirmaciones' pasando el nombre de usuario como parámetro.
		    regisFrame.setLocationRelativeTo(null); // Se centra la ventana en la pantalla.
		    regisFrame.setResizable(false); // Se desactiva la posibilidad de redimensionar la ventana.
		    regisFrame.setVisible(true); // Se hace visible la ventana.
	    }
	    
	    public void entradaAdmin() {
	        System.out.println("Entrada correcta"); // Mensaje para depuración que indica que se ha ingresado correctamente.
	        regisFrame = new UsuarioAg(); // Se crea una instancia de la ventana o frame 'UsuarioAg'.
	        regisFrame.setLocationRelativeTo(null); // Se centra la ventana en la pantalla.
	        regisFrame.setResizable(false); // Se desactiva la posibilidad de redimensionar la ventana.
	        regisFrame.setVisible(true); // Se hace visible la ventana.
	        
	    }
	   
	    // Método para cargar una animación o pantalla de carga (opcional)
	    public void loadingDos() throws MalformedURLException, InterruptedException {
	        
	        Image imgCanD = new ImageIcon(Objects.requireNonNull(Login.class
	                            .getResource("/com/grammer/code/img/LogoIT.png"))).getImage();
	        Icon icon = new ImageIcon(imgCanD);// Crea un Icon a partir de la imagen cargada
	        JLabel label = new JLabel(icon);// Crea un JLabel y le asigna el Icon creado
	        
	        // Crea un JFrame (ventana) con el título "Animation"
	        JFrame f = new JFrame("Animation");
	        f.setUndecorated(true);// Elimina los bordes y la barra de título del JFrame
	        f.getContentPane().add(label);// Añade el JLabel (con la imagen) al contenido del JFrame
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Configura la operación de cierre para que el programa se termine al cerrar la ventana
	        f.pack();// Ajusta el tamaño del JFrame al tamaño preferido de sus componentes
	        f.setResizable(false);
	        f.setLocationRelativeTo(null);// Centra el JFrame en la pantalla
	        f.setVisible(true);
	        
	        // Establece el fondo del JFrame como transparente (permite que los componentes sean visibles, pero el fondo no)
	        f.setBackground(new Color(0, 0, 0, 0));
	        
	        // Pausa la ejecución del hilo actual durante 1000 milisegundos (1 segundo)
	        Thread.sleep(1000);
	        
	        // Cierra y elimina el JFrame después de la pausa
	        f.dispose();
	    }
}
