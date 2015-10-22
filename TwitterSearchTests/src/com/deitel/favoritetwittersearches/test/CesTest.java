package com.deitel.favoritetwittersearches.test;

import java.io.File;
import java.util.logging.Logger;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;

import com.deitel.favoritetwittersearches.FavoriteTwitterSearches;
import com.robotium.solo.Solo;
import com.robotium.solo.Solo.Config;

import junit.framework.Assert;




/**
 * Created by Bruno Moura Cavalcante on 01/03/15.
 */
public class CesTest extends ActivityInstrumentationTestCase2<FavoriteTwitterSearches> {

    private Solo solo;
    private Adaptor adaptor;
    public Process process;
    public File dir; 
    private static int inicio=0;
    EventRunner runner = new EventRunner();
    String nomeTeste = "";
    protected static Logger logs = Logger.getLogger(CesTest.class.getName());

    public CesTest() {
        super(FavoriteTwitterSearches.class);
    }
    
    public void clean(File mFile) {
        if (null == mFile || !mFile.exists() || !mFile.isDirectory()) return;
        for (File file : mFile.listFiles()) {
            delete(file);
        }
    }

    private void delete(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                delete(child);
            }
        }
        file.delete();
    }

    public void captureLog(){
    	 try{
             File dir = Environment.getExternalStorageDirectory();
             /*if(process!=null)
                 process.destroy();*/
             File logFiles = new File(dir.toString()+"/LogFile.txt");
             if(logFiles.exists()==true)
                 logFiles.delete();
             process=Runtime.getRuntime().exec("/system/bin/logcat -c");
             process=Runtime.getRuntime().exec("/system/bin/logcat -f "+logFiles);
             //process=Runtime.getRuntime().exec(new String[]{"logcat", "-f", logFiles.getAbsolutePath(), "EventRunner:*", "*:S", "TestRunner:*", "*:S"});
         }
         catch(Exception e){
         }

    }


    public void setUp() throws Exception {
        
        if(inicio == 0){
            File dir = new File(Environment.getExternalStorageDirectory(), "screenshots");
            clean(dir);
            delete(dir);
            captureLog();
            inicio = 1;
        }
        Config config = new Config();
        config.screenshotFileType = Config.ScreenshotFileType.PNG;
        config.screenshotSavePath = String.valueOf(Environment.getExternalStorageDirectory())+"/screenshots/";
        solo = new Solo(getInstrumentation(), config);
        getActivity();
        adaptor = new Adaptor(solo);
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
    
    public void testCES00(){
    	nomeTeste = "CES00";
    	logs.info(nomeTeste+"#inicio");
    	String ces = "telaInicial";
    	Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    	logs.info(nomeTeste+"#fim");   
    	
    }
    
    public void testCES01(){
    	nomeTeste = "CES01";
    	logs.info(nomeTeste+"#inicio");
    	String ces="telaInicial,pressBotaoClearTag,exibirMsgThisWillDelete,pressBotaoErase,telaInicial";
    	Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    	logs.info(nomeTeste+"#fim");
    }
    
    public void testCES02(){
    	nomeTeste = "CES02";
    	logs.info(nomeTeste+"#inicio");
    	String ces="telaInicial,pressBotaoClearTag,exibirMsgThisWillDelete,pressBotaoCancel,telaInicial";
    	Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    	logs.info(nomeTeste+"#fim");
    	
    }
    
    
    public void testCES03(){
    	nomeTeste = "CES03";
    	logs.info(nomeTeste+"#inicio");
    	String ces = "telaInicial,preencherCampoSearch,preencherCampoTag,preessionarBotaoSave,telaInicialcomTags";
    	Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    	logs.info(nomeTeste+"#fim");    	
    	
    }
    
    public void testCES04(){
    	nomeTeste = "CES04";
    	logs.info(nomeTeste+"#inicio");
    	String ces = "telaInicialcomTags";
    	Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    	logs.info(nomeTeste+"#fim");    	
    	
    }
    
    public void testCES05(){
    		nomeTeste = "CES05";
    		logs.info(nomeTeste+"#inicio");
    		String ces = "telaInicialcomTags,pressBotaoEditarTagGerada,preencherCampoSearch,preencherCampoTag,preessionarBotaoSave,telaInicialcomTags";
    		Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    		logs.info(nomeTeste+"#fim");    	
    }
    
    public void testCES06(){
    	nomeTeste = "CES06";
    	logs.info(nomeTeste+"#inicio");
    	String ces="telaInicialcomTags,pressBotaoClearTag,exibirMsgThisWillDelete," +
    			"pressBotaoCancel,telaInicialcomTags";
    	Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    	logs.info(nomeTeste+"#fim");
    }
    
    public void testCES07(){
    	nomeTeste = "CES07";
    	logs.info(nomeTeste+"#inicio");
    	String ces="telaInicialcomTags,pressBotaoClearTag,exibirMsgThisWillDelete," +
    			"pressBotaoErase,telaInicial";
    	Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    	logs.info(nomeTeste+"#fim");
    }
    
    public void testCES08(){
    	nomeTeste = "CES08";
    	logs.info(nomeTeste+"#inicio");
    	String ces="telaInicial,preencherCampoTag,preessionarBotaoSave,exibirMsgMissingText,telaInicial";
    	Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    	logs.info(nomeTeste+"#fim");
    }
  
    public void testCES09(){
    	nomeTeste = "CES09";
    	logs.info(nomeTeste+"#inicio");
    	String ces="telaInicial,preencherCampoSearch,preessionarBotaoSave,exibirMsgMissingText,telaInicial";
    	Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    	logs.info(nomeTeste+"#fim");
    }
    
    
   
    //Sequência defeituosa
    /*public void testCES08(){
    	nomeTeste = "CES08";
    	logs.info(nomeTeste+"#inicio");
    	String ces="telaInicial,preencherCampoSearch,preencherCampoTag,preessionarBotaoSave," +
    			"telaInicialcomTags,pressBotaoEditarTagGerada,pressBotaoClearTag,exibirMsgThisWillDelete," +
    			"pressBotaoErase,telaInicial";
    	Assert.assertTrue(runner.executeCompleteEventSequence(adaptor, ces,solo,nomeTeste));
    	logs.info(nomeTeste+"#fim");
    }*/
    

}
