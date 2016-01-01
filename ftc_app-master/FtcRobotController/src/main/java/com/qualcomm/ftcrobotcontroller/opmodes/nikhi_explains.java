package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Sahaj on 12/30/2015.
 */
public class nikhi_explains {

    DcMotor pulleyMotor;

    pulleyMotor=hardwareMap.DcMotor.get("pulley");
    boolean array[4];

    //inside main loop

    while(true)

    {
        int encoderVal = pulleyMotor.getCurrentPosition();

        if (gamepad1.x && !array[x]) {


        }

        encoderVal = pulleyMotor.getCurrentPosition();
    }
}