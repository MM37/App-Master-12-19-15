package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by rkhaj on 10/31/2015.
 */
public class AutonomousRepairZoneBlueEncoder extends LinearOpMode {

    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    DcMotor armMotor;
    //Servo climber;
    //ColorSensor colorSensor;

    public static final int ARM_DOWN_POSITION = 0;
    public static final int ARM_UP_POSITION = 0;
    public static final double ZIP_UP_POSITION = 0;
    public static final double ZIP_DOWN_POSITION = 0;
    public static final double CLIMBER_UP_POSITION = 0;
    public static final double CLIMBER_DOWN_POSITION = 0;

    public void turn(double power, int degrees) throws InterruptedException {
        double ticks = degrees * (1750 / 90);
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rfMotor.setPower(power);
        rbMotor.setPower(power);
        int test = lfMotor.getCurrentPosition();
        lfMotor.setTargetPosition(lfMotor.getCurrentPosition() + (int) ticks);
        lbMotor.setTargetPosition(lbMotor.getCurrentPosition() + (int) ticks);
        rfMotor.setTargetPosition(rfMotor.getCurrentPosition() - (int) ticks);
        rbMotor.setTargetPosition(rbMotor.getCurrentPosition() - (int) ticks);
        //while (lfMotor.getCurrentPosition() != test + (int) ticks) {}
    }

    public void move(double power, double inches) {
        double ticks = inches * 1220 / (4 * Math.PI);
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rfMotor.setPower(power);
        rbMotor.setPower(power);
        int test = lfMotor.getCurrentPosition();
        lfMotor.setTargetPosition(lfMotor.getCurrentPosition() + (int) ticks);
        lbMotor.setTargetPosition(lbMotor.getCurrentPosition() + (int) ticks);
        rfMotor.setTargetPosition(rfMotor.getCurrentPosition() + (int) ticks);
        rbMotor.setTargetPosition(rbMotor.getCurrentPosition() + (int) ticks);
        //while (lfMotor.getCurrentPosition() != test + (int) ticks) {}
    }

    @Override
    public void runOpMode() throws InterruptedException{

        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");
        armMotor = hardwareMap.dcMotor.get("armMotor");
        //climber = hardwareMap.servo.get("climber"); [UNCOMMENT WHEN CLIMBER IS READY]

        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lbMotor.setDirection(DcMotor.Direction.REVERSE);

        lfMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        lbMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rfMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rbMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        armMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        for(int i=0; i<15; i++){
            waitOneFullHardwareCycle();
            telemetry.addData("cycle", i);
        }

        lfMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        lbMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rfMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rbMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        telemetry.addData("lf", lfMotor.getCurrentPosition());
        telemetry.addData("lb", lbMotor.getCurrentPosition());
        telemetry.addData("rf", rfMotor.getCurrentPosition());
        telemetry.addData("rb", rbMotor.getCurrentPosition());

        //waitOneFullHardwareCycle();
        waitForStart();

        telemetry.clearData();

        move(0.4, 86.5);
        telemetry.addData("part1", "");
        sleep(5000);
        turn(0.25, 52);
        telemetry.addData("part2", "");
        sleep(5000);
        move(0.25, 6.50);
        telemetry.addData("part3", "");
        sleep(5000);
        armMotor.setPower(0.65);
        telemetry.addData("part4", "");
        armMotor.setTargetPosition(-800);
        telemetry.addData("part5", "");
        /*move(0.75, 50);
        turnRight(0.75, 1000);
        move(0.75, 12);
        move(0.25, 1);
        sleep(250);
        move(-0.25, 1);
        //climber.setPosition(CLIMBER_DOWN_POSITION);
        sleep(1000);
        //climber.setPosition(CLIMBER_UP_POSITION);*/
    }
}
