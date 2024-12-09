package com.grammer.code.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class RegQuery {

	static StreamReader reader = null;
  private static final String REGQUERY_UTIL = "reg query ";
  private static final String REGSTR_TOKEN = "REG_SZ";
  private static final String REGDWORD_TOKEN = "REG_DWORD";

  private static final String PERSONAL_FOLDER_CMD = REGQUERY_UTIL +
    "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\" 
     + "Explorer\\Shell Folders\" /v Personal";
  private static final String CPU_SPEED_CMD = REGQUERY_UTIL +
    "\"HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\"" 
     + " /v ~MHz";
  private static final String CPU_NAME_CMD = REGQUERY_UTIL +
   "\"HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\"" 
     + " /v ProcessorNameString";

  private static final String SO_NAME_CMD = REGQUERY_UTIL +
		   "\"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\"" 
		     + " /v ProductName";

  private static final String PC_NAME_CMD = REGQUERY_UTIL +
		   "\"HKLM\\SYSTEM\\CurrentControlSet\\Control\\ComputerName\\ActiveComputerName\"" 
		     + " /v ComputerName";
  
  private static final String DBCamino = REGQUERY_UTIL +
		   "\"HKCU\\Software\\Arketipo\\APU\\Costura\"" 
		     + " /v DBCamino";
  
  private static final String DBNombre = REGQUERY_UTIL +
		   "\"HKCU\\Software\\Arketipo\\APU\\Costura\"" 
		     + " /v DBNombre";
  
  private static final String IdLinea = REGQUERY_UTIL +
		   "\"HKCU\\Software\\Grammer\\TI\\ILUO\"" 
		     + " /v Linea";
  
  private static final String Modulo = REGQUERY_UTIL +
		   "\"HKCU\\Software\\Arketipo\\APU\\Costura\"" 
		     + " /v Modulo";
  
  private static final String Vel = REGQUERY_UTIL +
		   "\"HKCU\\Software\\Arketipo\\Config\"" 
		     + " /v Velocidad";
  
  private static final String PERSONAL_USERNAME = REGQUERY_UTIL +
		    "\"HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\"
		    + "Authentication\\LogonUI\" /v LastLoggedOnDisplayName";
  
  private static final String Tipo = REGQUERY_UTIL +
		   "\"HKCU\\Software\\Grammer\\TI\\ILUO\"" 
		     + " /v Tipo";
  
  private static final String Operacion = REGQUERY_UTIL +
		   "\"HKCU\\Software\\Grammer\\TI\\ILUO\"" 
		     + " /v Operacion";
  
  public static String getUserName(){
	    try {
	      Process process = Runtime.getRuntime().exec(PERSONAL_USERNAME);
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1)
	         return null;

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return null;
	    }
	  }
  
  public static String getCurrentUserPersonalFolderPath() {
    try {
      Process process = Runtime.getRuntime().exec(PERSONAL_FOLDER_CMD);
      reader = new StreamReader(process.getInputStream());

      reader.start();
      process.waitFor();
      reader.join();

      String result = reader.getResult();
      int p = result.indexOf(REGSTR_TOKEN);

      if (p == -1)
         return null;

      return result.substring(p + REGSTR_TOKEN.length()).trim();
    }
    catch (Exception e) {
      return null;
    }
  }

  public static String getCPUSpeed() {
    try {
      Process process = Runtime.getRuntime().exec(CPU_SPEED_CMD);
      reader = new StreamReader(process.getInputStream());

      reader.start();
      process.waitFor();
      reader.join();

      String result = reader.getResult();
      int p = result.indexOf(REGDWORD_TOKEN);

      if (p == -1)
         return null;

      // CPU speed in Mhz (minus 1) in HEX notation, convert it to DEC
      String temp = result.substring(p + REGDWORD_TOKEN.length()).trim();
      return Integer.toString
          ((Integer.parseInt(temp.substring("0x".length()), 16) + 1));
    }
    catch (Exception e) {
      return null;
    }
  }

  public static String getCPUName() {
    try {
      Process process = Runtime.getRuntime().exec(CPU_NAME_CMD);
      reader = new StreamReader(process.getInputStream());

      reader.start();
      process.waitFor();
      reader.join();

      String result = reader.getResult();
      int p = result.indexOf(REGSTR_TOKEN);

      if (p == -1)
         return null;

      return result.substring(p + REGSTR_TOKEN.length()).trim();
    }
    catch (Exception e) {
      return null;
    }
  }

  public static String getSOName() {
	    try {
	      Process process = Runtime.getRuntime().exec(SO_NAME_CMD);
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1)
	         return null;

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return null;
	    }
	  }

  public static String getPCName() {
	    try {
	      Process process = Runtime.getRuntime().exec(PC_NAME_CMD);
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1)
	         return null;

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return null;
	    }
	  }
  
  public static String getTipo() {
	    try {
	      Process process = Runtime.getRuntime().exec(Tipo);
	      StreamReader reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1)
	         return null;

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return null;
	    }
	  }
  
  public static String getOperacion() {
	    try {
	      Process process = Runtime.getRuntime().exec(Operacion);
	      StreamReader reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1)
	         return null;

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return null;
	    }
	  }

  static class StreamReader extends Thread {
    private InputStream is;
    private StringWriter sw;

    StreamReader(InputStream is) {
      this.is = is;
      sw = new StringWriter();
    }

    public void run() {
      try {
        int c;
        while ((c = is.read()) != -1)
          sw.write(c);
        }
        catch (IOException e) { ; }
      }

    String getResult() {
      return sw.toString();
    }
  }

  	public static String getDBCamino() {
	    try {
	      Process process = Runtime.getRuntime().exec(DBCamino);
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1)
	         return null;

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return null;
	    }
	  }
  	
  	public static String getDBNombre() {
	    try {
	      Process process = Runtime.getRuntime().exec(DBNombre);
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1)
	         return null;

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return null;
	    }
	  }
  	
  	public static String getIdLinea() {
	    try {
	      Process process = Runtime.getRuntime().exec(IdLinea);
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1)
	         return null;

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return null;
	    }
	  }
  
  	public static String getModulo() {
	    try {
	      Process process = Runtime.getRuntime().exec(Modulo);
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1)
	         return null;

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return null;
	    }
	  }
  	
  	public static int getVelocidad() {
	    try {
	      Process process = Runtime.getRuntime().exec(Vel);
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1) {
	         return -1;
	      }

	      return Integer.parseInt(result.substring(p + REGSTR_TOKEN.length()).trim());
	    }
	    catch (Exception e) {
	      return -1;
	    }
	  }
  	
  	public static int getVelocidad(String nomLinea) {
	    try {
	      Process process = Runtime.getRuntime().exec(REGQUERY_UTIL +
	   		   "\"HKCU\\Software\\Arketipo\\" + nomLinea + "\"" 
			     + " /v Velocidad");
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1) {
	         return -1;
	      }

	      return Integer.parseInt(result.substring(p + REGSTR_TOKEN.length()).trim());
	    }
	    catch (Exception e) {
	      return -1;
	    }
	  }
  	
  	public static String getPuerto(String nomLinea) {
	    try {
	      Process process = Runtime.getRuntime().exec(REGQUERY_UTIL +
	   		   "\"HKCU\\Software\\Grammer\\TI\\" + nomLinea + "\"" 
			     + " /v plc");
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1) {
	    	  return "400";
	      }

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return "400";
	    }
	  }
  	
  	public static String getImpIndividual(String nomLinea) {
	    try {
	      Process process = Runtime.getRuntime().exec(REGQUERY_UTIL +
	   		   "\"HKCU\\Software\\Grammer\\TI\\" + nomLinea + "\"" 
			     + " /v impIndividual");
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1) {
	    	  return "400";
	      }

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return "400";
	    }
	  }
  	
  	public static String getModulo(String nomLinea) {
	    try {
	      Process process = Runtime.getRuntime().exec(REGQUERY_UTIL +
	   		   "\"HKCU\\Software\\Grammer\\TI\\" + nomLinea + "\"" 
			     + " /v modulo");
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1) {
	    	  return "400";
	      }

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	      return "400";
	    }
	  }
  	
  	public static String getImpCaja(String nomLinea) {
	    try {
	      Process process = Runtime.getRuntime().exec(REGQUERY_UTIL +
	    		"\"HKCU\\Software\\Grammer\\TI\\" + nomLinea + "\"" 
			     + " /v impCaja");
	      reader = new StreamReader(process.getInputStream());

	      reader.start();
	      process.waitFor();
	      reader.join();

	      String result = reader.getResult();
	      int p = result.indexOf(REGSTR_TOKEN);

	      if (p == -1) {
	    	  return "400";
	      }

	      return result.substring(p + REGSTR_TOKEN.length()).trim();
	    }
	    catch (Exception e) {
	    	return "400";
	    }
	  }
  	public static void crearRegistro(String nomLinea) throws InterruptedException {
		String key = "HKEY_CURRENT_USER\\SOFTWARE\\Arketipo\\" + nomLinea;
		String valName = "Velocidad";
		String val = "1";
		try {
			//Process process = Runtime.getRuntime().exec("reg add \"" + key + "\" /v \"" + valName + "\" /d \"" + val);
			Process p = Runtime.getRuntime().exec("REG ADD " + key + " /v " + valName + " /t REG_SZ /d " + val);
			p = Runtime.getRuntime().exec("REG ADD " + key + " /v " + "impEtiqueta" + " /t REG_SZ /d " + "Etiquetas");
			p = Runtime.getRuntime().exec("REG ADD " + key + " /v " + "impCaja" + " /t REG_SZ /d " + "CajaEtiquetas");
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader (new InputStreamReader (is));
			String aux = br.readLine();
			while (aux!=null)
            {
                // Se escribe la linea en pantalla
                System.out.println (aux);
                
                // y se lee la siguiente.
                aux = br.readLine();
            }
			
			//process.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	}
  	public static void main(String s[]) throws InterruptedException {
  		System.out.println("Personal directory : " + getCurrentUserPersonalFolderPath());
	    System.out.println("CPU Name : " + getCPUName());
	    System.out.println("CPU Speed : " + getCPUSpeed() + " Mhz");
	    System.out.println("SO Name : " + getSOName());
	    System.out.println("PC Name : " + getPCName());
	    crearRegistro("PRueba");
	    System.out.println(getImpCaja("KL_Descansabrazos"));
  	}
}
