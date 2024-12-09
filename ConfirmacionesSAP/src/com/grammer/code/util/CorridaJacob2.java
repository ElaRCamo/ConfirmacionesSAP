package com.grammer.code.util;

import com.grammer.code.dao.MpsRes;
import com.grammer.code.entity.MPS;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class CorridaJacob2 {

    /*
     * El objetivo de corridaJacob es automatizar una serie de interacciones con SAP GUI, 
     * incluyendo la apertura de una transacci�n, la entrada de datos espec�ficos (cantidad, liga, n�mero de parte) 
     * y la recuperaci�n de un mensaje de resultado desde la barra de estado de SAP. 
     * El m�todo devuelve este mensaje como un String que puede ser usado para verificar el resultado de las acciones automatizadas.
     */
	public String corridaJacob(String numeroParte, String cantidad, String liga) {

	    String sapStatusMessage = null;
	    try {
	    // Declaraci�n de variables para manejar componentes ActiveX de SAP GUI
	    ActiveXComponent sapRotWrapper, sapGuiApp, sapConnection, sapSession, sapGuiElement;
	    Dispatch rotEntryPoint;
	    Variant sapScriptEngine;

	    // Inicializa el subproceso STA necesario para trabajar con COM
	    ComThread.InitSTA();
	    // Crea una instancia del componente ActiveX para interactuar con SAP ROT Wrapper
	    sapRotWrapper = new ActiveXComponent("SapROTWr.SapROTWrapper");

	        // Obtiene una referencia al objeto de scripting de SAP GUI
	        rotEntryPoint = sapRotWrapper.invoke("GetrotEntry", "SAPGUI").toDispatch();
	        // Obtiene el motor de scripting de SAP GUI
	        sapScriptEngine = Dispatch.call(rotEntryPoint, "GetScriptingEngine");
	        // Crea una instancia del componente ActiveX para la aplicaci�n SAP GUI
	        sapGuiApp = new ActiveXComponent(sapScriptEngine.toDispatch());

	        // Obtiene la conexi�n activa de SAP GUI
	        sapConnection = new ActiveXComponent(sapGuiApp.invoke("Children", 0).toDispatch());
	        // Obtiene la sesi�n activa dentro de la conexi�n de SAP GUI
	        sapSession = new ActiveXComponent(sapConnection.invoke("Children", 0).toDispatch());
	        System.out.println("Corriendo");

	        // Navega a la transacci�n especificada en SAP (/nmfbf)
	        sapGuiElement = new ActiveXComponent(sapSession.invoke("FindById", "wnd[0]/tbar[0]/okcd").toDispatch());
	        sapGuiElement.setProperty("Text", "/nmfbf"); // Establece la transacci�n en el campo de comando
	        sapGuiElement = new ActiveXComponent(sapSession.invoke("FindById", "wnd[0]").toDispatch());
	        sapGuiElement.invoke("sendVKey", 0); // Env�a la tecla 'Enter' para ejecutar la transacci�n

	        // Ingresa la cantidad en el campo correspondiente en SAP
	        sapGuiElement = new ActiveXComponent(sapSession.invoke("FindById", "wnd[0]/usr/subSUB800:SAPLBARM:0811/txtRM61B-ERFMG").toDispatch());
	        sapGuiElement.setProperty("Text", cantidad); // Establece la cantidad en el campo de la GUI

	        // Ingresa el documento de cabecera (liga) en el campo correspondiente en SAP
	        sapGuiElement = new ActiveXComponent(sapSession.invoke("FindById", "wnd[0]/usr/txtRM61B-BKTXT").toDispatch());
	        sapGuiElement.setProperty("Text", liga); // Establece la liga en el campo de la GUI

	        // Ingresa el n�mero de parte en el campo correspondiente en SAP
	        sapGuiElement = new ActiveXComponent(sapSession.invoke("FindById", 
	                "wnd[0]/usr/tabsTAB800/tabpLAGER/ssubTABSUB800:SAPLBARM:0801/ctxtRM61B-MATNR").toDispatch());
	        sapGuiElement.setProperty("Text", numeroParte); // Establece el n�mero de parte en el campo de la GUI

	        // Env�a la tecla 'F11' para proceder con la transacci�n en SAP
	        sapGuiElement = new ActiveXComponent(sapSession.invoke("FindById", "wnd[0]").toDispatch());
	        sapGuiElement.invoke("sendVKey", 11); // Enviar la tecla 'F11' en la GUI

	        // Lee el mensaje en la barra de estado de SAP
	        sapGuiElement = new ActiveXComponent(sapSession.invoke("FindById", "wnd[0]/sbar").toDispatch());
	        sapStatusMessage = String.valueOf(sapGuiElement.getProperty("Text")); // Captura el texto de la barra de estado

	        // Verifica si el mensaje de la barra de estado est� vac�o
	        if (sapStatusMessage.equals("") || sapStatusMessage.equals(" ")) {
	            // Si est� vac�o, intenta capturar un mensaje de error o advertencia
	            sapGuiElement = new ActiveXComponent(sapSession.invoke("FindById", "wnd[1]/tbar[0]/btn[0]").toDispatch());
	            sapGuiElement.invoke("press"); // Presiona el bot�n para continuar
	            sapGuiElement = new ActiveXComponent(sapSession.invoke("FindById", "wnd[0]/sbar").toDispatch());
	            sapStatusMessage = String.valueOf(sapGuiElement.getProperty("Text")); // Captura el texto actualizado de la barra de estado
	        }

	    } catch (Exception e) {
	        // Captura y maneja cualquier excepci�n que ocurra durante la ejecuci�n
	        e.printStackTrace();
	    } finally {
	        // Libera los recursos COM para evitar fugas de memoria
	        ComThread.Release();
	    }

	    // Devuelve el mensaje capturado o null si ocurri� un error
	    System.out.println("--> class CorridaJacob - corridaJacob(String numeroParte, String cantidad, String liga) | aprobacion = " + sapStatusMessage);
	    return sapStatusMessage;
	}

	/*
	 *  automatiza la entrada de datos en una transacci�n espec�fica de SAP (MB11), 
	 *  permitiendo la creaci�n o actualizaci�n de documentos de material con valores predefinidos y 
	 *  los datos proporcionados por los par�metros de la funci�n (fecha, liga, plataforma, pvb). 
	 *  Est� dise�ada para interactuar con la interfaz de SAP GUI y realizar operaciones repetitivas de manera automatizada.
	 */
	public static void carton(String fecha, String liga, String plataforma, String pvb) {

	    // Declaraci�n de componentes ActiveX necesarios para interactuar con SAP GUI
	    ActiveXComponent saprotwr, guiApp, connection, session, obj;
	    Dispatch rotEntry;
	    Variant scriptEngine;
	    final MpsRes mpsRes = new MpsRes();

	    // Inicializa el subproceso de COM para poder interactuar con componentes ActiveX
	    ComThread.InitSTA();
	    saprotwr = new ActiveXComponent("SapROTWr.SapROTWrapper");

	    try {
	        // Obtiene una referencia a la entrada ROT (Running Object Table) para SAP GUI
	        rotEntry = saprotwr.invoke("GetrotEntry", "SAPGUI").toDispatch();
	        // Obtiene el motor de scripting de SAP GUI
	        scriptEngine = Dispatch.call(rotEntry, "GetScriptingEngine");
	        guiApp = new ActiveXComponent(scriptEngine.toDispatch());

	        // Accede a la conexi�n activa de SAP GUI y luego a la sesi�n
	        connection = new ActiveXComponent(guiApp.invoke("Children", 0).toDispatch());
	        session = new ActiveXComponent(connection.invoke("Children", 0).toDispatch());
	        System.out.println("Corriendo");

	        // Encuentra el campo de comando y ejecuta la transacci�n MB11
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/tbar[0]/okcd").toDispatch());
	        obj.setProperty("Text", "/nmb11");
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]").toDispatch());
	        obj.invoke("sendVKey", 0);

	        // Configura las fechas en los campos correspondientes
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtMKPF-BLDAT").toDispatch());
	        obj.setProperty("Text", fecha);

	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtMKPF-BUDAT").toDispatch());
	        obj.setProperty("Text", fecha);

	        // Configura la liga en el campo de texto correspondiente
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/txtMKPF-BKTXT").toDispatch());
	        obj.setProperty("Text", liga);

	        // Configura otros campos necesarios para la transacci�n
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtRM07M-BWARTWA").toDispatch());
	        obj.setProperty("Text", "961");

	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtRM07M-WERKS").toDispatch());
	        obj.setProperty("Text", "3330");

	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtRM07M-LGORT").toDispatch());
	        obj.setProperty("Text", "3331");

	        // Establece el foco en un campo espec�fico
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtRM07M-LGORT").toDispatch());
	        obj.invoke("setFocus");

	        // Configura la posici�n del cursor en un campo espec�fico
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtRM07M-LGORT").toDispatch());
	        obj.setProperty("caretPosition", "4");

	        // Env�a la tecla Enter para proceder
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]").toDispatch());
	        obj.invoke("sendVKey", 0);

	        // Configura un campo adicional
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/subBLOCK:SAPLKACB:1003/ctxtCOBL-AUFNR").toDispatch());
	        obj.setProperty("Text", "484446");

	        // Itera sobre una lista de objetos MPS para llenar ciertos campos en SAP
	        int contador = 0;
	        for (MPS mps : mpsRes.getByPlatformList(plataforma)) {

	            // Configura el n�mero de parte en una tabla
	            obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/sub:SAPMM07M:0421/ctxtMSEG-MATNR[" + contador + ",7]").toDispatch());
	            obj.setProperty("Text", mps.getNumeroParte());

	            // Configura la cantidad en una tabla
	            obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/sub:SAPMM07M:0421/txtMSEG-ERFMG[" + contador + ",26]").toDispatch());
	            obj.setProperty("Text", String.valueOf(mps.getQty()));
	            contador = contador + 1;
	        }

	        // Establece el foco en un campo espec�fico y ajusta la posici�n del cursor
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/sub:SAPMM07M:0421/txtMSEG-ERFMG[1,26]").toDispatch());
	        obj.invoke("setFocus");

	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/sub:SAPMM07M:0421/txtMSEG-ERFMG[1,26]").toDispatch());
	        obj.setProperty("caretPosition", "1");

	        // Env�a la tecla Enter para proceder
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]").toDispatch());
	        obj.invoke("sendVKey", 0);

	        // Repite la configuraci�n para otro conjunto de campos, iterando sobre la lista de MPS
	        for (MPS mps : mpsRes.getByPlatformList(plataforma)) {
	            obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtMSEG-LGTYP").toDispatch());
	            obj.setProperty("Text", "100");

	            obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtMSEG-LGPLA").toDispatch());
	            obj.setProperty("Text", pvb);

	            obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtMSEG-LGPLA").toDispatch());
	            obj.invoke("setFocus");

	            obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]/usr/ctxtMSEG-LGPLA").toDispatch());
	            obj.setProperty("caretPosition", "7");

	            // Env�a la tecla Enter para proceder
	            obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]").toDispatch());
	            obj.invoke("sendVKey", 0);
	        }

	        // Finaliza el proceso enviando la tecla F11 (asociada a "Guardar" o "Procesar")
	        obj = new ActiveXComponent(session.invoke("FindById", "wnd[0]").toDispatch());
	        obj.invoke("sendVKey", 11);

	    } catch (Exception e) {
	        // Captura cualquier excepci�n que ocurra durante el proceso y muestra la traza del error
	        e.printStackTrace();
	    }
	}

}
