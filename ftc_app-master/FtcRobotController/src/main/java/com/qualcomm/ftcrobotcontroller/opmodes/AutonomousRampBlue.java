package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by rkhaj on 11/14/2015.
 */
public class AutonomousRampBlue extends LinearOpMode{

    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    Servo lockServos;
    DcMotor armMotor;
    DcMotor slideMotor;

    public static final double LOCK_SERVO_CLOSED_POSITION = 0.99;

    public void moveForward(double power, long time) throws InterruptedException{
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rbMotor.setPower(power);
        rfMotor.setPower(power);
        sleep(time);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
        rbMotor.setPower(0);
        rfMotor.setPower(0);
        sleep(500);
    }

    public void turnRight(double power, long time) throws InterruptedException{
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rbMotor.setPower(-power);
        rfMotor.setPower(-power);
        sleep(time);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
        rbMotor.setPower(0);
        rfMotor.setPower(0);
        sleep(500);
    }

    public void turnLeft(double power, long time) throws InterruptedException{
        lfMotor.setPower(-power);
        lbMotor.setPower(-power);
        rbMotor.setPower(power);
        rfMotor.setPower(power);
        sleep(time);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
        rbMotor.setPower(0);
        rfMotor.setPower(0);
        sleep(500);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        slideMotor = hardwareMap.dcMotor.get("pulley1");
        lockServos = hardwareMap.servo.get("lockServos");

        lbMotor.setDirection(DcMotor.Direction.REVERSE);
        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setDirection(DcMotor.Direction.REVERSE);

        waitOneFullHardwareCycle();
        waitForStart();

        moveForward(0.75, 1800);
        turnLeft(0.75, 945);

        /*armMotor.setPower(0.25);
        sleep(350);
        armMotor.setPower(0);*/

        /*slideMotor.setPower(0.75);
        sleep(2000);
        slideMotor.setPower(0);*/

        lfMotor.setPower(-0.75);
        lbMotor.setPower(-0.75);
        rbMotor.setPower(-0.75);
        rfMotor.setPower(-0.75);
        sleep(4000);
        /*lockServos.setPosition(LOCK_SERVO_CLOSED_POSITION);
        sleep(500);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
        rbMotor.setPower(0);
        rfMotor.setPower(0);*/
    }
}