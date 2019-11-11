package frc.team5115.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;

public class Robot extends TimedRobot {

    private Ultrasonic front, back;

    double DANGER_ZONE = 4;
    double throttle = 1;

    private TalonSRX frontleft, frontright, backleft, backright;

    private Joystick joystick;

    public void robotInit() {
        front = new Ultrasonic(0,1);
        back = new Ultrasonic(2,3);

        frontleft = new TalonSRX(0);
        frontright = new TalonSRX(1);
        backleft = new TalonSRX(2);
        backright = new TalonSRX(3);

        joystick = new Joystick(0);
    }

    public void robotPeriodic() {
        System.out.println("Front: " + front.getRangeInches());
        System.out.println("Back: " + back.getRangeInches());

        if (DriverStation.getInstance().isEnabled()) {
            if (!inRange()) {
                drive(joystick.getRawAxis(1), joystick.getRawAxis(0), throttle);
            }
            else {
                drive(0,0,0);
            }
        }
    }

    private boolean inRange() {
        return (front.getRangeInches() < DANGER_ZONE) || (back.getRangeInches() < DANGER_ZONE);
    }

    private void drive(double x, double y, double t) {
        double leftSpd = (y-x)*t;
        double rightSpd = (y+x)*t;

        frontleft.set(ControlMode.PercentOutput, leftSpd);
        frontright.set(ControlMode.PercentOutput, rightSpd);
        backleft.set(ControlMode.PercentOutput, leftSpd);
        backright.set(ControlMode.PercentOutput, rightSpd);
    }
}


