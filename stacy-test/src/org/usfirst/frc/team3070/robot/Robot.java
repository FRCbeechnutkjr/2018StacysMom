/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;


//fuckingotem
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	final int PORT_LM = 4;
	final int PORT_LF = 5;
	final int PORT_RM = 3;
	final int PORT_RF = 6;
	final int PORT_JL = 1;
	final int PORT_JR = 0;
	
	TalonSRX TalLM = new TalonSRX(PORT_LM);
	TalonSRX TalLF = new TalonSRX(PORT_LF);
	TalonSRX TalRM = new TalonSRX(PORT_RM);
	TalonSRX TalRF = new TalonSRX(PORT_RF);

	
	Joystick JoyL = new Joystick(PORT_JL);
	Joystick JoyR = new Joystick(PORT_JR);
	
	double JoyRAmount;
	double JoyLAmount;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		TalLM.setInverted(false);
		TalLF.setInverted(false);
		TalRM.setInverted(true);
		TalRF.setInverted(true);	
		
		TalLM.setNeutralMode(NeutralMode.Coast);
		TalLF.setNeutralMode(NeutralMode.Coast);
		TalRM.setNeutralMode(NeutralMode.Coast);
		TalRF.setNeutralMode(NeutralMode.Coast);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		if(Math.abs(JoyR.getRawAxis(1)) >= .2) {
			setRight(0-(JoyR.getRawAxis(1)) * (0 - ((JoyR.getRawAxis(2) / 2) + 1)));
		}
		else {
			setRight(0);
		}
		if(Math.abs(JoyL.getRawAxis(1)) >= .2) {
			setLeft(0-(JoyL.getRawAxis(1)) * (0 - ((JoyL.getRawAxis(2) / 2) + 1)));
		}
		else {
			setLeft(0);
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	private void setRight(double amount) {
		TalRM.set(ControlMode.PercentOutput, amount);
		TalRF.set(ControlMode.Follower, PORT_RM);
	}
	private void setLeft(double amount) {
		TalLM.set(ControlMode.PercentOutput, amount);
		TalLF.set(ControlMode.Follower, PORT_LM);
	}
}
