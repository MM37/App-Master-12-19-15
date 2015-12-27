package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by rkhaj on 12/11/2015.
 */
public class AutonomousRampBlue extends LinearOpMode{

    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    Servo lockServoLeft;
    Servo lockServoRight;

    public static final int LOCK_SERVO_LEFT_CLOSED_POSITION = 0;
    public static final int LOCK_SERVO_RIGHT_CLOSED_POSITION = 0;

    public void moveForward(double power, long time) throws InterruptedException{
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rbMotor.setPower(power);
        rfMotor.setPower(power);
        sleep(time * 1000);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
        rbMotor.setPower(0);
        rfMotor.setPower(0);
        sleep(500);
    }

    public void turnRight(double power, long time) throws InterruptedException{
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

    public void turnLeft(double power, long time) throws InterruptedException{
        lfMotor.setPower(-power);
        lbMotor.setPower(-power);
        rbMotor.setPower(power);
        rfMotor.setPower(power);
        sleep(time * 1000);
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
        lockServoLeft = hardwareMap.servo.get("lockServoLeft");
        lockServoRight = hardwareMap.servo.get("lockServoRight");

        rbMotor.setDirection(DcMotor.Direction.REVERSE);
        lfMotor.setDirection(DcMotor.Direction.REVERSE);

        waitOneFullHardwareCycle();
        waitForStart();

        moveForward(0.75, 2);
        turnLeft(0.75, 1100);
        lfMotor.setPower(0.75);
        lbMotor.setPower(0.75);
        rbMotor.setPower(0.75);
        rfMotor.setPower(0.75);
        sleep(4000);
        lockServoLeft.setPosition(LOCK_SERVO_LEFT_CLOSED_POSITION);
        lockServoRight.setPosition(LOCK_SERVO_RIGHT_CLOSED_POSITION);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
        rbMotor.setPower(0);
        rfMotor.setPower(0);
    }
}