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

        for(int i=0; i<90; i++){
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

        //First move to zone area
        move(0.5, 86.5);
        sleep(7500);
        //turn towards zone area
        turn(0.25, 52);
        sleep(5000);
        //prepares arm for movements
        armMotor.setPower(.15);
        //armMotor.setTargetPosition(-350);
        //sleep(3000);
        //moves into zone
        move(0.25, 11);
        sleep(5000);
        //moves arm to score
        armMotor.setTargetPosition(-500);
        sleep(4000);
        move(0.20, -1.5);
        //moves back to drag climbers off

        sleep(5000);
        armMotor.setTargetPosition(-850);
        sleep(2000);
        //moves arm back up
        armMotor.setTargetPosition(50);
        sleep(3000);
        //moves back into zone
        /*move(0.25, 4);
        sleep(5000);*/
    }
}
