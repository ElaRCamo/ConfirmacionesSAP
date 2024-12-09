package com.grammer.code.frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.grammer.code.dao.ScriptDao;
import com.grammer.code.entity.ScriptEntity;

import java.awt.Label;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TimeZone;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Administrador extends JFrame {

	private static final long serialVersionUID = 1L;
	JTextArea area = new JTextArea();
	ScriptEntity ent = new ScriptEntity();
	ScriptDao dao = new ScriptDao();
	JFrame regisFrame;
	Color colorPrincipal = new Color(0, 81, 149);
    Color colorError = new Color(255, 0, 20);
    Color colorAviso = new Color(50,130,199);
    Color colorBlanco = new Color(250,251,253);

	public Administrador() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-6"));
		setTitle("Confirmaciones SAP | Guardar script");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/grammer/code/img/chrecking.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 740, 550);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(colorPrincipal);
		panel.setBounds(0, 0, 734, 521);
		contentPane.add(panel);
		panel.setLayout(null);
		
		Label label = new Label("INGRESAR EL SCRIPT");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Segoe UI", Font.BOLD, 20));
		label.setAlignment(Label.CENTER);
		label.setBounds(257, 10, 217, 39);
		panel.add(label);
		
		JButton Guardar = new JButton("GUARDAR");
		Guardar.setBackground(Color.WHITE);
		Guardar.setForeground(colorPrincipal);
		Guardar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		Guardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ent.setScript(area.getText());
				ent.setId("2");
				dao.updateScriptU(ent);
				
				JOptionPane.showMessageDialog(panel, "Actualización realizada exitosamente");
				
			}
		});
		Guardar.setBounds(317, 437, 124, 40);
		panel.add(Guardar);
		area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		area.setWrapStyleWord(true);
		
		JScrollPane sBar = new JScrollPane(area);
		sBar.setBounds(21, 71, 686, 344);
		panel.add(sBar);
		
		JButton btnRegresar = new JButton("REGRESAR");
		btnRegresar.setBackground(Color.WHITE);
		btnRegresar.setForeground(colorPrincipal);
		btnRegresar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				entradaA();
				dispose();
			}
		});
		btnRegresar.setBounds(604, 469, 100, 23);
		panel.add(btnRegresar);
		
		for(ScriptEntity ent : dao.getScript()) {
			area.setText(ent.getScript());
		}
		
	}
	public void entradaA() {
		regisFrame = new UsuarioAg();
		regisFrame.setLocationRelativeTo(null);
		regisFrame.setResizable(false);
		regisFrame.setVisible(true);
		
	}
}
