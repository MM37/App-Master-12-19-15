package com.qualcomm.ftcrobotcontroller.opmodes;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Sahaj on 1/10/2016.
 */
public class SahajInputTest extends LinearOpMode {
    /*
    code will test ability to change background color of app and ability to input color choice before start
     */
    //by default, robot is on red alliance
    boolean isAllianceColorRed = true;


    @Override
    public void runOpMode() throws InterruptedException{
        //took code from MRRGBExample.java
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);


        //holds if program has been started yet
        boolean blnReady = false;



        while(!blnReady){
            telemetry.clearData();
            telemetry.addData("Alliance Color? Red[B] or Blue[X]", "");

            if (gamepad1.x)
                isAllianceColorRed = false;
            else if(gamepad1.b)
                isAllianceColorRed = true;

            if (isAllianceColorRed)
                telemetry.addData("RED", "");
            else
                telemetry.addData("BLUE","");

            telemetry.addData("Ready? [DPadUp]", "");

            blnReady=gamepad1.dpad_up;
        }

        telemetry.clearData();
        telemetry.addData("READY", "");

        waitForStart();

        while(opModeIsActive()) {
            //taken and modified
            relativeLayout.post(new Runnable() {
                public void run() {
                    if (isAllianceColorRed)
                        relativeLayout.setBackgroundColor(Color.RED);
                    else
                        relativeLayout.setBackgroundColor(Color.BLUE);
                }
            });

        }
    }
}
