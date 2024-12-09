package com.grammer.code.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class CorridaJacob {
    
	public String guardarRegistroSAP(String numeroParte, String cantidad, String liga)  {
		String sapMessage = "";
		
		try {
	   	    //Declaracion de variables
			ActiveXComponent saprotwr, guiApp, connection, session, obj;
	   	    Dispatch rotEntry;
	   	    Variant scriptEngine;
	   	 
	   	    
	   	    //iniciar sesion
	   	    ComThread.InitSTA();
	   	    saprotwr = new ActiveXComponent("SapROTWr.SapROTWrapper");
	   	    rotEntry = saprotwr.invoke("GetrotEntry", "SAPGUI").toDispatch();
	   	    scriptEngine = Dispatch.call(rotEntry, "GetScriptingEngine");
	   	    guiApp = new ActiveXComponent(scriptEngine.toDispatch());
	   	    connection = new ActiveXComponent(guiApp.invoke("Children", 0).toDispatch());
	   	    session = new ActiveXComponent(connection.invoke("Children", 0).toDispatch());
	   	    System.out.println("Corriendo SAP");
	   	    
	   	    obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]").toDispatch());
	   	    obj.invoke("sendVKey", 0);
	   	    
	   	    obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/tbar[0]/okcd").toDispatch());
	   	    obj.setProperty("Text", "/nmfbf");
	   	    
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]").toDispatch());
	   	    obj.invoke("sendVKey", 0);//enter
	   	    
	   	    //Cantidad ("Conf. Qty")
            obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/subSUB800:SAPLBARM:0811/txtRM61B-ERFMG").toDispatch());
            obj.setProperty("Text", cantidad);

            //Doc Header es decir liga ("Doc.Header Text")
            obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/txtRM61B-BKTXT").toDispatch());
            obj.setProperty("Text", liga);

            //Numero parte
            obj = new ActiveXComponent(session.invoke("FindById",
                    "wnd[0]/usr/tabsTAB800/tabpLAGER/ssubTABSUB800:SAPLBARM:0801/ctxtRM61B-MATNR").toDispatch());
            obj.setProperty("Text", numeroParte);

            obj = new ActiveXComponent(session.invoke("FindById",
                    "wnd[0]/usr/tabsTAB800/tabpLAGER/ssubTABSUB800:SAPLBARM:0801/ctxtRM61B-MATNR").toDispatch());
            obj.invoke("setFocus");
            obj = new ActiveXComponent(session.invoke("FindById",
                    "wnd[0]/usr/tabsTAB800/tabpLAGER/ssubTABSUB800:SAPLBARM:0801/ctxtRM61B-MATNR").toDispatch());
            obj.setProperty("caretPosition", 7);
            
            //Numero planta
            obj = new ActiveXComponent(session.invoke("FindById",
                    "wnd[0]/usr/tabsTAB800/tabpLAGER/ssubTABSUB800:SAPLBARM:0801/ctxtRM61B-WERKS").toDispatch());
            obj.setProperty("Text", "3330");
            obj = new ActiveXComponent(session.invoke("FindById",
                    "wnd[0]/usr/tabsTAB800/tabpLAGER/ssubTABSUB800:SAPLBARM:0801/ctxtRM61B-WERKS").toDispatch());
            obj.invoke("setFocus");
            obj = new ActiveXComponent(session.invoke("FindById",
                    "wnd[0]/usr/tabsTAB800/tabpLAGER/ssubTABSUB800:SAPLBARM:0801/ctxtRM61B-WERKS").toDispatch());
            obj.setProperty("caretPosition", 4);
            
            //F11 para confirmar
            obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]").toDispatch());
            obj.invoke("sendVKey", 11);

            obj = new ActiveXComponent(session.invoke("FindById",
                    "wnd[0]/sbar").toDispatch());
            
            
            //Obtener mensaje de la ventana asistente
            sapMessage = String.valueOf(obj.getProperty("Text"));

            if (sapMessage.equals("") || sapMessage.equals(" ")) {
                obj = new ActiveXComponent(session.invoke("FindById", "wnd[1]/tbar[0]/btn[0]").toDispatch());
                obj.invoke("press");

                obj = new ActiveXComponent(session.invoke("FindById", "wnd[1]/tbar[0]/btn[0]").toDispatch());
                obj.invoke("press");

                obj = new ActiveXComponent(session.invoke("FindById",
                        "wnd[0]/sbar").toDispatch());
                sapMessage = String.valueOf(obj.getProperty("Text"));
            }

	   	} catch (Exception e) {
	   	    e.printStackTrace();
	   	    System.err.println("Error : " + e);
	   	 sapMessage = "Error SAP: " + e.getMessage();
	   	}
	    
	    System.out.println("---> class CorridaJacob - corridaJacob | numConfSap: " + sapMessage);
	    return sapMessage;
	}
}