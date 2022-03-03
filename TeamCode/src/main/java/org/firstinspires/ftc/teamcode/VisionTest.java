package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="VisionTest", group="Tests")
public class VisionTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot();
        r.initialize(this);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.update();
        }
    }
}
