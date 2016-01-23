package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Sahaj on 1/13/2016.
 */
public class Auto_LineFollow2 extends LinearOpMode{

    public static final int THRESHOLD = 50;

    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    OpticalDistanceSensor ods;
    TouchSensor touch;

    public void turn(double power, int degrees) throws InterruptedException {
        double ticks = degrees * (1750 / 90);
        lfMotor.setPower(power);
        lbMotor.setPower(power);
        rfMotor.setPower(power);
        rbMotor.setPower(power);

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

        lfMotor.setTargetPosition(lfMotor.getCurrentPosition() + (int) ticks);
        lbMotor.setTargetPosition(lbMotor.getCurrentPosition() + (int) ticks);
        rfMotor.setTargetPosition(rfMotor.getCurrentPosition() + (int) ticks);
        rbMotor.setTargetPosition(rbMotor.getCurrentPosition() + (int) ticks);
    }

    @Override
    public void runOpMode() throws InterruptedException{

        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");
        ods = hardwareMap.opticalDistanceSensor.get("ods");
        touch = hardwareMap.touchSensor.get("touch");

        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lbMotor.setDirection(DcMotor.Direction.REVERSE);

        lfMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        lbMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rfMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rbMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        for(int i=0; i<90; i++){
            waitOneFullHardwareCycle();
            telemetry.addData("cycle", i);
        }

        lfMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        lbMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rfMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rbMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        /*to get color of alliance*/
        boolean ready = false;
        boolean isRed = true; //by default, robot is red alliance
        /*  */

        int matColor = ods.getLightDetectedRaw(); //stores mat color
        //int matColor = ods.getLightDetected();    //stores mat color


        telemetry.addData("Select alliance color: blue[X], red[B]", "");

        int tempCounter = 0;

        while (!ready) {
            if (gamepad1.x){
                telemetry.clearData();
                telemetry.addData("BLUE", "");
                isRed = false;
                ready = true;
            }
            else if (gamepad2.b){
                telemetry.clearData();
                telemetry.addData("RED", "");
                isRed = true;
                ready = true;
            }
        }

        waitForStart();
        waitOneFullHardwareCycle();


        telemetry.clearData();
        /*uncomment to add delay*/
        sleep(1000);

        if(isRed) {
        /*movement to line*/
            //First move to zone area
            //turn(0.5, 15);      //values changed for testing to save space and time on practice field

            //sleep(2000);

            //searches for white tape
            while (ods.getLightDetectedRaw() < (THRESHOLD))    //threshold determines difference between white tape and mat
                turn(0.2, 10);

            telemetry.addData("stage 1", "");
            turn(0.2, -16);
            sleep(1000);
            telemetry.addData("stage 2", matColor);

            boolean touchPressed = false;

            while(!touchPressed) {
                while (ods.getLightDetectedRaw() >= THRESHOLD && !touchPressed) {
                    move(0.2, 1);
                    sleep(100);
                    turn(0.3, -10);
                    sleep(100);
                    touchPressed = touch.isPressed();
                    waitOneFullHardwareCycle(); //added
                }
                while (ods.getLightDetectedRaw() < THRESHOLD && !touchPressed) {
                    turn(0.1, 5);
                    sleep(100);
                    touchPressed = touch.isPressed();
                    waitOneFullHardwareCycle(); //added
                }
                waitOneFullHardwareCycle(); //added
            }


            telemetry.addData("REACHED END!", "");

        }






    }

}

