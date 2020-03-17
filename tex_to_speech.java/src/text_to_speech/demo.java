package text_to_speech;
import com.sun.speech.freetts.Gender;
import java.beans.PropertyVetoException;
import java.util.Locale;
import java.util.Scanner;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;
public class demo {
    SynthesizerModeDesc desc;
    Synthesizer synthesizer;
    Voice voice;
    public void voice(String voiceName)
            throws EngineException, AudioException, EngineStateError,
            PropertyVetoException
    {
        if (desc == null) {

            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            desc = new SynthesizerModeDesc(Locale.US);
            Central.registerEngineCentral
                    ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            synthesizer = Central.createSynthesizer(desc);
            synthesizer.allocate();
            synthesizer.resume();
            SynthesizerModeDesc smd =
                    (SynthesizerModeDesc)synthesizer.getEngineModeDesc();
            Voice[] voices = smd.getVoices();
            Voice voice = null;
            for(int i = 0; i < voices.length; i++) {
                if(voices[i].getName().equals(voiceName)) {
                    voice = voices[i];
                    break;
                }
            }
            synthesizer.getSynthesizerProperties().setVoice(voice);
        }

    }

    public void terminate() throws EngineException, EngineStateError {
        synthesizer.deallocate();
    }

    public void doSpeak(String speakText)
            throws EngineException, AudioException, IllegalArgumentException,
            InterruptedException
    {
        synthesizer.speakPlainText(speakText, null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
    }
    public static void main (String[]args) throws Exception{
        demo speak = new demo();

        speak.voice("kevin16");
        // high quality
        speak.doSpeak("Enter your name?");
        System.out.println("Enter your name?");
        Scanner sc =new Scanner(System.in);
        String s=sc.nextLine();
        speak.doSpeak("Hello there. "+s+" nice to meet you ");
        System.out.println("Hello there. "+s+" nice to meet you ");
        speak.terminate();
    }

}

