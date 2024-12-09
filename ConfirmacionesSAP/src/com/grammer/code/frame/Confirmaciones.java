package com.grammer.code.frame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.grammer.code.dao.ApprovalDao;
import com.grammer.code.dao.MaterialCostumerRes;
import com.grammer.code.entity.ApprovalEntity;
import com.grammer.code.util.TurnoUtil;
import com.grammer.code.util.BotonEstilizado;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.grammer.code.util.CorridaJacob;
import com.grammer.code.util.CustomScrollBarUI;
import com.grammer.code.util.HoraXHora;
import com.grammer.code.util.JPing;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.Timer;

public class Confirmaciones extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private JTextField NumP = new JTextField(), Quantity = new JTextField();
    private final JLabel errorRed = new JLabel();
    private final JPanel panelPrincipal = new JPanel();
    private final JPanel panelHistorico = new JPanel();
    ApprovalDao daoConfirmacion = new ApprovalDao();
    JPing ping = new JPing();
    final JLabel stts = new JLabel();
    Color colorProceso = new Color(10, 160, 225);
    Color colorPrincipal = new Color(0, 81, 149);
    Color colorError = new Color(255, 0, 20);
    Color colorAviso = new Color(50,130,199);
    Color colorBlanco = new Color(250,251,253);
    Color colorVerde = Color.decode("#69A032");
    String hostname = "Unknown";
    ApprovalEntity confirmacionSolicitada = new ApprovalEntity();
    private final JPanel panel = new JPanel();
    private final JLabel imgWifiError = new JLabel("");
    private JLabel lblProgressBar = new JLabel("Procesando confirmaci\u00F3n, por favor espere...");
    Font fuentePrincipalBold = new Font("Segoe UI", Font.BOLD, 15);
    private JProgressBar progressBar = new JProgressBar();
    int x, y;
    private final JTextField txtFecha = new JTextField();
    private final JTextField txtUsuario = new JTextField();
    private final JTextField txtTurno = new JTextField();
    private final JLabel error = new JLabel("");
    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel modelPalets = new DefaultTableModel();
    private BotonEstilizado btnOk = new BotonEstilizado("OK", 15, colorPrincipal, colorBlanco, 0, 40);
    private final JPanel panelPalets = new JPanel();
    private final JPanel panelRegistros = new JPanel();
    private JLabel tituloTabla = new JLabel("Confirmaciones individuales");
    private final JLabel lblTituloPalets = new JLabel("Pallets confirmados");
    private final JLabel lblDocSAP = new JLabel("Documento SAP: ");
    private final JPanel panelSAP = new JPanel();
    private final JTextField txtDocSAP = new JTextField();
    private final JPanel panelDatos = new JPanel();
    private JPanel panelDentro = new JPanel();
    
    
    public Confirmaciones(String usuario) {
    	
    	 // Establecer foco en el JTextField cuando se inicie la ventana
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	panelDatos.setVisible(true);
                NumP.setBounds(199, 27, 154, 36);
                panelDatos.setBounds(20, 35, 365, 163);
                panelDentro.add(panelDatos);
                panelDatos.add(NumP);
                NumP.requestFocusInWindow(); 
            }
        });
    	
    	TimeZone.setDefault(TimeZone.getTimeZone("GMT-6"));
    	
    	UIManager.put("OptionPane.background", colorPrincipal);
        UIManager.put("Panel.background", colorPrincipal);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.BOLD,25));
        UIManager.put("OptionPane.messageForeground", colorBlanco);
    
        setTitle("Confirmaciones SAP");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/grammer/code/img/chrecking.png"))); //ícono de la ventana
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 754, 613);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
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
    		getContentPane().add(panelPrincipal);


        /*---------------------------Ingreso de los Paneles-------------------------------------*/
        panelPrincipal.setBackground(new Color(0, 81, 149));
        panelPrincipal.setBounds(0, 0, 754, 613); //setBounds(x, y, width, height)
        contentPane.add(panelPrincipal);
        panelPrincipal.setLayout(null);

        /*---------------------------Ingreso del texto de confirmación-------------------------------------*/
        JLabel lblNewLabel = new JLabel("Confirmaciones SAP");
        lblNewLabel.setForeground(colorBlanco);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblNewLabel.setBounds(194, 24, 372, 36);
        panelPrincipal.add(lblNewLabel);

        /*---------------------------Panel de entrada de datos----------------------------------------------*/
        
        panelDentro.setBackground(colorBlanco);
        panelDentro.setBounds(20, 82, 716, 218);
        //panelDentro.setBorder(brdrRight);//bordes redondos
        panelPrincipal.add(panelDentro);
        panelDentro.setLayout(null);

        /*-----------------------Textfiel donde ingresa el numero de parte --------------------------------*/
        Color blancoGrisaseo = new Color(240, 240, 240);
        /*---------------------------Label para señalar la version del programa---------------------*/
        JLabel version = DefaultComponentFactory.getInstance().createLabel("Version 6.0.3");
        version.setForeground(colorBlanco);
        version.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        version.setBounds(20, 586, 118, 14);
        panelPrincipal.add(version);
        
        /*---------------------------Panel para el historico de confirmaciones---------------------*/
        panelHistorico.setBounds(20, 295, 716, 285);
        panelPrincipal.add(panelHistorico);
        panelHistorico.setBackground(colorBlanco);
        //panelHistorico.setBorder(brdrRight);
        panelHistorico.setLayout(null);
                
        /*---------------------------Tabla para el historico de confirmaciones---------------------*/
        txtFecha.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    	txtFecha.setForeground(colorBlanco);
    	txtFecha.setBounds(652, 50, 84, 22);
    	txtFecha.setColumns(10);
    	txtFecha.setOpaque(false);
        txtFecha.setBorder(BorderFactory.createEmptyBorder());
        txtFecha.setHorizontalAlignment(JTextField.RIGHT);
        txtFecha.setEditable(false); 
        Date fechaNormal = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaTexto = dateFormat.format(fechaNormal);
        txtFecha.setText(fechaTexto);
        
        TurnoUtil.TurnoInfo info = TurnoUtil.determinarTurno();
        hostname = info.getHostname();
        String turno = info.getTurno();
        int horas = info.getHoras();
        int minutos = info.getMinutos();
        String horaConMin = horas +""+ minutos;
        int horaActual = Integer.parseInt(horaConMin);
        configurarTablaHistorico(panelRegistros, usuario, fechaNormal, turno, horaActual);
        configurarTablaPalets(panelPalets, usuario, hostname,  fechaNormal, turno, horaActual);
        // Configurar el borde blanco
        Border whiteBorder = BorderFactory.createLineBorder(colorBlanco, 1); // Borde blanco de 1 pixel de grosor
        
        
        /*---------------------------JProgressBar para las confirmaciones---------------------*/
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        progressBar.setBounds(0, 266, 716, 19);
        panelHistorico.add(progressBar);
        progressBar.setForeground(new Color(0, 51, 153));
        progressBar.setStringPainted(true);
        progressBar.setBackground(colorBlanco);
        lblProgressBar.setBounds(12, 232, 694, 40);
        panelHistorico.add(lblProgressBar);
        lblProgressBar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblProgressBar.setForeground(new Color(0, 51, 102));
        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setBounds(12, 222, 682, 42);
        panelHistorico.add(error);
        error.setText("<html></html>");
        error.setForeground(Color.RED);
        error.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panelPalets.setBounds(486, 10, 212, 30);
        
        panelHistorico.add(panelPalets);
        panelPalets.add(lblTituloPalets);
        lblTituloPalets.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloPalets.setForeground(new Color(0, 81, 149));
        lblTituloPalets.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTituloPalets.setForeground(colorBlanco); // Configura el color del título
        lblTituloPalets.setBackground(colorPrincipal);
        panelRegistros.setBounds(20, 10, 450, 30);
        panelHistorico.add(panelRegistros);
        panelRegistros.add(tituloTabla);
        

    	// Crear el JLabel para el título "Confirmaciones individuales"
        tituloTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tituloTabla.setForeground(colorBlanco); // Configura el color del título
        tituloTabla.setBackground(colorPrincipal);
        tituloTabla.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblProgressBar.setVisible(false);
        progressBar.setVisible(false);
        
        errorRed.setBounds(420, 169, 274, 25);
        panelDentro.add(errorRed);
        errorRed.setForeground(new Color(220, 20, 60));
        errorRed.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        errorRed.setHorizontalAlignment(SwingConstants.CENTER);
        imgWifiError.setBounds(644, 13, 50, 50);
        panelDentro.add(imgWifiError);
        imgWifiError.setIcon(new ImageIcon(Confirmaciones.class.getResource("/com/grammer/code/img/wifiRed.png")));
        JLabel imgWifi = new JLabel("");
        imgWifi.setBounds(644, 13, 50, 50);
        panelDentro.add(imgWifi);
        imgWifi.setIcon(new ImageIcon(Confirmaciones.class.getResource("/com/grammer/code/img/wifi.png")));
        panelSAP.setBounds(20, 35, 365, 163);
        panelSAP.setBackground(colorBlanco);
        
        panelDentro.add(panelSAP);
        panelSAP.setVisible(false);
        panelSAP.setLayout(null);
        lblDocSAP.setBounds(88, 13, 169, 36);
        panelSAP.add(lblDocSAP);
        lblDocSAP.setHorizontalAlignment(SwingConstants.CENTER);
        lblDocSAP.setForeground(new Color(0, 81, 149));
        lblDocSAP.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnOk.setBounds(136, 114, 68, 36);
        panelSAP.add(btnOk);
        btnOk.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ocultarDocSAP();
        	}
        });
        btnOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtDocSAP.setBounds(71, 62, 199, 36);
        panelSAP.add(txtDocSAP);
        txtDocSAP.setHorizontalAlignment(JTextField.CENTER);
        txtDocSAP.setForeground(new Color(0, 81, 149));
        txtDocSAP.setFont(new Font("Segoe UI", Font.BOLD, 25));
        txtDocSAP.setColumns(10);
        txtDocSAP.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 81, 149)));
        
        JLabel lblLogoGrammer = new JLabel("");
        lblLogoGrammer.setBounds(500, 55, 194, 114);
        panelDentro.add(lblLogoGrammer);
        lblLogoGrammer.setIcon(new ImageIcon(Confirmaciones.class.getResource("/com/grammer/code/img/Grammer.png")));
        
        panelPrincipal.add(txtFecha);
        txtUsuario.setBounds(20, 24, 151, 22);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsuario.setColumns(10);
        txtUsuario.setText(usuario);
        txtUsuario.setBorder(whiteBorder);
        txtUsuario.setForeground(colorBlanco);
        txtUsuario.setOpaque(false);
        txtUsuario.setBorder(BorderFactory.createEmptyBorder());
        txtUsuario.setEditable(false); 
        panelPrincipal.add(txtUsuario);

        txtTurno.setBounds(20, 50, 118, 22);
        txtTurno.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtTurno.setColumns(10);
        txtTurno.setText(turno);
        txtTurno.setBorder(whiteBorder);
        txtTurno.setForeground(colorBlanco);
        txtTurno.setOpaque(false);
        txtTurno.setBorder(BorderFactory.createEmptyBorder());
        txtTurno.setEditable(false); 
        panelPrincipal.add(txtTurno);
        
        
        JButton btnSalir = new JButton("X");
        btnSalir.setHorizontalAlignment(SwingConstants.RIGHT);
        btnSalir.setBounds(685, 20, 46, 29);
        btnSalir.setFont(fuentePrincipalBold);
        btnSalir.setForeground(colorBlanco);
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
        panelPrincipal.add(btnSalir);
        panelDatos.setLayout(null);
        panelDatos.setBackground(colorBlanco);
        JLabel lblNumDeParte = new JLabel("Número de parte");
        lblNumDeParte.setBounds(12, 35, 175, 27);
        panelDatos.add(lblNumDeParte);
        lblNumDeParte.setForeground(colorPrincipal);
        lblNumDeParte.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumDeParte.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JLabel lblCantidad = new JLabel("Cantidad");
        lblCantidad.setBounds(22, 105, 91, 36);
        panelDatos.add(lblCantidad);
        lblCantidad.setForeground(colorPrincipal);
        lblCantidad.setHorizontalAlignment(SwingConstants.CENTER);
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 20));
        NumP.requestFocus();
        NumP.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        NumP.setForeground(colorPrincipal);
        NumP.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	NumP.setBackground(blancoGrisaseo); // Cambia al color blanco grisáceo al ganar el foco
            }

            @Override
            public void focusLost(FocusEvent e) {
            	NumP.setBackground(colorBlanco); // Cambia de nuevo al color original cuando pierde el foco
                
            }
        });
        // Establece un borde personalizado para el componente NumP
        NumP.setBorder(BorderFactory.createMatteBorder(
            0, // Grosor del borde superior (sin borde)
            0, // Grosor del borde izquierdo (sin borde)
            3, // Grosor del borde inferior (3 píxeles de grosor)
            0, // Grosor del borde derecho (sin borde)
            new Color(0, 81, 149) 
        ));
        NumP.setColumns(10);
        Quantity.setBounds(199, 100, 154, 36);
        panelDatos.add(Quantity);
        
        Quantity.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                  /*if(JOptionPane.showConfirmDialog(null, 
                		  "¿Confirma que los datos son correctos: \n Núm. de parte = " + 
                			NumP.getText().replace("P", "").replace("W", "").trim() +
                			" \n Cantidad = " + Quantity.getText().replace("Q", "").trim() + "?", 
                			"WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {*/
                	  
                	  			configuracionesProceso();
                	  			String[] resultado = procesarEntradas(NumP.getText(), Quantity.getText());

                	  			if (resultado != null) {
                	  		        String numParteProcesado = resultado[0];
                	  		        String cantidadProcesada = resultado[1];
                	  		        
                	  		        // Llamar a la función filtros con los valores procesados
                	  		        filtros(usuario, numParteProcesado, cantidadProcesada);
                	  		        
                	  		    //} 
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        Quantity.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        Quantity.setForeground(colorPrincipal);
        Quantity.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, colorPrincipal));
        Quantity.setColumns(10);
        
        
     // Listener para cambiar el color de fondo al tener el foco
        Quantity.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Quantity.setBackground(blancoGrisaseo); // Cambia al color blanco grisáceo al ganar el foco
               
            }

            @Override
            public void focusLost(FocusEvent e) {
                Quantity.setBackground(colorBlanco); // Cambia de nuevo al color original cuando pierde el foco
                
            }
        });
        NumP.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Quantity.requestFocus();
                }
            }
        });
        
           // Agrega un KeyListener para manejar eventos de teclado en NumP
           NumP.addKeyListener(new KeyAdapter() {
               @Override
               public void keyPressed(KeyEvent e) {
                   // Verifica si la tecla presionada es Enter
                   if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                       // Si es Enter, solicita el foco en el componente Quantity
                       Quantity.requestFocus();
                   }
               }
           });
        
        if (ping.validaPing("10.83.21.12")) {
        	imgWifi.setVisible(true);
        	imgWifiError.setVisible(false);
        } else {
        	imgWifi.setVisible(false);
        	imgWifiError.setVisible(true);
            errorRed.setText("ERROR DE RED");
        }
    }
    
    public void configuracionesProceso() {
    	NumP.setEditable(false);
        Quantity.setEditable(false);
        NumP.setEnabled(false);
        Quantity.setEnabled(false);
        btnOk.setVisible(false);
        panel.setBackground(colorProceso);
    }
    
    public void interrupcionProceso() {
    	NumP.setText("");
    	Quantity.setText("");
    	NumP.setEditable(true);
        Quantity.setEditable(true);
        NumP.setEnabled(true);
        Quantity.setEnabled(true);
        btnOk.setVisible(true);
        panel.setBackground(colorPrincipal);
    }
    
    public String[] procesarEntradas(String numParte, String cantidad) {
    	panelPrincipal.setBackground(new Color(0, 81, 149));
        String[] resultado = new String[2];

        // Validar campos vacíos
        if (numParte.isEmpty() || cantidad.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Campos vacíos.", "Error", JOptionPane.WARNING_MESSAGE);
            lblProgressBar.setVisible(false);
            interrupcionProceso();
            return null;
        }

        // Procesar cantidad
        String cantidadProcesada;
        String numParteProcesado;
        if (cantidad.matches("Q[^Q]*") && (numParte.matches("P[^P]*") || numParte.matches("W[^W]*"))) {
            cantidadProcesada = cantidad.replace("Q", "").trim();
            numParteProcesado = numParte.replace("'", "-").replace("P", "").replace("W", "").trim().toUpperCase();
            
            // Validar que cantidadProcesada solo contiene números enteros del 0 al 9
            if (!cantidadProcesada.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Cantidad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                lblProgressBar.setVisible(false);
                interrupcionProceso();
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            lblProgressBar.setVisible(false);
            interrupcionProceso();
            return null;
        }

        // Asignar resultados procesados
        resultado[0] = numParteProcesado;
        resultado[1] = cantidadProcesada;

        return resultado;
    }

    /*---------------------------Ingreso de los datos de los filtros y envio de información-------------------------------------*/
    public void filtros(String usuario, String numParte, String quantity) {
        // Hacer visible la barra de progreso y el label
    	lblProgressBar.setText("Confirmación en proceso, por favor espere...");
    	lblProgressBar.setForeground(colorPrincipal);
    	progressBar.setVisible(true);
        lblProgressBar.setVisible(true);
        error.setVisible(false);
       

        // Crear y ejecutar el SwingWorker
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            private boolean success = false; // Indica si todo el proceso fue exitoso
            String errorSAP = "";

            @Override
            protected Void doInBackground() throws Exception {
                try {
                	
                    publish(20);
                    
                    // Paso 1: Consulta de número de parte en la BD
                    MaterialCostumerRes materialCostumerRes = new MaterialCostumerRes();
                    //Object numeroParteCorrecto = materialCostumerRes.consultarNumero(numParte);
                    String numeroParteCorrecto = numParte; // Simulación de la consulta
                    if (numeroParteCorrecto == null) {
                    	mostrarError("No se encontró el número de parte en la base de datos.", "Error en Núm. Parte");
                    	success = false;
                        interrupcionProceso();
                        return null;
                    }//fin if (numeroParteCorrecto == null) 
                    publish(30);

                    // Paso 2: Obtención de la fecha y hora actuales
                    String fechaTime = String.valueOf(new Date().getTime());
                    Date fechaNormal = new Date();
                    if (fechaTime == null || fechaNormal == null) {
                    	mostrarError("Fallo en la obtención de fechas.", "Error");
                        success = false;
                        interrupcionProceso();
                        return null;
                    }//if (fechaTime == null || fechaNormal == null)
                    publish(40);
                    

                    // Paso 3: Determinación del turno
                    TurnoUtil.TurnoInfo info = TurnoUtil.determinarTurno();
                    String turno = info.getTurno();
                    int horas = info.getHoras();
                    int minutos = info.getMinutos();
                    String horaConMin = horas +""+ minutos;
                    int horaActual = Integer.parseInt(horaConMin);
                    
                    String hostname = info.getHostname();
                    if (turno == null ||  horas < 0 || horas > 23 || hostname == null) {
                    	mostrarError("Fallo en la obtención de hostname.", "Error");
                        success = false;
                        interrupcionProceso();
                        return null;
                    }// fin if (turno == null ||  horas < 0 || horas > 23 || hostname == null) 
                    HoraXHora horaXHora = new HoraXHora();
                    String horasCalculadas = horaXHora.getHora(horas, minutos,turno);
                    
                    publish(50);
                        
                    // Paso 4: Confirmación en SAP
                    CorridaJacob corridaJacob = new CorridaJacob();
                    String sapMessage = corridaJacob.guardarRegistroSAP(numeroParteCorrecto, quantity, fechaTime);
                    
                    publish(60);
                    
                    if(sapMessage.startsWith("Error")) {
                    	errorSAP = procesarErrorSAP(sapMessage);
                    	mostrarError(errorSAP, "ERROR SAP");
                        success = false;
                        interrupcionProceso();
                        return null;
                    }else {
                        
                    	String numConfSap = procesarConfirmacion(sapMessage);
                    	publish(70);
                    	
                    	if(numConfSap.startsWith("Error")) {
                    		errorSAP = procesarErrorSAP(numConfSap);
                        	mostrarError(errorSAP, "ERROR SAP");
                            success = false;
                            interrupcionProceso();
                            return null;
                            
                    	}else {
                    		//Validar que el numero de confirmacion no exista
                    		ApprovalDao aprovalDao = new ApprovalDao();
                    		ApprovalEntity confirmacionAux = new ApprovalEntity();
                    		confirmacionAux = aprovalDao.verificarNumConfirmacion(numConfSap);
                        	
                    		if(confirmacionAux == null) {
                    			System.out.println("numConfSap;"+numConfSap);
                    			publish(80);
                    			String idGenerado = java.util.UUID.randomUUID().toString(); // Generar el UUID
                    			
                    			// Paso 5: Configuración de la entidad y guardado
                                confirmacionSolicitada = new ApprovalEntity();
                                confirmacionSolicitada.setLiga(fechaTime);
                                confirmacionSolicitada.setNumP(numeroParteCorrecto);
                                confirmacionSolicitada.setFechaIns(fechaNormal);
                                confirmacionSolicitada.setTurno(turno);
                                confirmacionSolicitada.setUsuario(usuario);
                                confirmacionSolicitada.setId(idGenerado);
                                confirmacionSolicitada.setCantidad(quantity);
                                confirmacionSolicitada.setComputadora(hostname);
                                confirmacionSolicitada.setNumConfirmacion(numConfSap);
                                confirmacionSolicitada.setHoras(horasCalculadas);
                                System.out.println("numConfSap;"+numConfSap);
                                daoConfirmacion.crearApproval(confirmacionSolicitada);
                                
                                if (daoConfirmacion == null) {
                                    String errorBD = "Error al registrar en la base de datos. Capturar datos: \n" +
                                		    			"Liga: " + fechaTime + "\n" +
                                		    			"No. Parte: " + numeroParteCorrecto + "\n" +
                                		    			"Fecha: " + fechaNormal + "\n" +
                                		    			"Turno: " + turno + "\n" +
                                		    			"Usuario: " + usuario + "\n" +
                                		    			"Cantidad: " + quantity + "\n" +
                                		    			"Computadora: " + hostname + "\n" +
                                		    			"Número de Confirmación: " + numConfSap + "\n" +
                                		    			"ID: " + idGenerado;
                                    
                                	mostrarError(errorBD, "ERROR BD");
                                    success = false;
                                    interrupcionProceso();
                                    return null;
                                }else {
                                	publish(90);

                                     // Paso 6: Actualización historial
                                     cargarDatosEnTabla(model, usuario, hostname,fechaNormal,turno,horaActual);
                                     lblProgressBar.setText("Confirmación exitosa. Documento SAP: " + numConfSap);
                                     lblProgressBar.setForeground(colorVerde);
                                     panelPrincipal.setBackground(colorVerde);
                                     
                                     configurarTablaPalets(panelPalets,usuario, hostname,fechaNormal, turno, horaActual);
                                     
                                     interrupcionProceso();
                                     publish(100);
                                     
                                     mostrarDocSAP(numConfSap);
                                     
                             	}//fin if (daoConfirmacion == null)
                    			
                    		}else {
                    			errorSAP = "Error SAP: Es necesario reiniciar SAP.";
                            	mostrarError(errorSAP, "ERROR SAP");
                                success = false;
                                interrupcionProceso();
                                return null;
                    		}// fin if(confirmacionAux == null)
                    	}// fin if(numConfSap.startsWith("Error"))
                    }// fin  if(sapMessage.startsWith("Error"))
                } catch (Exception e) {
                	mostrarError(e.getMessage(), "Exception");
                    success = false;
                    interrupcionProceso();
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                for (Integer chunk : chunks) {
                    progressBar.setValue(chunk);
                }
            }

            @Override
            protected void done() {
                if (success) {
                    JOptionPane.showMessageDialog(null, "Confirmación realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } // No se muestra mensaje si no se completó al 100%
            }
        };

        // Agregar un listener opcional para eventos de progreso
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress".equals(evt.getPropertyName())) {
                    int progress = (Integer) evt.getNewValue();
                    progressBar.setValue(progress);
                }
            }
        });

        // Ejecutar el worker
        worker.execute();
    }
    
    public void mostrarDocSAP(String docSAP) {
    	panelSAP.setVisible(true);
    	panelDatos.setVisible(false);
    	txtDocSAP.setText(docSAP);
    	 SwingUtilities.invokeLater(new Runnable() {
    	        public void run() {
    	            if (panelSAP.isShowing()) {
    	                NumP.requestFocusInWindow();  
    	            }
    	        }
    	    });
    }
    
    public void ocultarDocSAP() {
    	panelSAP.setVisible(false);
    	panelDatos.setVisible(true);
    	txtDocSAP.setText("");
    	panelPrincipal.setBackground(colorPrincipal);
    	NumP.requestFocus();
    	
    }
    
    
    public String procesarErrorSAP(String numConfSap) {
    	String errorSAP="";
    	if (numConfSap.contains("80010108 / The object invoked has disconnected from its clients") 
                || numConfSap.contains("The enumerator of the collection cannot find an element with the specified index")
                || numConfSap.contains("Dispatch not hooked to windows memory")
                || numConfSap.contains("The control could not be found by id.")) {
    		
    		errorSAP = "Error SAP: No se inició sesión. \n Verifique e intente nuevamente.";

        }  else {
        	errorSAP = numConfSap;
        }
    
        return errorSAP;
    }
    
    public void mostrarError(String errorMsj, String encabezado) {
    	panelPrincipal.setBackground(colorError);
    	error.setText("<html>" + errorMsj + "</html>");
    	JOptionPane.showMessageDialog(null, errorMsj, encabezado, JOptionPane.ERROR_MESSAGE);
        error.setVisible(true);
        lblProgressBar.setVisible(false);
        
        // Iniciar el temporizador para volver al color original después de 10 segundos
        Timer timer = new Timer(10000, e -> {
            // Restaurar el color original del panel después de 10 segundos
            panelPrincipal.setBackground(colorPrincipal);
        });
        timer.setRepeats(false); // El temporizador solo se ejecuta una vez
        timer.start();
        
        // Llamar a la función que maneja la interrupción del proceso
        interrupcionProceso();
    }

    public String procesarConfirmacion(String sapMessage) {
        String numSAPConfirmacion = "";

        if (sapMessage.contains("does not exist") || sapMessage.contains("No existe") || sapMessage.contains("No extiste")) {
            numSAPConfirmacion = "Error SAP en numero de parte: \n" + sapMessage;
        } else {
        	
        	// Expresión regular para encontrar números precedidos por "document " o "documento "
        	String regex = "(?<=\\b(doc(?:umento)?\\.?\\s|document\\s))\\d+";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sapMessage);
            
            if (matcher.find()) {
                numSAPConfirmacion = matcher.group();
            } else {
                // Si no hay número que cumpla la condición, devuelve el mensaje completo
                numSAPConfirmacion = "Error SAP: " + sapMessage;
            }
        }

        return numSAPConfirmacion;
    }
    
    public void configurarTablaPalets(JPanel panel,String usuario,String hostname, Date fecha, String turno, int horas) {
    	JScrollPane scrollConfPalets = new JScrollPane();
    	scrollConfPalets.setBounds(486, 40, 212, 176);
    	scrollConfPalets.setBackground(colorBlanco);
    	scrollConfPalets.getViewport().setBackground(colorBlanco);
    	scrollConfPalets.setBorder(new EmptyBorder(0, 0, 0, 0)); 
    	
    	// Personalizar la barra de desplazamiento
    	JScrollBar verticalScrollBar = scrollConfPalets.getVerticalScrollBar();
    	verticalScrollBar.setPreferredSize(new Dimension(10, 0)); // Grosor de 10px
    	verticalScrollBar.setUI(new CustomScrollBarUI());


        panelHistorico.add(scrollConfPalets);

        Object[] columnP = {
                "Núm. Parte",
                "Pallets"
            };
        modelPalets.setColumnIdentifiers(columnP);
        // Cargar los datos en la tabla
        cargarDatosPalets(modelPalets, usuario,hostname, fecha, turno, horas);

        JTable tablePalets = new JTable(modelPalets);
        tablePalets.setEnabled(false);
        tablePalets.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tablePalets.setForeground(colorPrincipal);
        
        // Crear un margen/padding para las celdas
        tablePalets.setRowHeight(30); // Altura de la fila para acomodar el padding
        
        // Crear un renderizador para centrar el texto y a�adir padding
        DefaultTableCellRenderer centerRendererPalets = new DefaultTableCellRenderer();
        centerRendererPalets.setHorizontalAlignment(SwingConstants.CENTER);
        centerRendererPalets.setVerticalAlignment(SwingConstants.CENTER);
        
        // Configurar fondo blanco
        tablePalets.setBackground(colorBlanco);
        
        //encabezaDO
        JTableHeader header = tablePalets.getTableHeader();
        header.setBackground(colorBlanco);
        header.setForeground(colorPrincipal);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Fuente para el encabezado
        header.setBorder(BorderFactory.createLineBorder(colorPrincipal, 1));

        // Configurar borde de la tabla
        tablePalets.setBorder(new LineBorder(colorPrincipal, 1, true));
        
        // Aplicar el renderizador a todas las columnas
        for (int i = 0; i < columnP.length; i++) {
            tablePalets.getColumnModel().getColumn(i).setCellRenderer(centerRendererPalets);
        }
        
        scrollConfPalets.setViewportView(tablePalets);
    }
    
    public void cargarDatosPalets(DefaultTableModel modelP,String usuario, String hostname, Date fecha, String turno, int horas) {
    	if (modelP.getRowCount() != 0) {
    		modelP.setRowCount(0);
    	}

        ApprovalDao appDao = new ApprovalDao();
        List<Object> resultados = appDao.contLineaDos(usuario, hostname, turno, horas);

        // Iterar sobre los resultados para agregar filas a la tabla
        for (int i = 0; i < resultados.size(); i += 2) {
            if (i + 1 < resultados.size()) {  // Asegúrate de que i + 1 está dentro de los límites
                Object numParte = resultados.get(i);     // Primer valor: número de parte
                Object contador = resultados.get(i + 1); // Segundo valor: cantidad

                // Añadir la fila al modelo de la tabla
                modelP.addRow(new Object[]{numParte, contador});
            } else {
                // Manejo de error o aviso si hay un número impar de elementos
                System.out.println("Resultado incompleto en el índice: " + i);
            }
        }
    }


    
    public void configurarTablaHistorico(JPanel panelRegistros, String idUser, Date varFeC, String turno, int horas) {
    	JScrollPane scrollBitacora = new JScrollPane();
    	scrollBitacora.setBounds(20, 40, 450, 176);
    	scrollBitacora.setBackground(colorBlanco);
    	scrollBitacora.getViewport().setBackground(colorBlanco);
    	scrollBitacora.setBorder(new EmptyBorder(0, 0, 0, 0)); 
    	
    	// Personalizar la barra de desplazamiento
    	JScrollBar verticalScrollBar = scrollBitacora.getVerticalScrollBar();
    	verticalScrollBar.setPreferredSize(new Dimension(10, 0)); // Grosor de 10px
    	verticalScrollBar.setUI(new CustomScrollBarUI());


        panelHistorico.add(scrollBitacora);

        Object[] columnD = {
            "Turno",
            "Usuario",
            "Hora",
            "ID",
            "Núm. Parte",
            "Confirmación SAP",
            "Cantidad"
        };
        model.setColumnIdentifiers(columnD);

        JTable tableHistorico = new JTable(model);
        tableHistorico.setEnabled(false);
        tableHistorico.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tableHistorico.setForeground(colorPrincipal);
        
        // Crear un renderizador para centrar el texto y a�adir padding
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);
        // Crear un margen/padding para las celdas
        tableHistorico.setRowHeight(30); // Altura de la fila para acomodar el padding
        
        // Ocultar columnas específicas
        ocultarColumna(tableHistorico, 0); // Ocultar columna 0 (Turno)
        ocultarColumna(tableHistorico, 1); // Ocultar columna 1 (Usuario)
        ocultarColumna(tableHistorico, 3); // Ocultar columna 3 (ID)
        
        //tamaño columna
        dimensionarColumna(tableHistorico, 2, 70);
        dimensionarColumna(tableHistorico, 5, 180);
        
        // Configurar fondo blanco
        tableHistorico.setBackground(colorBlanco);
        
        //encabezaDO
        JTableHeader header = tableHistorico.getTableHeader();
        header.setBackground(colorBlanco);
        header.setForeground(colorPrincipal);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Fuente para el encabezado
        header.setBorder(BorderFactory.createLineBorder(colorPrincipal, 1));

        // Configurar borde de la tabla
        tableHistorico.setBorder(new LineBorder(colorPrincipal, 1, true));
        
        // Cargar los datos en la tabla
        cargarDatosEnTabla(model,idUser, hostname, varFeC, turno, horas);
        
        // Aplicar el renderizador a todas las columnas
        for (int i = 0; i < tableHistorico.getColumnCount(); i++) {
            tableHistorico.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollBitacora.setViewportView(tableHistorico);
    }

    // Método auxiliar para ocultar una columna en una JTable
    private void ocultarColumna(JTable table, int columnIndex) {
        table.getColumnModel().getColumn(columnIndex).setMaxWidth(0);
        table.getColumnModel().getColumn(columnIndex).setMinWidth(0);
        table.getColumnModel().getColumn(columnIndex).setPreferredWidth(0);
        table.getColumnModel().getColumn(columnIndex).setResizable(false);
    }
    
    
   private void dimensionarColumna(JTable table, int columnIndex, int width) {
        table.getColumnModel().getColumn(columnIndex).setMaxWidth(width);
        table.getColumnModel().getColumn(columnIndex).setMinWidth(width);
        table.getColumnModel().getColumn(columnIndex).setPreferredWidth(width);
        table.getColumnModel().getColumn(columnIndex).setResizable(false);
    }


    private void cargarDatosEnTabla(DefaultTableModel modelPrincipal,String usuario, String hostame, Date varFeC, String turno, int horas) {
    	
    	if (modelPrincipal.getRowCount() != 0) {
    		modelPrincipal.setRowCount(0);
    	}
    	
    	ApprovalDao daoApproval = new ApprovalDao();
        Object[] row = new Object[7];
        
        // Crear un formateador de fecha para obtener solo la hora
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        for (ApprovalEntity a : daoApproval.consultar(usuario,hostame,varFeC,turno,horas)) {
            row[0] = a.getTurno();
            row[1] = a.getUsuario();
            row[2] = timeFormat.format(a.getFechaIns()); 
            row[3] = a.getId();
            row[4] = a.getNumP();
            row[5] = a.getNumConfirmacion();
            row[6] = a.getCantidad();
            modelPrincipal.addRow(row);
        }
    }
}