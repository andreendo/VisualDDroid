package com.deitel.favoritetwittersearches.test;

import com.robotium.solo.Solo;

//


/**
 * Created by Bruno Moura Cavalcante on 01/03/15.
 */
 
public class Adaptor
{
    public Solo solo;
    

    public Adaptor(Solo solo) {
        this.solo = solo;
    }

    @Event(label = "telaInicial")
    public boolean telaInicial(){
    	
    	if(solo.searchButton("Edit")){
    		return false;
    	}
    	if(!solo.getEditText(0).getText().toString().equals("")){
    		return false;
    	}
    	if(!solo.getEditText(1).getText().toString().equals("")){
    		return false;
    	}
    	return true;
    	
    }
    

    
    @Event(label = "telaInicialcomTags")
    public boolean telaInicialcomTags(){
    	
    	if(solo.searchButton("Edit"))
    		return true;
    	return false;
    }

    
    @Event(label = "preencherCampoSearch")
    public boolean preencherCampoSearch(){

        solo.clearEditText(0);
        solo.enterText(0, "teste2");
        return true;
    }
    @Event(label = "preencherCampoTag")
    public boolean preencherCampoTag(){

        solo.clearEditText(1);
        solo.enterText(1, "teste2");
        return true;
    }
    
    @Event(label = "preessionarBotaoSave")
    public boolean preessionarBotaoSave(){
    	if(solo.searchButton("Save")){
    		solo.clickOnButton("Save"); 
            return true;
        }
        return false;
    }

   

    @Event(label="pressBotaoEditarTagGerada")
    public boolean pressBotaoEditarTagGerada(){
    	if(solo.searchButton("Edit")){
    		solo.clickOnButton("Edit"); 
            return true;
        }
        return false;
        
    }
    
    @Event(label="pressBotaoTeste2")
    public boolean pressBotaoOla(){
    	if(solo.searchButton("teste2")){
    		solo.clickOnButton("teste2"); 
            return true;
        }
        return false;
        
    }

    @Event(label="pressBotaoClearTag")
    public boolean  pressBotaoClearTag(){
    	if(solo.searchButton("Clear Tags")){
    		solo.clickOnButton("Clear Tags");
    		return true;
        }
        return false;
    }

    @Event(label="exibirMsgThisWillDelete")
    public boolean exibirMsgThisWillDelete(){
        return solo.searchText("This will delete all saved searches");
    }
    
    @Event(label="exibirMsgMissingText")
    public boolean exibirMsgMissingText(){
        return solo.searchText("Please enter a search query and tag it.");
    }

    @Event(label="pressBotaoErase")
    public boolean pressBotaoErase(){
    	if(solo.searchButton("Erase")){
    		solo.clickOnButton("Erase");
    		return true;
        }
        return false;
    }

    @Event(label="pressBotaoCancel")
    public boolean pressBotaoCancel(){
    	if(solo.searchButton("ola")){
    		solo.clickOnButton("ola");
    		return true;
        }
        return false;
    }

}
