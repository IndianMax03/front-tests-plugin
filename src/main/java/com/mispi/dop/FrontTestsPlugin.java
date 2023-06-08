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
import java.util.logging.Logger;

@Mojo(name="run")
public class FrontTestsPlugin extends AbstractMojo {

    private final Logger logger = Logger.getLogger(FrontTestsPlugin.class.getName());

    @Parameter(property = "pathToSubproject")
    private String pathToSubproject;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (pathToSubproject == null) {
            getLog().error("Need pathToSubproject param!");
        }
        try {
            Process process = Runtime.getRuntime().exec(new String[]{
                    "echo",
                    "Выполняем фронт тесты... в процессе"
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

            getLog().info(project.getBuild().getSourceDirectory());

            process.waitFor();

        } catch (IOException  e) {
            getLog().error(e.getMessage());
        } catch (InterruptedException e) {
            getLog().error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
