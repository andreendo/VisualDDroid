package com.deitel.favoritetwittersearches.test;

import com.robotium.solo.Solo;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EventRunner {
    protected static int NumberPicture = 0;
    protected static boolean PictureTake = false;
    protected static int PicturesTaked = 0;
    protected static int position = 0, location=0;
    protected static String EMPTY = "EMPTY";
    protected static Logger logger = Logger.getLogger(EventRunner.class.getName());
    MethodSearcher methodSearcher;
   
    
    public EventRunner() {
        logger.setLevel(Level.INFO);
    }

    private String getRuleLabel(String eventLabel) {
        String tokens[] = eventLabel.split("#");
        if(tokens.length > 1)
            return tokens[1];
        else
            return EMPTY;
    }

    public boolean executeCompleteEventSequence(Object adaptor, String testSequence, Solo solo, String nomeTeste) {
        String eventLabels[] = testSequence.split(",");
        position = 0;
        location = 0;
        for(String eventLabel : eventLabels) {
            String eventRule = getRuleLabel(eventLabel);
            if(! eventRule.equals(EMPTY)){
                eventLabel = getRule(eventLabel);
            }
            logger.info("inicio#"+nomeTeste+"#"+location+"#"+ eventLabel + "#" + eventRule);
            
            if( ! executeEvent(adaptor, eventLabel, eventRule,solo,nomeTeste) ) {
                logger.info("Faulty#"+nomeTeste+"#"+location+"#"+eventLabel);
                return false;
            }
            logger.info("fim#"+nomeTeste+"#" +location+"#"+ eventLabel + "#" + eventRule);
            location++;
            
        }
        
        
        return true;
    }

    private String getRule(String eventLabel) {
        String tokens[] = eventLabel.split("#");
        return tokens[0];
    }

    public boolean executeEvent(Object adaptor, String eventLabel, String eventRule,Solo solo,String nomeTeste) {
        methodSearcher = new MethodSearcher(adaptor.getClass().getMethods());

        Method method = null;
        if(eventRule.equals("EMPTY"))
            method = methodSearcher.getMethodsEventAnnotatedWith(eventLabel);
        else
            method = methodSearcher.getMethodsEventAnnotatedWith(eventLabel, eventRule);

        if(method == null) {
            logger.info("Event "+eventLabel+" (rule: "+eventRule+") cannot be found in the adaptor "+adaptor.getClass().getName()+".");
            logger.info("Faulty#"+nomeTeste+"#"+location+"#"+eventLabel);
            return false;
        }

        try {
            String name = (NumberPicture++)+"#"+nomeTeste+"-"+(position++)+"-"+eventLabel;
            boolean ret = (Boolean) method.invoke(adaptor);            
            solo.takeScreenshot(name);
            return ret;
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

}
