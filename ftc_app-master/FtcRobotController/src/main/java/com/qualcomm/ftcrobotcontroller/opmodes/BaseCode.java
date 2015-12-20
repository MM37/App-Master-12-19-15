package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.ftcrobotcontroller.opmodes.BaseCode;

/**
 * Created by rkhaj on 11/13/2015.
 */
public class BaseCode extends LinearOpMode{
    DcMotor lfMotor = hardwareMap.dcMotor.get("lfMotor");
    DcMotor lbMotor = hardwareMap.dcMotor.get("lbMotor");
    DcMotor rfMotor = hardwareMap.dcMotor.get("rfMotor");
    DcMotor rbMotor = hardwareMap.dcMotor.get("rbMotor");


    public BaseCode () {}

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
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rbMotor.setPower(-power);
        rfMotor.setPower(-power);
        sleep(time * 1000);
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

    public void moveForwardEncoder(double power, double inches) {
        double ticks = inches * 1220 / (4 * Math.PI);
        byte direction=1;
        if (power<0) {
            direction = -1;
        }
        lfMotor.setTargetPosition(lfMotor.getCurrentPosition() + direction * (int) ticks);
        lbMotor.setTargetPosition(lbMotor.getCurrentPosition() + direction * (int) ticks);
        rfMotor.setTargetPosition(rfMotor.getCurrentPosition() + direction * (int) ticks);
        rbMotor.setTargetPosition(rbMotor.getCurrentPosition() + direction * (int) ticks);
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rfMotor.setPower(power);
        rbMotor.setPower(power);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        rbMotor.setDirection(DcMotor.Direction.REVERSE);
        lfMotor.setDirection(DcMotor.Direction.REVERSE);
    }
}
