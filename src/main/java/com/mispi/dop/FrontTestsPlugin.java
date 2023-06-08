package com.mispi.dop;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@Mojo(name="frontTests")
public class FrontTestsPlugin extends AbstractMojo {

    private final Logger logger = Logger.getLogger(FrontTestsPlugin.class.getName());

    @Parameter(property = "pathToSubproject")
    private String pathToSubproject;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (pathToSubproject == null) {
            logger.log(Level.SEVERE, "Need pathToSubproject param!");
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
                logger.log(Level.INFO, processLog);
            }
            while ((processLog = errorReader.readLine()) != null) {
                logger.log(Level.SEVERE, processLog);
            }

            process.waitFor();

        } catch (IOException  e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
