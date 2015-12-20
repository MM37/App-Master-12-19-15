package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Sahaj on 11/19/2015.
 */
public class EncoderTestSahaj extends OpMode {

    DcMotor lfMotor;
    DcMotor rfMotor;

    final int TARGETLF = -50, TARGETRF = 50;


    public EncoderTestSahaj(){
    }

    @Override
    public void init(){


        lfMotor = hardwareMap.dcMotor.get("lfMotor");
        rfMotor = hardwareMap.dcMotor.get("rfMotor");

        lfMotor.setDirection(DcMotor.Direction.REVERSE);

        lfMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        rfMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        telemetry.addData("Reset Complete", "");
    }

    @Override
    public void loop(){
        lfMotor.setTargetPosition(TARGETLF);
        rfMotor.setTargetPosition(TARGETRF);

        telemetry.clearData();
        telemetry.addData("TargetLeft: ", lfMotor.getTargetPosition());

        lfMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rfMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        lfMotor.setPower(.40);
        rfMotor.setPower(.40);
    }

    @Override
    public void stop(){}

}
