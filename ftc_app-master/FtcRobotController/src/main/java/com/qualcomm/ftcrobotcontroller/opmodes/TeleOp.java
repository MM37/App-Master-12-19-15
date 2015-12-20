package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import static java.lang.Math.abs;

/**
 * Created by rkhaj on 10/9/2015.
 * This is a basic teleop that we are going to use for testing purposes.
 */
public class TeleOp extends OpMode {

    /*
    Declares motor variables and creates link to hardware destinations
    first letter: left/right
    second letter: front/back

    Declares values for arm positions
    */
    DcMotor lfMotor = hardwareMap.dcMotor.get("lfMotor");
    DcMotor lbMotor = hardwareMap.dcMotor.get("lbMotor");
    DcMotor rfMotor = hardwareMap.dcMotor.get("rfMotor");
    DcMotor rbMotor = hardwareMap.dcMotor.get("rbMotor");
    DcMotor armMotor = hardwareMap.dcMotor.get("armMotor");
    DcMotor hangRotateMotor = hardwareMap.dcMotor.get("hangRotateMotor");
    DcMotor hangSlideMotor = hardwareMap.dcMotor.get("hangSlideMotor");

    Servo zipBlue = hardwareMap.servo.get("zipBlue");
    Servo zipRed = hardwareMap.servo.get("zipRed");
    Servo climber = hardwareMap.servo.get("climber");

    public static final int ARM_DOWN_POSITION = 0;
    public static final int ARM_UP_POSITION = 0;
    public static final double ZIP_UP_POSITION = 0;
    public static final double ZIP_DOWN_POSITION = 0;
    public static final double CLIMBER_UP_POSITION = 0;
    public static final double CLIMBER_DOWN_POSITION = 0;

    public TeleOp() {

    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        /*
        Creates variables for gamepad input
        "Ver"=vertical
        Creates variable for speed mode
        Creates variable for arm encoder control
        */
        float leftLeftVer;
        float leftRightVer;
        float rightRightVer;
        float rightLeftVer;
        String mode;
        boolean encoderMode = false;

        /*
        Updates the power variable from the sticks only if its absolute value is greater than 0.08
        This prevents values from being passed unless someone is actively moving a stick
        */
        if (abs(gamepad1.left_stick_y)>0.08) {
            leftLeftVer = -gamepad1.left_stick_y;
        } else {
            leftLeftVer = 0;
        }

        if (abs(gamepad1.right_stick_y)>0.08) {
            leftRightVer = -gamepad1.right_stick_y;
        } else {
            leftRightVer = 0;
        }

        if (abs(gamepad2.right_stick_y)>0.08) {
            rightRightVer = -gamepad2.right_stick_y;
        } else {
            rightRightVer = 0;
        }

        if (abs(gamepad2.left_stick_y)>0.08) {
            rightLeftVer = -gamepad2.left_stick_y;
        } else {
            rightLeftVer = 0;
        }

        /*
        Scales drivetrain gamepad values depending on buttons pressed
        This allows the driver to have more precision
        Also updates the mode display
        */
        if (gamepad1.left_bumper && gamepad1.right_bumper){
            leftLeftVer *= 0.2;
            leftRightVer *= 0.2;
            mode = "slower";
        } else if (gamepad1.left_bumper) {
            leftLeftVer *= 0.5;
            leftRightVer *= 0.5;
            mode = "slow";
        } else {
            mode = "regular";
        }

        /*
        Updates drive motor power
        */
        lfMotor.setPower(leftLeftVer);
        lbMotor.setPower(leftLeftVer);
        rfMotor.setPower(leftRightVer);
        rbMotor.setPower(leftRightVer);
        /*
        Updates arm motor power
        */
        if (rightRightVer != 0) {
            encoderMode = false;
            armMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            armMotor.setPower(rightRightVer);
        } else if (gamepad2.y) {
            encoderMode = true;
            armMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
            armMotor.setTargetPosition(ARM_UP_POSITION);
        } else if (gamepad2.a) {
            encoderMode = true;
            armMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
            armMotor.setTargetPosition(ARM_DOWN_POSITION);
        } else if (rightRightVer == 0 && encoderMode == false) {
            armMotor.setPower(0);
        }
        /*
        Updates hang arm motor power
        */
        hangSlideMotor.setPower(rightLeftVer);
        if (gamepad2.left_bumper) {
            hangSlideMotor.setPower(-100);
        } else if (gamepad2.right_bumper) {
            hangSlideMotor.setPower(100);
        } else {
            hangSlideMotor.setPower(0);
        }
        /*
        Updates zip servo power
         */
        if (gamepad1.x) {
            zipBlue.setPosition(ZIP_DOWN_POSITION);
        } else {
            zipBlue.setPosition(ZIP_UP_POSITION);
        }

        if (gamepad1.b) {
            zipRed.setPosition(ZIP_DOWN_POSITION);
        } else {
            zipRed.setPosition(ZIP_UP_POSITION);
        }
        /*
        Updates climber servo power
         */
        if (gamepad2.x) {
            climber.setPosition(CLIMBER_DOWN_POSITION);
        } else if (gamepad2.b) {
            climber.setPosition(CLIMBER_UP_POSITION);
        }

        /*
        Display:
        the mode we're in (regular, slow, or slower)
        left joystick output
        right joystick output
        left trigger output
        right trigger output
         */
        telemetry.addData("Mode", mode);
        telemetry.addData("Left Vertical Ouput", lfMotor.getPower());
        telemetry.addData("Right Vertical Output", rfMotor.getPower());
        telemetry.addData("Left Bumper", gamepad1.left_bumper);
        telemetry.addData("Right Bumper", gamepad1.right_bumper);
    }

    @Override
    public void stop() {

    }
}
