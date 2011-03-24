
package com.prealpha.butler.voice;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


/**
 * @author Ty O.
 * Reads the commands.gram file and listens for you to say something.
 * When it encounters a command, it prints what you said.
 */
public class ReadLoop {

    public static void main(String[] args) {
        ConfigurationManager cm;
        cm = new ConfigurationManager(ReadLoop.class.getResource("helloworld.config.xml"));
        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        // start the microphone or exit if the programm if this is not possible
        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }


        // loop the recognition until the programm exits.
        while (true) {
            System.out.println("Start speaking. Look at the commands.gram file to see what you can say.");

            Result result = recognizer.recognize();
            String resultText = result.getBestFinalResultNoFiller();
            if (result != null && resultText.trim() != "") {
                System.out.println("You said: " + resultText + '\n');
            } else {
                System.out.println("I can't hear what you said.\n");
            }
        }
    }
}
