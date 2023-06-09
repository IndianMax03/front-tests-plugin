package com.mispi.dop;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Mojo(name="run")
public class FrontTestsPlugin extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Parameter(defaultValue = "com.github.eirslett:frontend-maven-plugin:1.11.0:yarn")
    private String command;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("*********************************************************");
        getLog().info("**************** ТЕСТИРОВЩИК?😊 ОТДЫХАЙ!☺️***************");
        getLog().info("*********************************************************");

        try {
            Process process = Runtime.getRuntime().exec(new String[]{
                    "mvn",
                    command
            });
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String processLog;
            while ((processLog = outputReader.readLine()) != null) {
                getLog().info(processLog);
            }
            while ((processLog = errorReader.readLine()) != null) {
                getLog().error(processLog);
            }

            process.waitFor();

        } catch (IOException  e) {
            getLog().error(e.getMessage());
        } catch (InterruptedException e) {
            getLog().error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
