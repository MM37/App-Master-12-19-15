package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by rkhaj on 11/9//2015.
 */
public class AutonomousRepairZoneRedHard extends LinearOpMode {

    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    Servo climber;
    //ColorSensor colorSensor;

    public static final int ARM_DOWN_POSITION = 0;
    public static final int ARM_UP_POSITION = 0;
    public static final double ZIP_UP_POSITION = 0;
    public static final double ZIP_DOWN_POSITION = 0;
    public static final double CLIMBER_UP_POSITION = 0.7;
    public static final double CLIMBER_DOWN_POSITION = 0;

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
    public void runOpMode() throws InterruptedException{

        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");
        climber = hardwareMap.servo.get("climber");

        lbMotor.setDirection(DcMotor.Direction.REVERSE);
        rbMotor.setDirection(DcMotor.Direction.REVERSE);

        //colorSensor = hardwareMap.colorSensor.get("colorSensor");

        waitOneFullHardwareCycle();
        waitForStart();

        moveForward(-0.75, 3785);
        turnRight(0.75, 450);
        moveForward(0.75, 950);
        climber.setPosition(CLIMBER_UP_POSITION);
        /*turnRight(0.75, 1000);
        moveForward(0.75, 1000);
        if (colorSensor.blue() < 100) {
            turnLeft(0.75, 500);
            moveForward(0.25, 1000);
            turnRight(0.75, 500);
        }
        moveForward(0.25, 500);
        sleep(250);
        moveForward(-0.25, 500);
        climber.setPosition(CLIMBER_DOWN_POSITION);
        sleep(1000);
        climber.setPosition(CLIMBER_UP_POSITION);*/
    }
}
