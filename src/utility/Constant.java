package utility;


public class Constant {
	    public static String URL = "";
	    public static String ACTION_SHEET = "";
	    public static final String UID = "dev";
	    public static final String PWD ="j8c9B2CM";
//		public static final String Path_TestData = "\\src\\testData\\";
	    public static final String PATH_TESTDATA = "/src/testData/";
//		public static final String Path_ResultsData = "\\src\\zresults\\";
		public static final String PATH_RESULTSDATA = "/src/zresults/";
		public static final String FILE_TESTDATA = "TestData.xlsx";
		public static final String FILE_OBJECTSHEET = "CGG_Objects.xlsx";
		public static final String FILE_DATASHEET = "CGG_Data.xlsx";
		public static final String FILE_DATADICTIONARY = "Data Dictionary.xlsx";
		public static final String FILE_KeyChecking = "KeysChecking.xlsx";
		
//		public static final String Path_To_Chrome_Driver = "\\driver\\chromedriver.exe";
		public static final String PATH_TO_CHROME_DRIVER = "/driver/chromedriver.exe";
		public static final String PATH_TO_IE_DRIVER = "/driver/IEDriverServer_x64.exe";
		public static final String PATH_TO_OPERA_DRIVER = "/driver/operadriver.exe";
		public static final String PATH_TO_SAFARI_DRIVER = "/driver/chromedriver.exe";
		
		//Test Data Sheet Columns
		public static final int COL_TESTCASENAME = 0;	
		public static final int COL_USERNAME = 1;
		public static final int COL_PASSWORD = 2;
		public static final int COL_BROWSER = 3;
		public static final int COL_PARAM1 = 4;
		public final static int COL_PARAM2 = 5;
		public final static int COL_EXCELFLAG_TESTCASE = 6;
		
		//Action Sheet Columns
		public static final int COL_STEPNO = 0;
		public static final int COL_STEPNAME = 1;
		public static final int COL_EXCELFLAG_TESTINGSTEP = 2;	
		public static final int COL_SCREEN_POINTER = 3;
		public static final int COL_OBJECT_IDENTIFIER = 4;	
		public static final int COL_DATAIDENTIFIER = 5;
		public static final int COL_ACTION = 6;	
		public static final int COL_ARGS = 7;
		public static final int COL_RESULT = 8;	
		public static final String USRDIR=System.getProperty("user.dir");
		public static final int MAX_REPEAT_TIME = 1;
		
//		public static final String Path_ScreenShot = "\\src\\zScreenshots\\";
		public static final String PATH_SCREENSHOT = "/src/zScreenshots/";
		
		public static final int EXPLICITWAIT_MILLIS = 1000;
		public static final int IMPLICITWAIT_MILLIS = 3000;
		public static final int WAIT_AJAXELEMENT_MILLIS = 4000;
		public static final int HIGHLIGHT_ELEMENT_MILLIS = 2000;
		public static final boolean PRINT_ELEMENT_NOT_FOUND_MSG = true;
		public static final boolean ENABLE_HIGHLIGHTER = false;
		public static final boolean DEBUG_MODE = false;
		public static boolean ENABLE_BROWSER_MOB = true;
		public static boolean ENABLE_FUNNEL_TESTER = true;
		public static boolean ENABLE_USER_INPUT = true;
		
		public static final String DATE_FORMAT = "MM-dd-yyyy";
		
//		public static final String File_Redirection_URL = "TW CIAB Migration.xlsx";
		public static final String FILE_REDIRECTION_URL = "HK CIAB Migration.xlsx";
		
		public static final int ERROR_CODE_BAD_CONTENT = 100;
		
		public static final WebDriverUtils.Type DEFAULT_BROWSER = WebDriverUtils.Type.FireFox;
		
		public static final String UID_ALFRESCO = "martin.wang";
		
		public static final String PWD_ALFRESCO = "12345qwert";
		
		public static final double DELTA_VALUES = 4.0;
		
		public static final int MOBILE_WIDTH = 743;
		
		public static final int MOBILE_HEIGHT = 288;
}
