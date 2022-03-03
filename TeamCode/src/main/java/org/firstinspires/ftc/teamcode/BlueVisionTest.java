package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.eyesight.Pipe_line;


@TeleOp(name="BLUE Vision Test", group="Tests")
public class BlueVisionTest extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot();
        r.initialize(this, Pipe_line.Team.BLUE);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.update();
        }
    }
}
