package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class autonomous5 extends LinearOpMode {
    public void runOpMode() {
        Robot robot = new Robot();
        robot.initialize(this);
        SampleTankDrive drive = new SampleTankDrive(hardwareMap);

        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(0, -66, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)
                .lineTo(new Vector2d(-70, -66))
                .addDisplacementMarker(() -> {
                    robot.duckWheel.setPower(0.55);
                })
                .waitSeconds(5)
                .addDisplacementMarker(() -> {
                    robot.duckWheel.setPower(0);
                })
                .lineTo(new Vector2d(-50, -66))
                .splineTo(new Vector2d(-70, -34), Math.toRadians(180))
                .build();
        drive.followTrajectorySequence(traj1);
    }
}