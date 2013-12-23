package Utils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
public class SQLiteDBHelper extends SQLiteOpenHelper {

	public SQLiteDBHelper (Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 11;
	// Database Name
	private static final String DATABASE_NAME = "MyExpenseManager.db";
	// Categories table name
	public static final String TABLE_CATEGORIES = "categories";
	//Store list table name
	public static final String TABLE_STORES = "stores";
	// Categories table columns names
	private static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";

	//Stores table column names
	private static final String STORE_ID = "id";
	public static final String STORE_NAME="name";
	public static final String STORE_CATEGORY = "category";

	//Category and amount
	public static final String TABLE_EXPENSELIST2013 = "expenselist2013";

	//Expense list column names, expense list category and the 12 months
	public static final String EXPENSELIST_CATEGORY = "category";
	public static final String EXPENSELIST_JAN = "jan";
	public static final String EXPENSELIST_FEB = "feb";
	public static final String EXPENSELIST_MAR = "mar";
	public static final String EXPENSELIST_APR = "apr";
	public static final String EXPENSELIST_MAY = "may";
	public static final String EXPENSELIST_JUN = "jun";
	public static final String EXPENSELIST_JUL = "jul";
	public static final String EXPENSELIST_AUG = "aug";
	public static final String EXPENSELIST_SEP = "sep";
	public static final String EXPENSELIST_OCT = "oct";
	public static final String EXPENSELIST_NOV = "nov";
	public static final String EXPENSELIST_DEC = "dec";


	public SQLiteDBHelper (Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSELIST2013);
		
		String CREATE_CATEGORIES_TABLE = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY NOT NULL)", TABLE_CATEGORIES, KEY_NAME);
		String CREATE_STORES_TABLE = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY NOT NULL, %s TEXT NOT NULL)", TABLE_STORES, STORE_NAME, STORE_CATEGORY);
		String CREATE_EXPENSELIST2013_TABLE = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY NOT NULL, %s REAL, %s REAL, %s REAL,%s REAL,%s REAL,%s REAL,%s REAL,%s REAL,%s REAL,%s REAL,%s REAL,%s REAL)", TABLE_EXPENSELIST2013, EXPENSELIST_CATEGORY, EXPENSELIST_JAN, EXPENSELIST_FEB, EXPENSELIST_MAR, EXPENSELIST_APR, EXPENSELIST_MAY, EXPENSELIST_JUN, EXPENSELIST_JUL, EXPENSELIST_AUG, EXPENSELIST_SEP, EXPENSELIST_OCT, EXPENSELIST_NOV, EXPENSELIST_DEC);
		db.execSQL(CREATE_CATEGORIES_TABLE);
		db.execSQL(CREATE_STORES_TABLE);
		db.execSQL(CREATE_EXPENSELIST2013_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORES);
		// Create tables again
		onCreate(db);
	}

	// TODO Auto-generated constructor stub
}

