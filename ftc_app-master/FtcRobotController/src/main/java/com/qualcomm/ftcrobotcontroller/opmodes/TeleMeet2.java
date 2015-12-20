package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Math.abs;

/**
 * Created by Sahaj on 11/21/2015.
 */
public class TeleMeet2 extends OpMode {


    /*Declares motors*/
    DcMotor lfMotor;
    DcMotor lbMotor;
    DcMotor rfMotor;
    DcMotor rbMotor;
    DcMotor hangSlideMotor;
    DcMotor hangRotateMotor;
    DcMotor armMotor;

    /*Declares servos*/
    Servo climber;
    Servo rSwitch;
    Servo lSwitch;

    /*Assigns constant values; !!!TO BE EDITED!!!*/
    public static final double CLIMBER_UP_POSITION = 0.7;
    public static final double CLIMBER_DOWN_POSITION = 0.0;
    public static final double RSWITCH_OPEN_POSITION = 0.25;
    public static final double RSWITCH_CLOSED_POSITION = 0.90;
    public static final double LSWITCH_CLOSED_POSITION = 0.25;
    public static final double LSWITCH_OPEN_POSITION = 0.90;

    @Override
    public void init(){
         /* Assigns DcMotor objects to physical motors
            using Hardware Mapping */
        lbMotor = hardwareMap.dcMotor.get("lbMotor");
        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");
        rbMotor = hardwareMap.dcMotor.get("rbMotor");
        hangRotateMotor = hardwareMap.dcMotor.get("hangRotateMotor");
        hangSlideMotor = hardwareMap.dcMotor.get("hangSlideMotor");
        armMotor = hardwareMap.dcMotor.get("armMotor");

         /* Assigns Servo objects to physical servos
         using Hardware Mapping */
        climber = hardwareMap.servo.get("climber");
        rSwitch = hardwareMap.servo.get("rSwitch");
        lSwitch = hardwareMap.servo.get("lSwitch");

        /* resets Servo positions */
        climber.setPosition(CLIMBER_DOWN_POSITION);
        rSwitch.setPosition(RSWITCH_CLOSED_POSITION);
        lSwitch.setPosition(LSWITCH_CLOSED_POSITION);

        /* runs backward motors in opposite direction */
        lfMotor.setDirection(DcMotor.Direction.REVERSE);
        lbMotor.setDirection(DcMotor.Direction.REVERSE );

        telemetry.addData("init complete", "");
    }

    @Override
    public void loop(){
        telemetry.clearData();

        /* variables to control motor power */
        float rPwr, lPwr, armPwr, rotatePwr, slidePwr;

        Boolean lBump; //status of left bumper on gamepad 1
        Boolean rBump; //status of right bumper on gamepad 1

        /*sets threshold for gamepad input*/
        if (abs(gamepad1.left_stick_y)>0.08) {
            lPwr = gamepad1.left_stick_y;
        } else {
            lPwr = 0;
        }

        if (abs(gamepad1.right_stick_y)>0.08) {
            rPwr = gamepad1.right_stick_y;
        } else {
            rPwr = 0;
        }

        if (abs(gamepad2.right_stick_y)>0.08) {
            armPwr = (float)0.2*gamepad2.right_stick_y;
        } else {
            armPwr = 0;
        }

        if (abs(gamepad2.left_stick_y)>0.08)
            rotatePwr = (float) 0.40 * gamepad2.left_stick_y;
        else if (gamepad2.a)
            rotatePwr = (float) -0.16;
        else
            rotatePwr = 0;

        if (gamepad1.left_trigger == 1)
            slidePwr = 1;
        else if (gamepad1.right_trigger == 1)
            slidePwr = -1;
        else
            slidePwr = 0;

        if (gamepad2.right_trigger > 0) {
            rSwitch.setPosition(RSWITCH_OPEN_POSITION);
        } else {
            rSwitch.setPosition(RSWITCH_CLOSED_POSITION);
        }

        if (gamepad2.left_trigger > 0) {
            lSwitch.setPosition(LSWITCH_OPEN_POSITION);
        } else {
            lSwitch.setPosition(LSWITCH_CLOSED_POSITION);
        }


        if (gamepad1.left_bumper)
            lBump=true;
        else
            lBump=false;

        if (gamepad1.right_bumper)
            rBump=true;
        else
            rBump=false;

        /*assigns power based on lBump value */
        if(lBump && rBump){
            lbMotor.setPower(0.30*lPwr);
            lfMotor.setPower(0.30*lPwr);
            rfMotor.setPower(0.30 * rPwr);
            rbMotor.setPower(0.30 * rPwr);
        }
        else if(lBump){
            lbMotor.setPower(0.60*lPwr);
            lfMotor.setPower(0.60*lPwr);
            rfMotor.setPower(0.60*rPwr);
            rbMotor.setPower(0.60*rPwr);
        }
        else{
            lbMotor.setPower(lPwr);
            lfMotor.setPower(lPwr);
            rfMotor.setPower(rPwr);
            rbMotor.setPower(rPwr);
        }

        armMotor.setPower(armPwr);

        hangSlideMotor.setPower(slidePwr);
        hangRotateMotor.setPower(rotatePwr);

        if(gamepad1.x)
            climber.setPosition(CLIMBER_UP_POSITION);
        if(gamepad1.y)
            climber.setPosition(CLIMBER_DOWN_POSITION);


        telemetry.addData("rightDrive: ", rfMotor.getPower());
        telemetry.addData("leftDrive: ", lfMotor.getPower());
        telemetry.addData("arm: ", armMotor.getPower());
        telemetry.addData("climber: ", climber.getPosition());
        telemetry.addData("rSwitch position: ", rSwitch.getPosition());

    }

    @Override
    public void stop(){

    }
}
