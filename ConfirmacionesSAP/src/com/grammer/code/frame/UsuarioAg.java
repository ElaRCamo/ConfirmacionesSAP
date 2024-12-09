package com.grammer.code.frame;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import com.grammer.code.dao.PUserHorasDao;
import com.grammer.code.entity.UserEntity;
import com.grammer.code.frame.Administrador;
import com.grammer.code.util.Datos;
import com.grammer.code.util.TextBubbleBorder;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.TimeZone;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Insets;

public class UsuarioAg extends JFrame {
	
    private static final long serialVersionUID = 1L;
    final JPanel contentPane;
    JTextField txtClave = new JTextField();
    JTextField txtUsuario = new JTextField();
    JFrame regisFrame;
    boolean cerrarFrame;
    private static final JLabel grammer = new JLabel();
    String fifoUUID = java.util.UUID.randomUUID().toString();
    Timer timer;
    Color colorPrincipal = new Color(0, 81, 149);
    Color colorError = new Color(255, 0, 20);
    Color colorAviso = new Color(50,130,199);
    Color colorBlanco = new Color(250,251,253);
    private static final JLabel agregar = DefaultComponentFactory.getInstance().createTitle("");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UsuarioAg frame = new UsuarioAg();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UsuarioAg() {
    	TimeZone.setDefault(TimeZone.getTimeZone("GMT-6"));
    	setTitle("Confirmaciones | Agregar usuario");
    	setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/grammer/code/img/chrecking.png"))); //ícono de la ventana
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 748, 584);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(colorPrincipal);
        panel.setBounds(0, 0, 742, 549);
        contentPane.add(panel);
        panel.setLayout(null);

        AbstractBorder brdrRight = new TextBubbleBorder(Color.BLACK, 2, 16, 16, false);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        txtUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                txtClave.requestFocus();
            }
        });
        txtUsuario.setBounds(64, 196, 600, 71);
        txtUsuario.setBorder(brdrRight);
        panel.add(txtUsuario);
        txtClave.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtClave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agregar();
            }
        });

        txtClave.setBounds(64, 336, 600, 71);
        txtClave.setBorder(brdrRight);
        panel.add(txtClave);

        JLabel lblTitulo = DefaultComponentFactory.getInstance().createTitle("AGREGAR NUEVO USUARIO");
        lblTitulo.setBackground(SystemColor.controlHighlight);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(255, 255, 255));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblTitulo.setBounds(0, 77, 481, 43);
        panel.add(lblTitulo);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setMargin(new Insets(2, 2, 2, 2));
        menuBar.setForeground(Color.WHITE);
        menuBar.setBackground(colorPrincipal);
        menuBar.setBounds(0, 0, 86, 40);
        panel.add(menuBar);

        JMenu mnNewMenu = new JMenu("Men\u00FA");
        mnNewMenu.setHorizontalAlignment(SwingConstants.CENTER);
        mnNewMenu.setBackground(colorPrincipal);
        mnNewMenu.setForeground(Color.WHITE);
        mnNewMenu.setFont(new Font("Segoe UI", Font.BOLD, 20));
        menuBar.add(mnNewMenu);
        menuBar.setVisible(false);

        JButton btnScript = new JButton("Script");
        btnScript.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	if (null != regisFrame && cerrarFrame) {
                    regisFrame.setVisible(false);
                }
                entradaAdministrador();
                dispose();
            }
        });
        btnScript.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnScript.setForeground(Color.WHITE);
        btnScript.setBounds(294, 450, 100, 40);
        btnScript.setBackground(colorPrincipal);
        mnNewMenu.add(btnScript);
        btnScript.setVisible(false);

        JButton btnReturn = new JButton("Return");
        btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				entradaLogin();
				dispose();
			}
		});
        btnReturn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnReturn.setForeground(Color.WHITE);
        btnReturn.setBounds(294, 450, 100, 40);
        btnReturn.setBackground(colorPrincipal);
        mnNewMenu.add(btnReturn);

        JLabel lblUsuario = DefaultComponentFactory.getInstance().createLabel("USUARIO");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblUsuario.setForeground(new Color(255, 255, 255));
        lblUsuario.setBounds(72, 162, 315, 21);
        panel.add(lblUsuario);

        JLabel lblContrasea = DefaultComponentFactory.getInstance().createLabel("CONTRASE\u00D1A");
        lblContrasea.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblContrasea.setForeground(Color.WHITE);
        lblContrasea.setBounds(64, 300, 315, 21);
        panel.add(lblContrasea);

        grammer.setVerticalAlignment(SwingConstants.TOP);
        grammer.setHorizontalAlignment(SwingConstants.CENTER);
        grammer.setLocation(269, 0);
        grammer.setSize(235, 53);
        panel.add(grammer);
        
        JLabel logoGrammer = new JLabel("");
        logoGrammer.setVerticalAlignment(SwingConstants.TOP);
        logoGrammer.setIcon(new ImageIcon(UsuarioAg.class.getResource("/com/grammer/code/img/logoBlanco.png")));
        logoGrammer.setBounds(527, 39, 137, 81);
        panel.add(logoGrammer);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		agregar();
        		JOptionPane.showMessageDialog(panel, "Usuario creado exitosamente");
        	}
        });
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnGuardar.setForeground(colorPrincipal);
        btnGuardar.setBounds(294, 450, 131, 43);
        btnGuardar.setBackground(Color.WHITE);
        panel.add(btnGuardar);
        
        
        agregar.setForeground(Color.WHITE);
        agregar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        agregar.setBounds(64, 506, 600, 30);
        panel.add(agregar);

        timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregar.setText("");
                agregar.setVisible(false);
            }
        });
        timer.setRepeats(false);
    }

    public void agregar() {
        try {
			if (txtUsuario.getText().length() > 0) {
                PUserHorasDao dao = new PUserHorasDao();

                if (dao.getAccesoUser(txtUsuario.getText()) != null) {
                    agregar.setText("Error: el usuario ya existe.");
                    txtUsuario.setText("");
                    txtClave.setText("");
                } else {
                    if (txtClave.getText().length() > 0) {

                        UserEntity ents = new UserEntity();
                        fifoUUID = java.util.UUID.randomUUID().toString(); // identificador único
                        ents.setId(fifoUUID);
                        ents.setIdUsuario(txtUsuario.getText().toUpperCase());
                        ents.setPassword(txtClave.getText());
                        dao.createUserHorasEntity(ents);

                        txtUsuario.setText("");
                        txtClave.setText("");
                        txtUsuario.requestFocus();
                        agregar.setText("Usuario agregado éxitosamente.");
                        timer.start();
                    }
                }
            } else {
                agregar.setText("Favor de ingresar los datos del usuario.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void entradaAdministrador() {
        System.out.println("Entrada a Administrador");
        regisFrame = new Administrador();
		regisFrame.setLocationRelativeTo(null);
		regisFrame.setResizable(false);
		regisFrame.setVisible(true);
    }

    public void entradaLogin() {
        System.out.println("Entrada a login");
        regisFrame = new Login();
        regisFrame.setLocationRelativeTo(null);
        regisFrame.setResizable(false);
        regisFrame.setVisible(true);
    }
}