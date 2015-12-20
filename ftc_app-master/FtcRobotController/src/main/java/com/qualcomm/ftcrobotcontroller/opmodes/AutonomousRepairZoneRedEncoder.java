package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.ftcrobotcontroller.opmodes.BaseCode;

/**
 * Created by rkhaj on 10/31/2015.
 */
public class AutonomousRepairZoneRedEncoder extends LinearOpMode {

    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    Servo climber = hardwareMap.servo.get("climber");
    ColorSensor colorSensor;

    public static final int ARM_DOWN_POSITION = 0;
    public static final int ARM_UP_POSITION = 0;
    public static final double ZIP_UP_POSITION = 0;
    public static final double ZIP_DOWN_POSITION = 0;
    public static final double CLIMBER_UP_POSITION = 0;
    public static final double CLIMBER_DOWN_POSITION = 0;

    BaseCode baseReference = new BaseCode();

    @Override
    public void runOpMode() throws InterruptedException{

        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");

        colorSensor = hardwareMap.colorSensor.get("colorSensor");

        lfMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        lbMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rfMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rbMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        waitOneFullHardwareCycle();
        waitForStart();

        baseReference.moveForwardEncoder(0.75, 60);
        baseReference.turnLeft(0.75, 1000);
        baseReference.moveForwardEncoder(0.75, 50);
        baseReference.turnLeft(0.75, 1000);
        baseReference.moveForwardEncoder(0.75, 12);
        if (colorSensor.blue() < 100) {
            baseReference.turnRight(0.75, 500);
            baseReference.moveForward(0.5, 5);
            baseReference.turnLeft(0.75, 500);
        }
        baseReference.moveForwardEncoder(0.25, 1);
        sleep(250);
        baseReference.moveForwardEncoder(-0.25, 1);
        climber.setPosition(CLIMBER_DOWN_POSITION);
        sleep(1000);
        climber.setPosition(CLIMBER_UP_POSITION);
    }
}
