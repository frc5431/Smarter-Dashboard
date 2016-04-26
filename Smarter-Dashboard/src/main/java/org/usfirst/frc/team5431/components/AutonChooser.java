package main.java.org.usfirst.frc.team5431.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.org.usfirst.frc.team5431.SmarterDashboard;

public class AutonChooser {
	// 0 - What to cross first
	// 1 - degress to turn
	// 2 - distance to drive forward or backword
	// 3 auto shoot (true or false)

	private enum FIRST_AUTO_STEP {
		RoughTerrain,Rockwall,Lowbar;
	}

	private static final double[] output = new double[4];

	private static void refreshValues() {
		SmarterDashboard.table.putNumberArray("AUTO-STEP-SELECT", output);
	}
	// AUTO-STEP-SELECT

	public static void create() {
		final JFrame f = new JFrame("Create your own auton");
		f.setSize(200, 500);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setVisible(true);

		final JLabel firststeptitle = new JLabel("First step");
		firststeptitle.setBounds(0, 0,100, 25);
		f.add(firststeptitle);
		final JComboBox<?> firststepchooser = new JComboBox<FIRST_AUTO_STEP>(FIRST_AUTO_STEP.values());
		firststepchooser.setBounds(0, 25, 100, 25);
		firststepchooser.setToolTipText("Where to cross first");
		firststepchooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				output[0] = firststepchooser.getSelectedIndex();
				refreshValues();
				// put it into network table
			}

		});
		f.add(firststepchooser);

		final JLabel degreesturntitle = new JLabel("Degrees turn");
		degreesturntitle.setBounds(0, 50,100, 25);
		f.add(degreesturntitle);
		final JSpinner degreesturn = new JSpinner(new SpinnerNumberModel(45, -360, 360, 5));
		degreesturn.setBounds(0, 75, 100, 25);
		degreesturn.setToolTipText("Degrees to turn");
		degreesturn.setEnabled(true);
		degreesturn.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// put it into network table
				output[1] = (int) degreesturn.getValue();
				refreshValues();
			}

		});
		f.add(degreesturn);

		final JLabel drivedistancetitle = new JLabel("Inches drive");
		drivedistancetitle.setBounds(0, 100,100, 25);
		f.add(drivedistancetitle);
		final JSpinner drivedistance = new JSpinner(new SpinnerNumberModel(55, -400, 400, 10));
		drivedistance.setBounds(0, 125, 100, 25);
		drivedistance.setToolTipText("Inches to drive");
		drivedistance.setEnabled(true);
		drivedistance.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// put it into network table
				output[2] = (int) drivedistance.getValue();
				refreshValues();
			}

		});
		f.add(drivedistance);

		final JLabel autoshoottitle = new JLabel("Auto shoot?");
		autoshoottitle.setBounds(0, 150,100, 25);
		f.add(autoshoottitle);
		final JCheckBox autoshoot = new JCheckBox("Auto shoot?");
		autoshoot.setBounds(0, 175, 100, 25);
		autoshoot.setToolTipText("Auto shoot");
		autoshoot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// put it into network tables
				output[3] = autoshoot.isSelected() ? 1 : 0;
				refreshValues();
			}

		});
		f.add(autoshoot);

		f.setSize(400, 501);
		f.repaint();

	}

}
