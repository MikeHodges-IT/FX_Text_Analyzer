package application;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.stream.Stream;
/**
* <h1>Java db</h1>
* 
* 
* 
* <b>Note:</b>
* The TextAnalyzer class contains the following methods:
* 
* public void sql(String)
* public LinkedHashMap<String,Long> sqlCountToHashMap(String)  
*
*
* 
* @author  Mike Hodges
* @version 1.0
* @since   2020-11-30
*/

public class Javadb {
		String databaseName = "wordoccurrencesdb";
		String url    = "jdbc:MySql://localhost:3306/"+ databaseName+"?useSSL=false";
    	String user     = "root";
    	String password = "password";
     	
	/**
	 * Initializes a new instance.
	 */
	public Javadb() {
	
	}
	/**
	 * 
	 * Initializes a new instance with database name.
	 * 
	 * @param databaseName
	 */
	public Javadb(String databaseName) {
		this.databaseName = databaseName;
	}
	/**
	 * 
	 * Initializes a new instance with:
	 * Database name 
	 * URL Location of Database	
	 * 	 
	 * @param databaseName
	 * @param url 
	 */
	public Javadb(String databaseName, String url) {
		this.databaseName = databaseName;
		this.url = url;
	}
	/**
	 * 
	 * Initializes a new instance with:
	 * Database name 
	 * URL Location of Database	
	 * User Name 
	 * Password
	 * 
	 * 
	 * @param databaseName
	 * @param url
	 * @param user
	 * @param password
	 */
	public Javadb(String databaseName, String url, String user, String password) {
		this.databaseName = databaseName;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	/**
	 * Execute an Sql Statement
	 * 
	 * @param sql
	 */
	public void sql(String sql) {
//		System.out.println("Running SQL");
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Connection connection =  DriverManager.getConnection(url, user, password);
				PreparedStatement ps  = connection.prepareStatement(sql);
				int status = ps.executeUpdate();
				if(status != 0) {
//					System.out.println("SQL Success");
				
				}
				
				
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	/**
	 * 
	 * public LinkedHashMap<String,Long> sqlCountToHashMap(String str)  
	 * Takes a string and returns a linkHashMap of word occurrence.
	 * Uses a database to count word occurrence.
	 *
	 * Covert a string of html to an array array of strings and insert the data into a database.
	 * Then uses SQl, SQL stored procedures to count the word occurrences and insert 
	 * the record set into a  LinkedHashMap. LinkedHashMap String, Long of value pair
	 * containing words and how many times each word occurred in the string. 
	 * 
	 * 
	 * @param  str String of words to count how many time each word occurred.
	 * @return wordFreqHashMap hashmap of value pair containing words and how many times each word occurred in the string
	 */
	public LinkedHashMap<String,Long> sqlCountToHashMap(String str) {
//		System.out.println("Running SQL count");
        String word ;
        long  count ;
		ResultSet rs = null;
		Statement st = null;
		
		
		LinkedHashMap<String, Long>wordFreqHashMap = new LinkedHashMap<String, Long>();
		Javadb j = new Javadb();
			j.sql("Call deleteAllWord();");
			Stream.of(
			str
			.replaceAll("&mdash;" , " ")
			.replaceAll("<[^>]*>"," ")
			.replaceAll("[\\s+\\W\\d]", " ")
			.trim()			
			.toLowerCase()
			.split("\\s+")).forEach(e ->{ j.sql("Call insertWord('"+ e +"')");});
			 

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Connection connection =  DriverManager.getConnection(url, user, password);
				st  = connection.createStatement();
				rs  = st.executeQuery("Call wordCount;");
				System.out.println("Running getResultSet()");
	            while(rs.next()){
	                word = rs.getString("word");
	                count = rs.getLong("cnt");
	                
	                System.out.println(word + " " + count);
	                // set data in the hashmap
	                wordFreqHashMap.put(word,count);
	            }
			
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		System.out.println(wordFreqHashMap);
			return  wordFreqHashMap;
		 
	}
	
	
	
	
	
}
