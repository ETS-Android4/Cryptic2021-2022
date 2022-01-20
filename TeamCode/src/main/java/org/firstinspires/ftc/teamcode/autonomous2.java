package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;

public class autonomous2 extends LinearOpMode {
    public void runOpMode() {
        SampleTankDrive drive = new SampleTankDrive(hardwareMap);

        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(0, 70, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        Trajectory traj1 = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(0, 55), Math.toRadians(-90))
                .splineTo(new Vector2d(55, 45), Math.toRadians(0))
                .build();
        drive.followTrajectory(traj1);
    }
}