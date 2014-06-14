package com.tren.time;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TrenTimeHelper extends SQLiteOpenHelper {
	
    public static String DB_PATH;
    public static String DB_NAME;
    public SQLiteDatabase database;
    public final Context context;

     public SQLiteDatabase getDb() {
        return database;
    }

     public TrenTimeHelper(Context context, String databaseName) {
        super(context, databaseName, null, 1);
        this.context = context;
     //Write a full path to the databases of your application
     String packageName = context.getPackageName();
     DB_PATH = String.format("//data//data//%s//databases//", packageName);
     DB_NAME = databaseName;
     openDataBase();
    }

     //This piece of code will create a database if it’s not yet created
    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), "Copying error");
                throw new Error("Error copying database!");
            }
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }

    //Performing a database existence check
    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null,
                          SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error while checking db");
        }
        //Android doesn’t like resource leaks, everything should 
        // be closed
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }

    //Method for copying the database
    private void copyDataBase() throws IOException {
        //Open a stream for reading from our ready-made database
        //The stream source is located in the assets
        InputStream externalDbStream = context.getAssets().open(DB_NAME);

         //Path to the created empty database on your Android device
        String outFileName = DB_PATH + DB_NAME;

         //Now create a stream for writing the database byte by byte
        OutputStream localDbStream = new FileOutputStream(outFileName);

         //Copying the database
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        //Don’t forget to close the streams
        localDbStream.close();
        externalDbStream.close();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDataBase();
            database = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}

	/*public TrenTimeHelper(Context context,int version) {
		super(context, "trenTime", null, version);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
			
			db.execSQL( "CREATE TABLE ESTACIONES(" +
	            " _id INTEGER PRIMARY KEY," +
	            " estacion_nombre TEXT NOT NULL)" );

	        db.execSQL("CREATE TABLE Horarios_Urquiza (_id INTEGER PRIMARY KEY, E1 INTEGER,E2 INTEGER,E3 INTEGER,E4 INTEGER,E5 INTEGER,E6 INTEGER," +
	        		" E7 INTEGER, E8 INTEGER, E9 INTEGER, E10 INTEGER, E11 INTEGER, E12 INTEGER, E13 INTEGER, E14 INTEGER, E15 INTEGER, " +
	        		"E16 INTEGER, E17 INTEGER , E18 INTEGER, E19 INTEGER, E20 INTEGER, E21 INTEGER, E22 INTEGER, E23 INTEGER)");


	        Log.i(this.getClass().toString(), "Tablas creadas");

	        /*
	         * Insertamos datos iniciales
	         *//*
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(0,'Federico Lacroze')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(1,'Artigas')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(2,'Arata')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(3,'Beiro')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(4,'Libertador')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(5,'Devoto')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(6,'Lynch')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(7,'Fernandez Moreno')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(8,'Lourdes')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(9,'Tropezon')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(10,'Jose Maria Bosch')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(11,'Martin Coronado')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(12,'Podesta')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(13,'Newbery')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(14,'Ruben Dario')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(15,'Ejercito de los Andes')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(16,'La Salle')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(17,'Barrufaldi')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(18,'Lozano')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(19,'Agneta')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(20,'Campo De Mayo')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(21,'Sgto Cabral')");
	        db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(22,'Lemos')");
	        
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (3,420,423,425,427,429,431,433,435,437,439,441,444,446,448,449,453,454,457,459,501,503,505,507)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (4,440,443,445,447,449,451,453,455,457,459,501,504,506,508,509,513,514,517,519,521,523,525,527)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (5,500,503,505,507,509,511,513,515,517,519,521,524,526,528,529,533,534,537,539,541,543,545,547)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (6,514,517,519,521,523,525,527,529,531,533,535,538,540,542,543,547,548,551,553,555,557,559,601)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (7,524,527,529,531,533,535,537,539,541,543,545,548,550,552,553,557,558,601,603,605,607,609,611)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (8,534,537,539,541,543,545,547,549,551,553,555,558,600,602,603,607,608,611,613,615,617,619,621)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (9,544,547,549,551,553,555,557,559,601,603,605,608,610,612,613,617,618,621,623,625,627,629,631)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (10,552,555,557,559,601,603,605,607,609,611,613,616,618,620,621,625,626,629,631,633,635,637,639)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (11,559,602,604,606,608,610,612,614,616,618,620,623,625,627,628,632,633,636,638,640,642,644,646)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (12,607,610,612,614,616,618,620,622,624,626,628,631,633,635,636,640,641,644,646,648,650,652,654)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (13,615,618,620,622,624,626,628,630,632,634,636,639,641,643,644,648,649,652,654,656,658,700,702)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (14,623,626,628,630,632,634,636,638,640,642,644,647,649,651,652,656,657,700,702,704,706,708,710)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (15,631,634,636,638,640,642,644,646,648,650,652,655,657,659,700,704,705,708,710,712,714,716,718)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (16,639,642,644,646,648,650,652,654,656,658,700,703,705,707,708,712,713,716,718,720,722,724,726)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (17,647,650,652,654,656,658,700,702,704,706,708,711,713,715,716,720,721,724,726,728,730,732,734)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (18,655,658,700,702,704,706,708,710,712,714,716,719,721,723,724,728,729,732,734,736,738,740,742)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (19,703,706,708,710,712,714,716,718,720,722,724,727,729,731,732,736,737,740,742,744,746,748,750)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (21,711,714,716,718,720,722,724,726,728,730,732,735,737,739,740,744,745,748,750,752,754,756,758)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (22,719,722,724,726,728,730,732,734,736,738,740,743,745,747,748,752,753,756,758,800,802,804,806)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (23,727,730,732,734,736,738,740,742,744,746,748,751,753,755,756,800,801,804,806,808,810,812,814)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (24,735,738,740,742,744,746,748,750,752,754,756,759,801,803,804,808,809,812,814,816,818,820,822)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (25,747,750,752,754,756,758,800,802,804,806,808,811,813,815,816,820,821,824,826,828,830,832,834)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (26,757,800,802,804,806,808,810,812,814,816,818,821,823,825,826,830,831,834,836,838,840,842,844)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (27,807,810,812,814,816,818,820,822,824,826,828,831,833,835,836,840,841,844,846,848,850,852,854)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (28,817,820,822,824,826,828,830,832,834,836,838,841,843,845,846,850,851,854,856,858,900,902,904)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (29,827,830,832,834,836,838,840,842,844,846,848,851,853,855,856,900,901,904,906,908,910,912,914)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (30,837,840,842,844,846,848,850,852,854,856,858,901,903,905,906,910,911,914,916,918,920,922,924)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (31,852,855,857,859,901,903,905,907,909,911,913,916,918,920,921,925,926,929,931,933,935,937,939)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (32,907,910,912,914,916,918,920,922,924,926,928,931,933,935,936,940,941,944,946,948,950,952,954)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (33,922,925,927,929,931,933,935,937,939,941,943,946,948,950,951,955,956,959,1001,1003,1005,1007,1009)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (34,939,942,944,946,948,950,952,954,956,958,1000,1003,1005,1007,1008,1012,1013,1016,1018,1020,1022,1024,1026)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (35,959,1002,1004,1006,1008,1010,1012,1014,1016,1018,1020,1023,1025,1027,1028,1032,1033,1036,1038,1040,1042,1044,1046)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (36,1015,1018,1020,1022,1024,1026,1028,1030,1032,1034,1036,1039,1041,1043,1044,1048,1049,1052,1054,1056,1058,1100,1102)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (37,1030,1033,1035,1037,1039,1041,1043,1045,1047,1049,1051,1054,1056,1058,1059,1103,1104,1107,1109,1111,1113,1115,1117)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (38,1045,1048,1050,1052,1054,1056,1058,1100,1102,1104,1106,1109,1111,1113,1114,1118,1119,1122,1124,1126,1128,1130,1132)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (39,1100,1103,1105,1107,1109,1111,1113,1115,1117,1119,1121,1124,1126,1128,1129,1133,1134,1137,1139,1141,1143,1145,1147)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (40,1115,1118,1120,1122,1124,1126,1128,1130,1132,1134,1136,1139,1141,1143,1144,1148,1149,1152,1154,1156,1158,1200,1202)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (41,1130,1133,1135,1137,1139,1141,1143,1145,1147,1149,1151,1154,1156,1158,1159,1203,1204,1207,1209,1211,1213,1215,1217)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (42,1145,1148,1150,1152,1154,1156,1158,1200,1202,1204,1206,1209,1211,1213,1214,1218,1219,1222,1224,1226,1228,1230,1232)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (43,1200,1203,1205,1207,1209,1211,1213,1215,1217,1219,1221,1224,1226,1228,1229,1233,1234,1237,1239,1241,1243,1245,1247)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (44,1215,1218,1220,1222,1224,1226,1228,1230,1232,1234,1236,1239,1241,1243,1244,1248,1249,1252,1254,1256,1258,1300,1302)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (45,1230,1233,1235,1237,1239,1241,1243,1245,1247,1249,1251,1254,1256,1258,1259,1303,1304,1307,1309,1311,1313,1315,1317)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (46,1245,1248,1250,1252,1254,1256,1258,1300,1302,1304,1306,1309,1311,1313,1314,1318,1319,1322,1324,1326,1328,1330,1332)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (47,1300,1303,1305,1307,1309,1311,1313,1315,1317,1319,1321,1324,1326,1328,1329,1333,1334,1337,1339,1341,1343,1345,1347)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (48,1315,1318,1320,1322,1324,1326,1328,1330,1332,1334,1336,1339,1341,1343,1344,1348,1349,1352,1354,1356,1358,1400,1402)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (49,1330,1333,1335,1337,1339,1341,1343,1345,1347,1349,1351,1354,1356,1358,1359,1403,1404,1407,1409,1411,1413,1415,1417)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (50,1345,1348,1350,1352,1354,1356,1358,1400,1402,1404,1406,1409,1411,1413,1414,1418,1419,1422,1424,1426,1428,1430,1432)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (51,1400,1403,1405,1407,1409,1411,1413,1415,1417,1419,1421,1424,1426,1428,1429,1433,1434,1437,1439,1441,1443,1445,1447)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (52,1415,1418,1420,1422,1424,1426,1428,1430,1432,1434,1436,1439,1441,1443,1444,1448,1449,1452,1454,1456,1458,1500,1502)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (53,1430,1433,1435,1437,1439,1441,1443,1445,1447,1449,1451,1454,1456,1458,1459,1503,1504,1507,1509,1511,1513,1515,1517)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (54,1445,1448,1450,1452,1454,1456,1458,1500,1502,1504,1506,1509,1511,1513,1514,1518,1519,1522,1524,1526,1528,1530,1532)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (55,1500,1503,1505,1507,1509,1511,1513,1515,1517,1519,1521,1524,1526,1528,1529,1533,1534,1537,1539,1541,1543,1545,1547)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (56,1515,1518,1520,1522,1524,1526,1528,1530,1532,1534,1536,1539,1541,1543,1544,1548,1549,1552,1554,1556,1558,1600,1602)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (57,1530,1533,1535,1537,1539,1541,1543,1545,1547,1549,1551,1554,1556,1558,1559,1603,1604,1607,1609,1611,1613,1615,1617)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (62,1545,1548,1550,1552,1554,1556,1558,1600,1602,1604,1606,1609,1611,1613,1614,1618,1619,1622,1624,1626,1628,1630,1632)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (63,1600,1603,1605,1607,1609,1611,1613,1615,1617,1619,1621,1624,1626,1628,1629,1633,1634,1637,1639,1641,1643,1645,1647)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (64,1615,1618,1620,1622,1624,1626,1628,1630,1632,1634,1636,1639,1641,1643,1644,1648,1649,1652,1654,1656,1658,1700,1702)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (65,1630,1633,1635,1637,1639,1641,1643,1645,1647,1649,1651,1654,1656,1658,1659,1703,1704,1707,1709,1711,1713,1715,1717)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (66,1645,1648,1650,1652,1654,1656,1658,1700,1702,1704,1706,1709,1711,1713,1714,1718,1719,1722,1724,1726,1728,1730,1732)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (67,1700,1703,1705,1707,1709,1711,1713,1715,1717,1719,1721,1724,1726,1728,1729,1733,1734,1737,1739,1741,1743,1745,1747)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (68,1710,1713,1715,1717,1719,1721,1723,1725,1727,1729,1731,1734,1736,1738,1739,1743,1744,1747,1749,1751,1753,1755,1757)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (69,1720,1723,1725,1727,1729,1731,1733,1735,1737,1739,1741,1744,1746,1748,1749,1753,1754,1757,1759,1801,1803,1805,1807)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (71,1730,1733,1735,1737,1739,1741,1743,1745,1747,1749,1751,1754,1756,1758,1759,1803,1804,1807,1809,1811,1813,1815,1817)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (72,1740,1743,1745,1747,1749,1751,1753,1755,1757,1759,1801,1804,1806,1808,1809,1813,1814,1817,1819,1821,1823,1825,1827)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (73,1750,1753,1755,1757,1759,1801,1803,1805,1807,1809,1811,1814,1816,1818,1819,1823,1824,1827,1829,1831,1833,1835,1837)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (74,1800,1803,1805,1807,1809,1811,1813,1815,1817,1819,1821,1824,1826,1828,1829,1833,1834,1837,1839,1841,1843,1845,1847)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (75,1810,1813,1815,1817,1819,1821,1823,1825,1827,1829,1831,1834,1836,1838,1839,1843,1844,1847,1849,1851,1853,1855,1857)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (76,1820,1823,1825,1827,1829,1831,1833,1835,1837,1839,1841,1844,1846,1848,1849,1853,1854,1857,1859,1901,1903,1905,1907)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (77,1830,1833,1835,1837,1839,1841,1843,1845,1847,1849,1851,1854,1856,1858,1859,1903,1904,1907,1909,1911,1913,1915,1917)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (78,1840,1843,1845,1847,1849,1851,1853,1855,1857,1859,1901,1904,1906,1908,1909,1913,1914,1917,1919,1921,1923,1925,1927)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (80,1850,1853,1855,1857,1859,1901,1903,1905,1907,1909,1911,1914,1916,1918,1919,1923,1924,1927,1929,1931,1933,1935,1937)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (81,1900,1903,1905,1907,1909,1911,1913,1915,1917,1919,1921,1924,1926,1928,1929,1933,1934,1937,1939,1941,1943,1945,1947)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (82,1910,1913,1915,1917,1919,1921,1923,1925,1927,1929,1931,1934,1936,1938,1939,1943,1944,1947,1949,1951,1953,1955,1957)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (83,1920,1923,1925,1927,1929,1931,1933,1935,1937,1939,1941,1944,1946,1948,1949,1953,1954,1957,1959,2001,2003,2005,2007)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (84,1930,1933,1935,1937,1939,1941,1943,1945,1947,1949,1951,1954,1956,1958,1959,2003,2004,2007,2009,2011,2013,2015,2017)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (85,1940,1943,1945,1947,1949,1951,1953,1955,1957,1959,2001,2004,2006,2008,2009,2013,2014,2017,2019,2021,2023,2025,2027)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (86,1950,1953,1955,1957,1959,2001,2003,2005,2007,2009,2011,2014,2016,2018,2019,2023,2024,2027,2029,2031,2033,2035,2037)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (87,2000,2003,2005,2007,2009,2011,2013,2015,2017,2019,2021,2024,2026,2028,2029,2033,2034,2037,2039,2041,2043,2045,2047)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (88,2015,2018,2020,2022,2024,2026,2028,2030,2032,2034,2036,2039,2041,2043,2044,2048,2049,2052,2054,2056,2058,2100,2102)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (89,2030,2033,2035,2037,2039,2041,2043,2045,2047,2049,2051,2054,2056,2058,2059,2103,2104,2107,2109,2111,2113,2115,2117)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (90,2045,2048,2050,2052,2054,2056,2058,2100,2102,2104,2106,2109,2111,2113,2114,2118,2119,2122,2124,2126,2128,2130,2132)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (91,2100,2103,2105,2107,2109,2111,2113,2115,2117,2119,2121,2124,2126,2128,2129,2133,2134,2137,2139,2141,2143,2145,2147)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (92,2120,2123,2125,2127,2129,2131,2133,2135,2137,2139,2141,2144,2146,2148,2149,2153,2154,2157,2159,2201,2203,2205,2207)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (93,2140,2143,2145,2147,2149,2151,2153,2155,2157,2159,2201,2204,2206,2208,2209,2213,2214,2217,2219,2221,2223,2225,2227)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (94,2200,2203,2205,2207,2209,2211,2213,2215,2217,2219,2221,2224,2226,2228,2229,2233,2234,2237,2239,2241,2243,2245,2247)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (95,2220,2223,2225,2227,2229,2231,2233,2235,2237,2239,2241,2244,2246,2248,2249,2253,2254,2257,2259,2301,2303,2305,2307)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (96,2240,2243,2245,2247,2249,2251,2253,2255,2257,2259,2301,2304,2306,2308,2309,2313,2314,2317,2319,2321,2323,2325,2327)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (97,2308,2311,2313,2315,2317,2319,2321,2323,2325,2327,2329,2332,2334,2336,2337,2341,2342,2345,2347,2349,2351,2353,2355)");
	        db.execSQL("INSERT INTO Horarios_Urquiza VALUES (98,2350,2353,2355,2357,2359,001,003,005,007,009,011,014,016,018,019,023,024,027,029,031,033,035,037)");
	        
	        

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}*/
