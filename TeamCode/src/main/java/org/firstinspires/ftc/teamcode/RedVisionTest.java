package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.eyesight.Pipe_line;


@TeleOp(name="RED Vision Test", group="Tests")
public class RedVisionTest extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot();
        r.initRedVision(this);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.update();
        }
    }
}
